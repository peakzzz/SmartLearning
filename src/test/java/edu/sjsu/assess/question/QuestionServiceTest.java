package edu.sjsu.assess.question;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.sjsu.assess.Application;
import edu.sjsu.assess.model.JobCode;
import edu.sjsu.assess.model.Option;
import edu.sjsu.assess.model.Question;
import edu.sjsu.assess.model.QuestionSearchParams;
import edu.sjsu.assess.service.QuestionServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MockServletContext.class,
        Application.class })
public class QuestionServiceTest {
	
	@Autowired
	QuestionServiceImpl questionServiceImpl;
	
	@Test
	public void testCreateQuestion() throws Exception {
		
		Question qs = new Question();
//		qs.setJobCodeID(1);
		qs.setCategoryID(2);
		//qs.setCorrectAnswer("1");
		qs.setMultipleChoice(true);
		qs.setTrueOrFalse(false);
		qs.setQuestionText("Sample Question 5");
		
		List<Option> options = new ArrayList<Option>();
		
		Option op1 = new Option();
		op1.setText("option 1");
		op1.setCorrectOption(false);
		options.add(op1);
		
		Option op2 = new Option();
		op2.setText("option 2");
		op2.setCorrectOption(true);
		options.add(op2);
		
		Option op3 = new Option();
		op3.setText("option 3");
		op3.setCorrectOption(false);
		options.add(op3);
		
		Option op4 = new Option();
		op4.setText("option 4");
		op4.setCorrectOption(false);
		options.add(op4);
		
		qs.setOptions(options);
		
		qs.setType(JobCode.EntityType.PREDEFINED.getValue());
		
		Question savedQS = null;
		try{
			savedQS = questionServiceImpl.saveQuestion(qs);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
		
		if(savedQS == null){
			Assert.fail();
		}
	}
	
	//@Test
	public void testGetQuestionList() throws Exception {
		QuestionSearchParams searchParams = new QuestionSearchParams();
//		searchParams.setJobcodeID(12);
//		searchParams.setCategoryID(9);
		
		List<Question> questionsList = null;
		
		try{
			questionsList = questionServiceImpl.getQuestionsList(searchParams);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
		
		if(questionsList == null){
			Assert.fail();
		}
	}
	
	//@Test
	public void testUpdateQuestion() throws Exception {
		
		Question qs = new Question();
		qs.setId(3);
//		qs.setJobCodeID(12);
		qs.setCategoryID(10);
		qs.setQuestionText("Sample Question 3");
		qs.setTrueOrFalse(false);
		qs.setMultipleChoice(true);
		
		List<Option> options = new ArrayList<>();
		
		Option op1 = new Option();
		op1.setText("option 11");
		op1.setCorrectOption(true);
		options.add(op1);
		
		Option op2 = new Option();
		op2.setText("option 22");
		op2.setCorrectOption(false);
		options.add(op2);
		
		Option op3 = new Option();
		op3.setText("option 33");
		op3.setCorrectOption(false);
		options.add(op3);
		
		Option op4 = new Option();
		op4.setText("option 44");
		op4.setCorrectOption(false);
		options.add(op4);
		
		qs.setOptions(options);
		
		qs.setLevel("Begineer");
		qs.setFocus("Concepts");
		
		try{
			questionServiceImpl.updateQuestion(qs);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	//@Test
	public void testDeleteQuestion() throws Exception {
		
		try{
			QuestionSearchParams searchParams = new QuestionSearchParams();
			searchParams.setId(4);
			questionServiceImpl.deleteQuestion(searchParams);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
	}

}
