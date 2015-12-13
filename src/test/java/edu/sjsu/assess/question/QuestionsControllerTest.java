package edu.sjsu.assess.question;

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
import edu.sjsu.assess.controller.QuestionsControllerImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MockServletContext.class,
        Application.class })
@WebAppConfiguration
public class QuestionsControllerTest {

	private MockMvc mvc;
    
    @Autowired
    QuestionsControllerImpl questionsControllerImpl;

    @Before
    public void setup() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(questionsControllerImpl)
                .build();
    }
    
    @Test
    public void testSuccessfulCreateJobCode() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/question")
                        .param("jobCodeID", "12")
                        .param("categoryID", "9")
                        .param("isMultipleChoice", "false")
                        .param("isTrueOrFalse", "true")
                        .param("questionText", "Sample Question 4")
                        .param("userid", "1")
                        .accept(MediaType.TEXT_HTML)).andExpect(
                model().attribute("error", is("")));
    }
}
