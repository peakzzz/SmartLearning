package edu.sjsu.assess.dao;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.AdminGraphData.JobCodeAttempt;
import edu.sjsu.assess.model.AdminGraphData.JobCodeDetails;
import edu.sjsu.assess.model.AdminGraphData.JobCodeScore;
import edu.sjsu.assess.model.AdminGraphData.RegistrationCount;

public interface AdminGraphDAO {
	public JobCodeAttempt getJobcodeAttempts() throws DAOException;
	
	public JobCodeScore getJobCodeScores() throws DAOException;
	
	public JobCodeDetails getCategoryWiseScoresForQA() throws DAOException;
	
	public JobCodeDetails getCategoryWiseScoresForSoftwareDevelpment() throws DAOException;
	
	public RegistrationCount getProfessionBasedRegistration() throws DAOException;
	
	public RegistrationCount getAgeBasedRegistration() throws DAOException;
	
}
