package edu.sjsu.assess.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import edu.sjsu.assess.exception.TrainingModuleException;
import edu.sjsu.assess.model.TrainingModule;
import edu.sjsu.assess.model.TrainingModuleSearchParams;
import edu.sjsu.assess.service.JobCodeServiceImpl;
import edu.sjsu.assess.service.TrainingModuleServiceImpl;
import edu.sjsu.assess.util.Utility;
import edu.sjsu.assess.model.User;
import edu.sjsu.assess.model.UserSearchParams;
import edu.sjsu.assess.exception.UserException;
import edu.sjsu.assess.service.UserServiceImpl;

@Controller
@RequestMapping("/trainingModule")
public class TrainingModuleControllerImpl implements TrainingModuleController {

    @Autowired
    private TrainingModuleServiceImpl trainingModuleService;
    
    @Autowired
    private JobCodeServiceImpl jobCodeService;
    
    @Autowired
	private UserServiceImpl userService;

    @Override
    @RequestMapping(value = "/create", method = RequestMethod.POST,consumes="application/json")
    public String createTrainingModule(
    		@RequestBody TrainingModule tm, Model model) {
    	System.out.println("In the trainig module controller for create !!");
    	System.out.println(tm.getJobCodeID());
    	System.out.println(tm.getContent());
    	System.out.println(tm.getFocus());
    	System.out.println(tm.getTitle());
    	System.out.println("size of the map "+tm.getCategoriesContent().size());
    	
    	Map<Integer, String> categoriesContent = tm.getCategoriesContent();
    	for(Integer categoryID : categoriesContent.keySet()) {
			System.out.println("categoryId"+categoryID);
			System.out.println("content is -->"+categoriesContent.get(categoryID));
    	}
    	
        TrainingModule savedTM = null;
        model.addAttribute("error", "");
        model.addAttribute("message", "");

        if (tm == null) {
            model.addAttribute("error", "Null TrainingModule Object!");
        }
        else {

            try {
                savedTM = trainingModuleService.saveTrainingModule(tm);
                model.addAttribute("tm", savedTM);
                model.addAttribute("message",
                        "Training Module saved successfully.");
            } catch (TrainingModuleException e) {
                model.addAttribute("error", e.getMessage());
            }
        }

        return "createTraining";
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/getTrainingModuleDetails", method = RequestMethod.POST, consumes="application/json")
    public String getTrainingModules(@RequestBody TrainingModuleSearchParams searchParams,Model model) {
        System.out.println("Getting the details for the training modules");
    	System.out.println("jobcode id : "+searchParams.getJobcodeID());
    	System.out.println("category id: "+searchParams.getCategoryID());
    	System.out.println("focus "+searchParams.getFocus());
    	System.out.println("level "+searchParams.getLevel());
        model.addAttribute("error", "");
        model.addAttribute("message", "");
        try{
        	System.out.println("about to hit the API");
            List<TrainingModule> tmList = trainingModuleService.getTrainingModuleList(searchParams);
            System.out.println("Number of training modules are : -->"+tmList.size());
            for(int i=0;i<tmList.size();i++)
            {
            	System.out.println(tmList.get(i).getTitle());
            	System.out.println("Map size for categories content is :"+tmList.get(i).getCategoriesContent().size());
            }
            model.addAttribute("tmList", tmList);
            
        } 
        catch(Exception e){
            model.addAttribute("error", e.getMessage());
        }
        User loggedInUser = null;
		
		try{
			String userName = Utility.getLoggedInUserName();
			UserSearchParams searchParams1 = new UserSearchParams();
			searchParams1.setLogin(userName);
			loggedInUser = userService.getUser(searchParams1);
		} catch(UserException ue){
			return "login";
		}
        
        
        
        
        
        if(loggedInUser.isAdmin()){
			System.out.println("The user is admin --------------->>>");
			return "viewtraining";
		}
		else{
			System.out.println("The user is candidate --------------->>>");
			return "viewcandidatetraining";
		}
    }
    
    
    @RequestMapping(value = "/getProgress", method = RequestMethod.GET)
    public String getTrainingProgress(
            Model model) {
        
        model.addAttribute("error", "");
        model.addAttribute("message", "");
//        model.addAttribute("trainingmodule", trainingModule);
//        try{
//            List<TrainingModule> tmList = trainingModuleService.getTrainingModuleList(searchParams);
//            model.addAttribute("tmList", tmList);
//        } 
//        catch(TrainingModuleException e){
//            model.addAttribute("error", e.getMessage());
//        }
        
        return "trainingDashboard";
    }
    
    
    
    
    @RequestMapping(value = "/createTraining", method = RequestMethod.GET)
    public String displayTrainings(TrainingModuleSearchParams searchParams,
            Model model) {
        
        model.addAttribute("error", "");
        model.addAttribute("message", "");
        TrainingModule trainingModule = new TrainingModule();
        model.addAttribute("trainingmodule", trainingModule);
//        try{
//            List<TrainingModule> tmList = trainingModuleService.getTrainingModuleList(searchParams);
//            model.addAttribute("tmList", tmList);
//        } 
//        catch(TrainingModuleException e){
//            model.addAttribute("error", e.getMessage());
//        }
        
        return "createTraining";
    }
   
    
    @Override
    @RequestMapping(value = "/getTraining", method = RequestMethod.GET)
    public String getTraining(
            Model model) {
        System.out.println("It came in the training module !!");
        model.addAttribute("error", "");
        model.addAttribute("message", "");
 
        User loggedInUser = null;
		
		try{
			String userName = Utility.getLoggedInUserName();
			UserSearchParams searchParams = new UserSearchParams();
			searchParams.setLogin(userName);
			loggedInUser = userService.getUser(searchParams);
		} catch(UserException ue){
			return "login";
		}
		
		if(loggedInUser.isAdmin()){
			System.out.println("The user is admin --------------->>>");
			return "viewtraining";
		}
		else{
			System.out.println("The user is candidate --------------->>>");
			return "viewcandidatetraining";
		}
        
        
    }
    
    
    @Override
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String deleteTrainingModule(@RequestParam(value="id") String id,
            Model model) {
        model.addAttribute("error", "");
        model.addAttribute("message", "");
        
        if(id == null){
            model.addAttribute("error", "Search parameters missing!");
        }
        else{
            try{
            	TrainingModuleSearchParams searchParams=new TrainingModuleSearchParams();
            	searchParams.setId(Integer.parseInt(id));
            	
                trainingModuleService.deleteTrainingModule(searchParams);
            }catch(TrainingModuleException e){
                model.addAttribute("error", e.getMessage());
            }
        }
        
        return "viewtraining";
    }

    @Override
    @RequestMapping(value = "/update", method = RequestMethod.POST,consumes="application/json")
    public String updateTrainingModule(@RequestBody TrainingModule tm, Model model) {
        model.addAttribute("error", "");
        model.addAttribute("message", "");
        
        System.out.println("In the trainig module controller for updation------->>> !!");
    	System.out.println(tm.getJobCodeID());
    	System.out.println(tm.getContent());
    	System.out.println(tm.getFocus());
    	System.out.println(tm.getTitle());
    	System.out.println("size of the map "+tm.getCategoriesContent().size());
    	
        
        
        try{
            TrainingModule updatedTM = trainingModuleService.updateTrainingModule(tm);
            model.addAttribute("tm", updatedTM);
        }catch(TrainingModuleException e){
            model.addAttribute("error", e.getMessage());
        }
        return "viewtraining";
    }
    
    @Override
    @RequestMapping(value = "/completeTraining", method = RequestMethod.POST,consumes="application/json")
    public String completeTrainingModule(@RequestParam(value="id") String id, Model model) {
        model.addAttribute("error", "");
        model.addAttribute("message", "");
        
        try{
        	Integer intgr = Integer.valueOf(id);
            trainingModuleService.completeTrainingModule(intgr);
        }catch(TrainingModuleException e){
            model.addAttribute("error", e.getMessage());
        }
        return "viewcandidatetraining";
    }
    
    
}
