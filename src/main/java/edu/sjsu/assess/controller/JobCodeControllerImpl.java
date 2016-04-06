package edu.sjsu.assess.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sjsu.assess.exception.JobCodeException;
import edu.sjsu.assess.model.Category;
import edu.sjsu.assess.model.JobCode;
import edu.sjsu.assess.model.JobCodeSearchParams;
import edu.sjsu.assess.service.JobCodeServiceImpl;

@Controller
@RequestMapping("/jobcode")
public class JobCodeControllerImpl implements JobCodeController {

	@Autowired
	private JobCodeServiceImpl jobCodeService;

	private static final String jobCodeView = "jobCode";

	@Override
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createJobCode(@ModelAttribute("search") JobCode jc,
			Model model) {

		JobCode savedJC = null;
		model.addAttribute("error", "");
		model.addAttribute("message", "");

		if (jc == null) {
			model.addAttribute("error", "Null JobCode Object!");
		} else {

			try {

				savedJC = jobCodeService.saveJobCode(jc);
				model.addAttribute("jc", savedJC);
				model.addAttribute("message", "Job Code Saved Successfully.");
			} catch (JobCodeException e) {
				model.addAttribute("error", e.getMessage());
			}
		}

		return jobCodeView;
	}

	@Override
	@RequestMapping(value = "/createCategory", method = RequestMethod.POST)
	public String createCategory(@ModelAttribute("search") Category category, Model model) {
		
        Category savedCategory = null;
        model.addAttribute("error", "");
        model.addAttribute("message", "");

        if (category == null) {
            model.addAttribute("error", "Null Category Object!");
        }
        else {

            try {
            	savedCategory = jobCodeService.createCategoryForJobCode(category);
                model.addAttribute("category", savedCategory);
                model.addAttribute("message",
                        "Category Saved Successfully.");
            } catch (JobCodeException e) {
                model.addAttribute("error", e.getMessage());
            }
        }
        
		return jobCodeView;
	}

	@Override
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public String getJobCode(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("error", "");
		model.addAttribute("message", "");

		try {
            JobCodeSearchParams searchParams = new JobCodeSearchParams();
            searchParams.setId(id);
			JobCode jc = jobCodeService.getJobCode(searchParams);
			model.addAttribute("jc", jc);
		} catch (JobCodeException e) {
			model.addAttribute("error", e.getMessage());
		}

		return "viewjobcode";
	}

    @Override
    @RequestMapping(value = "/getForEdit/{id}", method = RequestMethod.GET)
    public @ResponseBody JobCode getJobCodeForEdit(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("error", "");
        model.addAttribute("message", "");
        JobCode jc = null;
        try {
            JobCodeSearchParams searchParams = new JobCodeSearchParams();
            searchParams.setId(id);
            jc = jobCodeService.getJobCode(searchParams);
            model.addAttribute("jc", jc);
        } catch (JobCodeException e) {
            model.addAttribute("error", e.getMessage());
        }

        return jc;
    }
//
//	@Override
//	@RequestMapping(value = "/getList", method = RequestMethod.GET)
//	public @ResponseBody List<JobCode> getJobCodeList(Model model) {
//
//		model.addAttribute("error", "");
//		model.addAttribute("message", "");
//
//		List<JobCode> jcList = null;
//		try {
//
//			jcList = jobCodeService.getJobCodeList();
//			model.addAttribute("jcList", jcList);
//		} catch (JobCodeException e) {
//			model.addAttribute("error", e.getMessage());
//		}
//
//		//return "viewjobcodes";
//		return jcList;
//	}

