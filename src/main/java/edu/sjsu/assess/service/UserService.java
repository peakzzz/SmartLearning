package edu.sjsu.assess.service;

import org.springframework.stereotype.Service;

import edu.sjsu.assess.exception.UserException;
import edu.sjsu.assess.model.User;
import edu.sjsu.assess.model.UserSearchParams;

@Service
public interface UserService {
	
	public User saveUser(User usr) throws UserException;
	
	public User getUser(UserSearchParams searchParams) throws UserException;
	
	public boolean deleteUser(UserSearchParams searchParams) throws UserException;
	
	public User updateUser(User usr) throws UserException;
	
	public boolean authenticateUser(User usr) throws UserException;

}
