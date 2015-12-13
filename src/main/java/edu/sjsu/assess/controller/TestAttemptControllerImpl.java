package edu.sjsu.assess.controller;


import edu.sjsu.assess.exception.TestSetAttemptException;
import edu.sjsu.assess.model.TestSetAttempt;
import edu.sjsu.assess.model.TestSetAttemptSearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import edu.sjsu.assess.service.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Sindhu on 4/14/15.
 */


@Controller
@RequestMapping("/testattempt")
public class TestAttemptControllerImpl implements TestAttemptController{

    @Autowired
    private TestSetAttemptServiceImpl testSetAttemptService;

    @Autowired
    private TestSetServiceImpl testSetService;


    @Override
    @RequestMapping(value="/list", method = RequestMethod.GET)
    public String listTestAttempts(Model model) {
        TestSetAttemptSearchParams searchParams = new TestSetAttemptSearchParams();
        List<TestSetAttempt>  tsaList = null;

        try {
             tsaList = testSetAttemptService.getTestSetAttemptList(searchParams);
        } catch (TestSetAttemptException e) {
            e.printStackTrace();
        }

        if(tsaList != null && tsaList.size() > 0) {
            model.addAttribute("testAttemptList", tsaList);
        } else {
            model.addAttribute("error", "No search results");
    }

        return "listtestattmepts";
    }

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
}
