package edu.sjsu.assess.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.sjsu.assess.exception.QuestionException;

import java.util.HashSet;
import java.util.Set;

import edu.sjsu.assess.exception.JobCodeException;
import edu.sjsu.assess.service.Pagination;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import edu.sjsu.assess.exception.TestSetException;
import edu.sjsu.assess.model.Assignment;
import edu.sjsu.assess.model.AssignmentSearchParams;
import edu.sjsu.assess.model.Category;
import edu.sjsu.assess.model.Question;
import edu.sjsu.assess.model.TestSet;
import edu.sjsu.assess.model.TestSetCategory;
import edu.sjsu.assess.model.TestSetSearchParams;
import edu.sjsu.assess.service.AssignmentServiceImpl;
import edu.sjsu.assess.service.CategoryServiceImpl;
import edu.sjsu.assess.service.TestSetServiceImpl;
import edu.sjsu.assess.util.Utility;

@Controller
@RequestMapping("/autocorrection")
public class AutoCorrectionControllerImpl implements AutoCorrectionController {

		@Autowired
	    private AssignmentServiceImpl assignmentService;

	    @Autowired
	    private CategoryServiceImpl categoryService;

	    @Autowired
	    private TestSetServiceImpl testSetService;

	    // called when menu item 'Attempt Test' is selected. This renders
	 	// viewautocorrection page.
	    
	 	@Override
	 	@RequestMapping(value = "/createProgram", method = RequestMethod.GET)
	 	public String showViewAutoCorrection(String setCategoryId, Model model){
	 		Assignment assignment = new Assignment();
	 		System.out.println("Autocorrectioncontrollerimpl   :"+setCategoryId);
 	        model.addAttribute("assignment", assignment);
 	        model.addAttribute("categories", getAllCategories());
	 		return "viewautocorrection";
	 	}
	    
//	    @Override
//	 	@RequestMapping(value = "/createProgram", method = RequestMethod.GET)
//	 	public String showViewAutoCorrection(String setCategoryId, Model model){
//	 		Question question = new Question();
// 	        model.addAttribute("question", question);
// 	        model.addAttribute("categories", getAllCategories());
//	 		return "viewautocorrection";
//	 	}


 	    @Override
 	    @RequestMapping(value="/{id}", method = RequestMethod.GET)
 	    public String getAssignment(@PathVariable("id") Integer id, @RequestParam(value="update", required=false) boolean isUpdate, Model model)
 	    {
 	    	model.addAttribute("error", "");
 	        model.addAttribute("message", "");
 	        List<Assignment> qsList = new ArrayList<>();
 	        try{
 	        	AssignmentSearchParams searchParams = new AssignmentSearchParams();
 	        	searchParams.setId(id);
 	            qsList = assignmentService.getAssignmentsList(searchParams);
 	        }
 	        catch(QuestionException e){
 	            model.addAttribute("error", e.getMessage());
 	        }
 	        if(qsList != null && qsList.size() > 0){
 	            if(isUpdate) {
 	                model.addAttribute("assignment", qsList.get(0));
 	                return "updateQuestion";
 	            }
 	            model.addAttribute("assignments", qsList);
 	        }

 	        return "viewAssignment";
 	    }

 	    @Override
 	    @RequestMapping(value="/viewProgram", method = RequestMethod.GET)
 	    public String getAssignmentsList(AssignmentSearchParams searchParams,
 	            @RequestParam(value="page", required = false) Integer pageNo, Model model){
 	        System.out.println("Searching assignments");
 	        model.addAttribute("error", "");
 	        model.addAttribute("message", "");
 	        double totalpages = 1;
 	        if(pageNo==null) {
 	            pageNo = 1;
 	        }
 	        try{
 	            List<Assignment> qsList = assignmentService.searchAssignments(searchParams);
 	            if(qsList!=null && qsList.size() > 25) {
 	                Pagination paginationSvc = new Pagination();
 	                qsList = (List<Assignment>) paginationSvc.getResultsForPage(qsList, pageNo);
 	                totalpages = paginationSvc.getTotalPages();
 	            }
 	            model.addAttribute("results", qsList);
 	            model.addAttribute("page", pageNo);
 	            model.addAttribute("totalpages", totalpages);
 	        }
 	        catch(QuestionException e){
 	            model.addAttribute("error", e.getMessage());
 	        }

 	        model.addAttribute("categories", getAllCategories());
 	        model.addAttribute("searchParam", searchParams);
 	        return "searchAssignments";
 	    }


