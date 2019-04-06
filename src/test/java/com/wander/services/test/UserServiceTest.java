package com.wander.services.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wander.entities.User;
import com.wander.repositories.RoleRepository;
import com.wander.repositories.UserRepository;
import com.wander.services.UserService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

	@InjectMocks
	UserService userService;

	UserService userServiceSpy;


	@Mock
	private UserRepository userRepository;
	
	@Mock
    private RoleRepository roleRepository;
	
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		userServiceSpy = Mockito.spy(userService);
	}
	
	@Test
	public void findByEmailTest() {
		User user = new User();
		doReturn(user).when(userRepository).findByEmail("1234@gmail.com");
		assertEquals(user, userService.findByEmail("1234@gmail.com"));
	}
	
	@Test
	public void saveUserTest() {
		User user = new User();
		user.setPassword("12345678");
		userService.saveUser(user);
		when(bCryptPasswordEncoder.encode("12345678")).thenReturn("$2a$10$bwwoRT7qnmYMzy7iSttDXuwZ1yfRUDTSgCjCThzP1k1hapqZ4rPCC");
		verify(userRepository).save(user);
	}
	
	@Test
	public void findByUsernameTest() {
		User user = new User();
		doReturn(user).when(userRepository).findByUsername("username");
		assertEquals(user, userService.findByUsername("username"));
	}
	
}
