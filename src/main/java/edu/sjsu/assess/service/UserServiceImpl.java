package edu.sjsu.assess.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.assess.dao.UserDAOImpl;
import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.exception.UserException;
import edu.sjsu.assess.model.User;
import edu.sjsu.assess.model.UserSearchParams;

@Service
public class UserServiceImpl implements UserService {
	public static enum Role {
		Candidate("candidate"), Admin("admin");
		private String s;

		private Role(String s) {
			this.s = s;
		}

		public Role getRole(String s) {
			if (s == null)
				return null;
			if (s.equals("candidate")) {
				return Candidate;
			} else if (s.equals("admin")) {
				return Admin;
			} else {
				return null;
			}
		}

		@Override
		public String toString() {
			return s;
		}
	}

	@Autowired
	private UserDAOImpl userDAO;

	@Override
	public User saveUser(User usr) throws UserException {

		try {
			if (userDAO.getUserByLogin(usr.getLogin()) != null) {
				throw new UserException("User already exists.");
			}

			return userDAO.createUser(usr);

		} catch (DAOException e) {
			throw new UserException("Failed to create user.", e);
		}
	}

	@Override
	public User getUser(UserSearchParams searchParams) throws UserException {
		try {
			User usr;
			if (searchParams.getId() != null) {
				usr = userDAO.getUserByID(searchParams.getId());
			} else if (searchParams.getLogin() != null) {
				usr = userDAO.getUserByLogin(searchParams.getLogin());
			} else {
				throw new UserException("Invalid Search Parameters!");
			}

//			Set<Integer> jobPreferences = userDAO.getJobPreferencesOfUser(usr.getId());
//			usr.setJobPreferences(jobPreferences);
//			
			return usr;
			
		} catch (UserException ue) {
			throw ue;
		} catch (Exception e) {
			UserException ue = new UserException();
			ue.setStackTrace(e.getStackTrace());
			throw new UserException("Failed to get user.", e);
		}
	}

	
	@Override
	public boolean deleteUser(UserSearchParams searchParams)
			throws UserException {
		boolean status = false;
		try {

			if (searchParams.getId() != null) {
				status = userDAO.deleteUserByID(searchParams.getId());
				userDAO.deleteJobPreferencesByID(searchParams.getId());
			} else if (searchParams.getLogin() != null) {
				status = userDAO.deleteUserByLogin(searchParams.getLogin());
				userDAO.deleteJobPreferencesByLogin(searchParams.getLogin());
			}

		} catch (Exception e) {
			throw new UserException("Failed to delete user.", e);
		}

		return status;
	}

	@Override
	public User updateUser(User usr) throws UserException {
		
		User updatedUser = null;
		try {
			updatedUser = userDAO.updateUser(usr);
			
			userDAO.deleteJobPreferencesByID(usr.getId());
			
			Set<Integer> jobPrefs = usr.getJobPreferences();
			if(jobPrefs != null && jobPrefs.size() > 0){
				userDAO.createUserJobPreferences(usr.getId(), jobPrefs);
			}
		} catch (Exception e) {
			throw new UserException("Failed to update user.", e);
		}
		
		return updatedUser;
	}

	@Override
	public boolean authenticateUser(User usr) {
		User dbUser = null;
		try {
			dbUser = userDAO.getUserByID(usr.getId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		if (dbUser == null) {
			return false;
		}

		if (!dbUser.getPassword().equals(usr.getPassword())) {
			return false;
		}

		return true;
	}

	/*
	 * public User findByUsername(String username) { User user = new User();
	 * user.setId("a@a.com"); user.setName("Admin user");
	 * user.setPassword("pwd"); user.setRole("admin"); return
	 * username.equals("a@a.com") ? user : null; }
	 */
}
