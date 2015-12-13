package edu.sjsu.assess.controller;

import org.springframework.ui.Model;

import edu.sjsu.assess.model.TrainingModule;
import edu.sjsu.assess.model.TrainingModuleSearchParams;

/**
 * Controller Interface for Training Module
 */
public interface TrainingModuleController {
	public String createTrainingModule(TrainingModule tm, Model model);
	
	public String getTrainingProgress(Model model);
	
	public String getTrainingModules(TrainingModuleSearchParams searchParam, Model model);
	
	public String deleteTrainingModule(String id, Model model);
	
	public String updateTrainingModule(TrainingModule tm, Model model);
	
	public String getTraining(Model model);
	
	public String completeTrainingModule(String id, Model model);
	
	
}
