package edu.sjsu.assess.model;


public class Assignmentoption {

	private Integer id;
	
	private Integer assigmentId;
	
	public Integer getAssigmentId() {
		return assigmentId;
	}

	public void setAssigmentId(Integer assigmentId) {
		this.assigmentId = assigmentId;
	}

	private String answerText;
	
	private Integer sid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}


	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}
	
}
