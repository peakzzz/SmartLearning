package edu.sjsu.assess.exception;

public class UserException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7510967962060516893L;

	public UserException(){
		super();
	}
	
	public UserException(String message){
		super(message);
	}
	
	public UserException(String message, Throwable cause){
		super(message, cause);
	}
}
