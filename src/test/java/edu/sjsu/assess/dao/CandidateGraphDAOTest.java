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
import edu.sjsu.assess.model.CandidateGraphData.AverageHolder;
import edu.sjsu.assess.model.CandidateGraphData.DomainWisePerformance;
import edu.sjsu.assess.model.CandidateGraphData.EffortsDevoted;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MockServletContext.class,
        Application.class })
public class CandidateGraphDAOTest {

	@Autowired
	CandidateGraphDAOImpl candidateGraphDAO;
	
	//@Test
	public void testGetFrequencyAttemptData(){
		try{
			AverageHolder result = candidateGraphDAO.getFrequencyAttemptData(46);
			Assert.assertNotNull(result);
		} catch(DAOException e){
			Assert.fail();
		}
	}
	
	//@Test
	public void testGetPerformanceProgression(){
		try{
			AverageHolder result = candidateGraphDAO.getPerformanceProgression(46);
			Assert.assertNotNull(result);
		} catch(DAOException e){
			Assert.fail();
		}
	}
	
	//@Test
	public void testGetEffortsDevoted(){
		try{
			EffortsDevoted result = candidateGraphDAO.getEffortsDevoted(46);
			Assert.assertNotNull(result);
		} catch(DAOException e){
			Assert.fail();
		}
	}
	
	@Test
	public void testGetDomainWisePerformance(){
		try{
			DomainWisePerformance result = candidateGraphDAO.getDomainWisePerformance(46);
			Assert.assertNotNull(result);
		} catch(DAOException e){
			Assert.fail();
		}
	}
}
