package edu.sjsu.assess.model;

public class UserSearchParams {
	
	/*
	 * User ID
	 */
	private Integer id;
	
	private String login;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    /**
	 * Default Constructor
	 */
	public UserSearchParams() {
		// TODO Auto-generated constructor stub
	}
	
	public UserSearchParams(Integer id){
		this.id = id;
	}
	
	public UserSearchParams(String login){
        this.login = login;
    }

}
