package edu.sjsu.assess.dao;

import java.util.List;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.ForumPost;
import edu.sjsu.assess.model.ForumReply;
import edu.sjsu.assess.model.Question;

public interface DiscussionForumDAO {
	
	public ForumPost createForumPost(final ForumPost forumPost) throws DAOException;
	public List<ForumPost> getPosts() throws DAOException;
	public ForumPost getPostByID(Integer id) throws DAOException;
	public ForumReply createForumReply(final ForumReply forumReply)  throws DAOException;
}
