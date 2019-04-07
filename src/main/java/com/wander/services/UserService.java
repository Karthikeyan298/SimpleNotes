package com.wander.services;

import com.wander.entities.User;

/**
 * The Interface UserService.
 */
public interface UserService {
    
	/**
	 * Find by email.
	 *
	 * @param email the email
	 * @return the user
	 */
	public User findByEmail(String email);
	
	/**
	 * Save user.
	 *
	 * @param user the user
	 */
	public void saveUser(User user);
	
	/**
	 * Find by username.
	 *
	 * @param username the username
	 * @return the user
	 */
	public User findByUsername(String username);
	
}
