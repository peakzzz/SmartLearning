package edu.sjsu.assess.controller;


import edu.sjsu.assess.exception.TestSetAttemptException;
import edu.sjsu.assess.exception.TrainingModuleException;
import edu.sjsu.assess.model.TestSetAttempt;
import edu.sjsu.assess.model.TestSetAttemptSearchParams;
import edu.sjsu.assess.model.TestSetAttempt.CategoryWiseRecord;
import edu.sjsu.assess.model.TrainingModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import edu.sjsu.assess.service.*;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sindhu on 4/14/15.
 */


@Controller
@RequestMapping("/testattempt")
public class TestAttemptControllerImpl implements TestAttemptController{

	@Autowired
	private TestSetAttemptServiceImpl testSetAttemptService;

	@Autowired
	private TestSetServiceImpl testSetService;

	@Autowired
	private TrainingModuleServiceImpl trainingModuleService;

	@Override
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public String listTestAttempts(Model model) {
		TestSetAttemptSearchParams searchParams = new TestSetAttemptSearchParams();
		List<TestSetAttempt>  tsaList = null;

		try {
			tsaList = testSetAttemptService.getTestSetAttemptList(searchParams);
		} catch (TestSetAttemptException e) {
			e.printStackTrace();
		}

		if(tsaList != null && tsaList.size() > 0) {
			model.addAttribute("testAttemptList", tsaList);
		} else {
			model.addAttribute("error", "No search results");
		}

		return "listtestattmepts";
	}

	@Override
	@RequestMapping(value = "/recommendation", method = RequestMethod.GET)
	public String viewrecommendationlist(Model model)
	{

		TestSetAttemptSearchParams searchParams = new TestSetAttemptSearchParams();
		List<TestSetAttempt>  tsaList = null;

		try {
			tsaList = testSetAttemptService.getTestSetAttemptList(searchParams);
		} catch (TestSetAttemptException e) {
			e.printStackTrace();
		}

		if(tsaList != null && tsaList.size() > 0) {
			model.addAttribute("testAttemptList", tsaList);
		} else {
			model.addAttribute("error", "No search results");
		}
		return "viewrecommendationlist";
	}
	
	//Here calculating, checking which section was badly performed and then passing recommending section	
	@RequestMapping(value="/attempt/recommendation/{id}", method = RequestMethod.GET)
	public String loadRecommendationOfAttempt(@PathVariable("id") String attemptId, Model model) {

		try {
			TestSetAttemptSearchParams searchParams = new TestSetAttemptSearchParams();
			searchParams.setId(Integer.parseInt(attemptId));

			//Full testSetAttempt details for the clicked id
			List<TestSetAttempt> testSetAttempts = testSetAttemptService.getTestSetAttemptList(searchParams);

			TestSetAttempt testsetAttempt = testSetAttempts.get(0);


			List<CategoryWiseRecord> recordsList = testsetAttempt.getCategoryWiseRecords();
			List<Integer> weakCategoryIds = new ArrayList<Integer>();
//			List<String> recommendedTrainings = new ArrayList<String>();
//			List<String> categoryNames = new ArrayList<String>();
			Map<String,List<String>> linksandCategory = new HashMap<String,List<String>>();
			try {
				
				for(CategoryWiseRecord cwr: recordsList)
				{
					if(!cwr.isCutoffReached())
					{
						linksandCategory.put(cwr.getCategory().getTitle(),trainingModuleService.getRecommendedModuleLinks(cwr.getCategoryID()));
					}
				}

				trainingModuleService.getRecommendedTrainingModulesForUser();

				
			} catch (TrainingModuleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			model.addAttribute("testAttemptId", attemptId);
			if(testSetAttempts!=null && testSetAttempts.size()>0) {
				model.addAttribute("results", testSetAttempts.get(0));
			} else {
				model.addAttribute("error", "No search results");
			}
			
			if(null != linksandCategory && linksandCategory.size()>0)
				model.addAttribute("modules", linksandCategory);
//
//			if(null != categoryNames && categoryNames.size() >0)
//				model.addAttribute("categories", categoryNames);
		} catch (TestSetAttemptException e) {
			e.printStackTrace();
			model.addAttribute("error", "Error fetching results, try again later!");
		}
		return "viewRecommendationResults";
	}
	
	@RequestMapping(value="/attempt/results/{id}", method = RequestMethod.GET)
	public String loadResultsOfAttempt(@PathVariable("id") String attemptId, Model model) {

		try {
			TestSetAttemptSearchParams searchParams = new TestSetAttemptSearchParams();
			searchParams.setId(Integer.parseInt(attemptId));

			List<TestSetAttempt> testSetAttempts = testSetAttemptService.getTestSetAttemptList(searchParams);
			if(testSetAttempts!=null && testSetAttempts.size()>0) {
				model.addAttribute("results", testSetAttempts.get(0));
			} else {
				model.addAttribute("error", "No search results");
			}

		} catch (TestSetAttemptException e) {
			e.printStackTrace();
			model.addAttribute("error", "Error fetching results, try again later!");
		}
		return "viewAttemptResults";
	}
}