    @Override
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public String getJobCodeList(Model model) {

        model.addAttribute("error", "");
        model.addAttribute("message", "");

        try {

            List<JobCode> jcList = jobCodeService.getJobCodeList();
            model.addAttribute("jcList", jcList);
        } catch (JobCodeException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "viewjobcodes";
    }

    @Override
    @RequestMapping(value = "/getListForUserJobcodeTraining", method = RequestMethod.GET)
    public @ResponseBody List<JobCode> getJobCodeListForUser(Model model) {

        model.addAttribute("error", "");
        model.addAttribute("message", "");
        List<JobCode> jcList = null;
        System.out.println("in the new method for getting jobcodes");
        try {

        	jcList = jobCodeService.getJobCodeList();
            model.addAttribute("jcList", jcList);
        } catch (JobCodeException e) {
            model.addAttribute("error", e.getMessage());
        }

        return jcList;
    }



	//Todo: The method signature is as below. I will be sending the job code id for deletion as a path variable. 
	//For testing, I have hard coded the values for searchParams object
//	@Override
//	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
//	public String deleteJobCode(JobCodeSearchParams searchParams, Model model) {
//		model.addAttribute("error", "");
//		model.addAttribute("message", "");
//
//		if (searchParams == null || searchParams.getId() == null) {
//			model.addAttribute("error", "Search parameters missing!");
//		} else {
//			try {
//				jobCodeService.deleteJobCode(searchParams);
//
//			} catch (JobCodeException e) {
//				model.addAttribute("error", e.getMessage());
//			}
//		}
//
//		return jobCodeView;
//	}

	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteJobCode(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("error", "");
        model.addAttribute("message", "");
        if(id == null){
            model.addAttribute("error", "Search parameters missing!");
        }
        else{
            try{
                JobCodeSearchParams searchParams = new JobCodeSearchParams();
                searchParams.setId(id);
                // TODO: Remove this because I am setting userID in service method
                //searchParams.setUserID(1);
                
                jobCodeService.deleteJobCode(searchParams);
                
            }catch(JobCodeException e){
                model.addAttribute("error", e.getMessage());
            }
        }
        
        return "alertdeletejobcode";
    }

	@Override
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateJobcode(JobCode jc, Model model) {

		model.addAttribute("error", "");
		model.addAttribute("message", "");

		try {
			JobCode updatedJC = jobCodeService.updateJobCode(jc);
			model.addAttribute("jc", updatedJC);
		} catch (JobCodeException e) {
			model.addAttribute("error", e.getMessage());
		}
		return jobCodeView;
	}

	@Override
	@RequestMapping(value = "/getAllPredefined", method = RequestMethod.GET)
	public @ResponseBody List<JobCode> getAllPredefinedJobCodes() {
		List<JobCode> jcList = null;
		try {
			jcList = jobCodeService.getAllPredefinedJobCodes();
		} catch (JobCodeException e) {
			System.out.println(e);
		}

		return jcList;
	}

	@Override
	@RequestMapping(value = "/getAllPredefinedCategories/{testAttempId}", method = RequestMethod.GET)
	public @ResponseBody List<JobCode> getAllPredefinedCategories(@PathVariable("testAttempId") Integer testAttemptId) {
//		List<JobCode> jcList = null;
//		try {
//			jcList = jobCodeService.getAllPrederinedJobCodesForTestAttempt();
//		} catch(JobCodeException e) {
//			System.out.println(e);
//		}
//		
//		return jcList;
		return null;
	}

    // Method called when user clicks on Create New Category Button.
    // through javascript/ajax.
    @RequestMapping(value = "/createAjaxCategory", method = RequestMethod.POST, headers = { "Content-type=application/json" })
    public @ResponseBody Category ajaxCreateCategory(@RequestBody Category category) {

            Category savedCategory = null;
            try {
                savedCategory = jobCodeService.createCategoryForJobCode(category);
            } catch (JobCodeException e) {
                //model.addAttribute("error", e.getMessage());
            }


        return savedCategory;
    }


    // Method called when user clicks on save in edit screen for jobcode
    // through javascript/ajax.
    @RequestMapping(value = "/editJobCode", method = RequestMethod.POST, headers = { "Content-type=application/json" })
    public @ResponseBody JobCode ajaxEditJobCode(@RequestBody JobCode jc) {

        JobCode updatedJC = null;
        try {
             updatedJC = jobCodeService.updateJobCode(jc);
        } catch (JobCodeException e) {
            //model.addAttribute("error", e.getMessage());
        }


        return updatedJC;
    }



	// Method called when user clicks on 'Create Job Code' button on create page
	// through javascript/ajax. Could not use the regular /create method
	// because, it was not easy to map
	// form data in create page to model attribute param.
	@RequestMapping(value = "/createJobCode", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody JobCode ajaxCreateJobCode(@RequestBody JobCode jc) {

		JobCode res = null;
		System.out.println(jc);

		try {
			res = jobCodeService.saveJobCode(jc);
		} catch (JobCodeException e) {
			System.out.println(e);
			return null;
		}

		return res;
	}

	// called when menu item 'Create Job Code' is selected. This renders
	// createjobcode page.
	@Override
	@RequestMapping(value = "/showcreatejobcode", method = RequestMethod.GET)
	public String showCreateJobCode(Model model) {

		return "createjobcode";
	}


    // called when menu item 'Edit Job Code' is selected. This renders
    // editjobcode page.
    @RequestMapping(value = "/showeditjobcode/{id}", method = RequestMethod.GET)
    public String showEditJobCode(@PathVariable("id") Integer id,Model model) {

        model.addAttribute("jobcodeId", id);
        return "editjobcode";
    }


	// the above 'get' method with JobCodeSearchParams is not needed because
	// searchParams requires a form to be created in view.
	// In view jobcodes ..we will need one form per jobcode..so we will have
	// multiple forms. The below controller method with request param
	// does not need a form in view
	//@RequestMapping(value = "/viewjobcode", method = RequestMethod.GET)
//	public String getJobCode(
//			@RequestParam(value = "id", required = false) Integer jobcodeId,
//			Model model) {
//
//		model.addAttribute("error", "");
//		model.addAttribute("message", "");
//
//		try {
//			if (jobcodeId == null) {
//				// Todo: change this to get all user defined jobcodes from the
//				// service. The below getAllPredefinedJobCodes method gets only
//				// predefined jobcodes
//				// List<JobCode> jcs =
//				// jobCodeService.getAllPredefinedJobCodes();
//
//				List<JobCode> jcs = jobCodeService.getJobCodeList();
//				model.addAttribute("list", jcs);
//				return "viewjobcodes";
//			} else {
//				JobCode jc = jobCodeService.getJobCode(new JobCodeSearchParams(
//						jobcodeId));
//				model.addAttribute("jc", jc);
//				return "viewjobcode";
//			}
//		} catch (JobCodeException e) {
//			model.addAttribute("error", e.getMessage());
//		}
//
//		return "viewjobcodes";
//	}

	@Override
	@RequestMapping(value = "/getCategories", method = RequestMethod.GET)
	public String getCategories(String jobCodeID, String parentCategoryID,
			Model model) {

		return jobCodeView;
	}


}
