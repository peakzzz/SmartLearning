package edu.sjsu.assess.exception;

public class TestSetException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4214138259128108537L;
	
	public TestSetException(){
		super();
	}
	
	public TestSetException(String message){
		super(message);
	}
	
	public TestSetException(String message, Throwable cause){
		super(message, cause);
	}

}
