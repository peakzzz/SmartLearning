package edu.sjsu.assess.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.sjsu.assess.model.StudentProject;



public interface ProjectController {
	public String loadCreateProject(Model model);
	public String createProject(StudentProject studentProject, Model model, RedirectAttributes redirectAttributes);
	public String viewProjects(Model model);
	public String viewProject(@PathVariable("id") Integer id, @RequestParam(value="update", required=false) boolean isUpdate, Model model);
}
