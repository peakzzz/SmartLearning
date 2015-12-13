package edu.sjsu.assess.model;

import java.util.List;

public class Category {
    
    private Integer id;
    
    private String title;
    
    private String type;
    
    private List<Category> subCategories;
    
    private Integer parentCategoryID;

    
    /**
     * Default Constructor
     */
    public Category() {

    }

    /**
     * Constructor
     * @param id
     * @param name
     * @param type
     * @param subCategories
     */
    public Category(Integer id, String name, String type, List<Category> subCategories) {
        this.id = id;
        this.title = name;
        this.type = type;
        this.subCategories = subCategories;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

	public Integer getParentCategoryID() {
		return parentCategoryID;
	}

	public void setParentCategoryID(Integer parentCategoryID) {
		this.parentCategoryID = parentCategoryID;
	}

}
