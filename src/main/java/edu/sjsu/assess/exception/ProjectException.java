package edu.sjsu.assess.exception;

public class ProjectException extends Exception{
	private static final long serialVersionUID = -4214138259128108537L;
	
	public ProjectException(){
		super();
	}
	
	public ProjectException(String message){
		super(message);
	}
	
	public ProjectException(String message, Throwable cause){
		super(message, cause);
	}
}
