package edu.sjsu.assess.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.assess.dao.DiscussionForumDAOImpl;
import edu.sjsu.assess.dao.UserDAOImpl;
import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.exception.DiscussionForumException;
import edu.sjsu.assess.model.DiscussionForumSearchParams;
import edu.sjsu.assess.model.ForumPost;
import edu.sjsu.assess.model.ForumReply;
import edu.sjsu.assess.model.User;
import edu.sjsu.assess.util.Utility;

@Service
public class DiscussionForumServiceImpl implements DiscussionForumService{
	
	@Autowired
	private DiscussionForumDAOImpl discussionForumDAO;
	@Autowired
	private UserDAOImpl userDAO;
	
	/*method to call the DAO method to save the post created by the user*/
	@Override
	public ForumPost saveForumPost(ForumPost forumPost) throws DiscussionForumException {
		System.out.println("Hi in service saveForumPost method"+forumPost.getDescription());
		ForumPost newforumPost = null;
		
		try {
			String userName = Utility.getLoggedInUserName();
			
			if(userName != null){
				User user = userDAO.getUserByLogin(userName);
				forumPost.setUserID(user.getId());
			}
			
			//qs.setUserID(1);
			
			newforumPost = discussionForumDAO.createForumPost(forumPost);

		} catch (DAOException e) {
			throw new DiscussionForumException("Failed to Create Post.", e);
		}

		return newforumPost;
	}
	@Override
	public List<ForumPost> getForumPostList(
			DiscussionForumSearchParams searchParams)
			throws DiscussionForumException {
		System.out.println("In getForumPostList method in service: "+ searchParams.getId());
		return null;
	}
	public List<ForumPost> getPosts() throws DiscussionForumException{
		System.out.println("In getPosts method in service: ");
		List<ForumPost> forumPosts = new ArrayList<>();
		try {
			forumPosts = discussionForumDAO.getPosts();
		} catch (DAOException e) {
			throw new DiscussionForumException("Failed to retrieve Posts.", e);
		}
		return forumPosts;
	}
	public ForumPost getPostByID(Integer id) throws DiscussionForumException{
		System.out.println("In getPostByID method in service: ");
		ForumPost forumPost = new ForumPost();
		try {
			forumPost = discussionForumDAO.getPostByID(id);
		} catch (DAOException e) {
			throw new DiscussionForumException("Failed to retrieve Post ." + id, e);
		}
		return forumPost;
	}
	public ForumReply saveForumReply(ForumReply forumReply)throws DiscussionForumException {
		System.out.println("Hi in service saveForumReply method"+forumReply.getDescription());
		ForumReply newforumReply = null;
		
		try {
			String userName = Utility.getLoggedInUserName();
			
			if(userName != null){
				User user = userDAO.getUserByLogin(userName);
				forumReply.setUserID(user.getId());
				forumReply.setFname(userName);
			}
			
			//qs.setUserID(1);
			
			
			newforumReply = discussionForumDAO.createForumReply(forumReply);

		} catch (DAOException e) {
			throw new DiscussionForumException("Failed to Create Post.", e);
		}

		return newforumReply;
	}
}