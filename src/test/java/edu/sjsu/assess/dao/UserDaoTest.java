package edu.sjsu.assess.dao;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.User;

public class UserDaoTest {

	
	//@Test
//	public void testviewJobCode() throws DAOException, SQLException{
//		try {
//			AdminDAOImpl q=new AdminDAOImpl();
//			String a= null;
//			q.viewJobCode(a);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail();
//		}
//	}
	
	//@Test(expected = DAOException.class)
	public void createUser() throws DAOException{
		
		User testUsr = new User();
		testUsr.setFname("vineet");
		testUsr.setPassword("1234");
		testUsr.setRole("admin");
		
		UserDAOImpl daoImpl =new UserDAOImpl();
		User g = daoImpl.createUser(testUsr);
		
		System.out.println("Id "+g.getId());
		System.out.println("category "+g.getFname());
		System.out.println("titile  "+g.getPassword());
		System.out.println("section"+ g.getRole());
	}
	
	//@Test
	public void getUser() throws DAOException{
		UserDAOImpl daoImpl = new UserDAOImpl();
		User g = daoImpl.getUserByID(1);
		System.out.println("Id "+g.getId());
		System.out.println("Name "+g.getFname());
		System.out.println("titile  "+g.getPassword());
		System.out.println("section"+ g.getRole());
	}
	
	//@Test
	public void deleteUser() throws DAOException{
		UserDAOImpl daoImpl =new UserDAOImpl();
		boolean q=daoImpl.deleteUserByID(1);
		System.out.println("Deleted !! "+q);
	}
	
}
