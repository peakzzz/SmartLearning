package edu.sjsu.assess.testSet;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.sjsu.assess.Application;
import edu.sjsu.assess.dao.QuestionDAOImpl;
import edu.sjsu.assess.dao.TestSetDAOImpl;
import edu.sjsu.assess.model.Question;
import edu.sjsu.assess.model.TestSet;
import edu.sjsu.assess.model.TestSetCategory;
import edu.sjsu.assess.model.TestSetSearchParams;
import edu.sjsu.assess.service.TestSetServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MockServletContext.class,
        Application.class })
public class TestSetServiceTest {

	@Autowired
	TestSetServiceImpl testSetServiceImpl;
	
	@Autowired
	TestSetDAOImpl testSetDAOImpl;
	
	@Autowired
	QuestionDAOImpl questionDAOImpl;
	
	@Test
	public void testCreateTestSet() throws Exception {
		
		TestSet ts = new TestSet();
		ts.setJobCodeID(1);
		ts.setUserID(1);
		ts.setCutoff((float) 60);
		ts.setLevel("Beginner");
		
		TestSetCategory tsc1 = new TestSetCategory();
		tsc1.setCategoryID(1);
		tsc1.setWeightage((float) 50);
		
		Question q1 = questionDAOImpl.getQuestionByID(3);
//		q1.setId(3);
//		q1.setJobCodeID(12);
//		q1.setCategoryID(10);
//		q1.setType(JobCode.EntityType.PREDEFINED.getValue());
//		q1.setTrueOrFalse(false);
//		q1.setMultipleChoice(true);
//		q1.setFocus("Concepts");
//		q1.setLevel("Beginner");
		
		tsc1.addQuestion(q1);
		ts.addTestSetCategory(tsc1);
		
		TestSetCategory tsc2 = new TestSetCategory();
		tsc2.setCategoryID(2);
		tsc2.setWeightage((float) 50);
		
		Question q2 = questionDAOImpl.getQuestionByID(4);
//		q2.setId(3);
//		q2.setJobCodeID(12);
//		q2.setCategoryID(10);
//		q2.setType(JobCode.EntityType.PREDEFINED.getValue());
//		q2.setTrueOrFalse(false);
//		q2.setMultipleChoice(true);
//		q2.setFocus("Concepts");
//		q2.setLevel("Beginner");
		
		tsc2.addQuestion(q2);
		ts.addTestSetCategory(tsc2);
		
		TestSet savedTS = null;
		
		try{
			savedTS = testSetServiceImpl.saveTestSet(ts);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
		
		if(savedTS == null || savedTS.getId() == null){
			Assert.fail();
		}
	}
	
	//@Test
	public void testGetTestSetList() throws Exception {
		
		TestSetSearchParams searchParams = new TestSetSearchParams();
		List<TestSet> tsList = null;

		try{
			tsList = testSetServiceImpl.getTestSetList(searchParams);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
		
		if(tsList == null){
			Assert.fail();
		}
	}
	
	//@Test
	public void testUpdateTestSet() throws Exception {
		
		TestSet updatedTS = null;
		try{
			
			TestSet ts = testSetDAOImpl.getTestSetByID(9);
			
			TestSetCategory tsc = new TestSetCategory();
			tsc.setCategoryID(9);
			tsc.setCutoff((float) 70);
			
			Question qs = questionDAOImpl.getQuestionByID(5);
			List<Question> qsList = Arrays.asList(qs);
			tsc.setQuestionList(qsList);
			
			ts.setTestSetCategories(Arrays.asList(tsc));
			
			updatedTS = testSetServiceImpl.updateTestSet(ts);
			
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
		
		if(updatedTS == null){
			Assert.fail();
		}
		
		// test set id = 9 (categories 9,10,11,12,13,14)
		// category id = 10
		// question id = 3
		// update to category id = 9, question id = 5
	}
	
	//@Test
	public void testDeleteTestSet() throws Exception {
		
		try{
			TestSetSearchParams searchParams = new TestSetSearchParams();
			searchParams.setId(10);
			
			testSetServiceImpl.deleteTestSet(searchParams);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
	}
}
