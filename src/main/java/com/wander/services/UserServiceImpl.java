package com.wander.services;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wander.entities.User;
import com.wander.repositories.RoleRepository;
import com.wander.repositories.UserRepository;

/**
 * The Class UserServiceImpl.
 */
@Service("userService")
public class UserServiceImpl implements UserService{

	/** The user repository. */
	@Autowired
	private UserRepository userRepository;
	
    /** The role repository. */
    @Autowired
    private RoleRepository roleRepository;
    
    /** The b crypt password encoder. */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
    /**
     * Instantiates a new user service impl.
     *
     * @param userRepository the user repository
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository) { 
      this.userRepository = userRepository;
    }
    
	/* (non-Javadoc)
	 * @see com.wander.services.UserService#findByEmail(java.lang.String)
	 */
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	/* (non-Javadoc)
	 * @see com.wander.services.UserService#saveUser(com.wander.entities.User)
	 */
	public void saveUser(User user) {
		System.out.println(bCryptPasswordEncoder);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		System.out.println(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
		userRepository.save(user);
	}
	
	/* (non-Javadoc)
	 * @see com.wander.services.UserService#findByUsername(java.lang.String)
	 */
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}