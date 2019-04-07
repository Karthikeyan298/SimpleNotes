package com.wander.services;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * The Class SecurityServiceImpl.
 */
@Service

/** The Constant log. */
public class SecurityServiceImpl implements SecurityService{
    
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