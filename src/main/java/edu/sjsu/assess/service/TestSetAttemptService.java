package edu.sjsu.assess.service;

import java.util.List;

import edu.sjsu.assess.exception.TestSetAttemptException;
import edu.sjsu.assess.model.TestSetAttempt;
import edu.sjsu.assess.model.TestSetAttemptSearchParams;

public interface TestSetAttemptService {

	public TestSetAttempt saveTestSetAttempt(TestSetAttempt ta)
			throws TestSetAttemptException;

	public List<TestSetAttempt> getTestSetAttemptList(
			TestSetAttemptSearchParams searchParams)
			throws TestSetAttemptException;

	public TestSetAttempt evaluateTestSetAttempt(TestSetAttempt ta)
			throws TestSetAttemptException;
}
