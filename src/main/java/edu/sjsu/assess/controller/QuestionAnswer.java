package edu.sjsu.assess.controller;

import edu.sjsu.assess.model.Option;
import edu.sjsu.assess.model.Question;

import java.util.List;

/**
 * Created by Sindhu on 4/8/15.
 */
public class QuestionAnswer {

    Question question;
    List<Integer> selectedOptions;

    public QuestionAnswer() {
    }


    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Integer> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<Integer> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }
}
