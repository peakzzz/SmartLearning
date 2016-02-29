package edu.sjsu.assess.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.assess.dao.CategoryDAOImpl;
import edu.sjsu.assess.dao.CourseDAOImpl;
import edu.sjsu.assess.dao.DepartmentDAOImpl;
import edu.sjsu.assess.dao.JobCodeDAOImpl;
import edu.sjsu.assess.dao.UserDAOImpl;
import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.exception.JobCodeException;
import edu.sjsu.assess.model.Category;
import edu.sjsu.assess.model.JobCode;
import edu.sjsu.assess.model.JobCodeSearchParams;
import edu.sjsu.assess.model.User;
import edu.sjsu.assess.util.Utility;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseDAOImpl courseDAO;

	@Autowired
	private DepartmentDAOImpl departmentDAO;

	@Autowired
	private UserDAOImpl userDAO;

	@Override
	public JobCode saveCourse(JobCode jc) throws JobCodeException {
		JobCode newJC = null;
		try {

			String userName = Utility.getLoggedInUserName();
			if (userName != null) {
				User user = userDAO.getUserByLogin(userName);
				jc.setUserID(user.getId());
			}

			newJC = courseDAO.createCourse(jc);

			// Create categories & subcategories
			this.createCategoriesForCourse(newJC);

		} catch (DAOException e) {
			throw new JobCodeException("Failed to create job code.", e);
		}

		return newJC;
	}

	/**
	 * This method creates the categories for a job code, if not already present
	 * in DB & creates mapping between jobcode & categories.
	 * 
	 * @param jc
	 */
	private void createCategoriesForCourse(JobCode jc) {

		List<Category> firstLevelCategories = jc.getFirstLevelCategories();
		if (firstLevelCategories != null) {
			System.out.println("First level categories ");
			for (Category firstLevelCat : firstLevelCategories) {
				this.checkAndCreateSubCategories(jc.getId(), firstLevelCat, 0);
			}
		}
	}

	/**
	 * This method recursively iterates over sub categories of a category &
	 * creates them if they don't exist in DB.
	 * 
	 * @param c
	 */
	private void checkAndCreateSubCategories(int jobCodeID, Category c,
			int parentCategoryID) {
		// Check if parent category exists in DB. If not, create.
		System.out.println("Not entering");
		if (c.getId() == 0) {
			try {
				System.out.println("create department");
				Category newCat = departmentDAO.createDepartment(c);
				c.setId(newCat.getId());
			} catch (Exception e) {
				// TODO: Log error
				// Failed to create parent category. Return.
				return;
			}
		}

		try {
			departmentDAO.createCourseDepartmentMapping(jobCodeID, c.getId(),
					parentCategoryID);
		} catch (Exception e) {
			// TODO: Log error
			// Failed to create parent category. Return.
			return;
		}

		List<Category> subcategoryList = c.getSubCategories();
		if (subcategoryList == null) {
			return;
		}

		for (Category subcat : subcategoryList) {
			this.checkAndCreateSubCategories(jobCodeID, subcat, c.getId());
		}
	}

	@Override
	public Category createCategoryForCourse(Category category) throws JobCodeException{

		Category savedCategory;
		try{

			savedCategory = departmentDAO.createDepartment(category);

		} catch (Exception e) {
			throw new JobCodeException("Failed to create category.", e);
		}

		return savedCategory;
	}


	@Override
	public JobCode getCourse(JobCodeSearchParams searchParams)
			throws JobCodeException {

		Integer jcID = searchParams.getId();

		JobCode jc = null;

		try {

			String userName = Utility.getLoggedInUserName();
			if (userName != null) {
				User user = userDAO.getUserByLogin(userName);
				searchParams.setUserID(user.getId());
			}

			jc = courseDAO.getCourseByID(jcID);

			this.getAllCategoriesOfCourse(jc, null);

		} catch (DAOException e) {
			throw new JobCodeException("Failed to course details serviceimpl.", e);
		}

		return jc;
	}

	/**
	 * 
	 * @param jobCodeID
	 * @param category
	 * @throws JobCodeException
	 */
	public void getAllCategoriesOfCourse(JobCode jobCode, Category category)
			throws JobCodeException {

		try {

			Integer parentCategoryID = null;
			if (category != null) {
				parentCategoryID = category.getId();
			}

			Integer jobCodeID = jobCode.getId();
			System.out.println("before entering department dao");
			List<Category> categoryList = departmentDAO.getCourseDepartments(
					jobCodeID, parentCategoryID);
			if (categoryList != null && categoryList.size() > 0) {
				if (parentCategoryID == null) {
					jobCode.setFirstLevelCategories(categoryList);
				} else {
					category.setSubCategories(categoryList);
				}
			}

			for (Category cat : categoryList) {
				this.getAllCategoriesOfCourse(jobCode, cat);
			}

		} catch (DAOException e) {
			throw new JobCodeException("Failed to get course.", e);
		}
	}

	@Override
	public List<JobCode> getCourseList() throws JobCodeException {
		List<Integer> jobCodeIDList = null;

		List<JobCode> jobCodeList = new ArrayList<JobCode>();

		try {

			jobCodeIDList = courseDAO.getCourseList();

			for (Integer jobCodeID : jobCodeIDList) {

				JobCode jc = this.getJobCode(jobCodeID);

				Integer predefinedJobCodeID = jc.getPredefinedJobCodeID();
				if(predefinedJobCodeID != null && !predefinedJobCodeID.equals(0)){
					JobCode predefinedJobCode = this.getJobCode(predefinedJobCodeID);
					jc.setPredefinedJobCode(predefinedJobCode);
				}

				jobCodeList.add(jc);
			}

		} catch (DAOException e) {
			throw new JobCodeException("Failed to get job code list.", e);
		}

		return jobCodeList;
	}

	/**
	 * 
	 * @param jobCodeID
	 * @return
	 * @throws JobCodeException
	 */
	private JobCode getJobCode(Integer jobCodeID) throws JobCodeException {

		JobCode jc = null;

		try{

			jc = courseDAO.getCourseByID(jobCodeID);

			this.getAllCategoriesOfCourse(jc, null);

		} catch (DAOException e) {
			throw new JobCodeException("Failed to get job code.", e);
		}

		return jc;
	}


	@Override
	public void deleteCourse(JobCodeSearchParams searchParams)
			throws JobCodeException {
		Integer jcID = searchParams.getId();
		try {
			String userName = Utility.getLoggedInUserName();

			if (userName != null) {
				User user = userDAO.getUserByLogin(userName);
				searchParams.setUserID(user.getId());
			}

			// Delete mapping entries of this job code with categories
			departmentDAO.deleteDepartmentMappingForCourseID(jcID);

			// Delete entry from jobcode table
			courseDAO.deleteCourseByID(jcID);

		} catch (DAOException e) {
			throw new JobCodeException("Failed to delete job code.", e);
		}
	}

	@Override
	public JobCode updateCourse(JobCode jc) throws JobCodeException {

		try {
			courseDAO.updateCourseByID(jc);

			departmentDAO.deleteDepartmentMappingForCourseID(jc.getId());

			this.createCategoriesForCourse(jc);

		} catch (DAOException e) {
			throw new JobCodeException("Failed to update job code.", e);
		}
		return jc;
	}

	@Override
	public List<JobCode> getAllPredefinedCourses() throws JobCodeException {
		List<JobCode> jcList = null;

		try {
			jcList = courseDAO.getAllPredefinedCourses();

			if (jcList != null && jcList.size() > 0) {
				for (JobCode jc : jcList) {
					this.getAllCategoriesOfCourse(jc, null);
				}
			}
		} catch (DAOException e) {
			throw new JobCodeException("Failed to get predefined courses.", e);
		}

		return jcList;
	}

	@Override
	public List<JobCode> getCourseForUserPreference() throws JobCodeException{

		List<JobCode> jcList = null;

		try {

			jcList = courseDAO.getCourseForUserPreference();

		} catch (DAOException e) {
			throw new JobCodeException("Failed to get job codes for setting user preferences.", e);
		}

		return jcList;
	}


	// public void getCategorySuggestions(JobCode jobCode) throws
	// JobCodeException {
	// List<Category> firstLevelCategories =
	// this.getCategorySuggestions(jobCode.getId(), null);
	// jobCode.setFirstLevelCategories(firstLevelCategories);
	//
	// for(Category category : firstLevelCategories){
	//
	// }
	// }
	//
	//
	// //@Override
	// /**
	// * This method can be called to get category suggestions
	// * for selected job code & parent category ID.
	// */
	// public List<Category> getCategorySuggestions(JobCode jobCode,
	// Category parentCategory) throws JobCodeException {
	//
	// List<Category> categoryList = null;
	//
	// try {
	//
	// String userName = Utility.getLoggedInUserName();
	// User user = userDAO.getUserByLogin(userName);
	//
	// categoryList = categoryDAO.getCategorySuggestions(jobCode.getId(),
	// parentCategory!=null ? parentCategory.getId():null, user.getId());
	//
	// if(parentCategory == null){
	// jobCode.setFirstLevelCategories(categoryList);
	// }
	// else{
	// parentCategory.setSubCategories(categoryList);
	// }
	//
	// for(Category category : categoryList){
	// this.getCategorySuggestions(jobCode, category);
	// }
	//
	// } catch (DAOException e) {
	// throw new JobCodeException("Failed to get predefined job codes.", e);
	// }
	//
	// return categoryList;
	// }

}
