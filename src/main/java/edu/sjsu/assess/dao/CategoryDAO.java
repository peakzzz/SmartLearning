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
public interface CategoryDAO {

	public Category createCategory(Category c) throws DAOException;

	public Category getCategoryByID(Integer cID) throws DAOException;

	public Category updateCategoryByID(Category c) throws DAOException;

	public void deleteCategoryByID(Integer cID) throws DAOException;

	public List<Category> getCategorySuggestions(Integer jobCodeID,
			Integer parentCategoryID, Integer userID) throws DAOException;

	public List<Category> getAllPredefinedCategories() throws DAOException;

	public void createJobCodeCategoryMapping(Integer jobCodeID,
			Integer categoryID, Integer parentCategoryID) throws DAOException;
	
	public void deleteCategoryMappingForJobCodeID(Integer jobCodeID) throws DAOException;

}
