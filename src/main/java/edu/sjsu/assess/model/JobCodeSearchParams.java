package edu.sjsu.assess.model;

public class JobCodeSearchParams {
	
	/*
	 *  Job Code ID
	 */
	private Integer id = null;
	
	private Integer userID = null;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	/**
	 * Default Constructor
	 */
	public JobCodeSearchParams() {
		// TODO Auto-generated constructor stub
	}
	
	public JobCodeSearchParams(Integer id){
		this.id = id;
	}


}
