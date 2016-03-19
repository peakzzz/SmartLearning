package edu.sjsu.assess.controller;

import org.springframework.ui.Model;


/**
 * Created by Sindhu on 4/14/15.
 */
public interface TestAttemptController {

    public String listTestAttempts(Model model);
    //public String showATestAttempt();
    
    public String viewrecommendationlist(Model model);
}
