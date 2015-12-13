package edu.sjsu.assess.service;

import java.util.List;

import edu.sjsu.assess.exception.TestSetException;
import edu.sjsu.assess.model.TestSet;
import edu.sjsu.assess.model.TestSetSearchParams;

/**
 * Test Set Service Interface
 * 
 * @author Shefali
 *
 */
public interface TestSetService {

	public TestSet saveTestSet(TestSet ts) throws TestSetException;

	public List<TestSet> getTestSetList(
			TestSetSearchParams searchParams)
			throws TestSetException;
	
	public TestSet updateTestSet(TestSet ts)
            throws TestSetException;
	
	public void deleteTestSet(TestSetSearchParams searchParams)
            throws TestSetException;
}
