package com.wander.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class SecurityServiceImpl.
 */
@Service

/** The Constant log. */
@Slf4j
public class SecurityServiceImpl implements SecurityService{
    
    /** The authentication manager. */
    @Autowired
    private AuthenticationManager authenticationManager;

    /** The user details service. */
    @Autowired
    private UserDetailsService userDetailsService;

    /* (non-Javadoc)
     * @see com.wander.services.SecurityService#findLoggedInUsername()
     */
    @Override
    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails)userDetails).getUsername();
        }
        return null;
    }
}