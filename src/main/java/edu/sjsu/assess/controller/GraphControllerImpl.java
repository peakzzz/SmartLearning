package edu.sjsu.assess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sjsu.assess.model.AdminGraphData;
import edu.sjsu.assess.model.CandidateGraphData;
import edu.sjsu.assess.model.TrainingModuleGraphData;
import edu.sjsu.assess.service.AdminGraphServiceImpl;
import edu.sjsu.assess.service.CandidateGraphServiceImpl;
import edu.sjsu.assess.service.JobCodeServiceImpl;

@Controller
@RequestMapping("/graph")
public class GraphControllerImpl implements GraphController {
	@Autowired
	private JobCodeServiceImpl jobCodeService;
	
	@Autowired
	private CandidateGraphServiceImpl candidateGraphService;
	
	@Autowired
	private AdminGraphServiceImpl adminGraphService;

	@Override
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public @ResponseBody
	AdminGraphData getAdminGrapthData(Model model) {
		AdminGraphData result = new AdminGraphData();
		result = adminGraphService.getAdminGraphData();
		
		//result.setJobCodeAttempt(populateJobcodeAttempts());
		//result.setJobCodeScore(populateJobCodeScore());
		//result.setTopJobCode(populateTopJobCode());
		//result.setNextToTopJobCode(populateNextToTopJobCode());
		//result.setProfessionBasedRegistration(getPopulationBasedRegistration());
		//result.setAgeBasedRegisration(getPopulationBasedOnAge());
		
		return result;
	}

//	private RegistrationCount getPopulationBasedOnAge() {
//		RegistrationCount result = new RegistrationCount(
//				"Age-Based Registration");
//		try {
//			List<ContentHolder> content = new ArrayList<>();
//			content.add(new ContentHolder("20-25 years", 15654));
//			content.add(new ContentHolder("25-30 years", 8765));
//			content.add(new ContentHolder("30-35 years", 4064));
//			content.add(new ContentHolder("35-40 years", 1674));
//			content.add(new ContentHolder("45-50 years", 329));
//			result.getEntries().addAll(content);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//	private RegistrationCount getPopulationBasedRegistration() {
//		RegistrationCount result = new RegistrationCount(
//				"Profession-Based Registration");
//		try {
//			List<ContentHolder> content = new ArrayList<>();
//			content.add(new ContentHolder("Software Engineer", 15654));
//			content.add(new ContentHolder("QA Engineer", 4064));
//			content.add(new ContentHolder("Students", 1987));
//			content.add(new ContentHolder("Unemployed", 976));
//			content.add(new ContentHolder("Product Manager", 846));
//			result.getEntries().addAll(content);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//	private JobCodeDetails populateTopJobCode() {
//		JobCodeDetails result = null;
//		try {
//			result = new JobCodeDetails(
//					"Category-wise scores (QA Engineering)", 972.0);
//			List<String> categories = new ArrayList<String>() {
//				private static final long serialVersionUID = 6319657917092317829L;
//
//				{
//					add("Black Box Testing");
//					add("Performance Testing");
//					add("Stress Testing");
//					add("Automation Technologies");
//					add("White Box Testing");
//				}
//			};
//			List<List<Double>> data = new ArrayList<List<Double>>();
//
//			List<Double> d1 = new ArrayList<Double>() {
//				private static final long serialVersionUID = -794480902452367260L;
//				{
//					add(760.0);
//					add(801.0);
//					add(848.0);
//					add(895.0);
//					add(965.0);
//				}
//			};
//			List<Double> d2 = new ArrayList<Double>() {
//				private static final long serialVersionUID = -794480902452367260L;
//				{
//					add(733.0);
//					add(853.0);
//					add(939.0);
//					add(980.0);
//					add(1080.0);
//				}
//			};
//			List<Double> d3 = new ArrayList<Double>() {
//				private static final long serialVersionUID = -794480902452367260L;
//				{
//					add(714.0);
//					add(762.0);
//					add(817.0);
//					add(870.0);
//					add(918.0);
//				}
//			};
//			List<Double> d4 = new ArrayList<Double>() {
//				private static final long serialVersionUID = -794480902452367260L;
//				{
//					add(724.0);
//					add(802.0);
//					add(806.0);
//					add(871.0);
//					add(950.0);
//				}
//			};
//			List<Double> d5 = new ArrayList<Double>() {
//				private static final long serialVersionUID = -794480902452367260L;
//				{
//					add(834.0);
//					add(836.0);
//					add(864.0);
//					add(882.0);
//					add(910.0);
//				}
//			};
//			data.add(d1);
//			data.add(d2);
//			data.add(d3);
//			data.add(d4);
//			data.add(d5);
//			result.getData().addAll(data);
//			result.getCategory().addAll(categories);
//		} catch (Exception e) {
//			e.printStackTrace();
//			if (result == null) {
//				result = JobCodeDetails.emptyInstance;
//			}
//		}
//		return result;
//	}
//
//	private JobCodeDetails populateNextToTopJobCode() {
//		JobCodeDetails result = null;
//		try {
//			result = new JobCodeDetails(
//					"Category-wise scores (Software Development)", 81.68);
//			List<String> categories = new ArrayList<String>() {
//				private static final long serialVersionUID = 6319657917092317829L;
//
//				{
//					add("Operating Systems");
//					add("Data Structures");
//					add("Programming Language");
//					add("Database Concepts");
//					add("Compiler Design");
//				}
//			};
//			List<List<Double>> data = new ArrayList<List<Double>>();
//
//			List<Double> d1 = new ArrayList<Double>() {
//				private static final long serialVersionUID = -794480902452367260L;
//				{
//					add(76.0);
//					add(81.0);
//					add(84.0);
//					add(89.0);
//					add(96.0);
//				}
//			};
//			List<Double> d2 = new ArrayList<Double>() {
//				private static final long serialVersionUID = -794480902452367260L;
//				{
//					add(73.0);
//					add(85.0);
//					add(93.0);
//					add(98.0);
//					add(100.0);
//				}
//			};
//			List<Double> d3 = new ArrayList<Double>() {
//				private static final long serialVersionUID = -794480902452367260L;
//				{
//					add(71.0);
//					add(76.0);
//					add(81.0);
//					add(87.0);
//					add(91.0);
//				}
//			};
//			List<Double> d4 = new ArrayList<Double>() {
//				private static final long serialVersionUID = -794480902452367260L;
//				{
//					add(72.0);
//					add(80.0);
//					add(81.0);
//					add(87.0);
//					add(95.0);
//				}
//			};
//			List<Double> d5 = new ArrayList<Double>() {
//				private static final long serialVersionUID = -794480902452367260L;
//				{
//					add(83.0);
//					add(83.0);
//					add(86.0);
//					add(88.0);
//					add(91.0);
//				}
//			};
//			data.add(d1);
//			data.add(d2);
//			data.add(d3);
//			data.add(d4);
//			data.add(d5);
//			result.getData().addAll(data);
//			result.getCategory().addAll(categories);
//		} catch (Exception e) {
//			e.printStackTrace();
//			if (result == null) {
//				result = JobCodeDetails.emptyInstance;
//			}
//		}
//		return result;
//	}
//
//	private JobCodeScore populateJobCodeScore() {
//		JobCodeScore result = new JobCodeScore();
//		try {
//			List<String> jobCodes = new ArrayList<String>() {
//				private static final long serialVersionUID = 6319657917092317829L;
//
//				{
//					add("QA Engineer");
//					add("QA Manager");
//					add("Software Engineering");
//					add("Agile Methodology");
//					add("Release Engineer");
//					add("Software Architect");
//					add("Project Management");
//					add("Product Management");
//					add("Programming");
//					add("Big Data");
//				}
//			};
//			List<Double> mean = new ArrayList<Double>() {
//				private static final long serialVersionUID = -794480902452367260L;
//
//				{
//					add(64.0);
//					add(53.0);
//					add(70.0);
//					add(45.0);
//					add(87.0);
//					add(65.0);
//					add(34.0);
//					add(65.0);
//					add(87.0);
//					add(76.0);
//				}
//			};
//			List<Double> median = new ArrayList<Double>() {
//				private static final long serialVersionUID = -794480902452367260L;
//
//				{
//					add(60.0);
//					add(50.0);
//					add(64.0);
//					add(36.0);
//					add(87.0);
//					add(67.0);
//					add(38.0);
//					add(62.0);
//					add(83.0);
//					add(70.0);
//				}
//			};
//			result.getJobcode().addAll(jobCodes);
//			result.getMean().addAll(mean);
//			result.getMedian().addAll(median);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//	private JobCodeAttempt populateJobcodeAttempts() {
//		JobCodeAttempt jca = new JobCodeAttempt();
//		try {
//			List<String> jobCodes = new ArrayList<String>() {
//				private static final long serialVersionUID = 6319657917092317829L;
//
//				{
//					add("QA Engineer");
//					add("QA Manager");
//					add("Software Engineering");
//					add("Agile Methodology");
//					add("Release Engineer");
//					add("Software Architect");
//					add("Project Management");
//					add("Product Management");
//					add("Programming");
//					add("Big Data");
//				}
//			};
//
//			List<Integer> attempts = new ArrayList<Integer>() {
//				private static final long serialVersionUID = -794480902452367260L;
//
//				{
//					add(10);
//					add(12);
//					add(15);
//					add(3);
//					add(4);
//					add(2);
//					add(10);
//					add(3);
//					add(17);
//					add(23);
//				}
//			};
//			jca.getAttempt().addAll(attempts);
//			jca.getJobcode().addAll(jobCodes);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return jca;
//	}

