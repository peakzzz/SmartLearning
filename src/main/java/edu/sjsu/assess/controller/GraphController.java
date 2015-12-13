package edu.sjsu.assess.controller;

import org.springframework.ui.Model;

import edu.sjsu.assess.model.AdminGraphData;
import edu.sjsu.assess.model.CandidateGraphData;
import edu.sjsu.assess.model.TrainingModuleGraphData;

public interface GraphController {
	AdminGraphData getAdminGrapthData(Model model);
	CandidateGraphData getCandidateGrapthData(Model model);
	
	TrainingModuleGraphData getTrainingData(Model model);
	
}
