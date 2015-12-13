package edu.sjsu.assess.dao;

import java.util.List;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.TestSetAttempt;
import edu.sjsu.assess.model.TestSetAttemptSearchParams;

public interface TestSetAttemptDAO {

	public TestSetAttempt createTestSetAttempt(TestSetAttempt ta) throws DAOException;
	
	public List<Integer> getTestSetAttemptList(TestSetAttemptSearchParams searchParams) throws DAOException;
	
	public TestSetAttempt getTestSetAttemptByID(Integer taID) throws DAOException;
	
	public void deleteTestSetAttempt(Integer taID) throws DAOException;
}
