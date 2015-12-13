package edu.sjsu.assess.user;

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
import edu.sjsu.assess.controller.UserControllerImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MockServletContext.class,
        Application.class })
@WebAppConfiguration
public class UserControllerTest {
	
	private MockMvc mvc;
	
	@Autowired
    UserControllerImpl userControllerImpl;
	
	@Before
	public void setup() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(userControllerImpl)
				.build();
	}
	
	//@Test
	public void testErrorConditionForCreateUser() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.post("/user/create").accept(
						MediaType.TEXT_HTML)).andExpect(
				model().attribute("error", is("Invalid user")));
	}
	
	//@Test
	public void testBlankNameInCreateUser() throws Exception{
		mvc.perform(
				MockMvcRequestBuilders.post("/user/create")
				.param("password", "test")
				.param("role", "admin")
				.accept(MediaType.TEXT_HTML))
				.andExpect(model()
						.attribute("error", is("Invalid user")));
	}
	
	
	//@Test
	public void testBlankPasswordInCreateUser() throws Exception{
		mvc.perform(
				MockMvcRequestBuilders.post("/user/create")
				.param("fname", "test")
				.param("role", "admin")
				.accept(MediaType.TEXT_HTML))
				.andExpect(model()
						.attribute("error", is("Invalid user")));
	}
	
	//@Test
	public void testBlankRoleInCreateUser() throws Exception{
		mvc.perform(
				MockMvcRequestBuilders.post("/user/create")
				.param("fname", "test")
				.param("password", "test")
				.accept(MediaType.TEXT_HTML))
				.andExpect(model()
						.attribute("error", is("Invalid user")));
	}
	
	@Test
	public void testValidUserInCreateUser() throws Exception{
		mvc.perform(
				MockMvcRequestBuilders.post("/user/create")
				.param("fname", "testUser")
				.param("login", "test")
				.param("password", "test")
				.param("role", "admin")
				.accept(MediaType.TEXT_HTML))
				.andExpect(model()
						.attribute("error", is("")));
	}
	
	//@Test
	public void testEmptySearchParamsInGet() throws Exception{
		
		mvc.perform(
				MockMvcRequestBuilders.get("/user/get")
					.accept(MediaType.TEXT_HTML))
					.andExpect(
						model().attribute("error", is("Invalid Search Parameters!")));
	}
	
	//@Test
	public void testGetByID() throws Exception{
		
		mvc.perform(
				MockMvcRequestBuilders.get("/user/get")
					.param("id", "2")
					.accept(MediaType.TEXT_HTML))
					.andExpect(
						model().attribute("error", is("")));
	}
	
	//@Test
	public void testGetByLogin() throws Exception{
		
		mvc.perform(
				MockMvcRequestBuilders.get("/user/get")
					.param("login", "test")
					.accept(MediaType.TEXT_HTML))
					.andExpect(
						model().attribute("error", is("")));
	}
	
	//@Test
	public void testDeleteById() throws Exception{
		
		mvc.perform(
				MockMvcRequestBuilders.delete("/user/delete")
					.param("id", "2")
					.accept(MediaType.TEXT_HTML))
					.andExpect(
						model().attribute("error", is("")));
	}
	
	//@Test
	public void testUpdate() throws Exception{
		
		mvc.perform(
				MockMvcRequestBuilders.put("/user/update")
					.param("id", "3")
					.param("fname", "testUser2")
					.param("lname", "testUser2")
					.param("password", "test2")
					.param("role", "candidate")
					.accept(MediaType.TEXT_HTML))
					.andExpect(
						model().attribute("error", is("")));
	}

}
