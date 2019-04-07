package com.wander.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter @Setter
public class User {
	
	/** The email. */
	@Id
	@Column(name = "email", unique = true)
	private String email;
	
	/** The password. */
	@Column(name = "password", nullable=false)
	private String password;
	
	/** The password confirm. */
	@Transient
    private String passwordConfirm;
	
	/** The username. */
	@Column(name = "username", nullable=false)
	private String username;

	/** The roles. */
	@ManyToMany
    private Set<Role> roles;
	

	/** The notes. */
	@OneToMany
	private Set<Note> notes; 

}