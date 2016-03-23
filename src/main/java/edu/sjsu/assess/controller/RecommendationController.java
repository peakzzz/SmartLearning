package edu.sjsu.assess.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

/**
 * Recommendation system interface
 * 
 * @author Niveditha
 *
 */
public interface RecommendationController {

	public String downloadFile(HttpServletResponse response, Model model,String moduleName, String fileName);
	
}
