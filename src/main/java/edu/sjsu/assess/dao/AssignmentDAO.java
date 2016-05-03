package edu.sjsu.assess.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.Assignment;
import edu.sjsu.assess.model.AssignmentSearchParams;
import edu.sjsu.assess.model.Option;
import edu.sjsu.assess.model.Question;
import edu.sjsu.assess.model.QuestionSearchParams;


@Repository
public interface AssignmentDAO {

	public Assignment createAssignment(Assignment qs) throws DAOException;
	
	public List<Integer> getAssignmentList(AssignmentSearchParams searchParams) throws DAOException;
	
	public Assignment getAssignmentByID(Integer qsID) throws DAOException;
	
	public void updateAssignmentByID(Assignment qs) throws DAOException;
	
	public void deleteAllOptions(Integer qsID) throws DAOException;
	
	public List<Option> createOptions(Integer qsID, List<Option> options) throws DAOException;
	
	public Option createOption(final Option op) throws DAOException;
	
	public void deleteAssignmentByID(Integer qsID) throws DAOException;
	
	public List<Assignment> getAssignmentList2(AssignmentSearchParams searchParams) throws DAOException;
	
}
