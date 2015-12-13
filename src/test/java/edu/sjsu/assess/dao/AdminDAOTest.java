//package edu.sjsu.assess.dao;
//
//import java.io.IOException;
//import java.sql.SQLException;
//
//import org.junit.Assert;
//import org.junit.Test;
//
//import edu.sjsu.assess.bean.Jobcode;
//import edu.sjsu.assess.bean.Question;
//import edu.sjsu.assess.bean.Section;
//import edu.sjsu.assess.exception.DAOException;
//
//
//
//public class AdminDAOTest {
//
//	//@Test
//	public void testviewJobCode() throws DAOException, SQLException{
//		try {
//			AdminDAOImpl q=new AdminDAOImpl();
//			String a=null;
//			q.viewJobCode(a);
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail();
//		}
//	}
//	
//		//@Test
//		public void testCreateJobCode() throws DAOException, SQLException{
//			try {
//				AdminDAOImpl q=new AdminDAOImpl();
//				Jobcode jb=new Jobcode();
//				jb.setJobId("1006BR");
//				jb.setTitle("Software engineer");
//				jb.setType("Full Time");
//				jb.setSkills("communications, reasoning, algorithm");
//				jb.setDescription("This is a exciting full time opportunities for new Grad");
//				q.createJobCode(jb);
//				
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//				Assert.fail();
//			}
//		}
//	
//		
//		//@Test
//		public void testUpdateJobCode() throws DAOException, SQLException{
//			try {
//				AdminDAOImpl q=new AdminDAOImpl();
//				Jobcode jb=new Jobcode();
//				jb.setJobId("1006BR");
//				jb.setTitle("Software engineer2");
//				jb.setType("Part Time");
//				jb.setSkills("communications, reasoning, algorithm");
//				jb.setDescription("This is a exciting full time opportunities for new Grad");
//				q.updateJobCode(jb);
//				
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//				Assert.fail();
//			}
//		}
//		
//		
//		//@Test
//		public void testDeleteJobCode() throws DAOException, SQLException{
//			try {
//				AdminDAOImpl q=new AdminDAOImpl();
//				Jobcode jb=new Jobcode();
//				jb.setJobId("1006BR");
//				jb.setTitle("Software engineer2");
//				jb.setType("Part Time");
//				jb.setSkills("communications, reasoning, algorithm");
//				jb.setDescription("This is a exciting full time opportunities for new Grad");
//				q.deleteJobCode(jb);
//				
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//				Assert.fail();
//			}
//		}
//	
//	//@Test
//	public void testcreateCategory(){ 
//	try {
//		AdminDAOImpl q=new AdminDAOImpl();
//		q.createCategory("Black Box Testing", "Intemediate");
//		
//		
//	} catch (Exception e) {
//		e.printStackTrace();
//		Assert.fail();
//	}
//	}
//	
//	
//	//@Test
//	public void testcreateSection(){ 
//	try {
//		AdminDAOImpl q=new AdminDAOImpl();
//		Section sec=new Section();
//		sec.setCutoff(10);
//		sec.setSectionName("concepts");
//		sec.setWeightage(20);
//		sec.setLevel("intermediate");
//		q.createSection(sec);
//		
//		
//	} catch (Exception e) {
//		e.printStackTrace();
//		Assert.fail();
//	}
//	}
//	
//	//@Test
//	public void viewExistingQuestion(){ 
//		try {
//			AdminDAOImpl q=new AdminDAOImpl();
////			Section sec=new Section();
////			sec.setCutoff(10);
////			sec.setSectionName("concepts");
////			sec.setWeightage(20);
////			sec.setLevel("intermediate");
//			
//			q.viewExistingQuestion("Black Box Testing","concepts");
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail();
//		}
//		}
//	
//	
//	//@Test
//	public void getCategoryId(){ 
//		try {
//			AdminDAOImpl q=new AdminDAOImpl();
//		int cat=	q.getCategoryId("Black Box Testing");
//		System.out.println("finally got "+cat);
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail();
//		}
//		}
//	
//	
//	//@Test
//	public void getsectionId(){ 
//		try {
//			AdminDAOImpl q=new AdminDAOImpl();
//		int cat=	q.getSectionId("concepts", 2);
//		System.out.println("finally got "+cat);
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail();
//		}
//		}
//	
//	
//	//@Test
//	public void createQuestion(){ 
//		try {
//			AdminDAOImpl q=new AdminDAOImpl();
//			Question qs =new Question();
//			qs.setQuestionText("What are the test cases for black box testing");
//			qs.setQuestionType("question answer");
//			
//		String cat=	q.createQuestion(qs, "Black Box Testing", "concepts");
//		System.out.println("finally got "+cat);
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail();
//		}
//		}
//}
