package edu.sjsu.assess.jobCode;

import java.util.List;

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
import edu.sjsu.assess.model.JobCodeSearchParams;
import edu.sjsu.assess.service.JobCodeServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MockServletContext.class,
        Application.class })
public class JobCodeServiceTest {
	
	@Autowired
	JobCodeServiceImpl jobCodeServiceImpl;
	
	//@Test
	public void testCreateJobCode() throws Exception {
		JobCode jc = new JobCode(0, null, "QA Engineer", "QA", JobCode.EntityType.PREDEFINED.getValue(), 0);
		
		Category category1 = new Category(9, "Black-Box Testing", JobCode.EntityType.PREDEFINED.getValue(), null);
		Category category2 = new Category(10, "White-Box Testing", JobCode.EntityType.PREDEFINED.getValue(), null);
		jc.addFirstLevelCategory(category1);
		jc.addFirstLevelCategory(category2);
		
		JobCode savedJC = null;
		
		try{
			savedJC = jobCodeServiceImpl.saveJobCode(jc);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
		
		if(savedJC == null){
			Assert.fail();
		}
	}
	
	//@Test
	public void testUpdateJobCode() throws Exception{
		
		JobCode jc = new JobCode(13, null, "QA Engineer", "QA Engg", JobCode.EntityType.USERDEFINED.getValue(), 1);
		
		Category cat = new Category(9, "Black-Box Testing", JobCode.EntityType.PREDEFINED.getValue(), null);
		jc.addFirstLevelCategory(cat);
		
		Category cat2 = new Category(10, "White-Box Testing", JobCode.EntityType.PREDEFINED.getValue(), null);
		jc.addFirstLevelCategory(cat2);
		
		try{
			jobCodeServiceImpl.updateJobCode(jc);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	//@Test
	public void testGetPredefinedJobCodes() throws Exception{
		List<JobCode> predefinedJobCodes = null;
		
		try{
			predefinedJobCodes = jobCodeServiceImpl.getAllPredefinedJobCodes();
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
		
		if(predefinedJobCodes == null){
			Assert.fail();
		}
	}
	
	//@Test
	public void testDeleteJobCode() throws Exception {
		
		try{
			JobCodeSearchParams searchParams = new JobCodeSearchParams();
			searchParams.setId(16);
			jobCodeServiceImpl.deleteJobCode(searchParams);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	
	@Test
	public void testGetJobCodeList() throws Exception {
		
//		try{
//			jobCodeServiceImpl.getJobCodeList();
//		}
//		catch(Exception e){
//			e.printStackTrace();
//			Assert.fail();
//		}
		
		List<JobCode> jcList = jobCodeServiceImpl.getJobCodeList();
    	int a = jcList.size();
    	System.out.println("th ejobcode size :"+a);
        for(int b=0;b<a;b++)
        {
        	System.out.println("Came in the jobcode list ");
        	List<Category> cate= jcList.get(b).getFirstLevelCategories();
        	int k=cate.size();
        	System.out.println("Category size is : "+k);
        	for(int t=0;t<k;t++)
        	{
        		System.out.println("Category title " +cate.get(t).getTitle());
        	}
        	
        }
	}
	
	
	//@Test
	public void testCreateCategory() throws Exception {
		
		Category category = new Category(0, "NewCategory", JobCode.EntityType.PREDEFINED.getValue(), null);
		Category savedCategory = null;
		
		try{
			savedCategory = jobCodeServiceImpl.createCategoryForJobCode(category);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
		
		if(savedCategory == null){
			Assert.fail();
		}
	}

}
