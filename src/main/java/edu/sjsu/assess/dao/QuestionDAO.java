package edu.sjsu.assess.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.Option;
import edu.sjsu.assess.model.Question;
import edu.sjsu.assess.model.QuestionSearchParams;


@Repository
public interface QuestionDAO {

	public Question createQuestion(Question qs) throws DAOException;
	
	public List<Integer> getQuestionList(QuestionSearchParams searchParams) throws DAOException;
	
	public Question getQuestionByID(Integer qsID) throws DAOException;
	
	public void updateQuestionByID(Question qs) throws DAOException;
	
	public void deleteAllOptions(Integer qsID) throws DAOException;
	
	public List<Option> createOptions(Integer qsID, List<Option> options) throws DAOException;
	
	public Option createOption(final Option op) throws DAOException;
	
	public void deleteQuestionByID(Integer qsID) throws DAOException;
	
}
