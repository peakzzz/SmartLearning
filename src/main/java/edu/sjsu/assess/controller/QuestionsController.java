package edu.sjsu.assess.controller;

import org.springframework.ui.Model;

import edu.sjsu.assess.model.Question;
import edu.sjsu.assess.model.QuestionSearchParams;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by bjoshi on 3/24/15.
 */
public interface QuestionsController
{
    public String getQuestion(Integer id, boolean isUpdate, Model model);
    
    public String getQuestionsList(QuestionSearchParams searchParams, Integer pageNo, Model model);

    public String createQuestion(Question question, Model model, RedirectAttributes redirectAttributes);

    public String deleteQuestion(Integer id, Model model);

    public String updateQuestion(Question question, Model model);

    public String getQuestionsForLevel(String category, String level, Model model);

   public String getAllQuestionsForCategory(String category, String testId,
           String testsetCategoryId, Model model);

    public String loadQuestionForNewQuestion(String categoryId, Model model);
}
