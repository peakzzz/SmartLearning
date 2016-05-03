package edu.sjsu.assess.dao;

import java.util.List;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.ProjectSubmission;
import edu.sjsu.assess.model.StudentProject;


public interface ProjectDAO {
	public StudentProject createProject(final StudentProject studentProject) throws DAOException;
	public List<StudentProject> getProjects() throws DAOException;
	public StudentProject getProjectByID(Integer id) throws DAOException;
	public ProjectSubmission createProjectSubmission(ProjectSubmission projectSubmission) throws DAOException;
}
