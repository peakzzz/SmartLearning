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
import edu.sjsu.assess.service.CourseServiceImpl;

@Controller
@RequestMapping("/course")
public class CourseControllerImpl implements CourseController {

	@Autowired
	private CourseServiceImpl courseService;

	private static final String jobCodeView = "jobCode";

	@Override
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createCourse(@ModelAttribute("search") JobCode jc,
			Model model) {

		JobCode savedJC = null;
		model.addAttribute("error", "");
		model.addAttribute("message", "");

		if (jc == null) {
			model.addAttribute("error", "Null JobCode Object!");
		} else {

			try {

				savedJC = courseService.saveCourse(jc);
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
	public String createDepartment(@ModelAttribute("search") Category category, Model model) {
		
        Category savedCategory = null;
        model.addAttribute("error", "");
        model.addAttribute("message", "");

        if (category == null) {
            model.addAttribute("error", "Null Category Object!");
        }
        else {

            try {
            	savedCategory = courseService.createCategoryForCourse(category);
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
	public String getCourse(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("error", "");
		model.addAttribute("message", "");

		try {
            JobCodeSearchParams searchParams = new JobCodeSearchParams();
            searchParams.setId(id);
			JobCode jc = courseService.getCourse(searchParams);
			model.addAttribute("jc", jc);
		} catch (JobCodeException e) {
			model.addAttribute("error", e.getMessage());
		}

		return "viewjobcode";
	}

    @Override
    @RequestMapping(value = "/getForEdit/{id}", method = RequestMethod.GET)
    public @ResponseBody JobCode getCourseForEdit(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("error", "");
        model.addAttribute("message", "");
        JobCode jc = null;
        try {
            JobCodeSearchParams searchParams = new JobCodeSearchParams();
            searchParams.setId(id);
            jc = courseService.getCourse(searchParams);
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
    public String getCourseList(Model model) {

        model.addAttribute("error", "");
        model.addAttribute("message", "");

        try {

            List<JobCode> jcList = courseService.getCourseList();
            model.addAttribute("jcList", jcList);
        } catch (JobCodeException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "viewcourses";
    }

    @Override
    @RequestMapping(value = "/getListForUserJobcodeTraining", method = RequestMethod.GET)
    public @ResponseBody List<JobCode> getCourseListForUser(Model model) {

        model.addAttribute("error", "");
        model.addAttribute("message", "");
        List<JobCode> jcList = null;
        try {

        	jcList = courseService.getCourseList();
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
	public String deleteCourse(@PathVariable("id") Integer id, Model model) {
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
                
                courseService.deleteCourse(searchParams);
                
            }catch(JobCodeException e){
                model.addAttribute("error", e.getMessage());
            }
        }
        
        return "alertdeletejobcode";
    }

	@Override
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateCourse(JobCode jc, Model model) {

		model.addAttribute("error", "");
		model.addAttribute("message", "");

		try {
			JobCode updatedJC = courseService.updateCourse(jc);
			model.addAttribute("jc", updatedJC);
		} catch (JobCodeException e) {
			model.addAttribute("error", e.getMessage());
		}
		return jobCodeView;
	}

	@Override
	@RequestMapping(value = "/getAllPredefined", method = RequestMethod.GET)
	public @ResponseBody List<JobCode> getAllPredefinedCourses() {
		List<JobCode> jcList = null;
		try {
			jcList = courseService.getAllPredefinedCourses();
		} catch (JobCodeException e) {
			System.out.println(e);
		}

		return jcList;
	}


    // Method called when user clicks on Create New Category Button.
    // through javascript/ajax.
    @RequestMapping(value = "/createAjaxCategory", method = RequestMethod.POST, headers = { "Content-type=application/json" })
    public @ResponseBody Category ajaxCreateCategory(@RequestBody Category category) {

            Category savedCategory = null;
            try {
                savedCategory = courseService.createCategoryForCourse(category);
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
             updatedJC = courseService.updateCourse(jc);
        } catch (JobCodeException e) {
            //model.addAttribute("error", e.getMessage());
        }


        return updatedJC;
    }



	// Method called when user clicks on 'Create Job Code' button on create page
	// through javascript/ajax. Could not use the regular /create method
	// because, it was not easy to map
	// form data in create page to model attribute param.
	@RequestMapping(value = "/createCourse", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody JobCode ajaxCreateJobCode(@RequestBody JobCode jc) {

		JobCode res = null;
		System.out.println(jc);

		try {
			res = courseService.saveCourse(jc);
		} catch (JobCodeException e) {
			System.out.println(e);
			return null;
		}

		return res;
	}

	// called when menu item 'Create Job Code' is selected. This renders
	// createjobcode page.
	@Override
	@RequestMapping(value = "/showcreatecourse", method = RequestMethod.GET)
	public String showCreateCourse(Model model) {

		return "createcourse";
	}


    // called when menu item 'Edit Job Code' is selected. This renders
    // editjobcode page.
    @RequestMapping(value = "/showeditcourse/{id}", method = RequestMethod.GET)
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
	public String getDepartments(String jobCodeID, String parentCategoryID,
			Model model) {

		return jobCodeView;
	}


}
