package edu.sjsu.assess.trainingModule;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.sjsu.assess.Application;
import edu.sjsu.assess.model.TrainingModule;
import edu.sjsu.assess.model.TrainingModuleSearchParams;
import edu.sjsu.assess.service.TrainingModuleServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MockServletContext.class,
        Application.class })
public class TrainingModuleServiceTest {
	
	@Autowired
	TrainingModuleServiceImpl trainingModuleServiceImpl;
	
	//@Test
	public void testCreateTrainingModule() throws Exception {
		TrainingModule tm = new TrainingModule();
		tm.setJobCodeID(13);
		tm.setTitle("TestTM");
		tm.setUserID(1);
		//tm.setContent("blahblahblah");
		tm.setLevel("Beginner");
		tm.setFocus("Concepts");
		
		tm.addCategoryContent(9, "blahblahblah");
		
		trainingModuleServiceImpl.saveTrainingModule(tm);
	}
	
	@Test
	public void testGetTrainingModuleList() throws Exception {
		
		TrainingModuleSearchParams tmSearchParam = new TrainingModuleSearchParams();
		//tmSearchParam.setId(2);
		tmSearchParam.setJobcodeID(13);
		tmSearchParam.setCategoryID(10);
		
		try{
			List<TrainingModule> tm = trainingModuleServiceImpl.getTrainingModuleList(tmSearchParam);
			
			if(tm == null){
				Assert.fail();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	
	//@Test
	public void testUpdateTrainingModule() throws Exception {
		TrainingModule tm = new TrainingModule();
		tm.setId(2);
		tm.setJobCodeID(13);
		tm.setTitle("TestTM2");
		tm.setUserID(1);
		//tm.setContent("blahblahblah");
		tm.setLevel("Beginner");
		tm.setFocus("Concepts");
		
		tm.addCategoryContent(10, "blahblahblah");
		
		try{
			trainingModuleServiceImpl.updateTrainingModule(tm);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	//@Test
	public void testDeleteTrainingModule() throws Exception{
		
		try{
			//trainingModuleServiceImpl.deleteTrainingModule(new TrainingModuleSearchParams(3));
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
	}

}
