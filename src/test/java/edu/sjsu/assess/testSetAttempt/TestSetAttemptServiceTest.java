package edu.sjsu.assess.testSetAttempt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.sjsu.assess.Application;
import edu.sjsu.assess.model.TestSetAttempt;
import edu.sjsu.assess.model.TestSetAttempt.CategoryWiseRecord;
import edu.sjsu.assess.model.TestSetAttempt.QuestionWiseRecord;
import edu.sjsu.assess.model.TestSetAttemptSearchParams;
import edu.sjsu.assess.service.TestSetAttemptServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MockServletContext.class,
        Application.class })
public class TestSetAttemptServiceTest {
	
	@Autowired
	TestSetAttemptServiceImpl testSetAttemptServiceImpl;
	
	@Test
	public void testCreateTestSetAttempt() throws Exception {
		
		for(int i1 = 0; i1 < 64; i1++){
			TestSetAttempt ta = new TestSetAttempt();
			ta.setTestSetID(16);
			
			CategoryWiseRecord cwr1 = new TestSetAttempt().new CategoryWiseRecord();
			cwr1.setCategoryID(26);
			cwr1.setScore((double) 64);
			cwr1.setIsCutoffReached(true);
			
			ta.addCategoryWiseRecord(cwr1);
			
			QuestionWiseRecord qwr1 = ta.new QuestionWiseRecord();
			qwr1.setQuestionID(28);
			qwr1.setIsCorrectAnswer(true);
			//qwr1.setUserAnswerOption(13);
			cwr1.addQuestionWiseRecord(qwr1);
			
			ta.setScore((double) 64);
			
			ta.setUserID(46);
			
			ta.setAttemptDate(new Date("01/01/2014"));
			TestSetAttempt savedTA = null;
			
			try{
				savedTA = testSetAttemptServiceImpl.saveTestSetAttempt(ta);
			}
			catch(Exception e){
				e.printStackTrace();
				Assert.fail();
			}
			
			if(savedTA == null){
				Assert.fail();
			}
		}
		
		
		for(int i1 = 0; i1 < 64; i1++){
			TestSetAttempt ta = new TestSetAttempt();
			ta.setTestSetID(16);
			
			CategoryWiseRecord cwr1 = new TestSetAttempt().new CategoryWiseRecord();
			cwr1.setCategoryID(26);
			cwr1.setScore((double) 64);
			cwr1.setIsCutoffReached(true);
			
			ta.addCategoryWiseRecord(cwr1);
			
			QuestionWiseRecord qwr1 = ta.new QuestionWiseRecord();
			qwr1.setQuestionID(28);
			qwr1.setIsCorrectAnswer(true);
			//qwr1.setUserAnswerOption(13);
			cwr1.addQuestionWiseRecord(qwr1);
			
			ta.setScore((double) 64);
			
			ta.setUserID(46);
			
			ta.setAttemptDate(new Date("01/01/2014"));
			TestSetAttempt savedTA = null;
			
			try{
				savedTA = testSetAttemptServiceImpl.saveTestSetAttempt(ta);
			}
			catch(Exception e){
				e.printStackTrace();
				Assert.fail();
			}
			
			if(savedTA == null){
				Assert.fail();
			}
		}
	}
	
	//@Test
	public void testGetTestSetList() throws Exception {
		
		TestSetAttemptSearchParams searchParams = new TestSetAttemptSearchParams();
		
		List<TestSetAttempt> taList = null;

		try{
			taList = testSetAttemptServiceImpl.getTestSetAttemptList(searchParams);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
		
		if(taList == null){
			Assert.fail();
		}
	}
	
	
	//@Test
	public void testEvaluateTestSet() throws Exception{
		TestSetAttempt ta = new TestSetAttempt();
		ta.setTestSetID(3);
		//ta.setAttemptDate(new Date(System.currentTimeMillis()));
		
//		ta.addCategoryWiseScore(10, (double) 50);
//		ta.addCategoryWiseScore(9, (double) 0);
		
		List<QuestionWiseRecord> questionWiseRecords1 = new ArrayList<TestSetAttempt.QuestionWiseRecord>();
		
		QuestionWiseRecord qwr1 = ta.new QuestionWiseRecord();
		qwr1.setQuestionID(3);
		qwr1.setIsCorrectAnswer(true);
		//qwr1.setUserAnswerOption(1);
		
		questionWiseRecords1.add(qwr1);
		
		//ta.addQuestionsRecord(1, questionWiseRecords1);
		
		QuestionWiseRecord qwr2 = ta.new QuestionWiseRecord();
		qwr2.setQuestionID(4);
		qwr2.setIsCorrectAnswer(false);
		//qwr2.setUserAnswerOption(5);
		
		List<QuestionWiseRecord> questionWiseRecords2 = new ArrayList<TestSetAttempt.QuestionWiseRecord>();
		questionWiseRecords2.add(qwr2);
		
		//ta.addQuestionsRecord(2, questionWiseRecords2);
		
		//ta.setScore((double) 50);
		
		ta.setUserID(1);
		
		TestSetAttempt evaluatedTA = null;
		
		try{
			
			evaluatedTA = testSetAttemptServiceImpl.evaluateTestSetAttempt(ta);
			
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
		
		if(evaluatedTA == null){
			Assert.fail();
		}
	}
}
