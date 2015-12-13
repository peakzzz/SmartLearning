package edu.sjsu.assess.trainingModule;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import edu.sjsu.assess.Application;
import edu.sjsu.assess.controller.TrainingModuleControllerImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MockServletContext.class,
        Application.class })
@WebAppConfiguration
public class TrainingModuleControllerTest {

    private MockMvc mvc;
    
    @Autowired
    TrainingModuleControllerImpl trainingModuleControllerImpl;

    @Before
    public void setup() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(trainingModuleControllerImpl)
                .build();
    }

    // @Test
    public void testMissingJobCodeInCreateTrainingModule() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/trainingModule/create").accept(
                        MediaType.TEXT_HTML)).andExpect(
                model().attribute("error",
                        is("Training module should belong to a job code.")));
    }

    @Test
    public void testMissingContentInCreateTrainingModule() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/trainingModule/create")
                        .param("jobCodeID", "jobCode1")
                        .accept(MediaType.TEXT_HTML)).andExpect(
                model().attribute("error", is("Failed to create training module.")));
    }

    @Test
    public void testSuccessfulCreateTrainingModule() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/trainingModule/create")
                        .param("title", "t2")
                        .accept(MediaType.TEXT_HTML)).andExpect(
                model().attribute("error", is("")));
    }
    
    //@Test
    public void testGetTrainingModule() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/trainingModule/get")
                        .param("id", "47ed7098-e4b0-40be-95fa-d45f5dbc9f8b")
                        .accept(MediaType.TEXT_HTML)).andExpect(
                model().attribute("error", is("")));
    }
    
    //@Test
    public void testDeleteTrainingModule() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.delete("/trainingModule/delete")
                        .param("id", "47ed7098-e4b0-40be-95fa-d45f5dbc9f8b")
                        .accept(MediaType.TEXT_HTML)).andExpect(
                model().attribute("error", is("")));
    }
    
    //@Test
    public void testUpdateTrainingModule() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/trainingModule/update")
                        .param("id", "47ed7098-e4b0-40be-95fa-d45f5dbc9f8b")
                        .param("content", "newBlahBlah")
                        .accept(MediaType.TEXT_HTML)).andExpect(
                model().attribute("error", is("")));
    }
}
