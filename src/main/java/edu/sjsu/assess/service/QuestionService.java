package edu.sjsu.assess.service;

import java.util.List;

import edu.sjsu.assess.exception.QuestionException;
import edu.sjsu.assess.model.Question;
import edu.sjsu.assess.model.QuestionSearchParams;

public interface QuestionService {
	
	public Question saveQuestion(Question qs) throws QuestionException;
	
	public List<Question> getQuestionsList(QuestionSearchParams searchParams) throws QuestionException;
	
	public void updateQuestion(Question qs) throws QuestionException;
	
	public void deleteQuestion(QuestionSearchParams searchParams) throws QuestionException;
}
