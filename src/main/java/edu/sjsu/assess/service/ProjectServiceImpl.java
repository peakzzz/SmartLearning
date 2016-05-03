package edu.sjsu.assess.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.assess.dao.ProjectDAOImpl;
import edu.sjsu.assess.dao.UserDAOImpl;
import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.exception.DiscussionForumException;
import edu.sjsu.assess.exception.ProjectException;
import edu.sjsu.assess.model.ForumPost;
import edu.sjsu.assess.model.ForumReply;
import edu.sjsu.assess.model.ProjectSubmission;
import edu.sjsu.assess.model.StudentProject;
import edu.sjsu.assess.model.User;
import edu.sjsu.assess.util.Utility;
@Service
public class ProjectServiceImpl implements ProjectService{

	@Autowired
	private ProjectDAOImpl projectDAO;
	@Autowired
	private UserDAOImpl userDAO;
	
	@Override
	public StudentProject saveProject(StudentProject studentProject)
			throws ProjectException {
		System.out.println("Hi in service saveForumPost method"+studentProject.getDescription());
		StudentProject newstudentProject = null;
		
		try {
			String userName = Utility.getLoggedInUserName();
			
			if(userName != null){
				User user = userDAO.getUserByLogin(userName);
				System.out.println("user role for saving inside projectserviceimp="+user.getRole());
				studentProject.setUserID(user.getId());
			}
			
			//qs.setUserID(1);
			
			newstudentProject = projectDAO.createProject(studentProject);

		} catch (DAOException e) {
			throw new ProjectException("Failed to Create Post.", e);
		}

		return newstudentProject;
	}

	public String getRole() throws ProjectException{
		String role = "";
		try {
				String userName = Utility.getLoggedInUserName();
				
				if(userName != null){
					User user = userDAO.getUserByLogin(userName);
					System.out.println("user role  projectserviceimp="+user.getRole());
					role = user.getRole();
				}
			}catch (DAOException e) {
				throw new ProjectException("Failed to Create Post.", e);
			}
		return role;
	}
	@Override
	public List<StudentProject> getProjects() throws ProjectException {
		System.out.println("In getProjects method in service: ");
		List<StudentProject> studentProjects = new ArrayList<>();
		try {
			studentProjects = projectDAO.getProjects();
		} catch (DAOException e) {
			throw new ProjectException("Failed to retrieve Projects.", e);
		}
		return studentProjects;
	}

	@Override
	public StudentProject getProjectByID(Integer id)
			throws ProjectException {
		System.out.println("In getProjectByID method in service: ");
		StudentProject studentProject = new StudentProject();
		try {
			studentProject = projectDAO.getProjectByID(id);
		} catch (DAOException e) {
			throw new ProjectException("Failed to retrieve Project ." + id, e);
		}
		return studentProject;
	}
	
	@Override
	public ProjectSubmission saveProjectSubmission(ProjectSubmission prjctSubmission)throws ProjectException {
		System.out.println("Hi in service saveForumReply method"+prjctSubmission.getGitLink());
		ProjectSubmission newProjectSubmission = null;
		
		try {
			String userName = Utility.getLoggedInUserName();
			
			if(userName != null){
				User user = userDAO.getUserByLogin(userName);
				prjctSubmission.setUserID(user.getId());
				prjctSubmission.setFname(userName);
			}
			
			//qs.setUserID(1);
			
			System.out.println("project service impl  prj id  "+prjctSubmission.getProjectId());
			newProjectSubmission = projectDAO.createProjectSubmission(prjctSubmission);

		} catch (DAOException e) {
			throw new ProjectException("Failed to save Project submission .", e);
		}

		return newProjectSubmission;
	}
	

}
