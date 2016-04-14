package edu.sjsu.assess.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.sjsu.assess.exception.JobCodeException;
import edu.sjsu.assess.service.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.sjsu.assess.exception.QuestionException;
import edu.sjsu.assess.exception.TestSetException;
import edu.sjsu.assess.model.Category;
import edu.sjsu.assess.model.Question;
import edu.sjsu.assess.model.QuestionSearchParams;
import edu.sjsu.assess.model.TestSet;
import edu.sjsu.assess.model.TestSetCategory;
import edu.sjsu.assess.model.TestSetSearchParams;
import edu.sjsu.assess.service.CategoryServiceImpl;
import edu.sjsu.assess.service.QuestionServiceImpl;
import edu.sjsu.assess.service.TestSetServiceImpl;
import edu.sjsu.assess.util.Utility;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by bjoshi on 3/24/15.
 */
@Controller
@RequestMapping("/question")
public class QuestionsControllerImpl implements QuestionsController
{
	@Autowired
    private QuestionServiceImpl questionService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private TestSetServiceImpl testSetService;

    @Override
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public String getQuestion(@PathVariable("id") Integer id, @RequestParam(value="update", required=false) boolean isUpdate, Model model)
    {
    	model.addAttribute("error", "");
        model.addAttribute("message", "");
        List<Question> qsList = new ArrayList<>();
        try{
        	QuestionSearchParams searchParams = new QuestionSearchParams();
        	searchParams.setId(id);
            qsList = questionService.getQuestionsList(searchParams);
        }
        catch(QuestionException e){
            model.addAttribute("error", e.getMessage());
        }
        if(qsList != null && qsList.size() > 0){
            if(isUpdate) {
                model.addAttribute("question", qsList.get(0));
                return "updateQuestion";
            }
            model.addAttribute("questions", qsList);
        }

        return "viewQuestion";
    }

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

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public String createQuestion(Question question, Model model, RedirectAttributes redirectAttributes)
    {
    	Question savedQS = null;
        model.addAttribute("error", "");
        model.addAttribute("message", "");

        if (question == null) {
            model.addAttribute("error", "Null Question Object!");
        }
        else {
           try {
                savedQS = questionService.saveQuestion(question);
                model.addAttribute("qs", savedQS);
                model.addAttribute("message",
                        "Question saved successfully.");
               redirectAttributes.addAttribute("message",
                       "Question saved successfully.");
            } catch (QuestionException e) {
                model.addAttribute("error", e.getMessage());
               redirectAttributes.addAttribute("error", e.getMessage());
            }
        }
        List<Question> questions = new ArrayList<Question>();
        questions.add(question);

        model.addAttribute("questions", questions);
        return "redirect:question/"+question.getId();
    }

    @Override
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public String deleteQuestion(Integer id, Model model)
    {
    	model.addAttribute("error", "");
        model.addAttribute("message", "");
        
        if(id == null){
            model.addAttribute("error", "Search parameters missing!");
        }
        else{
        	QuestionSearchParams searchParams = new QuestionSearchParams();
            searchParams.setId(id);
            
            try{
                questionService.deleteQuestion(searchParams);
            }catch(QuestionException e){
                model.addAttribute("error", e.getMessage());
            }
        }
        return "question";
    }

    @Override
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public String updateQuestion(Question question, Model model)
    {
        List<Question> questions = new ArrayList<>();
    	model.addAttribute("error", "");
        model.addAttribute("message", "");

        try{
            questionService.updateQuestion(question);
            questions.add(question);
            model.addAttribute("questions", questions);
        }catch(QuestionException e){
            model.addAttribute("error", e.getMessage());
        }
        return "viewQuestion";
    }

