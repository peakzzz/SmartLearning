package edu.sjsu.assess.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.assess.dao.CategoryDAOImpl;
import edu.sjsu.assess.dao.QuestionDAOImpl;
import edu.sjsu.assess.dao.TestSetAttemptDAOImpl;
import edu.sjsu.assess.dao.TestSetDAOImpl;
import edu.sjsu.assess.dao.TrainingModuleDAOImpl;
import edu.sjsu.assess.dao.UserDAOImpl;
import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.exception.TestSetAttemptException;
import edu.sjsu.assess.model.Category;
import edu.sjsu.assess.model.TestSet;
import edu.sjsu.assess.model.TestSetAttempt;
import edu.sjsu.assess.model.TestSetAttempt.CategoryWiseRecord;
import edu.sjsu.assess.model.TestSetAttempt.QuestionWiseRecord;
import edu.sjsu.assess.model.TestSetAttemptSearchParams;
import edu.sjsu.assess.model.TestSetCategory;
import edu.sjsu.assess.model.User;
import edu.sjsu.assess.util.Utility;

@Service
public class TestSetAttemptServiceImpl implements TestSetAttemptService{

	@Autowired
    private TestSetAttemptDAOImpl testSetAttemptDAO;
	
	@Autowired
    private TestSetDAOImpl testSetDAO;
    
    @Autowired
	private UserDAOImpl userDAO;
    
    @Autowired
	private CategoryDAOImpl categoryDAO;
    
    @Autowired
	private TrainingModuleDAOImpl trainingModuleDAO;

    @Autowired
    private QuestionDAOImpl questionDao;

	@Autowired
	private TestSetServiceImpl testSetService;
    
	@Override
	public TestSetAttempt saveTestSetAttempt(TestSetAttempt ta)
			throws TestSetAttemptException {
		
		TestSetAttempt newTA = null;
		
        try {
            //setting userId
        	if(ta.getUserID() == null){
	            String userName = Utility.getLoggedInUserName();
	    		User user = userDAO.getUserByLogin(userName);
	            ta.setUserID(user.getId());
        	}

            newTA = testSetAttemptDAO.createTestSetAttempt(ta);
            
        } catch (DAOException e) {
            throw new TestSetAttemptException(
                    "Failed to create test set attempt.", e);
        }

        return newTA;
	}
	
	@Override
	public List<TestSetAttempt> getTestSetAttemptList(
			TestSetAttemptSearchParams searchParams)
			throws TestSetAttemptException{
		
		List<TestSetAttempt> taList = new ArrayList<TestSetAttempt>();
    	
    	try {
    		String userName = Utility.getLoggedInUserName();
    		User user = userDAO.getUserByLogin(userName);
    		searchParams.setUserID(user.getId());
    		
    		//searchParams.setUserID(46);
    		
    		List<Integer> testSetAttemptIDList = testSetAttemptDAO.getTestSetAttemptList(searchParams);
    		
    		for(Integer testSetAttemptID : testSetAttemptIDList){
    			TestSetAttempt testSetAttempt = testSetAttemptDAO.getTestSetAttemptByID(testSetAttemptID);
    			this.getTestSetAttemptCategories(testSetAttempt);
    			
    			// Set TestSet object in TestSetAttempt object
    			Integer testSetID = testSetAttempt.getTestSetID();
    			TestSet testSet = testSetDAO.getTestSetByID(testSetID);
    			testSetAttempt.setTestSetObj(testSet);
    			
    			taList.add(testSetAttempt);
    		}
    		
        } catch (DAOException e) {
            throw new TestSetAttemptException("Failed to get test set attempt.", e);
        }
    	
    	return taList;
	}

