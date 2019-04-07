package com.wander.controllers.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.wander.controllers.NoteController;
import com.wander.entities.Note;
import com.wander.entities.User;
import com.wander.services.NoteServiceImpl;
import com.wander.services.UserServiceImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@SqlGroup({
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTest.sql"),
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTest.sql")})
public class NoteControllerTest {

	@InjectMocks
	NoteController NoteController;

	@Mock
	private NoteServiceImpl noteService;
	
	@Mock
	private UserServiceImpl userService;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
		
	}
	
	@Test
	public void shouldRedirectToLoginPage() throws Exception {
		this.mockMvc.perform(get("/welcome")).andExpect(redirectedUrl("http://localhost/login"));
	}
	
	@WithMockUser(username="1")
	@Test
	public void shouldCreateNote() throws Exception {
		this.mockMvc.perform(post("/notes/create").param("title", "abc").param("description", "xyz"))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/welcome"));
	
		
	}
	
	@WithMockUser(username="1")
	@Test
	public void shouldUpdateNote() throws Exception {
		this.mockMvc.perform(post("/notes/update/1").param("title", "abc").param("description", "xyz"))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/welcome"));
	
		
	}
	
	@WithMockUser(username="1")
	@Test
	public void shouldDeleteNote() throws Exception {
		this.mockMvc.perform(post("/notes/delete/1").param("title", "abc").param("description", "xyz"))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/welcome"));
	}
	
	@WithMockUser(username="1")
	@Test
	public void shouldRedirectToWelcomePageWithSuccessStatus() throws Exception {
		this.mockMvc.perform(get("/welcome")).andExpect(status().is2xxSuccessful());
	}


}
