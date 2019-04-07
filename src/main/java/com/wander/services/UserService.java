package com.wander.services;

import com.wander.entities.User;

public interface UserService {
    
	public User findByEmail(String email);
	
	public void saveUser(User user);
	
	public User findByUsername(String username);
	
}