	public List<TestSetAttempt> evaluateProgress(int testsetId, List<TestSetAttempt> attemptList) {
		try {
			TestSet test = testSetService.getTestsetById(testsetId);
			double totalScore = 0;
			for(TestSetCategory setCategory: test.getTestSetCategories()) {
				totalScore+=setCategory.getWeightage();
			}
			attemptList.get(0).setProgress(attemptList.get(0).getScore()*100/totalScore);
			if(attemptList.size() > 1) {
				for(int i=1; i<attemptList.size(); i++) {
					attemptList.get(i).setProgress(
							((attemptList.get(i-1).getScore()-attemptList.get(i).getScore())/totalScore)*100);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return attemptList;
	}
	
	private void getTestSetAttemptCategories(TestSetAttempt ta) throws TestSetAttemptException{
		
		List<CategoryWiseRecord> cwrList = ta.getCategoryWiseRecords();
		
		try{
			if(cwrList != null && cwrList.size() > 0){
				for(CategoryWiseRecord cwr : cwrList){
					
					Integer cid = cwr.getCategoryID();
					Category cat = categoryDAO.getCategoryByID(cid);
					cwr.setCategory(cat);
				}
			}
		} catch (DAOException e) {
            throw new TestSetAttemptException("Failed to get catgories within test set attempt.", e);
        }
	}
	
	
	@Override
	public TestSetAttempt evaluateTestSetAttempt(TestSetAttempt ta)
			throws TestSetAttemptException{
		
		Double score = (double) 0;
		
		try{
			String login = Utility.getLoggedInUserName();
			User loggedInUser = userDAO.getUserByLogin(login);
			Integer userID = loggedInUser.getId();
					
			List<CategoryWiseRecord> categoryRecords = ta.getCategoryWiseRecords();
			
			if(categoryRecords != null && categoryRecords.size() > 0){
				
				for(CategoryWiseRecord categoryWiseRecord : categoryRecords){
					
					Double categoryScore = (double) 0;
					
					Integer categoryID = categoryWiseRecord.getCategoryID();
                    categoryWiseRecord.setCategory(categoryDAO.getCategoryByID(categoryWiseRecord.getCategoryID()));
					
					// Get weightage & cutoff for this category
					TestSetCategory tsc = testSetDAO.getTestSetCategory(ta.getTestSetID(), categoryID);
					Float categoryWeightage = tsc.getWeightage();
					Float categoryCutOff = tsc.getCutoff();
					
					List<QuestionWiseRecord> questionRecords = categoryWiseRecord.getQuestionsRecord();
					
					// Get weightage of each question
					Float questionWeightage = categoryWeightage/questionRecords.size();
					
					if(questionRecords != null && questionRecords.size() > 0){
						for(QuestionWiseRecord questionWiseRecord : questionRecords){
							if(questionWiseRecord.isCorrectAnswer()){
								categoryScore += questionWeightage;
							}
                            questionWiseRecord.setQuestionObj(questionDao.getQuestionByID(questionWiseRecord.getQuestionID()));
						}
					}
					
					categoryWiseRecord.setScore(categoryScore);
					
					Integer tsID = ta.getTestSetID();
					TestSet ts = testSetDAO.getTestSetByID(tsID);
					
					// Get the IDs of jobCode to which this test set belongs
					Integer jobCodeID = ts.getJobCodeID();
					
					if(categoryScore >= categoryCutOff){
						categoryWiseRecord.setIsCutoffReached(true);
						
//						this.deleteTrainingModuleRecommendation(userID, jobCodeID, categoryID);
					}
					else{
						// Generate training module recommendation based on job code & category in which candidate scored low
//						this.generateTrainingModuleRecommendation(userID, jobCodeID, categoryID);
					}
					
					score += categoryScore;
				}
			}
			
			ta.setScore(score);
			
		} catch (DAOException e) {
            throw new TestSetAttemptException("Failed to evaluate test set attempt.", e);
        }
		
		return ta;
	}
	
	private void generateTrainingModuleRecommendation(Integer userID, Integer jobCodeID, Integer categoryID) throws TestSetAttemptException{
		
		try{
			trainingModuleDAO.createTrainingModuleRecommendations(userID, jobCodeID, categoryID);
		} catch (DAOException e) {
            throw new TestSetAttemptException("Failed to generate training module recommendations.", e);
        }
	}
	
	private void deleteTrainingModuleRecommendation(Integer userID, Integer jobCodeID, Integer categoryID) throws TestSetAttemptException{
		
		try{
			trainingModuleDAO.deleteTrainingModuleRecommendation(userID, jobCodeID, categoryID);
		} catch (DAOException e) {
            throw new TestSetAttemptException("Failed to delete training module recommendations.", e);
        }
	}
	
}
