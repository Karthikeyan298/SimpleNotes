package com.wander.services.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.wander.entities.Role;
import com.wander.entities.User;
import com.wander.repositories.UserRepository;
import com.wander.services.UserDetailsServiceImpl;
import static org.mockito.Mockito.*;

public class UserDetailsServiceTest {

	@InjectMocks
	UserDetailsServiceImpl userServiceDetails;
	
	@Mock
    private UserRepository userRepository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldLoadUserByUsernameWithoutRoleTest() {
		User user = new User();
		user.setEmail("1234@gmail.com");
		user.setPassword("12345678");
		user.setRoles(new HashSet<Role>());
		doReturn(user).when(userRepository).findByEmail("1234@gmail.com");
		assertEquals("1234@gmail.com", userServiceDetails.loadUserByUsername("1234@gmail.com").getUsername());
	}

	@Test
	public void shouldLoadUserByUsernameWithRoleTest() {
		User user = new User();
		Role role = new Role();
		role.setName("user");
		Set<Role> roles = new HashSet<Role>();
		roles.add(role);
		user.setRoles(roles);
		user.setEmail("1234@gmail.com");
		user.setPassword("12345678");
		user.setRoles(roles);
		doReturn(user).when(userRepository).findByEmail("1234@gmail.com");
		assertEquals("1234@gmail.com", userServiceDetails.loadUserByUsername("1234@gmail.com").getUsername());
	}

	
	@Test(expected=UsernameNotFoundException.class)
	public void shouldLoadUserByUsernameThrowUsernameNotFoundExceptionTest() {
		doReturn(null).when(userRepository).findByEmail(anyString());
		assertEquals("1234@gmail.com", userServiceDetails.loadUserByUsername("1234@gmail.com").getUsername());
	}
	
	
}