 	   @Override
 	    @RequestMapping(method = RequestMethod.POST)
		public String createAssignment(Assignment assignment, Model model, RedirectAttributes redirectAttributes)
 	    {
 	    	Assignment savedQS = null;
 	        model.addAttribute("error", "");
 	        model.addAttribute("message", "");

 	        if (assignment == null) {
 	            model.addAttribute("error", "Null Assignment Object!");
 	        }
 	        else {
 	           try {
 	                savedQS = assignmentService.saveAssignment(assignment);
 	                model.addAttribute("qs", savedQS);
 	                model.addAttribute("message",
 	                        "Assignment saved successfully.");
 	               redirectAttributes.addAttribute("message",
 	                       "Assignment saved successfully.");
 	            } catch (QuestionException e) {
 	                model.addAttribute("error", e.getMessage());
 	               redirectAttributes.addAttribute("error", e.getMessage());
 	            }
 	        }
 	        List<Assignment> assignments = new ArrayList<Assignment>();
 	       assignments.add(assignment);

 	        model.addAttribute("assignments", assignments);
 	       return "redirect:autocorrection/"+assignment.getId();
 	    }

 	    @Override
 	    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
 	    public String deleteAssignment(Integer id, Model model)
 	    {
 	    	model.addAttribute("error", "");
 	        model.addAttribute("message", "");
 	        
 	        if(id == null){
 	            model.addAttribute("error", "Search parameters missing!");
 	        }
 	        else{
 	        	AssignmentSearchParams searchParams = new AssignmentSearchParams();
 	            searchParams.setId(id);
 	            
 	            try{
 	                assignmentService.deleteAssignment(searchParams);
 	            }catch(QuestionException e){
 	                model.addAttribute("error", e.getMessage());
 	            }
 	        }
 	        return "assignments";
 	    }

 	    @Override
 	    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
 	    public String updateAssignment(Assignment assignment, Model model)
 	    {
 	        List<Assignment> assignments = new ArrayList<>();
 	    	model.addAttribute("error", "");
 	        model.addAttribute("message", "");

 	        try{
 	           assignmentService.updateAssignment(assignment);
 	           assignments.add(assignment);
 	           model.addAttribute("assignments", assignments);
 	        }catch(QuestionException e){
 	           model.addAttribute("error", e.getMessage());
 	        }
 	        return "viewAssignment";
 	    }

 	    @Override
 	    @RequestMapping(value="/category/{categoryId}/level/{level}", method = RequestMethod.GET)
 	    public String getAssignmentsForLevel(@PathVariable("categoryId") String categoryId, @PathVariable("level") String level, Model model)
 	    {
 	        List<Assignment> assignmentList = new ArrayList<>();
 	        AssignmentSearchParams searchParams = new AssignmentSearchParams();
 	        searchParams.setCategoryID(Integer.parseInt(categoryId));
 	        searchParams.setLevel(level);

 	        try {
 	            assignmentList = assignmentService.getAssignmentsList(searchParams);
 	            if(assignmentList!=null && assignmentList.size()>0) {
 	                model.addAttribute("assignmentList", assignmentList);
 	            }
 	            else {
 	                model.addAttribute("error", "No assignments found");
 	            }
 	        } catch (QuestionException e) {
 	            model.addAttribute("error", e.getMessage());
 	        }

 	        return "assignmentList";
 	    }


