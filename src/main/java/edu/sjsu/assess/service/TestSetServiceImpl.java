package edu.sjsu.assess.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.assess.dao.CategoryDAOImpl;
import edu.sjsu.assess.dao.TestSetDAOImpl;
import edu.sjsu.assess.dao.UserDAOImpl;
import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.exception.TestSetException;
import edu.sjsu.assess.model.Category;
import edu.sjsu.assess.model.TestSet;
import edu.sjsu.assess.model.TestSetCategory;
import edu.sjsu.assess.model.TestSetSearchParams;
import edu.sjsu.assess.model.User;
import edu.sjsu.assess.util.Utility;

@Service
public class TestSetServiceImpl implements TestSetService {

	@Autowired
	private TestSetDAOImpl testSetDAO;

	@Autowired
	private UserDAOImpl userDAO;
	
	@Autowired
	private CategoryDAOImpl categoryDAO;

	@Override
	public TestSet saveTestSet(TestSet ts) throws TestSetException {
		TestSet newTS = null;
		try {

			String userName = Utility.getLoggedInUserName();

			if(userName != null){
				User user = userDAO.getUserByLogin(userName);
				ts.setUserID(user.getId());
			}

			newTS = testSetDAO.createTestSet(ts);

			List<TestSetCategory> testSetCategories = testSetDAO
					.createTestSetCategories(ts.getTestSetCategories(),
							newTS.getId());
			newTS.setTestSetCategories(testSetCategories);

			if (testSetCategories != null) {
				for (TestSetCategory tsCategory : testSetCategories) {
					testSetDAO.createTestSetQuestions(
							tsCategory.getQuestionList(), tsCategory.getId());
				}
			}

		} catch (DAOException e) {
			e.printStackTrace();
			throw new TestSetException("Failed to create test set.", e);
		}

		return newTS;
	}

	public boolean addSetCategories(int testId, List<TestSetCategory> setCategories)
		throws TestSetException
	{
		try {
			List<TestSetCategory> testSetCategories = testSetDAO
                    .createTestSetCategories(setCategories,
							testId);
			return true;
		}
		catch (DAOException e) {
			e.printStackTrace();
			throw new TestSetException("Failed to create test set.", e);
		}
	}

	@Override
	public List<TestSet> getTestSetList(TestSetSearchParams searchParams)
			throws TestSetException {

		List<TestSet> tsList = new ArrayList<TestSet>();

		try {
			String userName = Utility.getLoggedInUserName();
			User user = userDAO.getUserByLogin(userName);
            if ("admin".equals(user.getRole())){
                searchParams.setUserID(user.getId());
            }

            System.out.println("Printing role.... "+user.getRole());

			// searchParams.setUserID(1);

			List<Integer> testSetIDList = testSetDAO
					.getTestSetList(searchParams);

			for (Integer testSetID : testSetIDList) {
				TestSet ts = testSetDAO.getTestSetByID(testSetID);
				
				List<TestSetCategory> tscList = ts.getTestSetCategories();
				if(tscList != null && tscList.size() > 0){
					for(TestSetCategory tsc : tscList){
						Category categoryObj = categoryDAO.getCategoryByID(tsc.getCategoryID());
						tsc.setCategory(categoryObj);
					}
				}
				
				tsList.add(ts);
			}

		} catch (DAOException e) {
			throw new TestSetException("Failed to get test set list.", e);
		}

		return tsList;
	}


	@Override
	public void deleteTestSet(TestSetSearchParams searchParams)
			throws TestSetException {

		Integer tsID = searchParams.getId();
		
		try {
			testSetDAO.deleteTestSetByID(tsID);

		} catch (DAOException e) {
			throw new TestSetException(
					"Failed to delete test set.", e);
		}
	}

	public void deleteSetCategoryFromTestset(int setcategoryId)
		throws TestSetException
	{
		try {
			testSetDAO.deleteTestSetCategories(setcategoryId);
		}
		catch (DAOException e) {
			throw new TestSetException(
					"Failed to delete test set.", e);
		}
	}

	@Override
	public TestSet updateTestSet(TestSet ts) throws TestSetException {

		try {

			testSetDAO.updateTestSetByID(ts);
			if(ts.getTestSetCategories()!=null && ts.getTestSetCategories().size()>0) {
				testSetDAO.updateSetCategories(ts.getTestSetCategories());
			}
/*
			testSetDAO.deleteTestSetCategories(ts.getId());

			testSetDAO.createTestSetCategories(ts.getTestSetCategories(),
					ts.getId());
*/

		} catch (DAOException e) {
			throw new TestSetException("Failed to update test set.", e);
		}

		return ts;
	}

	public boolean addQuestionsToTestSetCategory(TestSetCategory tsCategory) {
		if (tsCategory != null) {
			try {
				testSetDAO.createTestSetQuestions(
						tsCategory.getQuestionList(), tsCategory.getId());
			}
			catch (DAOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public int removeQuestionFromSetCategory(int setCategoryId, List<Integer> questionIds)
			throws TestSetException
	{
		int deletedQuestions = 0;
		try {
			deletedQuestions = testSetDAO.deleteQuestionFromSetCategory(setCategoryId, questionIds);
		} catch (DAOException e) {
			throw new TestSetException("Failed to delete question from setcategory.", e);
		}
		return deletedQuestions;
	}

	public TestSet getTestsetById(int testsetId) throws TestSetException{
		try {
			TestSet testset = testSetDAO.getTestSetByID(testsetId);
			return testset;
		} catch (DAOException e) {
			e.printStackTrace();
			throw new TestSetException(
					"Failed to delete test set.", e);
		}
	}
}
