package edu.sjsu.assess.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.assess.dao.CandidateGraphDAOImpl;
import edu.sjsu.assess.dao.UserDAOImpl;
import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.CandidateGraphData;
import edu.sjsu.assess.model.CandidateGraphData.AverageHolder;
import edu.sjsu.assess.model.CandidateGraphData.DomainWisePerformance;
import edu.sjsu.assess.model.CandidateGraphData.EffortsDevoted;
import edu.sjsu.assess.model.TrainingModuleGraphData;
import edu.sjsu.assess.model.TrainingModuleGraphData.JobCodeModule;
import edu.sjsu.assess.model.User;
import edu.sjsu.assess.util.Utility;

@Service
public class CandidateGraphServiceImpl implements CandidateGraphService{
	
	@Autowired
	CandidateGraphDAOImpl candidateGraphDAO;
	
	@Autowired
	UserDAOImpl userDAOImpl;

	@Override
	public CandidateGraphData getCandidateGrapthData() {
		CandidateGraphData result = new CandidateGraphData();
		
		Integer userID = null;
		try{
			String login = Utility.getLoggedInUserName();
			User user = userDAOImpl.getUserByLogin(login);
			userID = user.getId();
		} catch(DAOException de){
			
		}
		try{
			AverageHolder frequencyData = candidateGraphDAO.getFrequencyAttemptData(userID);
			result.setFrequencyAttempt(frequencyData);
		} catch(DAOException e1){
			result.setFrequencyAttempt(new AverageHolder());
		}
		
		try{
		result.setPerformanceProgression(candidateGraphDAO.getPerformanceProgression(userID));
		} catch(DAOException e2){
			result.setPerformanceProgression(new AverageHolder());
		}
		
		try{
			result.setEffortsDevoted(candidateGraphDAO.getEffortsDevoted(userID));
		} catch(DAOException e2){
			result.setEffortsDevoted(new EffortsDevoted());
		}
		
		try{
			result.setDomainWisePerformance(candidateGraphDAO.getDomainWisePerformance(userID));
		} catch(DAOException e2){
			result.setDomainWisePerformance(new DomainWisePerformance());
		}
		
		return result;
	}
	
	
	@Override
	public JobCodeModule getJobCodeTrainingModule()
	{
		//TrainingModuleGraphData result= new TrainingModuleGraphData();
		JobCodeModule result=null;
		Integer userID = null;
		try{
			String login = Utility.getLoggedInUserName();
			User user = userDAOImpl.getUserByLogin(login);
			userID = user.getId();
			result= candidateGraphDAO.getJobcodeTrainingData(userID);
			System.out.println("result----->>"+result.getCount());
		} catch(DAOException de){
			
		}
		
		return result;
	}
	
//	private AverageHolder populateFrequencyAttempt() {
//		AverageHolder result = new AverageHolder();
//		// 12 month data only
//		result.getAvg().addAll(new ArrayList<Double>() {
//			private static final long serialVersionUID = -794480902452367260L;
//
//			{
//				add(10.0);
//				add(15.0);
//				add(7.0);
//				add(12.0);
//				add(7.0);
//				add(16.0);
//				add(14.0);
//				add(12.0);
//				add(9.0);
//				add(6.0);
//				add(22.0);
//				add(2.0);
//			}
//		});
//		return result;
//	}
	
	
//	private AverageHolder populatePerformanceProgression() {
//		AverageHolder result = new AverageHolder();
//		// 12 month data only
//		result.getAvg().addAll(new ArrayList<Double>() {
//			private static final long serialVersionUID = -794480902452367260L;
//
//			{
//				add(64.0);
//				add(53.0);
//				add(70.0);
//				add(45.0);
//				add(87.0);
//				add(65.0);
//				add(34.0);
//				add(65.0);
//				add(87.0);
//				add(76.0);
//				add(88.0);
//				add(90.0);
//			}
//		});
//		return result;
//	}
	
//	private EffortsDevoted populateEffortsDevoted() {
//		EffortsDevoted result = new EffortsDevoted();
//		List<ContentHolder> a = new ArrayList<>();
//		a.add(new ContentHolder("Regression Test", 20));
//		a.add(new ContentHolder("Acceptance Test", 18));
//		a.add(new ContentHolder("Usability Test", 22));
//		a.add(new ContentHolder("Security Test", 31));
//		a.add(new ContentHolder("Others", 9));
//		result.getContent().addAll(a);
//		return result;
//	}
	
//private DomainWisePerformance populateDomainWisePerformance(){
//		
//		DomainWisePerformance result = new DomainWisePerformance();
//		
//		List<String> categories = new ArrayList<String>();
//		categories.add("Black-Box Testing");
//		categories.add("White-Box Testing");
//		categories.add("Regression Testing");
//		categories.add("Acceptance Testing");
//		categories.add("Performance Testing");
//		categories.add("Security Testing");
//		
//		result.getCategories().addAll(categories);
//		
//		List<PerformanceData> performanceDataList = new ArrayList<CandidateGraphData.PerformanceData>();
//		
//		List<Double> data1 = new ArrayList<Double>();
//		data1.add((double) 50);
//		data1.add((double) 45);
//		data1.add((double) 50);
//		data1.add((double) 40);
//		data1.add((double) 30);
//		data1.add((double) 35);
//		
//		PerformanceData performanceData1 = new PerformanceData("2013");
//		performanceData1.getData().addAll(data1);
//		performanceDataList.add(performanceData1);
//		
//		List<Double> data2 = new ArrayList<Double>();
//		data2.add((double) 75);
//		data2.add((double) 70);
//		data2.add((double) 50);
//		data2.add((double) 64);
//		data2.add((double) 45);
//		data2.add((double) 60);
//		
//		PerformanceData performanceData2 = new PerformanceData("2014");
//		performanceData2.getData().addAll(data2);
//		performanceDataList.add(performanceData2);
//		
//		result.getPerformanceDataList().addAll(performanceDataList);
//		
//		return result;
//	}

}
