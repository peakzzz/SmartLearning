package edu.sjsu.assess.exception;

public class DAOException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7548075986836148209L;

	public DAOException(){
		super();
	}
	
	public DAOException(String message){
		super(message);
	}
}
