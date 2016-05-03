package edu.sjsu.assess.controller;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.sjsu.assess.model.Assignment;
import edu.sjsu.assess.model.AssignmentSearchParams;

/**
 * Controller Interface for Auto Correction
 * 
 * @author Preeti
 *
 */
public interface AutoCorrectionController {

	public String showViewAutoCorrection(String setCategoryId, Model model);
	
	public String getAssignment(Integer id, boolean isUpdate, Model model);
    
    public String getAssignmentsList(AssignmentSearchParams searchParams, Integer pageNo, Model model);

    public String createAssignment(Assignment assignment, Model model, RedirectAttributes redirectAttributes);

    public String deleteAssignment(Integer id, Model model);

    public String updateAssignment(Assignment assignment, Model model);

    public String getAssignmentsForLevel(String category, String level, Model model);

    public String getAllAssignmentsForCategory(String category, String testId,
           String testsetCategoryId, Model model);


    public String viewAssignmentsList(AssignmentSearchParams searchParams, Integer pageNo, Model model);

	public String viewAssignmentsTestList(AssignmentSearchParams searchParams, Integer pageNo, Model model);
	
}
