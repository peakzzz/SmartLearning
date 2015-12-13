package edu.sjsu.assess.model;

import java.util.HashMap;
import java.util.Map;

public class TrainingModule {

    private Integer id;

    private String title;

    private Integer jobCodeID;
    
    private Integer userID;

    private String focus;

    private String level;

    private String content;

    private Map<Integer, String> categoriesContent;

    public static enum CompletionStatus{
    	NOT_COMPLETE("Not Complete"), COMPLETE("Complete");
    	
    	private final String value;
    	
    	private CompletionStatus(String value) {
            this.value = value;
        }
    	
    	public String getValue(){
    	       return this.value;
    	}
    	
    	public boolean equalsValue(String otherValue){
            return (otherValue == null)? false:value.equals(otherValue);
        }
    }
    
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<Integer, String> getCategoriesContent() {
		return categoriesContent;
	}

	public void setCategoriesContent(Map<Integer, String> categoriesContent) {
		this.categoriesContent = categoriesContent;
	}
	
	
	public void addCategoryContent(Integer categoryID, String content){
		if(this.categoriesContent == null){
			this.categoriesContent = new HashMap<Integer, String>();
		}
		
		String existingContent = this.categoriesContent.get(categoryID);
		if(existingContent != null){
			this.categoriesContent.put(categoryID, existingContent + content);
		}
		else{
			this.categoriesContent.put(categoryID, content);
		}
	}

	/**
     * Default Constructor
     */
    public TrainingModule() {

    }

    
    /**
     * Constructor
     * @param id
     * @param title
     * @param jobCodeID
     * @param userID
     * @param focus
     * @param level
     * @param content
     */
    public TrainingModule(Integer id, String title, Integer jobCodeID, Integer userID,
            String focus, String level, String content) {
        this.id = id;
        this.title = title;
        this.jobCodeID = jobCodeID;
        this.userID = userID;
        this.focus = focus;
        this.level = level;
        this.content = content;
    }

}
