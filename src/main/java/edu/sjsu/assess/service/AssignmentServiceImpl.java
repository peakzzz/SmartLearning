package edu.sjsu.assess.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.assess.dao.AssignmentDAOImpl;
import edu.sjsu.assess.dao.CategoryDAOImpl;
import edu.sjsu.assess.dao.UserDAOImpl;
import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.exception.QuestionException;
import edu.sjsu.assess.model.Assignment;
import edu.sjsu.assess.model.AssignmentSearchParams;
import edu.sjsu.assess.model.Assignmentoption;
import edu.sjsu.assess.model.Category;
import edu.sjsu.assess.model.User;
import edu.sjsu.assess.util.Utility;

@Service
public class AssignmentServiceImpl implements AssignmentService{

	@Autowired
	private AssignmentDAOImpl assignmentDAO;
	
	@Autowired
	private CategoryDAOImpl categoryDAO;
	
	@Autowired
	private UserDAOImpl userDAO;

	
	@Override
	public Assignment saveAssignment(Assignment qs) throws QuestionException {
		
		Assignment newQS = null;
		
		try {
			String userName = Utility.getLoggedInUserName();
			
			if(userName != null){
				User user = userDAO.getUserByLogin(userName);
				qs.setUserID(user.getId());
			}
			
			//qs.setUserID(1);
			
			newQS = assignmentDAO.createAssignment(qs);

		} catch (DAOException e) {
			throw new QuestionException("Failed to Create Assignment.", e);
		}

		return newQS;
	}
	
	public Assignmentoption saveAssignmentSubmission(Assignmentoption as) throws QuestionException {
		
		Assignmentoption newQS = null;
		try {
			String userName = Utility.getLoggedInUserName();
			
			if(userName != null){
				User user = userDAO.getUserByLogin(userName);
				as.setSid(user.getId());
			}
			
			//qs.setUserID(1);
			
			 newQS = assignmentDAO.saveAssignmentSubmission(as);

		} catch (DAOException e) {
			throw new QuestionException("Failed to Create Assignment.", e);
		}

		return newQS;
	}

	public List<Assignment> searchAssignments(AssignmentSearchParams searchParams)
			throws QuestionException
	{
		List<Assignment> searchResults = new ArrayList<>();
		try {
			searchResults = assignmentDAO.searchAssignments(searchParams);
		}
		catch (DAOException e) {
			e.printStackTrace();
			throw new QuestionException("Failed to search assignments"+e);
		}
		return searchResults;
	}

	@Override
	public List<Assignment> getAssignmentsList(AssignmentSearchParams searchParams) throws QuestionException {
		
		List<Assignment> qsList = new ArrayList<Assignment>();

    	try {

    		List<Integer> questionsIDList = assignmentDAO.getAssignmentList(searchParams);
    		
    		for(Integer questionID : questionsIDList){
    			Assignment qs = assignmentDAO.getAssignmentByID(questionID);
    			
    			Integer categoryID = qs.getCategoryID();
    			if(categoryID != null){
    				Category categoryObj = categoryDAO.getCategoryByID(categoryID);
    				qs.setCategoryObj(categoryObj);
    			}
    			
    			qsList.add(qs);
    		}
    		
        } catch (DAOException e) {
            throw new QuestionException("Failed to get assignments.", e);
        }
    	
    	return qsList;
	}

	public List<Assignment> getAssignmentsByIds(List<Integer> questionIds)
			throws QuestionException {

		List<Assignment> qsList = new ArrayList<Assignment>();
		try {
			qsList = assignmentDAO.getAssignmentByID(questionIds);

			// set categories
			for(Assignment ques: qsList) {
				Integer categoryID = ques.getCategoryID();
				if(categoryID != null){
					Category categoryObj = categoryDAO.getCategoryByID(categoryID);
					ques.setCategoryObj(categoryObj);
				}
			}
		} catch (DAOException e) {
			throw new QuestionException("Failed to get assignments.", e);
		}
		return qsList;
	}
	
	@Override
	public void updateAssignment(Assignment qs) throws QuestionException {
		
		try {
			assignmentDAO.updateAssignmentByID(qs);
            
            if(qs.isMultipleChoice()){
            	assignmentDAO.deleteAllOptions(qs.getId());
	            
            	assignmentDAO.createOptions(qs.getId(), qs.getOptions());
            }
            
        } catch (DAOException e) {
            throw new QuestionException("Failed to update assignment.",
                    e);
        }
	}
	
	@Override
	public void deleteAssignment(AssignmentSearchParams searchParams) throws QuestionException {
		
		Integer qsID = searchParams.getId();
        try {
        	
        	assignmentDAO.deleteAllOptions(qsID);
        	
        	assignmentDAO.deleteAssignmentByID(qsID);
            
        } catch (DAOException e) {
            throw new QuestionException("Failed to delete assignments.",
                    e);
        }
	}



}
