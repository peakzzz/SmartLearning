package edu.sjsu.assess.controller;

import edu.sjsu.assess.exception.JobCodeException;
import edu.sjsu.assess.exception.TestSetAttemptException;
import edu.sjsu.assess.model.*;
import edu.sjsu.assess.service.*;
import edu.sjsu.assess.util.Utility;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.web.servlet.mvc.support.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.sjsu.assess.exception.QuestionException;
import edu.sjsu.assess.exception.TestSetException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static edu.sjsu.assess.model.TestSetAttempt.*;

/**
 * Created by bjoshi on 3/28/15.
 */
@Controller
@RequestMapping("/testset")
public class TestsetControllerImpl implements TestsetController
{

    @Autowired
    private JobCodeServiceImpl jobcodeService;

    @Autowired
    private TestSetServiceImpl testSetService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private QuestionServiceImpl questionService;

    @Autowired
    private TestSetAttemptServiceImpl testSetAttemptService;


    @Override
    @RequestMapping(method = RequestMethod.POST)
    public String createTestset(@RequestParam("category") List<String> categories, @RequestParam("jobcodeId") String jobcodeId,
            @RequestParam("level") String level, @RequestParam("title") String title,
            @RequestParam("cutoff") Float[] cutoffArray,
            @RequestParam("weightage") Float[] weightageArray,
            @RequestParam("setTitle") List<String> categorySetTitle, Model model,
            RedirectAttributes redirectAttributes)
    {
        System.out.println("Code id:" +jobcodeId);
        System.out.println("Level:" + level);

        for (String category : categories) {
            System.out.println(category);
        }
        TestSet newTestSet = new TestSet();
        List<Float> cutoffList = null;
        List<Float> weightageList = null;

        if(cutoffArray!=null) {
            cutoffList = new ArrayList<>(Arrays.asList(cutoffArray));
            if(cutoffList!=null && cutoffList.size() >0)
                newTestSet.setCutoff(cutoffList.remove(0));
        }
        if(weightageArray!=null)
            weightageList = new ArrayList<>(Arrays.asList(weightageArray));

        newTestSet.setLevel(level);
        newTestSet.setTestSetCategories(
                getTestSetCategories(categories, cutoffList, weightageList, null, categorySetTitle));
        newTestSet.setJobCodeID(Integer.parseInt(jobcodeId));
        newTestSet.setTitle(title);

        try {
            newTestSet = testSetService.saveTestSet(newTestSet);
        }
        catch (TestSetException e) {
            e.printStackTrace();
        }
        if(newTestSet!=null && newTestSet.getId()!=null) {
            model.addAttribute("results", newTestSet);

            StringBuilder message = new StringBuilder("New test created for position-");
            try {
                JobCodeSearchParams searchParams = new JobCodeSearchParams();
                searchParams.setId(newTestSet.getJobCodeID());
                JobCode jobcode = jobcodeService.getJobCode(searchParams);
                message.append(jobcode.getPositionName());
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("error", "Error processing further. " +
                        "Testset Created with reference: "+newTestSet.getId());
            }
            message.append(". Ref. No: "+newTestSet.getId());
            redirectAttributes.addFlashAttribute("message", message.toString());
        }
        else {
            redirectAttributes.addFlashAttribute("error", "Oops something broke...Please try after sometime");
        }
        return "redirect:/testset/"+newTestSet.getId();
    }

    @Override
    @RequestMapping(value="/search", method = RequestMethod.GET)
    public String getAllTestSets(TestSetSearchParams testSetSearchParams,
            @RequestParam(value="page", required = false) Integer pageNo, Model model)
    {
        double totalpages = 1;
        if(pageNo==null) {
            pageNo = 1;
        }

        try {
            List<TestSet> testSetList = testSetService.getTestSetList(testSetSearchParams);
            if(testSetList!=null && testSetList.size()>0) {
                if(testSetList!=null && testSetList.size() > 15) {
                    Pagination paginationSvc = new Pagination(testSetList, 15);
                    testSetList = (List<TestSet>) paginationSvc.getResultsForPage(testSetList, pageNo);
                    totalpages = paginationSvc.getTotalPages();
                }
                model.addAttribute("results", testSetList);
                model.addAttribute("page", pageNo);
                model.addAttribute("totalpages", totalpages);
            } else {
                model.addAttribute("error", "No search results");
            }
        } catch (TestSetException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error fetching results, try again later!");
        }

        model.addAttribute("categories", getCategories());
        model.addAttribute("searchParams", testSetSearchParams);
        return "searchTestset";
    }

