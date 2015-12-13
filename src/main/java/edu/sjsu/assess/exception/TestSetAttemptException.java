package edu.sjsu.assess.exception;

public class TestSetAttemptException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3847558985517189156L;
	
	public TestSetAttemptException(){
		super();
	}
	
	public TestSetAttemptException(String message){
		super(message);
	}
	
	public TestSetAttemptException(String message, Throwable cause){
		super(message, cause);
	}

}
