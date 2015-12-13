package edu.sjsu.assess.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.sjsu.assess.Application;
import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.AdminGraphData;
import edu.sjsu.assess.model.AdminGraphData.JobCodeAttempt;
import edu.sjsu.assess.model.AdminGraphData.JobCodeScore;
import edu.sjsu.assess.model.AdminGraphData.RegistrationCount;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MockServletContext.class,
        Application.class })
public class AdminGraphDAOTest {

	@Autowired
	AdminGraphDAOImpl adminGraphDAO;
	
	//@Test
	public void testGetJobcodeAttempts(){
		try{
			JobCodeAttempt result = adminGraphDAO.getJobcodeAttempts();
			Assert.assertNotNull(result);
		} catch(DAOException e){
			Assert.fail();
		}
	} 
	
	//@Test
	public void testGetJobCodeScores(){
		try{
			JobCodeScore result = adminGraphDAO.getJobCodeScores();
			Assert.assertNotNull(result);
		} catch(DAOException e){
			Assert.fail();
		}
	}
	
	//@Test
	public void testSetCategoryWiseScoresForQA(){
		try{
			AdminGraphData.JobCodeDetails result = adminGraphDAO.getCategoryWiseScoresForQA();
			Assert.assertNotNull(result);
		} catch(DAOException e){
			Assert.fail();
		}
	}
	
	//@Test
	public void testGetProfessionBasedRegistration(){
		try{
			RegistrationCount result = adminGraphDAO.getProfessionBasedRegistration();
			Assert.assertNotNull(result);
		} catch(DAOException e){
			Assert.fail();
		}
	}
	
	@Test
	public void testGetAgeBasedRegistration(){
		try{
			RegistrationCount result = adminGraphDAO.getAgeBasedRegistration();
			Assert.assertNotNull(result);
		} catch(DAOException e){
			Assert.fail();
		}
	}
	
	
}
