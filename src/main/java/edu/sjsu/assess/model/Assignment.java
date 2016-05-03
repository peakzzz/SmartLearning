package edu.sjsu.assess.model;

import java.util.List;

public class Assignment {

	private Integer id;

//	private Integer jobCodeID;

	private Integer categoryID;

	private Category categoryObj;
	
	public String questionText;
	
	private String answerText;
	
	private String type;
	
	private boolean isTrueOrFalse;
	
	private boolean isMultipleChoice;
	
	public String focus;
	
	public String level;
	
	private Integer userID;
	
	private List<Option> options;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}

	public Category getCategoryObj() {
		return categoryObj;
	}

	public void setCategoryObj(Category categoryObj) {
		this.categoryObj = categoryObj;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isTrueOrFalse() {
		return isTrueOrFalse;
	}

	public void setTrueOrFalse(boolean isTrueOrFalse) {
		this.isTrueOrFalse = isTrueOrFalse;
	}

	public boolean isMultipleChoice() {
		return isMultipleChoice;
	}

	public void setMultipleChoice(boolean isMultipleChoice) {
		this.isMultipleChoice = isMultipleChoice;
	}

	public String getFocus() {
		return focus;
	}

	public void setFocus(String focus) {
		this.focus = focus;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	
	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}


	public Option getCorrectOption() {
		
		if(this.options == null){
			return null;
		}
		
		for(Option op : this.options){
			if(op.isCorrectOption()){
				return op;
			}
		}
		
		return null;
	}

    public Assignment(){

    }

    public boolean isCorrect(List<Integer> optionIds){
        if(optionIds == null || optionIds.isEmpty())
            return false;

        for (Option option : options) {
            if(option.isCorrectOption() && !optionIds.contains(option.getId())){
                return false;
            }
        }
        return true;
    }

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

}
