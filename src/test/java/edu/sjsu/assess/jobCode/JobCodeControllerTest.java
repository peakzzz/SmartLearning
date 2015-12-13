package edu.sjsu.assess.jobCode;

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
import edu.sjsu.assess.controller.JobCodeControllerImpl;
import edu.sjsu.assess.model.JobCode;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MockServletContext.class,
        Application.class })
@WebAppConfiguration
public class JobCodeControllerTest {
	
	private MockMvc mvc;
	    
    @Autowired
    JobCodeControllerImpl jobCodeControllerImpl;

    @Before
    public void setup() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(jobCodeControllerImpl)
                .build();
    }
    
    @Test
    public void testSuccessfulCreateJobCode() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/jobcode/create")
                        .param("positionName", "QA Engineer")
                        .param("description", "BlahBlahBlah")
                        .param("type", JobCode.EntityType.PREDEFINED.getValue())
                        .param("userid", "1")
                        .accept(MediaType.TEXT_HTML)).andExpect(
                model().attribute("error", is("")));
    }
	    
    //@Test
    public void testGetJobCode() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/jobcode/get")
                        .param("id", "3")
                        .accept(MediaType.TEXT_HTML)).andExpect(
                model().attribute("error", is("")));
    }
	    
    //@Test
    public void testUpdateJobCode() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/jobcode/update")
                        .param("id", "2")
                        .param("positionName", "QA Engineer")
                        .param("description", "newBlahBlah")
                        .accept(MediaType.TEXT_HTML)).andExpect(
                model().attribute("error", is("")));
    }
	    
    //@Test
    public void testDeleteJobCode() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.delete("/jobcode/delete")
                        .param("id", "2")
                        .accept(MediaType.TEXT_HTML)).andExpect(
                model().attribute("error", is("")));
    }
	    
    //@Test
    public void testGetAllPredefinedJobCodes() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/jobcode/getAllPredefined")
                        .accept(MediaType.TEXT_HTML)).andExpect(
                model().attribute("error", is("")));
    }

}
