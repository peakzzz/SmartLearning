package edu.sjsu.assess.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.assess.dao.AdminGraphDAOImpl;
import edu.sjsu.assess.dao.UserDAOImpl;
import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.AdminGraphData;
import edu.sjsu.assess.model.AdminGraphData.JobCodeAttempt;
import edu.sjsu.assess.model.AdminGraphData.JobCodeScore;
import edu.sjsu.assess.model.AdminGraphData.RegistrationCount;

@Service
public class AdminGraphServiceImpl implements AdminGraphService{

	@Autowired
	AdminGraphDAOImpl adminGraphDAO;
	
	@Autowired
	UserDAOImpl userDAOImpl;
	
	public AdminGraphData getAdminGraphData(){
		AdminGraphData result = new AdminGraphData();
		
		try{
			result.setJobCodeAttempt(adminGraphDAO.getJobcodeAttempts());
		} catch(DAOException e1){
			result.setJobCodeAttempt(new JobCodeAttempt());
		}
		
		try{
			result.setJobCodeScore(adminGraphDAO.getJobCodeScores());
		} catch(DAOException e2){
			result.setJobCodeScore(new JobCodeScore());
		}
		
		try{
			result.setTopJobCode(adminGraphDAO.getCategoryWiseScoresForQA());
		} catch(DAOException e3){
			result.setTopJobCode(new AdminGraphData.JobCodeDetails("Category-wise scores (QA Engineering)", 0.0));
		}
		
		try{
			result.setNextToTopJobCode(adminGraphDAO.getCategoryWiseScoresForSoftwareDevelpment());
		} catch(DAOException e4){
			result.setNextToTopJobCode(new AdminGraphData.JobCodeDetails("Category-wise scores (Software Development)", 0.0));
		}
		
		try{
			result.setProfessionBasedRegistration(adminGraphDAO.getProfessionBasedRegistration());
		} catch(DAOException e5){
			result.setProfessionBasedRegistration(new RegistrationCount("Profession-Based Registration"));
		}
		
		try{
			result.setAgeBasedRegisration(adminGraphDAO.getAgeBasedRegistration());
		} catch(DAOException e6){
			result.setAgeBasedRegisration(new RegistrationCount("Age-Based Registration"));
		}
		
		return result;
	}
}
