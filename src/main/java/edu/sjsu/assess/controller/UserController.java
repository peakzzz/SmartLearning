package edu.sjsu.assess.controller;

import org.springframework.ui.Model;

import edu.sjsu.assess.model.User;
import edu.sjsu.assess.model.User.UserPreferenceHolder;
import edu.sjsu.assess.model.UserSearchParams;

public interface UserController {

	public String createUser(UserPreferenceHolder usr, Model model);

	public String getUser(UserSearchParams searchParams, Model model);

	public String deleteUser(UserSearchParams searchParams, Model model);

	public String updateUser(UserPreferenceHolder usr, Model model);

	public String login(User usr, Model model);

	// new method added
	public String getLoginPage(String error, Model model);

	String register(Model model);

	public String getUserProfile(Model model);

	String getSuccess(Model model);

}
