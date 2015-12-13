package edu.sjsu.assess.dao;

import java.util.Set;

import org.springframework.stereotype.Repository;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.User;

@Repository
public interface UserDAO {
	
	public User createUser(User usr) throws DAOException;
	
	public User getUserByID(Integer id) throws DAOException;
	
	public User getUserByLogin(String login) throws DAOException;
	
	public boolean deleteUserByID(Integer id) throws DAOException;
	
	public boolean deleteUserByLogin(String login) throws DAOException;
	
	public User updateUser(User usr) throws DAOException;
	
	public void createUserJobPreferences(final Integer userID, Set<Integer> jobPreferenceList) throws DAOException;
			
	public Set<Integer> getJobPreferencesOfUser(Integer userID) throws DAOException;
	
	public void deleteJobPreferencesByID(Integer userID) throws DAOException;
	
	public void deleteJobPreferencesByLogin(String login) throws DAOException;
	
}
