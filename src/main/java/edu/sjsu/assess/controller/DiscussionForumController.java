package edu.sjsu.assess.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.sjsu.assess.model.ForumPost;

public interface DiscussionForumController {
	public String createPost(ForumPost forumPost, Model model, RedirectAttributes redirectAttributes);
	public String viewPost(Model model);
	public String loadCreatePost(Model model);
	public String getPost(@PathVariable("id") Integer id, @RequestParam(value="update", required=false) boolean isUpdate, Model model);
	String getPost(Integer id, Model model);
}
