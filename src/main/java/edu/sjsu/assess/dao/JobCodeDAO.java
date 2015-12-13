package edu.sjsu.assess.dao;

import java.util.List;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.JobCode;

/**
 * Job Code DAO Interface
 * @author Shefali
 *
 */
public interface JobCodeDAO {
	
	public JobCode createJobCode(JobCode jc) throws DAOException;
	
	public JobCode getJobCodeByID(Integer jcID) throws DAOException;
	
	public List<Integer> getJobCodeList() throws DAOException;
	
	public void updateJobCodeByID(JobCode jc) throws DAOException;
	
	public void deleteJobCodeByID(Integer jcID) throws DAOException;
	
	public List<JobCode> getAllPredefinedJobCodes() throws DAOException;
	
	public List<JobCode> getJobCodeForUserPreference() throws DAOException;
	
}
