package edu.sjsu.assess.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import edu.sjsu.assess.model.Category;
import edu.sjsu.assess.model.JobCode;

/**
 * Controller Interface for Job Code
 * 
 * @author Shefali
 *
 */
public interface JobCodeController {
	public String createJobCode(JobCode jc, Model model);

	public String getJobCode(Integer id, Model model);

	public String getJobCodeList(Model model);
	
	public List<JobCode> getJobCodeListForUser(Model model);
	
    public JobCode getJobCodeForEdit(Integer id, Model model);

    //public String deleteJobCode(JobCodeSearchParams searchParams, Model model);
    public String deleteJobCode(Integer id, Model model);
	
	public String updateJobcode(JobCode jc, Model model);

	// Removed this signature, as I am not using Model. Also it has to return
	// list of JobCodes to Ajax call from the view
	// public String getAllPredefinedJobCodes(Model model);

	public String getCategories(String jobCodeID, String parentCategoryID,
			Model model);

	public List<JobCode> getAllPredefinedJobCodes();

	public String showCreateJobCode(Model model);

	public String createCategory(@ModelAttribute("search") Category category, Model model);
}
