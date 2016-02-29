package edu.sjsu.assess.controller;

import edu.sjsu.assess.model.Question;
import edu.sjsu.assess.model.TestSetSearchParams;
import org.springframework.ui.Model;

import edu.sjsu.assess.model.TestSet;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by bjoshi on 3/28/15.
 */
public interface TestsetController
{


    public String createTestset(List<String> categories, String jobcodeId, String courseId, String level, String title,
            Float[] cutoff, Float[] weightage, List<String> categorySetTitle,
            Model model, RedirectAttributes redirectAttributes);

    public String getAllTestSets(TestSetSearchParams searchParams, Integer pageNo, Model model);

    public String updateTestset(int testId, String origin, Model model, RedirectAttributes redirectAttributes);

    public String deleteSetFromJobcode(int jobcode, int testsetId,
            Model model, RedirectAttributes redirectAttributes);

    public String loadTestSetToCreate(String jobcodeId, String courseId,String origin, Model model);

    public String addQuestionsToSet(Integer[] questionIds, Integer testsetId, Integer testsetcategoryId,
            Model model, RedirectAttributes redirectAttributes);

    public String addNewQuestionToSetCategory(String testId, String setCategoryId, Question question,
            Model model, RedirectAttributes redirectAttributes);

    public String deleteQuestionsFromSetCategory(Integer[] questionIds,
            String testsetId, String setCategoryId,
            Model model, RedirectAttributes redirectAttributes);

    public String getSetCategory(String testId, String setCategoryId, Model model);

    public String getSetById(int id, Model model);

    public String getSetsForJobcode(int jobcodeId, String origin, Model model);

}
