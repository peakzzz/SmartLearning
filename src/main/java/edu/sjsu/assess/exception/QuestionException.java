package edu.sjsu.assess.exception;

public class QuestionException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6356253857482387453L;

	public QuestionException(){
		super();
	}
	
	public QuestionException(String message){
		super(message);
	}
	
	public QuestionException(String message, Throwable cause){
		super(message, cause);
	}
}
