package edu.sjsu.assess.service;

import java.util.List;

import edu.sjsu.assess.exception.ProjectException;
import edu.sjsu.assess.model.ProjectSubmission;
import edu.sjsu.assess.model.StudentProject;


public interface ProjectService {
	public StudentProject saveProject(StudentProject studentProject) throws ProjectException;
	public List<StudentProject> getProjects() throws ProjectException;
	public StudentProject getProjectByID(Integer id) throws ProjectException;
	public ProjectSubmission saveProjectSubmission(ProjectSubmission projectSubmission) throws ProjectException;
}
