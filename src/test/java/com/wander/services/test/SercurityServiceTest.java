package com.wander.services.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.wander.services.SecurityServiceImpl;
import org.springframework.security.core.userdetails.*;
import static org.mockito.Mockito.*;

public class SercurityServiceTest {

	@InjectMocks
	SecurityServiceImpl securityServiceImpl;

	@Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;
    
    @Before
    public void setUp() {
    	MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void findLoggedInUsernameTest() {
    	Authentication authentication = mock(Authentication.class);
    	
    	SecurityContext securityContext = mock(SecurityContext.class);
    	when(securityContext.getAuthentication()).thenReturn(authentication);
    	SecurityContextHolder.setContext(securityContext);
    	when(SecurityContextHolder.getContext().getAuthentication().getDetails()).thenReturn(authentication);
    	securityServiceImpl.findLoggedInUsername();
    }
    
    @Test
    public void ShouldReturnNullfindLoggedInUsernameTest() {
    	Authentication authentication = mock(Authentication.class);
    	
    	SecurityContext securityContext = mock(SecurityContext.class);
    	when(securityContext.getAuthentication()).thenReturn(authentication);
    	SecurityContextHolder.setContext(securityContext);
    	when(SecurityContextHolder.getContext().getAuthentication().getDetails()).thenReturn(mock(UserDetails.class));
    	securityServiceImpl.findLoggedInUsername();
    }
	
}
