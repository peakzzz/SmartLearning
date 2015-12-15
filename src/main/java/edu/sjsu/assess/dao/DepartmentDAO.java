package edu.sjsu.assess.dao;

import java.util.List;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.Category;

/**
 * Category DAO Interface
 * 
 * @author Shefali
 *
 */
public interface DepartmentDAO {

	public Category createDepartment(Category c) throws DAOException;

	public Category getDepartmentByID(Integer cID) throws DAOException;

	public Category updateDepartmentByID(Category c) throws DAOException;

	public void deleteDepartmentByID(Integer cID) throws DAOException;

	public List<Category> getDepartmentSuggestions(Integer jobCodeID,
			Integer parentCategoryID, Integer userID) throws DAOException;

	public List<Category> getAllPredefinedDepartments() throws DAOException;

	public void createCourseDepartmentMapping(Integer jobCodeID,
			Integer categoryID, Integer parentCategoryID) throws DAOException;
	
	public void deleteDepartmentMappingForCourseID(Integer jobCodeID) throws DAOException;

}
