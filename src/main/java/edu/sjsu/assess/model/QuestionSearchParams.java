package edu.sjsu.assess.model;

public class QuestionSearchParams {
	
	private Integer id;
	
	private Integer jobcodeID;
	
	private Integer categoryID;
	
	private String focus;

	private String level;

	private String text;

	/*private boolean isTrueOrFalse;

	private boolean isMultipleChoice;
*/
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getJobcodeID() {
		return jobcodeID;
	}

	public void setJobcodeID(Integer jobcodeID) {
		this.jobcodeID = jobcodeID;
	}

	public Integer getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
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

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	/*public boolean isTrueOrFalse()
	{
		return isTrueOrFalse;
	}

	public void setTrueOrFalse(boolean isTrueOrFalse)
	{
		this.isTrueOrFalse = isTrueOrFalse;
	}

	public boolean isMultipleChoice()
	{
		return isMultipleChoice;
	}

	public void setMultipleChoice(boolean isMultipleChoice)
	{
		this.isMultipleChoice = isMultipleChoice;
	}
	*/
}
