package edu.sjsu.assess.model;

import java.util.ArrayList;
import java.util.List;

public class TestSet {
	
	private Integer id;
	
	private Integer jobCodeID;
	
	private Integer userID;
	
	private Float cutoff;
	
	private String level;

	private String title;

	private List<TestSetCategory> testSetCategories;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getJobCodeID() {
		return jobCodeID;
	}

	public void setJobCodeID(Integer jobCodeID) {
		this.jobCodeID = jobCodeID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Float getCutoff() {
		return cutoff;
	}

	public void setCutoff(Float cutoff) {
		this.cutoff = cutoff;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public List<TestSetCategory> getTestSetCategories() {
		return testSetCategories;
	}

	public void setTestSetCategories(List<TestSetCategory> testSetCategories) {
		this.testSetCategories = testSetCategories;
	}
	
	public void addTestSetCategory(TestSetCategory tsc){
		if(this.testSetCategories == null){
			this.testSetCategories = new ArrayList<TestSetCategory>();
		}
		
		this.testSetCategories.add(tsc);
	}



    public Question findQuestion(Integer questionId) {
        if(testSetCategories == null) return null;

        for (TestSetCategory testSetCategory : testSetCategories) {
            for (Question question : testSetCategory.getQuestionList()) {
                if(question.getId().equals(questionId))
                    return question;
            }
        }
        return null;
    }

}
