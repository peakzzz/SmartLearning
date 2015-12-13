package edu.sjsu.assess.model;

import java.util.ArrayList;
import java.util.List;


public class JobCode {

    private Integer id;
    
    private JobCode predefinedJobCode;
    
    private Integer predefinedJobCodeID;

    private String positionName;

    private String description;

    private String type;
    
    private Integer userID;
    
    private List<Category> firstLevelCategories;
    
    
    public static enum EntityType{
    	PREDEFINED("predefined"), USERDEFINED("userdefined");
    	
    	private final String value;
    	
    	private EntityType(String value) {
            this.value = value;
        }
    	
    	public String getValue(){
    	       return this.value;
    	}
    	
    	public boolean equalsValue(String otherValue){
            return (otherValue == null)? false:value.equals(otherValue);
        }
    }

    /**
     * Default Constructor
     */
    public JobCode() {

    }

    /**
     * Constructor
     * @param id
     * @param predefinedJobId
     * @param positionName
     * @param description
     * @param type
     * @param createdBy
     */
    public JobCode(Integer id, JobCode predefinedJobCode, String positionName, String description, String type, int userid) {
        this.id = id;
        this.predefinedJobCode = predefinedJobCode;
        this.positionName = positionName;
        this.description = description;
        this.type = type;
        this.userID = userid;
    }
    

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public JobCode getPredefinedJobCode() {
		return predefinedJobCode;
	}

	public void setPredefinedJobCode(JobCode predefinedJobCode) {
		this.predefinedJobCode = predefinedJobCode;
	}

	public Integer getPredefinedJobCodeID() {
		return predefinedJobCodeID;
	}

	public void setPredefinedJobCodeID(Integer predefinedJobCodeID) {
		this.predefinedJobCodeID = predefinedJobCodeID;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public List<Category> getFirstLevelCategories() {
		return firstLevelCategories;
	}

	public void setFirstLevelCategories(List<Category> firstLevelCategories) {
		this.firstLevelCategories = firstLevelCategories;
	}
	
	public void addFirstLevelCategory(Category category){
		if(firstLevelCategories == null){
			firstLevelCategories = new ArrayList<Category>();
		}
		
		firstLevelCategories.add(category);
	}
	

}
