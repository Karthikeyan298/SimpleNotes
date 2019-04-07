package com.wander.controllers.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.wander.controllers.UserController;
import com.wander.entities.User;
import com.wander.services.UserServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * The Class UserControllerTest.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class UserControllerTest {

	/** The user controller. */
	@InjectMocks
	UserController userController;

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
	 * Should redirect unauthorized access to login form.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldRedirectUnauthorizedAccessToLoginForm() throws Exception {
		this.mockMvc.perform(get("/welcome")).andExpect(status().is3xxRedirection());
	}

	/**
	 * Login permission test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void loginPermissionTest() throws Exception {
		this.mockMvc.perform(get("/login")).andExpect(status().isOk());
	}

	/**
	 * User create with correct params test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void userCreateWithCorrectParamsTest() throws Exception {
		this.mockMvc.perform(post("/user/create").param("username", "abcdesdsdfgh").param("email", "12344@gmail.com")
				.param("password", "12345678").param("passwordConfirm", "12345678").sessionAttr("userForm", new User()))
				.andExpect(status().is3xxRedirection());

	}

	/**
	 * User create with user null params test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void userCreateWithUserNullParamsTest() throws Exception {
		this.mockMvc
				.perform(post("/user/create").param("email", "1234@gmail.com").param("password", "12345678")
						.param("passwordConfirm", "12345678").sessionAttr("userForm", new User()))
				.andExpect(redirectedUrl("/user/register"));
	}

	/**
	 * User create with short password test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void userCreateWithShortPasswordTest() throws Exception {
		this.mockMvc.perform(post("/user/create").param("username", "abcdesdsdfgh").param("email", "12344@gmail.com")
				.param("password", "12345").param("passwordConfirm", "12345").sessionAttr("userForm", new User()))
				.andExpect(redirectedUrl("/user/register"));
	}

	/**
	 * User create with wrong confirm password test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void userCreateWithWrongConfirmPasswordTest() throws Exception {
		this.mockMvc.perform(post("/user/create").param("username", "abcdesdsdfgh").param("email", "12344@gmail.com")
				.param("password", "12345678").param("passwordConfirm", "12345679").sessionAttr("userForm", new User()))
				.andExpect(redirectedUrl("/user/register"));
	}

	/**
	 * User create with invalid email id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void userCreateWithInvalidEmailIdTest() throws Exception {
		this.mockMvc.perform(post("/user/create").param("username", "abcdesdsdfgh").param("email", "12344")
				.param("password", "12345678").param("passwordConfirm", "12345679").sessionAttr("userForm", new User()))
				.andExpect(redirectedUrl("/user/register"));
	}

	/**
	 * Regitration test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void regitrationTest() throws Exception {
		this.mockMvc.perform(get("/user/register")).andExpect(status().is2xxSuccessful());
	}
}