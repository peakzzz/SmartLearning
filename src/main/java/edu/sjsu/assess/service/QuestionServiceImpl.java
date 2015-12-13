package edu.sjsu.assess.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.assess.dao.CategoryDAOImpl;
import edu.sjsu.assess.dao.QuestionDAOImpl;
import edu.sjsu.assess.dao.UserDAOImpl;
import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.exception.QuestionException;
import edu.sjsu.assess.model.Category;
import edu.sjsu.assess.model.Question;
import edu.sjsu.assess.model.QuestionSearchParams;
import edu.sjsu.assess.model.User;
import edu.sjsu.assess.util.Utility;

@Service
public class QuestionServiceImpl implements QuestionService{

	@Autowired
	private QuestionDAOImpl questionDAO;
	
	@Autowired
	private CategoryDAOImpl categoryDAO;
	
	@Autowired
	private UserDAOImpl userDAO;
	
	@Override
	public Question saveQuestion(Question qs) throws QuestionException {
		
		Question newQS = null;
		
		try {
			String userName = Utility.getLoggedInUserName();
			
			if(userName != null){
				User user = userDAO.getUserByLogin(userName);
				qs.setUserID(user.getId());
			}
			
			//qs.setUserID(1);
			
			newQS = questionDAO.createQuestion(qs);

		} catch (DAOException e) {
			throw new QuestionException("Failed to Create Question.", e);
		}

		return newQS;
	}

	public List<Question> searchQuestions(QuestionSearchParams searchParams)
			throws QuestionException
	{
		List<Question> searchResults = new ArrayList<>();
		try {
			searchResults = questionDAO.searchQuestions(searchParams);
		}
		catch (DAOException e) {
			e.printStackTrace();
			throw new QuestionException("Failed to search questions"+e);
		}
		return searchResults;
	}

	@Override
	public List<Question> getQuestionsList(QuestionSearchParams searchParams)
			throws QuestionException {
		
		List<Question> qsList = new ArrayList<Question>();

    	try {

    		List<Integer> questionsIDList = questionDAO.getQuestionList(searchParams);
    		
    		for(Integer questionID : questionsIDList){
    			Question qs = questionDAO.getQuestionByID(questionID);
    			
    			Integer categoryID = qs.getCategoryID();
    			if(categoryID != null){
    				Category categoryObj = categoryDAO.getCategoryByID(categoryID);
    				qs.setCategoryObj(categoryObj);
    			}
    			
    			qsList.add(qs);
    		}
    		
        } catch (DAOException e) {
            throw new QuestionException("Failed to get questions.", e);
        }
    	
    	return qsList;
	}

	public List<Question> getQuestionsByIds(List<Integer> questionIds)
			throws QuestionException {

		List<Question> qsList = new ArrayList<Question>();
		try {
			qsList = questionDAO.getQuestionByID(questionIds);

			// set categories
			for(Question ques: qsList) {
				Integer categoryID = ques.getCategoryID();
				if(categoryID != null){
					Category categoryObj = categoryDAO.getCategoryByID(categoryID);
					ques.setCategoryObj(categoryObj);
				}
			}
		} catch (DAOException e) {
			throw new QuestionException("Failed to get questions.", e);
		}
		return qsList;
	}
	
	@Override
	public void updateQuestion(Question qs) throws QuestionException{
		
		try {
            questionDAO.updateQuestionByID(qs);
            
            if(qs.isMultipleChoice()){
	            questionDAO.deleteAllOptions(qs.getId());
	            
	            questionDAO.createOptions(qs.getId(), qs.getOptions());
            }
            
        } catch (DAOException e) {
            throw new QuestionException("Failed to update question.",
                    e);
        }
	}
	
	@Override
	public void deleteQuestion(QuestionSearchParams searchParams) throws QuestionException{
		
		Integer qsID = searchParams.getId();
        try {
        	
        	questionDAO.deleteAllOptions(qsID);
        	
            questionDAO.deleteQuestionByID(qsID);
            
        } catch (DAOException e) {
            throw new QuestionException("Failed to delete question.",
                    e);
        }
	}


}