    @Override
    @RequestMapping(value="/category/{categoryId}/level/{level}", method = RequestMethod.GET)
    public String getQuestionsForLevel(@PathVariable("categoryId") String categoryId, @PathVariable("level") String level, Model model)
    {
        List<Question> questionList = new ArrayList<>();
        QuestionSearchParams searchParams = new QuestionSearchParams();
        searchParams.setCategoryID(Integer.parseInt(categoryId));
        searchParams.setLevel(level);

        try {
            questionList = questionService.getQuestionsList(searchParams);
            if(questionList!=null && questionList.size()>0) {
                model.addAttribute("questionList", questionList);
            }
            else {
                model.addAttribute("error", "No questions found");
            }
        } catch (QuestionException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "questionList";
    }


    @Override
    @RequestMapping(value="/category/{categoryId}", method = RequestMethod.GET)
    public String getAllQuestionsForCategory(@PathVariable("categoryId") String categoryId,
            @RequestParam(value="testsetId", required = false) String testId, @RequestParam(value="testsetCategoryId", required = false) String categorySetId, Model model)
    {
        List<Question> questionList = new ArrayList<>();
        QuestionSearchParams searchParams = new QuestionSearchParams();
        searchParams.setCategoryID(Integer.parseInt(categoryId));

        try {
            questionList = questionService.getQuestionsList(searchParams);

            if(questionList!=null && questionList.size()>0) {
                model.addAttribute("questionList", questionList);
            }
            else {
                model.addAttribute("error", "No questions found");
            }
        } catch (QuestionException e) {
            model.addAttribute("error", e.getMessage());
        }
        if(testId!=null)
            model.addAttribute("testId", testId);
        if(categorySetId!=null)
            model.addAttribute("testsetCategoryId", categorySetId);

        return "questionList";
    }

    @Override
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String loadQuestionForNewQuestion (String setCategoryId, Model model) {
    	System.out.println("questionscontrollerimpl  :"+setCategoryId);
        Question question = new Question();
        model.addAttribute("question", question);
        model.addAttribute("categories", getAllCategories());
        return "createquestion";
    }

    @RequestMapping(value = "/new-question/{testsetId}/{setCategoryId}", method = RequestMethod.GET)
    public String loadQuestionForNewQuestion (@PathVariable(value="testsetId") String testsetId,
            @PathVariable(value="setCategoryId") String setCategoryId, Model model) {
        Question question = new Question();
        model.addAttribute("question", question);
        Set<Category> categories = new HashSet<>();

        if(Utility.isValidInteger(testsetId)
                && Utility.isValidInteger(setCategoryId)) {
            try {
                TestSet testset = testSetService.getTestsetById(Integer.parseInt(testsetId));
                categories.addAll(getCategoriesForJobCode(testset.getJobCodeID(), null));
                for(TestSetCategory setCategory: testset.getTestSetCategories()) {
                    if(setCategory.getId()==Integer.parseInt(setCategoryId)) {
                        if(setCategory.getCategory()!=null) {
                            categories.add(setCategory.getCategory());
                            if(setCategory.getCategory().getSubCategories()!=null) {
                                categories.addAll(setCategory.getCategory().getSubCategories());
                            }
                        }
                        question.setCategoryID(setCategory.getCategoryID());
                    }
                }
            }
            catch (TestSetException e) {
                e.printStackTrace();
            }
            catch (JobCodeException e) {
                e.printStackTrace();
            }
            if(categories.size()==0) {
                    categories.addAll(getAllCategories());
            }
            model.addAttribute("categories", categories);
            model.addAttribute("testsetId", testsetId);
            model.addAttribute("setCategoryId", setCategoryId);
        }
        return "createquestion";
    }

    // TODO: add information about testset & setcategory to add question page
    private Model loadTestsetData(int testsetId, int setCategoryId, Model model) {
        TestSetSearchParams searchParams = new TestSetSearchParams();
        searchParams.setId(testsetId);
        try {
            List<TestSet> testSetList = testSetService.getTestSetList(searchParams);
            if(testSetList!=null && testSetList.size()>0) {
                TestSet testset = testSetList.get(0);
                TestSetCategory setCategory = null;
                for(TestSetCategory set: testset.getTestSetCategories()) {
                    if(set.getId()==setCategoryId) {
                        model.addAttribute("setCategory", set);
                        set.setQuestionList(new ArrayList<Question>());
                    }
                }
                testset.setTestSetCategories(new ArrayList<TestSetCategory>());
                model.addAttribute("testset", testset);
            }
        }
        catch (TestSetException e) {
            e.printStackTrace();
        }
        return model;
    }

    private List<Category> getAllCategories() {
        return categoryService.getAllPredefinedCategories();
    }

    private List<Category> getCategoriesForJobCode(int jobcodeId, Integer parentCategoryId)
    throws JobCodeException
    {
        return categoryService.getCategoriesForJobCode(jobcodeId, parentCategoryId);

    }

}