    @Override
    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public String updateTestset(@PathVariable("id") int testId, @RequestHeader(value="referer", required = false) String origin,
            Model model, RedirectAttributes redirectAttributes) {
        try {
            TestSetSearchParams searchParams = new TestSetSearchParams();
            searchParams.setId(testId);
            List<TestSet> testSetList = testSetService.getTestSetList(searchParams);
            TestSet testset;
            if(testSetList!=null && testSetList.size()>0) {
                testset = testSetList.get(0);
                model.addAttribute("categories", getCategoriesForJobCode(testset.getJobCodeID(), null));
                model.addAttribute("testset", testSetList.get(0));
            } else {
                model.addAttribute("error", "No search results");
            }

        } catch (TestSetException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error fetching results, try again later!");
        }
        catch (JobCodeException e) {
            e.printStackTrace();
        }

        model.addAttribute("origin", origin);
        return "updateTestset";
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.POST)
    public String updateTestset(@PathVariable("id") int testId, TestSet testset,
            @RequestParam(value = "set-category", required = false) List<String> categories,
            @RequestParam(value = "set-cutoff", required = false) Float[] cutoffArray,
            @RequestParam(value = "set-weightage", required = false) Float[] weightageArray,
            @RequestParam(value = "set-title", required = false) List<String> categorySetTitle,
            Model model, RedirectAttributes redirectAttributes)
    {
        if(testset!=null) {
            List<Float> cutoffList = null;
            List<Float> weightageList = null;

            try {
                if(cutoffArray!=null) {
                    cutoffList = new ArrayList<>(Arrays.asList(cutoffArray));
                }
                if(weightageArray!=null) {
                    weightageList = new ArrayList<>(Arrays.asList(weightageArray));
                }
                List<TestSetCategory> newSets = getTestSetCategories(categories, cutoffList, weightageList, null, categorySetTitle);
                testset = testSetService.updateTestSet(testset);
                boolean result = testSetService.addSetCategories(testId, newSets);
                if(!result) {
                    redirectAttributes.addFlashAttribute("error", "Error adding new set categories.");
                }
                redirectAttributes.addFlashAttribute("message", "Testset updated successfully");
            } catch(Exception e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("error", "Error updating testset.");
                return "redirect:/testset/update/"+testId;
            }
        }
        model.addAttribute("categories", getCategories());
        return "redirect:/testset/"+testId;
    }


    @Override
    @RequestMapping(value="/delete/{jobcodeId}/{testsetId}", method = RequestMethod.GET)
    public String deleteSetFromJobcode(@PathVariable("jobcodeId") int jobcode,
            @PathVariable("testsetId") int testsetId, Model model, RedirectAttributes redirectAttributes)
    {
        TestSetSearchParams searchParams = new TestSetSearchParams();
        searchParams.setJobcodeID(jobcode);
        searchParams.setId(testsetId);

        try {
            testSetService.deleteTestSet(searchParams);
            redirectAttributes.addFlashAttribute("message", "Testset deleted successfully");
        }
        catch (TestSetException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Problem deleting the testset. Please try again later");
        }
        System.out.println("Removing testset " +testsetId+ " from jobcode "+jobcode);
        return "redirect:/testset/jobcode/"+jobcode;
    }

