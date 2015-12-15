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
public interface CourseService {

	public JobCode saveCourse(JobCode jc) throws JobCodeException;

	public JobCode getCourse(JobCodeSearchParams searchParams)
			throws JobCodeException;

	public List<JobCode> getCourseList() throws JobCodeException;

	public void deleteCourse(JobCodeSearchParams searchParams)
			throws JobCodeException;

	public JobCode updateCourse(JobCode jc) throws JobCodeException;

	public List<JobCode> getAllPredefinedCourses() throws JobCodeException;
	
	public List<JobCode> getCourseForUserPreference() throws JobCodeException;

	public Category createCategoryForCourse(Category category) throws JobCodeException;

}
