package edu.sjsu.assess.dao;

import java.util.List;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.JobCode;

/**
 * Job Code DAO Interface
 * @author Shefali
 *
 */
public interface CourseDAO {
	
	public JobCode createCourse(JobCode jc) throws DAOException;
	
	public JobCode getCourseByID(Integer jcID) throws DAOException;
	
	public List<Integer> getCourseList() throws DAOException;
	
	public void updateCourseByID(JobCode jc) throws DAOException;
	
	public void deleteCourseByID(Integer jcID) throws DAOException;
	
	public List<JobCode> getAllPredefinedCourses() throws DAOException;
	
	public List<JobCode> getCourseForUserPreference() throws DAOException;
	
}
