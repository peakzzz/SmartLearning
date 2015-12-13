package edu.sjsu.assess.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.assess.dao.CategoryDAOImpl;
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
public class JobCodeServiceImpl implements JobCodeService {

	@Autowired
	private JobCodeDAOImpl jobCodeDAO;

	@Autowired
	private CategoryDAOImpl categoryDAO;

	@Autowired
	private UserDAOImpl userDAO;

	@Override
	public JobCode saveJobCode(JobCode jc) throws JobCodeException {
		JobCode newJC = null;
		try {

			String userName = Utility.getLoggedInUserName();
			if (userName != null) {
				User user = userDAO.getUserByLogin(userName);
				jc.setUserID(user.getId());
			}

			newJC = jobCodeDAO.createJobCode(jc);

			// Create categories & subcategories
			this.createCategoriesForJobCode(newJC);

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
	private void createCategoriesForJobCode(JobCode jc) {

		List<Category> firstLevelCategories = jc.getFirstLevelCategories();
		if (firstLevelCategories != null) {
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
		if (c.getId() == 0) {
			try {
				Category newCat = categoryDAO.createCategory(c);
				c.setId(newCat.getId());
			} catch (Exception e) {
				// TODO: Log error
				// Failed to create parent category. Return.
				return;
			}
		}

		try {
			categoryDAO.createJobCodeCategoryMapping(jobCodeID, c.getId(),
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
	public Category createCategoryForJobCode(Category category) throws JobCodeException{
		
		Category savedCategory;
		try{
			
			savedCategory = categoryDAO.createCategory(category);
			
		} catch (Exception e) {
			throw new JobCodeException("Failed to create category.", e);
		}
		
		return savedCategory;
	}
	
	
	@Override
	public JobCode getJobCode(JobCodeSearchParams searchParams)
			throws JobCodeException {

		Integer jcID = searchParams.getId();

		JobCode jc = null;

		try {

			String userName = Utility.getLoggedInUserName();
			if (userName != null) {
				User user = userDAO.getUserByLogin(userName);
				searchParams.setUserID(user.getId());
			}

			jc = jobCodeDAO.getJobCodeByID(jcID);

			this.getAllCategoriesOfJobCode(jc, null);

		} catch (DAOException e) {
			throw new JobCodeException("Failed to get job code.", e);
		}

		return jc;
	}

	/**
	 * 
	 * @param jobCodeID
	 * @param category
	 * @throws JobCodeException
	 */
	public void getAllCategoriesOfJobCode(JobCode jobCode, Category category)
			throws JobCodeException {

		try {

			Integer parentCategoryID = null;
			if (category != null) {
				parentCategoryID = category.getId();
			}

			Integer jobCodeID = jobCode.getId();

			List<Category> categoryList = categoryDAO.getJobCodeCategories(
					jobCodeID, parentCategoryID);
			if (categoryList != null && categoryList.size() > 0) {
				if (parentCategoryID == null) {
					jobCode.setFirstLevelCategories(categoryList);
				} else {
					category.setSubCategories(categoryList);
				}
			}

			for (Category cat : categoryList) {
				this.getAllCategoriesOfJobCode(jobCode, cat);
			}

		} catch (DAOException e) {
			throw new JobCodeException("Failed to get job code.", e);
		}
	}

	@Override
	public List<JobCode> getJobCodeList() throws JobCodeException {
		List<Integer> jobCodeIDList = null;

		List<JobCode> jobCodeList = new ArrayList<JobCode>();

		try {

			jobCodeIDList = jobCodeDAO.getJobCodeList();

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
			
			jc = jobCodeDAO.getJobCodeByID(jobCodeID);
	
			this.getAllCategoriesOfJobCode(jc, null);
			
		} catch (DAOException e) {
			throw new JobCodeException("Failed to get job code.", e);
		}
		
		return jc;
	}
	
	
	@Override
	public void deleteJobCode(JobCodeSearchParams searchParams)
			throws JobCodeException {
		Integer jcID = searchParams.getId();
		try {
			String userName = Utility.getLoggedInUserName();

			if (userName != null) {
				User user = userDAO.getUserByLogin(userName);
				searchParams.setUserID(user.getId());
			}

			// Delete mapping entries of this job code with categories
			categoryDAO.deleteCategoryMappingForJobCodeID(jcID);

			// Delete entry from jobcode table
			jobCodeDAO.deleteJobCodeByID(jcID);

		} catch (DAOException e) {
			throw new JobCodeException("Failed to delete job code.", e);
		}
	}

	@Override
	public JobCode updateJobCode(JobCode jc) throws JobCodeException {

		try {
			jobCodeDAO.updateJobCodeByID(jc);

			categoryDAO.deleteCategoryMappingForJobCodeID(jc.getId());

			this.createCategoriesForJobCode(jc);

		} catch (DAOException e) {
			throw new JobCodeException("Failed to update job code.", e);
		}
		return jc;
	}

	@Override
	public List<JobCode> getAllPredefinedJobCodes() throws JobCodeException {
		List<JobCode> jcList = null;

		try {
			jcList = jobCodeDAO.getAllPredefinedJobCodes();

			if (jcList != null && jcList.size() > 0) {
				for (JobCode jc : jcList) {
					this.getAllCategoriesOfJobCode(jc, null);
				}
			}
		} catch (DAOException e) {
			throw new JobCodeException("Failed to get predefined job codes.", e);
		}

		return jcList;
	}

	@Override
	public List<JobCode> getJobCodeForUserPreference() throws JobCodeException{
		
		List<JobCode> jcList = null;

		try {
			
			jcList = jobCodeDAO.getJobCodeForUserPreference();

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