    @RequestMapping(value="/setcategory/delete/{testsetId}/{setcategoryId}", method = RequestMethod.GET)
    public String deleteSetCategoryFromTest(@PathVariable("testsetId") int testId,
            @PathVariable("setcategoryId") int setcategoryId,
            Model model, RedirectAttributes redirectAttributes)
    {
        System.out.println("testId: "+testId+"\tsetcategoryId: "+setcategoryId);
        try {
            testSetService.deleteSetCategoryFromTestset(setcategoryId);
            redirectAttributes.addFlashAttribute("message", "Set category deleted successfully");
        }
        catch (TestSetException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error",
                    "Error deleting Set category. Please try again later");
        }
        return "redirect:/testset/update/"+testId;
    }

    @Override
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public String getSetById(@PathVariable("id") int id, Model model)
    {
        try {
            TestSetSearchParams searchParams = new TestSetSearchParams();
            searchParams.setId(id);
            List<TestSet> testSetList = testSetService.getTestSetList(searchParams);

            if(testSetList!=null && testSetList.size()>0) {
                model.addAttribute("testset", testSetList.get(0));
            } else {
                model.addAttribute("error", "No search results");
            }

        } catch (TestSetException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error fetching results, try again later!");
        }
        return "viewTestset";
    }


    @Override
    @RequestMapping(value="/jobcode/{id}", method = RequestMethod.GET)
    public String getSetsForJobcode(@PathVariable("id") int jobcodeId,
            @RequestHeader(value="referer", required = false) String origin, Model model)
    {

        try {
            TestSetSearchParams searchParams = new TestSetSearchParams();
            searchParams.setJobcodeID(jobcodeId);
            List<TestSet> testSetList = testSetService.getTestSetList(searchParams);

            if(testSetList!=null && testSetList.size()>0) {
                model.addAttribute("results", testSetList);
            } else {
                model.addAttribute("error", "No search results");
            }

        } catch (TestSetException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error fetching results, try again later!");
        }
        model.addAttribute("origin", origin);
        model.addAttribute("jobCodeID", jobcodeId);
        return "viewtestsetlist";
    }


    @RequestMapping(value="/new", method = RequestMethod.GET)
    public String loadTestSetToCreate(@RequestParam(value="jobcodeId") String jobcodeId,
            @RequestHeader(value="referer", required = false) String origin, Model model) {

        if(jobcodeId != null ) {
            // fetch the categories for this job code
            model.addAttribute("jobcodeId", jobcodeId);
        } else {
            //fetch all predefined categories
        }
        try {
            model.addAttribute("categories", getCategoriesForJobCode(Integer.parseInt(jobcodeId), null));
        }
        catch (JobCodeException e) {
            e.printStackTrace();
            model.addAttribute("categories", getCategories());
        }
        model.addAttribute("origin", origin);
        return "createTestset";
    }

