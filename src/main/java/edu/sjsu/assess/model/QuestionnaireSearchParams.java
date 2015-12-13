package edu.sjsu.assess.model;

public class QuestionnaireSearchParams {
	
	/*
	 * Questionnaire ID
	 */
	private String id = null;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Default Constructor
	 */
	public QuestionnaireSearchParams() {
		// TODO Auto-generated constructor stub
	}
	
	public QuestionnaireSearchParams(String id){
		this.id = id;
	}

}
