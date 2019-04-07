package com.wander.entities;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "role")
@Getter @Setter
public class Role {
    
    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The name. */
    private String name;

    /** The users. */
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}