 	    @Override
 	    @RequestMapping(value="/category/{categoryId}", method = RequestMethod.GET)
 	    public String getAllAssignmentsForCategory(@PathVariable("categoryId") String categoryId,
 	            @RequestParam(value="testsetId", required = false) String testId, @RequestParam(value="testsetCategoryId", required = false) String categorySetId, Model model)
 	    {
 	        List<Assignment> assignmentList = new ArrayList<>();
 	        AssignmentSearchParams searchParams = new AssignmentSearchParams();
 	        searchParams.setCategoryID(Integer.parseInt(categoryId));

 	        try {
 	            assignmentList = assignmentService.getAssignmentsList(searchParams);

 	            if(assignmentList!=null && assignmentList.size()>0) {
 	                model.addAttribute("assignmentList", assignmentList);
 	            }
 	            else {
 	                model.addAttribute("error", "No assignments found");
 	            }
 	        } catch (QuestionException e) {
 	            model.addAttribute("error", e.getMessage());
 	        }
 	        if(testId!=null)
 	            model.addAttribute("testId", testId);
 	        if(categorySetId!=null)
 	            model.addAttribute("testsetCategoryId", categorySetId);

 	        return "assignmentList";
 	    }

 	    @RequestMapping(value = "/newassignment/{testsetId}/{setCategoryId}", method = RequestMethod.GET)
 	    public String loadAssignmentForNewAssignment(@PathVariable(value="testsetId") String testsetId,
 	            @PathVariable(value="setCategoryId") String setCategoryId, Model model) {
 	        Assignment assignment = new Assignment();
 	        model.addAttribute("assignment", assignment);
 	        Set<Category> categories = new HashSet<>();

 	        if(Utility.isValidInteger(testsetId)
 	                && Utility.isValidInteger(setCategoryId)) {
 	            try {
 	                TestSet testset = testSetService.getTestsetById(Integer.parseInt(testsetId));
 	                categories.addAll(getCategoriesForJobCode(testset.getJobCodeID(), null));
 	                for(TestSetCategory setCategory: testset.getTestSetCategories()) {
 	                    if(setCategory.getId()==Integer.parseInt(setCategoryId)) {
 	                        if(setCategory.getCategory()!=null) {
 	                            categories.add(setCategory.getCategory());
 	                            if(setCategory.getCategory().getSubCategories()!=null) {
 	                                categories.addAll(setCategory.getCategory().getSubCategories());
 	                            }
 	                        }
 	                        assignment.setCategoryID(setCategory.getCategoryID());
 	                    }
 	                }
 	            }
 	            catch (TestSetException e) {
 	                e.printStackTrace();
 	            }
 	            catch (JobCodeException e) {
 	                e.printStackTrace();
 	            }
 	            if(categories.size()==0) {
 	                    categories.addAll(getAllCategories());
 	            }
 	            model.addAttribute("categories", categories);
 	            model.addAttribute("testsetId", testsetId);
 	            model.addAttribute("setCategoryId", setCategoryId);
 	        }
 	        return "viewautocorrection";
 	    }

 	    // TODO: add information about testset & setcategory to add question page
 	    private Model loadTestsetData(int testsetId, int setCategoryId, Model model) {
 	        TestSetSearchParams searchParams = new TestSetSearchParams();
 	        searchParams.setId(testsetId);
 	        try {
 	            List<TestSet> testSetList = testSetService.getTestSetList(searchParams);
 	            if(testSetList!=null && testSetList.size()>0) {
 	                TestSet testset = testSetList.get(0);
 	                TestSetCategory setCategory = null;
 	                for(TestSetCategory set: testset.getTestSetCategories()) {
 	                    if(set.getId()==setCategoryId) {
 	                        model.addAttribute("setCategory", set);
 	                        set.setAssignmentList(new ArrayList<Assignment>());
 	                    }
 	                }
 	                testset.setTestSetCategories(new ArrayList<TestSetCategory>());
 	                model.addAttribute("testset", testset);
 	            }
 	        }
 	        catch (TestSetException e) {
 	            e.printStackTrace();
 	        }
 	        return model;
 	    }

 	    private List<Category> getAllCategories() {
 	        return categoryService.getAllPredefinedCategories();
 	    }

 	    private List<Category> getCategoriesForJobCode(int jobcodeId, Integer parentCategoryId)
 	    throws JobCodeException
 	    {
 	        return categoryService.getCategoriesForJobCode(jobcodeId, parentCategoryId);

 	    }


}
