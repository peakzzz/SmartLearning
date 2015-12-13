package edu.sjsu.assess.exception;

public class QuestionnaireException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2836611928701206598L;
	
	public QuestionnaireException(){
		super();
	}
	
	public QuestionnaireException(String message){
		super(message);
	}
	
	public QuestionnaireException(String message, Throwable cause){
		super(message, cause);
	}
}
