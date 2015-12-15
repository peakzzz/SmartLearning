package edu.sjsu.assess.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.sjsu.assess.exception.DiscussionForumException;
import edu.sjsu.assess.model.DiscussionForumSearchParams;
import edu.sjsu.assess.model.ForumPost;
import edu.sjsu.assess.service.DiscussionForumServiceImpl;


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
        }
        catch(DiscussionForumException e){
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
	
	/*
    @Override
    @RequestMapping(value="/search", method = RequestMethod.GET)
    public String getQuestionsList(QuestionSearchParams searchParams,
            @RequestParam(value="page", required = false) Integer pageNo, Model model){
        System.out.println("Searching questions");
        model.addAttribute("error", "");
        model.addAttribute("message", "");
        double totalpages = 1;
        if(pageNo==null) {
            pageNo = 1;
        }
        try{
            List<Question> qsList = questionService.searchQuestions(searchParams);
            if(qsList!=null && qsList.size() > 25) {
                Pagination paginationSvc = new Pagination();
                qsList = (List<Question>) paginationSvc.getResultsForPage(qsList, pageNo);
                totalpages = paginationSvc.getTotalPages();
            }
            model.addAttribute("results", qsList);
            model.addAttribute("page", pageNo);
            model.addAttribute("totalpages", totalpages);
        }
        catch(QuestionException e){
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("categories", getAllCategories());
        model.addAttribute("searchParam", searchParams);
        return "searchQuestions";
    }
    */

	
	@Override
	@RequestMapping(value="/createPost", method = RequestMethod.POST)
	public String createPost(ForumPost forumPost, Model model, RedirectAttributes redirectAttributes) {
		// TODO Auto-generated method stub
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

	@Override
	@RequestMapping(value="/viewPost", method = RequestMethod.GET)
	public String viewPost(Model model) {
		// TODO Auto-generated method stub
		System.out.println("Hi in viewPost method");
		model.addAttribute("error", "");
        model.addAttribute("message", "");
        try{
            List<ForumPost> forumPosts = discussionForumService.getPosts();
            System.out.println("viewing posts:" + forumPosts.get(0).getUserID());
            
            /*
            JsonArray jsonForumPosts = new JsonArray();
            for(ForumPost obj:forumPosts)
            {
            	// create a data set
    	        JsonObject dataset = new JsonObject();
            	// add the properties key and value to the data set
            	dataset.addProperty("title",obj.getTitle());
            	dataset.addProperty("description",obj.getDescription());
            	dataset.addProperty("username",obj.getfName());
            	//System.out.println("dataset obj"+dataset);
            	jsonForumPosts.add(dataset);
            	System.out.println("jsonForumPosts:"+jsonForumPosts);
            }
            */
            
            model.addAttribute("forumPosts",forumPosts);
            
            //model.addAttribute("jsonForumPosts"+jsonForumPosts);
            
        }
        catch(DiscussionForumException e){
            model.addAttribute("error", e.getMessage());
        }
		return "viewPosts";
	}
	
	@Override
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public String getPost(@PathVariable("id") Integer id, Model model) {
		
		model.addAttribute("error", "");
		model.addAttribute("message", "");

		try {
			ForumPost forumPost = discussionForumService.getPostByID(id);
			model.addAttribute("forumPost", forumPost);
		} catch (DiscussionForumException e) {
			model.addAttribute("error", e.getMessage());
		}

		return "viewPost";
	}
	
   @Override
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String loadCreatePost(Model model) {
	   ForumPost forumPost = new ForumPost();
        model.addAttribute("forumPost", forumPost);
        System.out.println("Hi in loadCreatePost method");
        return "createPost";
    }
   
   

}
