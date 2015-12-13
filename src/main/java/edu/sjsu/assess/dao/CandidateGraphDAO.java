package edu.sjsu.assess.dao;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.CandidateGraphData.AverageHolder;
import edu.sjsu.assess.model.CandidateGraphData.DomainWisePerformance;
import edu.sjsu.assess.model.CandidateGraphData.EffortsDevoted;
import edu.sjsu.assess.model.TrainingModuleGraphData.JobCodeModule;

public interface CandidateGraphDAO {
	
	public AverageHolder getFrequencyAttemptData(Integer userID) throws DAOException;
	
	public AverageHolder getPerformanceProgression(Integer userID) throws DAOException;
	
	public EffortsDevoted getEffortsDevoted(Integer userID) throws DAOException;
	
	public DomainWisePerformance getDomainWisePerformance(Integer userID) throws DAOException;
	
	
	public JobCodeModule getJobcodeTrainingData(Integer userID) throws DAOException;

}
