package edu.sjsu.assess.exception;

public class JobCodeException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5115905891376647661L;
	

	public JobCodeException(){
		super();
	}
	
	public JobCodeException(String message){
		super(message);
	}
	
	public JobCodeException(String message, Throwable cause){
		super(message, cause);
	}
}
