package edu.sjsu.assess.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.sjsu.assess.exception.TrainingModuleException;
import edu.sjsu.assess.model.TrainingModule;
import edu.sjsu.assess.model.TrainingModuleSearchParams;

/**
 * Training Module Service Interface
 * 
 * @author Shefali
 *
 */
@Service
public interface TrainingModuleService {

	public TrainingModule saveTrainingModule(TrainingModule tm)
			throws TrainingModuleException;

	public List<TrainingModule> getTrainingModuleList(
			TrainingModuleSearchParams searchParams)
			throws TrainingModuleException;

	public void deleteTrainingModule(TrainingModuleSearchParams searchParams)
			throws TrainingModuleException;

	public TrainingModule updateTrainingModule(TrainingModule tm)
			throws TrainingModuleException;

	public List<TrainingModule> getRecommendedTrainingModulesForUser()
			throws TrainingModuleException;

	public void completeTrainingModule(Integer traningModuleID) throws TrainingModuleException;
	
	public List<String> getRecommendedModuleLinks(Integer categoryID) throws TrainingModuleException;

}
