package edu.sjsu.assess.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.assess.dao.TrainingModuleDAOImpl;
import edu.sjsu.assess.dao.UserDAOImpl;
import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.exception.TrainingModuleException;
import edu.sjsu.assess.model.TrainingModule;
import edu.sjsu.assess.model.TrainingModuleSearchParams;
import edu.sjsu.assess.model.User;
import edu.sjsu.assess.util.Utility;

@Service
public class TrainingModuleServiceImpl implements TrainingModuleService {

	@Autowired
	private TrainingModuleDAOImpl trainingModuleDAO;

	@Autowired
	private UserDAOImpl userDAO;

	@Override
	public TrainingModule saveTrainingModule(TrainingModule tm)
			throws TrainingModuleException {
		TrainingModule newTM = null;
		try {

			String userName = Utility.getLoggedInUserName();
			User user = userDAO.getUserByLogin(userName);
			tm.setUserID(user.getId());

			newTM = trainingModuleDAO.createTrainingModule(tm);

			// Create entries in TrainingModuleCategories table
			this.createTrainingModuleCategories(newTM);

		} catch (DAOException e) {
			throw new TrainingModuleException(
					"Failed to create training module.", e);
		}

		return newTM;
	}

	// @Override
	// public List<TrainingModule> getTrainingModule(
	// TrainingModuleSearchParams searchParams)
	// throws TrainingModuleException {
	//
	// List<TrainingModule> tmList = null;
	//
	// try {
	// tmList = trainingModuleDAO.getTrainingModuleByID(tmID);
	// } catch (DAOException e) {
	// throw new TrainingModuleException("Failed to get training module.", e);
	// }
	//
	// return tmList;
	// }

	@Override
	public List<TrainingModule> getTrainingModuleList(
			TrainingModuleSearchParams searchParams)
			throws TrainingModuleException {

		List<TrainingModule> tmList = new ArrayList<TrainingModule>();

		try {
			// String userName = Utility.getLoggedInUserName();
			// User user = userDAO.getUserByLogin(userName);

			List<Integer> trainingModuleIDList = trainingModuleDAO
					.getTrainingModuleList(searchParams);

			for (Integer trainingModuleID : trainingModuleIDList) {
				tmList.add(trainingModuleDAO
						.getTrainingModuleByID(trainingModuleID));
			}

		} catch (DAOException e) {
			throw new TrainingModuleException("Failed to get training module.",
					e);
		}

		return tmList;
	}

	@Override
	public void deleteTrainingModule(TrainingModuleSearchParams searchParams)
			throws TrainingModuleException {

		Integer tmID = searchParams.getId();
		try {

			trainingModuleDAO.deleteTrainingModuleCategories(tmID);

			trainingModuleDAO.deleteTrainingModuleByID(tmID);

		} catch (DAOException e) {
			throw new TrainingModuleException(
					"Failed to delete training module.", e);
		}
	}

	@Override
	public TrainingModule updateTrainingModule(TrainingModule tm)
			throws TrainingModuleException {

		try {
			trainingModuleDAO.updateTrainingModuleByID(tm);

			trainingModuleDAO.deleteTrainingModuleCategories(tm.getId());

			this.createTrainingModuleCategories(tm);

		} catch (DAOException e) {
			throw new TrainingModuleException(
					"Failed to update training module.", e);
		}
		return tm;
	}

	/**
	 * This method creates the mapping of training module & categories.
	 * 
	 * @param tm
	 */
	private void createTrainingModuleCategories(TrainingModule tm) {

		Map<Integer, String> categoriesContent = tm.getCategoriesContent();

		try {
			for (Integer categoryID : categoriesContent.keySet()) {
				trainingModuleDAO.createTrainingModuleCategories(tm.getId(),
						categoryID, categoriesContent.get(categoryID));
			}
		} catch (Exception e) {
			return;
		}
	}

	@Override
	public List<TrainingModule> getRecommendedTrainingModulesForUser()
			throws TrainingModuleException {
		List<TrainingModule> recommendedTrainingModules = null;

		try {
			String login = Utility.getLoggedInUserName();
			User loggedInUser = userDAO.getUserByLogin(login);
			recommendedTrainingModules = trainingModuleDAO
					.getRecommendedTrainingModulesForUser(loggedInUser.getId());

		} catch (DAOException e) {
			throw new TrainingModuleException(
					"Failed to get recommended training modules.", e);
		}

		return recommendedTrainingModules;
	}
	
	@Override
	public List<String> getRecommendedModuleLinks(Integer categoryId)
			throws TrainingModuleException
	{
		List<String> tmlist = null;
		try {
			tmlist = trainingModuleDAO.getTrainingModuleByCategoryID(categoryId);
			System.out.println("Trainingmoduleserviceimpl delte this ln 174 \n");
			for(String tm : tmlist)
			{
				System.out.println("delete this trainingmodule file name  :"+tm);
			}
		} catch (DAOException e) {
			throw new TrainingModuleException(
					"Failed to get recommended training modules.", e);
		}
		return tmlist;
	}

	@Override
	public void completeTrainingModule(Integer traningModuleID)
			throws TrainingModuleException {

		try {
			String login = Utility.getLoggedInUserName();
			User loggedInUser = userDAO.getUserByLogin(login);
			
			trainingModuleDAO.updateTrainingModuleStatus(loggedInUser.getId(),
					traningModuleID,
					TrainingModule.CompletionStatus.COMPLETE.getValue());

		} catch (DAOException e) {
			throw new TrainingModuleException(
					"Failed to get update the status of training module to complete in DB.", e);
		}
	}

}
