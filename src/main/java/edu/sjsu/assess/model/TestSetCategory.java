package edu.sjsu.assess.model;

import java.util.ArrayList;
import java.util.List;

public class TestSetCategory {
	
	private Integer id;
	
	private Integer categoryID;

	private String title;

    //Needed this object to get category name at this level
    private Category categoryObj;
	
	private Category category;
	
	private Float cutoff;
	
	private Float weightage;
	
	private List<Question> questionList;

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Float getCutoff() {
		return cutoff;
	}

	public void setCutoff(Float cutoff) {
		this.cutoff = cutoff;
	}

	public Float getWeightage() {
		return weightage;
	}

	public void setWeightage(Float weightage) {
		this.weightage = weightage;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

    public Category getCategoryObj() { return categoryObj;}

    public void setCategoryObj(Category categoryObj) { this.categoryObj = categoryObj; }

	public void addQuestion(Question qs){
		if(this.questionList == null){
			this.questionList = new ArrayList<Question>();
		}
		
		this.questionList.add(qs);
	}

}
