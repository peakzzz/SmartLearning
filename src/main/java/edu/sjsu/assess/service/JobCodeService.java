package edu.sjsu.assess.service;

import java.util.List;

import edu.sjsu.assess.exception.JobCodeException;
import edu.sjsu.assess.model.Category;
import edu.sjsu.assess.model.JobCode;
import edu.sjsu.assess.model.JobCodeSearchParams;

/**
 * Job Code Service Interface
 * 
 * @author Shefali
 *
 */
public interface JobCodeService {

	public JobCode saveJobCode(JobCode jc) throws JobCodeException;

	public JobCode getJobCode(JobCodeSearchParams searchParams)
			throws JobCodeException;

	public List<JobCode> getJobCodeList() throws JobCodeException;

	public void deleteJobCode(JobCodeSearchParams searchParams)
			throws JobCodeException;

	public JobCode updateJobCode(JobCode jc) throws JobCodeException;

	public List<JobCode> getAllPredefinedJobCodes() throws JobCodeException;
	
	public List<JobCode> getJobCodeForUserPreference() throws JobCodeException;

	public Category createCategoryForJobCode(Category category) throws JobCodeException;

}
