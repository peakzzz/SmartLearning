package edu.sjsu.assess.service;

import edu.sjsu.assess.model.CandidateGraphData;
import edu.sjsu.assess.model.TrainingModuleGraphData.JobCodeModule;


public interface CandidateGraphService {
	public CandidateGraphData getCandidateGrapthData();
	
	
	public  JobCodeModule getJobCodeTrainingModule();
}
