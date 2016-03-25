package edu.sjsu.assess.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.sjsu.assess.exception.DiscussionForumException;
import edu.sjsu.assess.exception.JobCodeException;
import edu.sjsu.assess.exception.QuestionException;
import edu.sjsu.assess.model.DiscussionForumSearchParams;
import edu.sjsu.assess.model.ForumPost;
import edu.sjsu.assess.model.ForumReply;
import edu.sjsu.assess.model.JobCode;
import edu.sjsu.assess.model.JobCodeSearchParams;
import edu.sjsu.assess.model.Question;
import edu.sjsu.assess.model.QuestionSearchParams;
import edu.sjsu.assess.service.DiscussionForumServiceImpl;
import edu.sjsu.assess.service.Pagination;


@Controller
@RequestMapping("/forum")
public class DiscussionForumControllerImpl implements DiscussionForumController{

	@Autowired
    private DiscussionForumServiceImpl discussionForumService;
	
	@Override
    @RequestMapping(value="forum/{id}", method = RequestMethod.GET)
    public String getPost(@PathVariable("id") Integer id, @RequestParam(value="update", required=false) boolean isUpdate, Model model)
    {
    	System.out.println("in getPost method of controller.");
    	model.addAttribute("error", "");
        model.addAttribute("message", "");
        List<ForumPost> forumPostList = new ArrayList<>();
        try{
        	DiscussionForumSearchParams searchParams = new DiscussionForumSearchParams();
        	searchParams.setId(id);
            forumPostList = discussionForumService.getForumPostList(searchParams);
        }catch(DiscussionForumException e){
            model.addAttribute("error", e.getMessage());
        }
        if(forumPostList != null && forumPostList.size() > 0){
            if(isUpdate) {
                model.addAttribute("question", forumPostList.get(0));
                return "updateQuestion";
            }
            model.addAttribute("questions", forumPostList);
        }

        return "viewPost";
    }
	
	/*method to return a page to create a post*/
   @Override
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String loadCreatePost(Model model) {
	   ForumPost forumPost = new ForumPost();
        model.addAttribute("forumPost", forumPost);
        System.out.println("Hi in loadCreatePost method");
        return "createPost";
    }
	   
	/*method to create a post by the user
	 * invoked when user clicks on create post button*/
	@Override
	@RequestMapping(value="/createPost", method = RequestMethod.POST)
	public String createPost(ForumPost forumPost, Model model, RedirectAttributes redirectAttributes) {
		ForumPost savedforumPost = null;
		model.addAttribute("error", "");
        model.addAttribute("message", "");
        
        if (forumPost == null) {
            model.addAttribute("error", "Null forumPost Object!");
        }
        else { 
           try {
        	    System.out.println("Hi in controller createPost method: "+forumPost.getDescription());
        	    savedforumPost = discussionForumService.saveForumPost(forumPost);
                model.addAttribute("savedforumPost", savedforumPost);
                model.addAttribute("message",
                        "Post saved successfully.");
                redirectAttributes.addAttribute("message",
                        "Post saved successfully.");
             } catch (DiscussionForumException e) {
                 model.addAttribute("error", e.getMessage());
                redirectAttributes.addAttribute("error", e.getMessage());
             }
        }
        List<ForumPost> forumPosts = new ArrayList<ForumPost>();
        forumPosts.add(forumPost);
        System.out.println("Id of forumPost :"+forumPost.getId());
        model.addAttribute("forumPosts", forumPosts);
        return "redirect:get/"+forumPost.getId();
        
	}
	/*method to view all the posts*/
	@Override
	@RequestMapping(value="/viewPost", method = RequestMethod.GET)
	public String viewPost(Model model) {
		
		System.out.println("Hi in viewPost method");
		model.addAttribute("error", "");
        model.addAttribute("message", "");
        try{
            List<ForumPost> forumPosts = discussionForumService.getPosts();
           // System.out.println("viewing posts:" + forumPosts.get(0).getUserID());           
            model.addAttribute("forumPosts",forumPosts);
            
            //model.addAttribute("jsonForumPosts"+jsonForumPosts);
            
        }
        catch(DiscussionForumException e){
            model.addAttribute("error", e.getMessage());
        }
		return "viewPosts";
	}
	/*method to view particular post*/
	@Override
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public String getPost(@PathVariable("id") Integer id, Model model) {
		
		model.addAttribute("error", "");
		model.addAttribute("message", "");

		try {
			ForumPost forumPost = discussionForumService.getPostByID(id);
			model.addAttribute("forumPost", forumPost);
			List<ForumReply> forumreplys = discussionForumService.getReplys(id);
	           // System.out.println("viewing posts:" + forumPosts.get(0).getUserID());           
	        model.addAttribute("forumReplys",forumreplys);
	         
		} catch (DiscussionForumException e) {
			model.addAttribute("error", e.getMessage());
		}

		return "viewPost";
	}
	
   
   @RequestMapping(value="/reply/{id}", method = RequestMethod.GET)
   public String replyPost(@PathVariable("id") Integer id, Model model) {
	   System.out.println("Hi in replyPost method");
	   try {	
			ForumPost forumPost = discussionForumService.getPostByID(id);
			model.addAttribute("forumPost", forumPost);
	   } catch (DiscussionForumException e) {
			model.addAttribute("error", e.getMessage());
	   }
       return "replyPost";
   }
   /*create reply button invokes this method*/
    @Override
    @RequestMapping(value="/createReply", method = RequestMethod.POST)
	public String createReply(ForumReply forumReply, Model model,
			RedirectAttributes redirectAttributes) {
	   ForumReply savedforumReply = null;
		model.addAttribute("error", "");
       model.addAttribute("message", "");
       
       if (forumReply == null) {
           model.addAttribute("error", "Null forumReply Object!");
       }
       else { 
          try {
	       	    //System.out.println("Hi in controller createReply method: "+forumReply.getDescription()
	       	    //		+",name:"+ forumReply.getFname() + ",postid:"+forumReply.getForumPostId());
	       	    //System.out.println("forumpostid----------->"+id);
	      
	       	    savedforumReply = discussionForumService.saveForumReply(forumReply);
	            model.addAttribute("savedforumReply", savedforumReply);
	            model.addAttribute("message",
	                       "Post saved successfully.");
	            redirectAttributes.addAttribute("message",
                       "Post saved successfully.");
            }catch (DiscussionForumException e) {
                model.addAttribute("error", e.getMessage());
                redirectAttributes.addAttribute("error", e.getMessage());
            }
       }
       List<ForumReply> ForumReplys = new ArrayList<ForumReply>();
       ForumReplys.add(forumReply);
       System.out.println("Id of ForumReply :"+forumReply.getId());
       model.addAttribute("ForumReplys", ForumReplys);
       return "redirect:forum/get/"+forumReply.getForumPostId();
	}

    /*reply button invokes this method*/
	@Override
	 @RequestMapping(value="/reply", method = RequestMethod.GET)
	public String loadCreateReply(@RequestParam(value = "postId") Integer postId,Model model) {
		ForumReply forumReply = new ForumReply();
		model.addAttribute("error", "");
		model.addAttribute("message", "");

		try {
			ForumPost forumPost = discussionForumService.getPostByID(postId);
			model.addAttribute("forumPost", forumPost);
		} catch (DiscussionForumException e) {
			model.addAttribute("error", e.getMessage());
		}
		forumReply.setForumPostId(postId);
        model.addAttribute("forumReply", forumReply);
        System.out.println("Hi in loadCreateReply method:"+postId);
        return "replyPost";
	} 

}