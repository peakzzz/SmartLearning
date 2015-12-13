package edu.sjsu.assess.exception;

public class TrainingModuleException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7510967962060516893L;

	public TrainingModuleException(){
		super();
	}
	
	public TrainingModuleException(String message){
		super(message);
	}
	
	public TrainingModuleException(String message, Throwable cause){
		super(message, cause);
	}
}
