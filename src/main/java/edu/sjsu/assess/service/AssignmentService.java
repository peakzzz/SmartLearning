package edu.sjsu.assess.service;

import java.util.List;

import edu.sjsu.assess.exception.QuestionException;
import edu.sjsu.assess.model.Assignment;
import edu.sjsu.assess.model.AssignmentSearchParams;

public interface AssignmentService {
	
	public Assignment saveAssignment(Assignment qs) throws QuestionException;
	
	public List<Assignment> getAssignmentsList(AssignmentSearchParams searchParams) throws QuestionException;
	
	public void updateAssignment(Assignment qs) throws QuestionException;
	
	public void deleteAssignment(AssignmentSearchParams searchParams) throws QuestionException;
}
