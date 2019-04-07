package com.wander.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wander.entities.User;

/**
 * The Interface UserRepository.
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
     
     /**
      * Find by email.
      *
      * @param email the email
      * @return the user
      */
     User findByEmail(String email);
     
     /**
      * Find by username.
      *
      * @param username the username
      * @return the user
      */
     User findByUsername(String username);
}