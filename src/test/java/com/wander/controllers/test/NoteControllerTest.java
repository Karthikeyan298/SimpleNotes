package com.wander.controllers.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
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
import com.wander.services.NoteServiceImpl;
import com.wander.services.UserServiceImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * The Class NoteControllerTest.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@SqlGroup({
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTest.sql"),
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTest.sql")})
public class NoteControllerTest {

	/** The Note controller. */
	@InjectMocks
	NoteController NoteController;

	/** The note service. */
	@Mock
	private NoteServiceImpl noteService;
	
	/** The user service. */
	@Mock
	private UserServiceImpl userService;

	/** The context. */
	@Autowired
	private WebApplicationContext context;

	/** The mock mvc. */
	private MockMvc mockMvc;

	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
		
	}
	
	/**
	 * Should redirect to login page.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldRedirectToLoginPage() throws Exception {
		this.mockMvc.perform(get("/welcome")).andExpect(redirectedUrl("http://localhost/login"));
	}
	
	/**
	 * Should create note.
	 *
	 * @throws Exception the exception
	 */
	@WithMockUser(username="1")
	@Test
	public void shouldCreateNote() throws Exception {
		this.mockMvc.perform(post("/notes/create").param("title", "abc").param("description", "xyz"))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/welcome"));
	
		
	}
	
	/**
	 * Should update note.
	 *
	 * @throws Exception the exception
	 */
	@WithMockUser(username="1")
	@Test
	public void shouldUpdateNote() throws Exception {
		this.mockMvc.perform(post("/notes/update/1").param("title", "abc").param("description", "xyz"))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/welcome"));
	
		
	}
	
	/**
	 * Should delete note.
	 *
	 * @throws Exception the exception
	 */
	@WithMockUser(username="1")
	@Test
	public void shouldDeleteNote() throws Exception {
		this.mockMvc.perform(post("/notes/delete/1").param("title", "abc").param("description", "xyz"))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/welcome"));
	}
	
	/**
	 * Should redirect to welcome page with success status.
	 *
	 * @throws Exception the exception
	 */
	@WithMockUser(username="1")
	@Test
	public void shouldRedirectToWelcomePageWithSuccessStatus() throws Exception {
		this.mockMvc.perform(get("/welcome")).andExpect(status().is2xxSuccessful());
	}


}
