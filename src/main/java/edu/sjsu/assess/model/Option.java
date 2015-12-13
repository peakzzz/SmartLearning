package edu.sjsu.assess.model;

/**
 * Option Model Class
 * @author Shefali
 *
 */
public class Option {

	private Integer id;
	
	private Integer questionID;
	
	private String text;

	private boolean isCorrectOption;
	
	private boolean isSelectedByUser;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuestionID() {
		return questionID;
	}

	public void setQuestionID(Integer questionID) {
		this.questionID = questionID;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isCorrectOption() {
		return isCorrectOption;
	}

	public void setCorrectOption(boolean isCorrectOption) {
		this.isCorrectOption = isCorrectOption;
	}

    public boolean isSelectedByUser() {
		return isSelectedByUser;
	}

	public void setSelectedByUser(boolean isSelectedByUser) {
		this.isSelectedByUser = isSelectedByUser;
	}

	public Option(){

    }
}
