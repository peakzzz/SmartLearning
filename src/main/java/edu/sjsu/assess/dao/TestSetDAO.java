package edu.sjsu.assess.dao;

import java.util.List;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.Question;
import edu.sjsu.assess.model.TestSet;
import edu.sjsu.assess.model.TestSetCategory;
import edu.sjsu.assess.model.TestSetSearchParams;

public interface TestSetDAO {

	public TestSet createTestSet(TestSet ts, Boolean jobcode) throws DAOException;
	
	public List<Integer> getTestSetList(TestSetSearchParams searchParams) throws DAOException;
	
	public TestSet getTestSetByID(Integer tsID) throws DAOException;

	public void updateTestSetByID(TestSet ts) throws DAOException;
	
	public void deleteTestSetByID(Integer tsID) throws DAOException;
	
	public void deleteTestSetCategories(Integer tsID) throws DAOException;
	
	public List<TestSetCategory> createTestSetCategories(
			List<TestSetCategory> tsCategories, Integer testSetID, Boolean jobCode)
			throws DAOException;

	public void createTestSetQuestions(List<Question> questions,
			Integer testSetCategoryID) throws DAOException;

	public int deleteQuestionFromSetCategory(Integer testsetCategoryId, List<Integer> questionIds)
			throws DAOException;
	
	public TestSetCategory getTestSetCategory(Integer testSetID, Integer categoryID) throws DAOException;
}
