package edu.sjsu.assess.controller;

import edu.sjsu.assess.model.Category;

import java.util.List;

/**
 * Created by Sindhu on 4/8/15.
 */
public class Section {

    Category category;
    List<QuestionAnswer> qaa;


    public Section() {
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<QuestionAnswer> getQaa() {
        return qaa;
    }

    public void setQaa(List<QuestionAnswer> qaa) {
        this.qaa = qaa;
    }
}
