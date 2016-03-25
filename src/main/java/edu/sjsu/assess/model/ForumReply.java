package edu.sjsu.assess.model;

public class ForumReply {
	private int id;
	private Integer userID;
	private int forumPostId;
	private String fname;
	private String description;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public int getForumPostId() {
		return forumPostId;
	}
	public void setForumPostId(int forumPostId) {
		this.forumPostId = forumPostId;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}