// Add questions : append url wid "questions" unlike for Delete
    @RequestMapping(value="/{testsetId}/{testsetcategoryId}/questions", method = RequestMethod.POST)
    public String addQuestionsToSet(@RequestParam("questionIds") Integer[] questionIds,
            @PathVariable("testsetId") Integer testsetId, @PathVariable("testsetcategoryId") Integer testsetcategoryId,
            Model model, RedirectAttributes redirectAttributes) {

        try {
            TestSetSearchParams searchParams = new TestSetSearchParams();
            searchParams.setId(testsetId);
            List<TestSet> testSetList = testSetService.getTestSetList(searchParams);

            if(testSetList!=null && testSetList.size()>0) {
                TestSet testSet = testSetList.get(0);

                for(TestSetCategory setCategory: testSet.getTestSetCategories()) {
                    if(setCategory.getId()==testsetcategoryId) {
                        setCategory.setQuestionList(questionService.getQuestionsByIds(Arrays.asList(questionIds)));
                        boolean questionsAdded = testSetService.addQuestionsToTestSetCategory(setCategory);

                        if(!questionsAdded) {
                            redirectAttributes.addFlashAttribute("error",
                                    "Oops...problem adding questions. please contact our technical team");
                        } else {
                            testSetList = testSetService.getTestSetList(searchParams);
                            if(testSetList!=null && testSetList.size()>0) {
                                redirectAttributes.addFlashAttribute("message",
                                        questionIds.length + " questions added to set category successfully");
                            }
                        }
                    }
                }
            } else {
                redirectAttributes.addFlashAttribute("error",
                        "Error adding question, try again later!");
            }
        } catch (TestSetException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error",
                    "Error adding question, try again later!");
        }
        catch (QuestionException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error",
                    "Error adding question, try again later!");
        }
        return "redirect:/testset/"+testsetId+"/"+testsetcategoryId;
    }

    @RequestMapping(value = "/new-question/{testsetId}/{setCategoryId}", method = RequestMethod.POST)
    public String addNewQuestionToSetCategory(@PathVariable(value="testsetId") String testsetId,
            @PathVariable(value="setCategoryId") String setCategoryId, Question question,
            Model model, RedirectAttributes redirectAttributes)
    {
        Question newQuestion = null;
        try {
            newQuestion = questionService.saveQuestion(question);
        }
        catch (QuestionException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error",
                    "Error creating question. Please try again after sometime.");
            redirectAttributes.addFlashAttribute("question", question);
            return "redirect:/question/new-question/"+testsetId+"/"+setCategoryId;
        }
        try {
            if (newQuestion != null && newQuestion.getId() != null) {
                Integer[] questionIds = {newQuestion.getId()};
                return addQuestionsToSet(questionIds, Integer.parseInt(testsetId), Integer.parseInt(setCategoryId),
                        model, redirectAttributes);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error",
                    "Error adding question to test-set. Please try again after sometime.");
        }
        return "redirect:/testset/"+testsetId+"/"+setCategoryId;
    }

    @RequestMapping(value = "/{setId}/{setCategoryId}", method = RequestMethod.POST)
    public String deleteQuestionsFromSetCategory(@RequestParam("questionIds") Integer[] questionIds,
            @PathVariable("setId") String testsetId, @PathVariable("setCategoryId") String setCategoryId,
            Model model, RedirectAttributes redirectAttributes)
    {
        int deletedQues = 0;
        try {
            if (Utility.isValidInteger(setCategoryId)) {
                if (questionIds != null && questionIds.length > 0) {
                    deletedQues = testSetService.removeQuestionFromSetCategory(Integer.parseInt(setCategoryId),
                            new ArrayList<Integer>(Arrays.asList(questionIds)));
                }
            }
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error: could not delete question from set category");
            return "redirect:/testset/"+testsetId+"/"+setCategoryId;
        }

        if(deletedQues==0) {
            redirectAttributes.addFlashAttribute("message", "No question deleted");
        } else {
            redirectAttributes.addFlashAttribute("message", deletedQues+" question deleted successfully fromm the set");
        }

        return "redirect:/testset/"+testsetId+"/"+setCategoryId;
    }

    @RequestMapping(value = "/{setId}/{setCategoryId}", method = RequestMethod.GET)
    public String getSetCategory(@PathVariable("setId") String testsetId,
            @PathVariable("setCategoryId") String setCategoryId, Model model) {
        TestSet testSet = null;
        TestSetCategory setCategory = null;

        try {
            int testId = Integer.parseInt(testsetId);
            int setId = Integer.parseInt(setCategoryId);

            TestSetSearchParams searchParams = new TestSetSearchParams();
            searchParams.setId(testId);
            List<TestSet> testSetList = testSetService.getTestSetList(searchParams);
            if(testSetList!=null && testSetList.size()>0) {
                testSet = testSetList.get(0);
                if(testSet.getTestSetCategories()!=null && testSet.getTestSetCategories().size()>0) {
                    for (TestSetCategory categorySet : testSet.getTestSetCategories()) {
                        if (categorySet.getId()==setId) {
                            setCategory = categorySet;
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            //message: wrong numbers
        }
        catch (TestSetException e) {
            e.printStackTrace();
            //message: error accessing testset
        }
        model.addAttribute("setCategory", setCategory);
        model.addAttribute("testsetId", testsetId);

        return "viewSetCategory";
    }

    private List<TestSetCategory> getTestSetCategories(List<String> categories, List<Float> cutoffList,
        List<Float> weightageList, List<Integer> questionIds, List<String> title) {

        List<TestSetCategory> testSetCategories = new ArrayList<>();
        if(categories!=null && categories.size()>0) {
            for(int i=0; i<categories.size(); i++) {
                TestSetCategory setCategory = new TestSetCategory();
                setCategory.setCategoryID(Integer.parseInt(categories.get(i)));
                setCategory.setCutoff(cutoffList.get(i));
                setCategory.setWeightage(weightageList.get(i));
                setCategory.setTitle(title.get(i));
                testSetCategories.add(setCategory);
                printTestSetCategory(setCategory);
            }
            List<Question> questionList = new ArrayList<>();
            if(questionIds!=null) {
                for (int id : questionIds) {
                    Question q = new Question();
                    q.setId(id);
                    questionList.add(q);
                }
                if (questionIds != null) {
                    for (TestSetCategory category : testSetCategories) {
                        category.setQuestionList(questionList);
                    }
                }
            }
        }
        return testSetCategories;
    }

    private List<Category> getCategories() {
        List<Category> categoryList = new ArrayList<>();
        categoryList = categoryService.getAllPredefinedCategories();
        return categoryList;
    }

    private List<Category> getCategoriesForJobCode(int jobcodeId, Integer parentCategoryId)
            throws JobCodeException
    {
        return categoryService.getCategoriesForJobCode(jobcodeId, parentCategoryId);
    }

    private void printTestSetCategory(TestSetCategory category) {
        System.out.println(category.getCategoryID());
        System.out.println(category.getCutoff());
        System.out.println(category.getWeightage());
        if(category.getQuestionList()!=null)
            System.out.println(category.getQuestionList().size());
    }



    @RequestMapping(value="/list",method = RequestMethod.GET)
    public String getTestSets(Model model)
    {
        try {
            List<TestSet> testSetList = testSetService.getTestSetList(new TestSetSearchParams());

            if(testSetList!=null && testSetList.size()>0) {
                model.addAttribute("results", testSetList);
            } else {
                model.addAttribute("error", "No search results");
            }
        } catch (TestSetException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error fetching results, try again later!");
        }

        return "testsetlist";
    }


    //@Override
    @RequestMapping(value="view/{id}", method = RequestMethod.GET)
    public String viewTestset(@PathVariable("id") int id, Model model){
        try {
            TestSetSearchParams searchParams = new TestSetSearchParams();
            searchParams.setId(id);
            List<TestSet> testSetList = testSetService.getTestSetList(searchParams);
            printTestSetCategory(testSetList.get(0).getTestSetCategories().get(0));
            if (testSetList != null && testSetList.size() > 0) {
                model.addAttribute("testset", testSetList.get(0));
            } else {
                model.addAttribute("error", "No search results");
            }

        } catch (TestSetException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error fetching results, try again later!");
        }
        return "testsetdetails";
    }



    @RequestMapping(value="submittestset", method = RequestMethod.POST)
    public String submitTestset(TestForm inputTestSet, Model model) {

        List<TestSet> testSetList = null;
        TestSetSearchParams searchParams = new TestSetSearchParams();
        searchParams.setId(inputTestSet.getId());
        try {
            testSetList = testSetService.getTestSetList(searchParams);
        } catch (TestSetException e) {
            e.printStackTrace();
        }

        TestSet originalTestSet = testSetList.get(0);
        TestSetAttempt tsa = new TestSetAttempt();
        List<Section> sectionList = inputTestSet.getSections();

        //TestAttempt properties
        tsa.setTestSetID(inputTestSet.getId());
        tsa.setAttemptDate(new Date());

        for (Section section : sectionList) {
            TestSetAttempt.CategoryWiseRecord cwr = tsa.new CategoryWiseRecord();
            cwr.setCategoryID(section.getCategory().getId());

            for (QuestionAnswer qanda : section.getQaa()) {
                TestSetAttempt.QuestionWiseRecord questionWiseRecord = tsa.new QuestionWiseRecord();
                questionWiseRecord.setCategoryID(section.getCategory().getId());
                questionWiseRecord.setQuestionID(qanda.getQuestion().getId());
                questionWiseRecord.setUserAnswers(qanda.getSelectedOptions());

                Question q = originalTestSet.findQuestion(qanda.getQuestion().getId());
                if(q != null){
                    if(q.isCorrect(qanda.getSelectedOptions())) {
                        questionWiseRecord.setIsCorrectAnswer(true);
                    }
                }

                cwr.addQuestionWiseRecord(questionWiseRecord);
            }
            tsa.addCategoryWiseRecord(cwr);

        }


        try {
            tsa = testSetAttemptService.evaluateTestSetAttempt(tsa);
            tsa = testSetAttemptService.saveTestSetAttempt(tsa);
            if (tsa != null ) {
                model.addAttribute("results", tsa);
            } else {
                model.addAttribute("error", "No search results");
            }
        } catch (TestSetAttemptException e) {
            e.printStackTrace();
        }

        return "redirect:/testattempt/attempt/results/"+tsa.getId();
    }

    @RequestMapping(value="show/{id}", method = RequestMethod.GET)
    public String showTestset(@PathVariable("id") int id, Model model){
        System.out.println("Inside showtestset");
        try {
            TestSetSearchParams searchParams = new TestSetSearchParams();
            searchParams.setId(id);
            List<TestSet> testSetList = testSetService.getTestSetList(searchParams);
            printTestSetCategory(testSetList.get(0).getTestSetCategories().get(0));

            TestSetCategory tsc = testSetList.get(0).getTestSetCategories().get(0);
            List<TestSetCategory> tscList = testSetList.get(0).getTestSetCategories();
            List<Section> sList = new ArrayList<Section>();
            TestForm tf = new TestForm();

            for (int i=0; i<tscList.size(); i++ ){
                TestSetCategory testSetCategory = tscList.get(i);
                if(testSetCategory == null || testSetCategory.getQuestionList() == null)
                    continue;
                Section sec = new Section();
                sec.setCategory(testSetCategory.getCategory());
                List<QuestionAnswer> qas = new ArrayList<QuestionAnswer>();
                for (int j=0;j< testSetCategory.getQuestionList().size(); j ++){
                    QuestionAnswer qa = new QuestionAnswer();
                    qa.setQuestion(testSetCategory.getQuestionList().get(j));
                    qa.setSelectedOptions(new ArrayList<Integer>());
                    qas.add(qa);
                }

                sec.setQaa(qas);
                sList.add(sec);
            }
            tf.setSections(sList);
            tf.setId(testSetList.get(0).getId());

            if (testSetList != null && testSetList.size() > 0) {
                model.addAttribute("testset", tf);
            } else {
                model.addAttribute("error", "No search results");
            }


        } catch (TestSetException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error fetching results, try again later!");
        }
        return "showtest";
    }

//    private Option createOption(int id, boolean isCorrect, String text) {
//        Option op1 = new Option();
//        op1.setId(id);
//        op1.setCorrectOption(isCorrect);
//        op1.setCorrectOption(isCorrect);
//        op1.setText(text);
//        return op1;
//    }

    @RequestMapping(value="/attempt/results/{id}", method = RequestMethod.GET)
    public String loadResultsOfAttempt(@PathVariable("id") String attemptId, Model model) {

        try {
            TestSetAttemptSearchParams searchParams = new TestSetAttemptSearchParams();
            searchParams.setId(Integer.parseInt(attemptId));

            List<TestSetAttempt> testSetAttempts = testSetAttemptService.getTestSetAttemptList(searchParams);
            if(testSetAttempts!=null && testSetAttempts.size()>0) {
                model.addAttribute("results", testSetAttempts.get(0));
            } else {
                model.addAttribute("error", "No search results");
            }

        } catch (TestSetAttemptException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error fetching results, try again later!");
        }
        return "viewAttemptResults";
    }

    @RequestMapping(value="/history", method = RequestMethod.GET)
    public String showAttemptHistory(@RequestParam("testid") int id, Model model) {
        TestSetAttemptSearchParams searchParams = new TestSetAttemptSearchParams();
        Map<Integer, Double> average = new HashMap<>();
        Map<Integer, TestSet> testsetMap = new HashMap<>();
        Map<Integer, List<TestSetAttempt>> attemptMap = new HashMap<>();
        if(id > 0) {
            searchParams.setTestSetID(id);
        }
        try {
            List<TestSetAttempt> attemptHistory = testSetAttemptService.getTestSetAttemptList(searchParams);
            if(attemptHistory!=null && attemptHistory.size() > 0) {
                for(TestSetAttempt attempt: attemptHistory) {
                    if(!attemptMap.containsKey(attempt.getTestSetID())) {
                        attemptMap.put(attempt.getTestSetID(), new ArrayList<TestSetAttempt>());
                    }
                    attemptMap.get(attempt.getTestSetID()).add(attempt);

                    if(!testsetMap.containsKey(attempt.getTestSetID())) {
                        TestSetSearchParams testSetSearchParams = new TestSetSearchParams();
                        testSetSearchParams.setId(attempt.getTestSetID());
                        List<TestSet> testsetResults = testSetService.getTestSetList(testSetSearchParams);
                        if(testsetResults!=null && testsetResults.size() > 0) {
                            testsetMap.put(attempt.getTestSetID(), testsetResults.get(0));
                        }
                    }
                }
                // evaluate progress
                for(Integer testId: attemptMap.keySet()) {
                    List<TestSetAttempt> attemptList = testSetAttemptService.evaluateProgress(testId, attemptMap.get(testId));
                    attemptMap.put(testId, attemptList);

                    double totalScore = 0.0;
                    for(TestSetAttempt attempt: attemptList) {
                        totalScore+=attempt.getScore();
                    }
                    average.put(testId, totalScore/attemptList.size());
                }
                model.addAttribute("results", testsetMap.keySet());
                model.addAttribute("testsetmap", testsetMap);
                model.addAttribute("attemptmap", attemptMap);
                model.addAttribute("average", average);
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "There was a problem loading history. Please try again later.");
        }
        return "testsetattempthistory";
    }


}



