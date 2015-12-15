package edu.sjsu.assess.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import edu.sjsu.assess.model.Category;
import edu.sjsu.assess.model.JobCode;

/**
 * Controller Interface for Job Code
 * 
 * @author Niveditha
 *
 */
public interface CourseController {
	public String createCourse(JobCode jc, Model model);

	public String getCourse(Integer id, Model model);

	public String getCourseList(Model model);
	
	public List<JobCode> getCourseListForUser(Model model);
	
    public JobCode getCourseForEdit(Integer id, Model model);

    //public String deleteJobCode(JobCodeSearchParams searchParams, Model model);
    public String deleteCourse(Integer id, Model model);
	
	public String updateCourse(JobCode jc, Model model);

	// Removed this signature, as I am not using Model. Also it has to return
	// list of JobCodes to Ajax call from the view
	// public String getAllPredefinedJobCodes(Model model);

	public String getDepartments(String jobCodeID, String parentCategoryID,
			Model model);

	public List<JobCode> getAllPredefinedCourses();

	public String showCreateCourse(Model model);

	public String createDepartment(@ModelAttribute("search") Category category, Model model);
}
