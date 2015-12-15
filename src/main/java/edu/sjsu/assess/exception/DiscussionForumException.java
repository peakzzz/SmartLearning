package edu.sjsu.assess.exception;

public class DiscussionForumException extends Exception{
	
	private static final long serialVersionUID = 6548075986836148209L;

	public DiscussionForumException(){
		super();
	}
	
	public DiscussionForumException(String message){
		super(message);
	}
	public DiscussionForumException(String message, Throwable cause){
		super(message, cause);
	}
}
