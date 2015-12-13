package edu.sjsu.assess.controller;

import java.util.List;

/**
 * Created by Sindhu on 4/8/15.
 */
public class TestForm {

    Integer id;
    List<Section> sections;

    public TestForm(){

    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}