	@Override
	@RequestMapping(value = "/candidate", method = RequestMethod.GET)
	public @ResponseBody
	CandidateGraphData getCandidateGrapthData(Model model) {
		CandidateGraphData result = new CandidateGraphData();
		result = candidateGraphService.getCandidateGrapthData();
		return result;
	}

	
	@Override
	@RequestMapping(value = "/candidateTaining", method = RequestMethod.GET)
	public @ResponseBody
	TrainingModuleGraphData getTrainingData(Model model)
	{
		TrainingModuleGraphData result  = new TrainingModuleGraphData();
		result.setJobCodeModule(candidateGraphService.getJobCodeTrainingModule());
		return result;
		
	}
	
//	private JobCodeModule populateJobCodeTrainingModule() {
//		JobCodeModule result = new JobCodeModule();
//		result = candidateGraphService.getJobCodeTrainingModule();
//		try {
//			List<String> jobCodes = new ArrayList<String>() {
//				private static final long serialVersionUID = 6319657917092317829L;
//
//				{
//					add("QA Engineer");
//					add("QA Manager");
//					add("Software Engineering");
//					add("Big Data");
//				}
//			};
//			List<Integer> count = new ArrayList<Integer>() {
//				private static final long serialVersionUID = -794480902452367260L;
//
//				{
//					add(12);
//					add(13);
//					add(15);
//					add(18);
//					
//				}
//			};
//			
//			result.getJobcode().addAll(jobCodes);
//			result.getCount().addAll(count);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
	
}
