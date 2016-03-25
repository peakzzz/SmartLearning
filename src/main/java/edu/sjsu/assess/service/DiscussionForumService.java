package edu.sjsu.assess.service;

import java.util.List;

import edu.sjsu.assess.exception.DiscussionForumException;
import edu.sjsu.assess.exception.QuestionException;
import edu.sjsu.assess.model.DiscussionForumSearchParams;
import edu.sjsu.assess.model.ForumPost;
import edu.sjsu.assess.model.ForumReply;
import edu.sjsu.assess.model.Question;
import edu.sjsu.assess.model.QuestionSearchParams;

public interface DiscussionForumService {
	public ForumPost saveForumPost(ForumPost forumPost) throws DiscussionForumException;
	public List<ForumPost> getForumPostList(DiscussionForumSearchParams searchParams) throws DiscussionForumException;
	public List<ForumPost> getPosts() throws DiscussionForumException;
	public ForumPost getPostByID(Integer id) throws DiscussionForumException;
	public ForumReply saveForumReply(ForumReply forumReply)throws DiscussionForumException;
}
