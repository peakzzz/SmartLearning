package edu.sjsu.assess.model;

/**
 * This class contains parameters on the basis of which training modules are searched.
 * @author Shefali
 *
 */
public class TrainingModuleSearchParams {
	
	/*
	 *  TrainingModule ID
	 */
	private Integer id;
	
	private Integer jobcodeID;
	
	private Integer categoryID;
	
	private String focus;
	
	private String level;
	
	
	/**
	 * Default Constructor
	 */
	public TrainingModuleSearchParams() {
		// TODO Auto-generated constructor stub
	}
	
	public TrainingModuleSearchParams( Integer id, Integer jobcodeID, Integer categoryID, String focus, String level)
	{
		
		
		this.id=id;
		this.jobcodeID=jobcodeID;
		this.categoryID=categoryID;
		this.focus=focus;
		this.level=level;
		
	}
	
	
	
//	public TrainingModuleSearchParams(Integer id){
//		this.id = id;
//	}
	
	
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

}
