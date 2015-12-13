package edu.sjsu.assess.dao;

import java.util.List;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.TrainingModule;
import edu.sjsu.assess.model.TrainingModuleSearchParams;

public interface TrainingModuleDAO {
	
	public TrainingModule getTrainingModuleByID(Integer tmID) throws DAOException;
	
	public List<Integer> getTrainingModuleList(TrainingModuleSearchParams searchParams) throws DAOException;
	
	public void deleteTrainingModuleByID(Integer tmID) throws DAOException;
	
	public void updateTrainingModuleByID(TrainingModule tm) throws DAOException;
	
	public TrainingModule createTrainingModule(TrainingModule tm) throws DAOException;
	
	public void createTrainingModuleCategories(Integer trainingModuleID, Integer categoryID, String content) throws DAOException;
	
	public void deleteTrainingModuleCategories(Integer trainingModuleID) throws DAOException;
	
	public void createTrainingModuleRecommendations(Integer userID, Integer jobCodeID, Integer categoryID) throws DAOException;
	
	public List<TrainingModule> getRecommendedTrainingModulesForUser(Integer userID) throws DAOException;
	
	public void deleteTrainingModuleRecommendation(Integer userID, Integer jobCodeID, Integer categoryID) throws DAOException;
	
	public void updateTrainingModuleStatus(Integer userID, Integer trainingModuleID, String status) throws DAOException;
	
}
