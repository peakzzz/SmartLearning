package edu.sjsu.assess.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.sjsu.assess.exception.JobCodeException;
import edu.sjsu.assess.exception.UserException;
import edu.sjsu.assess.model.JobCode;
import edu.sjsu.assess.model.User;
import edu.sjsu.assess.model.User.JobCodeSelected;
import edu.sjsu.assess.model.User.UserPreferenceHolder;
import edu.sjsu.assess.model.UserSearchParams;
import edu.sjsu.assess.service.JobCodeServiceImpl;
import edu.sjsu.assess.service.UserServiceImpl;
import edu.sjsu.assess.service.UserServiceImpl.Role;
import edu.sjsu.assess.util.Utility;

@Controller
@RequestMapping("/user")
public class UserControllerImpl implements UserController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private JobCodeServiceImpl jobCodeService;

	@Override
	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public String getLoginPage(
			@RequestParam(value = "error", required = false) String error,
			Model model) {
		if (error != null) {
			model.addAttribute("error", true);
		}
		User usr = new User();
		model.addAttribute("usr", usr);

		return "login";
	}

	@Override
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String login(User usr, Model model) {
		model.addAttribute("usr", usr);
		System.out.println("Inside user controller's login method");
		return "home";
	}

	@Override
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model) {
		model.addAttribute("error", "");
		model.addAttribute("message", "");

		try {
			List<JobCode> jcList = jobCodeService.getJobCodeForUserPreference();
			List<JobCodeSelected> jcList2 = new ArrayList<User.JobCodeSelected>();
			for (JobCode a : jcList) {
				jcList2.add(new JobCodeSelected(a, false));
			}

			UserPreferenceHolder upHolder = new UserPreferenceHolder(
					new User(), jcList2);
			model.addAttribute("usr", upHolder);
		} catch (JobCodeException jce) {
			model.addAttribute("error", jce.getMessage());
		}

		return "register";
	}

	@Override
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String getUserProfile(Model model) {
		model.addAttribute("error", "");
		model.addAttribute("message", "");

		try {
			String userName = Utility.getLoggedInUserName();
			UserSearchParams searchParams = new UserSearchParams();
			searchParams.setLogin(userName);

			User user = userService.getUser(searchParams);

			List<JobCodeSelected> jcList2 = new ArrayList<User.JobCodeSelected>();
			List<JobCode> jcList = jobCodeService.getJobCodeForUserPreference();
			for (JobCode a : jcList) {
				if (user.getJobPreferences().contains(a.getId())) {
					jcList2.add(new JobCodeSelected(a, true));
				} else {
					jcList2.add(new JobCodeSelected(a, false));
				}
			}
			UserPreferenceHolder upHolder = new UserPreferenceHolder(user,
					jcList2);
			model.addAttribute("usr", upHolder);
		} catch (UserException ue) {
			model.addAttribute("error", ue.getMessage());
		} catch (JobCodeException jce) {
			model.addAttribute("error", jce.getMessage());
		}

		return "profile";
	}

	@Override
	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public String getSuccess(Model model) {
		model.addAttribute("error", "");
		model.addAttribute("message", "");

		// try {
		// String userName = Utility.getLoggedInUserName();
		// UserSearchParams searchParams = new UserSearchParams();
		// searchParams.setLogin(userName);
		//
		// User user = userService.getUser(searchParams);
		//
		// List<JobCodeSelected> jcList2 = new
		// ArrayList<User.JobCodeSelected>();
		// List<JobCode> jcList = jobCodeService.getJobCodeForUserPreference();
		// for (JobCode a : jcList) {
		// if (user.getJobPreferences().contains(a.getId())) {
		// jcList2.add(new JobCodeSelected(a, true));
		// } else {
		// jcList2.add(new JobCodeSelected(a, false));
		// }
		// }
		// UserPreferenceHolder upHolder = new UserPreferenceHolder(
		// user, jcList2);
		// model.addAttribute("usr", upHolder);
		// } catch (UserException ue) {
		// model.addAttribute("error", ue.getMessage());
		// } catch (JobCodeException jce) {
		// model.addAttribute("error", jce.getMessage());
		// }

		User loggedInUser = null;

		try {
			String userName = Utility.getLoggedInUserName();
			UserSearchParams searchParams = new UserSearchParams();
			searchParams.setLogin(userName);
			loggedInUser = userService.getUser(searchParams);
		} catch (UserException ue) {
			return "login";
		}

		if (loggedInUser.isAdmin()) {
			return "admindashboard";
		} else {
			return "candidatedashboard";
		}
	}

	@Override
	@RequestMapping(value = "/createcandidate", method = RequestMethod.POST)
	public String createUser(@ModelAttribute("usr") UserPreferenceHolder usr,
			Model model) {
		User savedUsr = null;
		model.addAttribute("error", "");
		model.addAttribute("message", "");

		if (usr == null) {
			model.addAttribute("error", "User Details not present");
		} else if (!Utility.isValidUserObject(usr.getUser())) {
			model.addAttribute("error", "Invalid user");
		} else {
			try {
				usr.getUser().setRole(Role.Candidate.toString());
				for (JobCodeSelected a : usr.getJcList()) {
					if (a.isSelected())
						usr.getUser().addJobPreference(a.getJobCode().getId());
				}
				savedUsr = userService.saveUser(usr.getUser());
				model.addAttribute("usr", new UserPreferenceHolder(savedUsr,
						usr.getJcList()));
				model.addAttribute("message", "User created successfully.");
			} catch (UserException ue) {
				model.addAttribute("error", ue.getMessage());
				model.addAttribute("usr", usr);
			}

		}
		return "register";
	}

	@Override
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String getUser(
			@ModelAttribute("searchParams") UserSearchParams searchParams,
			Model model) {
		model.addAttribute("error", "");
		model.addAttribute("message", "");

		try {
			User usr = userService.getUser(searchParams);
			model.addAttribute("usr", usr);
		} catch (UserException ue) {
			model.addAttribute("error", ue.getMessage());
		}

		return "user";
	}

	@Override
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public String deleteUser(
			@ModelAttribute("searchParams") UserSearchParams searchParams,
			Model model) {

		model.addAttribute("error", "");
		model.addAttribute("message", "");

		try {
			boolean status = userService.deleteUser(searchParams);
			if (!status) {
				model.addAttribute("error", "Failed to delete user.");
			}
		} catch (UserException ue) {
			model.addAttribute("error", ue.getMessage());
		}

		return "user";
	}

	@Override
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("usr") UserPreferenceHolder usr,
			Model model) {
		User savedUsr = null;
		model.addAttribute("error", "");
		model.addAttribute("message", "");

		if (usr == null || usr.getUser().getId() == 0) {
			model.addAttribute("error", "User Details not present");
		} else if (!Utility.isValidUserObject(usr.getUser())) {
			model.addAttribute("error", "Invalid user");
		} else {
			try {
				for (JobCodeSelected a : usr.getJcList()) {
					if (a.isSelected())
						usr.getUser().addJobPreference(a.getJobCode().getId());
				}
				savedUsr = userService.updateUser(usr.getUser());
				model.addAttribute("usr", new UserPreferenceHolder(savedUsr,
						usr.getJcList()));
				model.addAttribute("message",
						"User information updated successfully.");
			} catch (UserException ue) {
				model.addAttribute("error", ue.getMessage());
				model.addAttribute("usr", usr);
			}

		}
		return "profile";
	}

}
