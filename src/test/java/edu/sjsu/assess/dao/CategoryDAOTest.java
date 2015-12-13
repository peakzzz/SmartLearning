package edu.sjsu.assess.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.sjsu.assess.Application;
import edu.sjsu.assess.model.Category;
import edu.sjsu.assess.model.JobCode;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MockServletContext.class,
        Application.class })
public class CategoryDAOTest {

	@Autowired
	CategoryDAOImpl categoryDAOImpl;
	
	
	//@Test
	public void testCreateCategory() throws Exception {
		Category category = new Category();
		category.setTitle("testCat");
		category.setType(JobCode.EntityType.PREDEFINED.getValue());
		
		try{
			categoryDAOImpl.createCategory(category);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetCategory() throws Exception {
		Category category = null;
		
		try{
			category = categoryDAOImpl.getCategoryByID(9);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
		
		Assert.assertNotNull(category);
	}
	
	//@Test
	public void testUpdateCategory() throws Exception {
		Category category = new Category();
		category.setId(1);
		category.setTitle("testCat2");
		category.setType(JobCode.EntityType.PREDEFINED.getValue());
		
		try{
			categoryDAOImpl.updateCategoryByID(category);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	//@Test
	public void testDeleteCategory() throws Exception {
		
		try{
			categoryDAOImpl.deleteCategoryByID(1);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
	}
}
