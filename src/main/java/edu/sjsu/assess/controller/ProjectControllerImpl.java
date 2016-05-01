package edu.sjsu.assess.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.sjsu.assess.exception.DiscussionForumException;
import edu.sjsu.assess.exception.ProjectException;
import edu.sjsu.assess.model.ForumPost;
import edu.sjsu.assess.model.ForumReply;
import edu.sjsu.assess.model.StudentProject;
import edu.sjsu.assess.service.ProjectServiceImpl;
import edu.sjsu.assess.service.UserServiceImpl;

@Controller
@RequestMapping("/project")
public class ProjectControllerImpl implements ProjectController{
	
	@Autowired
    private ProjectServiceImpl projectService;
	@Autowired
	private UserServiceImpl userService;
	/*method to return a page to create a project*/
    @Override
    @RequestMapping(value = "/create", method = RequestMethod.GET)
	public String loadCreateProject(Model model) {
    	 StudentProject studentProject = new StudentProject();
         model.addAttribute("studentProject", studentProject);
         System.out.println("Hi in loadCreateProject method");
         return "createProject";
	}

    /*method to create a project by the user
	 * invoked when user clicks on create project button*/
	@Override
	@RequestMapping(value="/createProject", method = RequestMethod.POST)
	public String createProject(StudentProject studentProject, Model model,
			RedirectAttributes redirectAttributes) {
		System.out.println("Hi in createProject method");
		StudentProject savedstudentProject = null;
		model.addAttribute("error", "");
        model.addAttribute("message", "");
        
        if (studentProject == null) {
            model.addAttribute("error", "Null studentProject Object!");
        }
        else { 
           try {
        	    System.out.println("Hi in controller createProject method: "+studentProject.getDescription());
        	    savedstudentProject = projectService.saveProject(studentProject);
                model.addAttribute("savedstudentProject", savedstudentProject);
                model.addAttribute("message",
                        "Project saved successfully.");
                redirectAttributes.addAttribute("message", "Project saved successfully.");
             } catch (ProjectException e) {
                 model.addAttribute("error", e.getMessage());
                redirectAttributes.addAttribute("error", e.getMessage());
             }
        }
        List<StudentProject> studentProjects = new ArrayList<StudentProject>();
        studentProjects.add(studentProject);
        System.out.println("Id of studentproject :"+studentProject.getId());
        model.addAttribute("studentProjects", studentProjects);
        return "redirect:get/"+studentProject.getId();
	}

	/*method to view all the projects*/
	@Override
	@RequestMapping(value="/viewProjects", method = RequestMethod.GET)
	public String viewProjects(Model model) {
		System.out.println("Hi in viewProjects method");
		model.addAttribute("error", "");
        model.addAttribute("message", "");
        try{
            List<StudentProject> studentProjects = projectService.getProjects();
            System.out.println("viewing projects:" + studentProjects.get(0).getId());           
            model.addAttribute("studentProjects",studentProjects);
            
            //model.addAttribute("jsonForumPosts"+jsonForumPosts);
            
        }
        catch(ProjectException e){
            model.addAttribute("error", e.getMessage());
        }
		return "viewProjects";
	}
	
	/*method to view particular project*/
	@Override
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public String viewProject(@PathVariable("id") Integer id, boolean isUpdate, Model model) {
		model.addAttribute("error", "");
		model.addAttribute("message", "");
		String userRole  = "";
		try {
			System.out.println("inside viewProject by id="+id);
			StudentProject studentProject = projectService.getProjectByID(id);
			model.addAttribute("studentProject", studentProject);
			userRole = projectService.getRole();
			System.out.println("userRole inside viewProject controller="+userRole);
			//List<ForumReply> forumreplys = projectService.getReplys(id);
	           // System.out.println("viewing posts:" + forumPosts.get(0).getUserID());           
	       // model.addAttribute("forumReplys",forumreplys);
	         
		} catch (ProjectException e) {
			model.addAttribute("error", e.getMessage());
		}
		System.out.println("userRole after try catch viewProject controller="+userRole);
		if(userRole.equals("admin")){
			System.out.println("inside if block="+userRole);
			return "viewProject";
		}
		else{
			System.out.println("inside else block="+userRole);
			return "viewProjectStudent";
		}
	}

	

}
