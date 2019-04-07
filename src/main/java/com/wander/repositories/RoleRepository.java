package com.wander.repositories;

import com.wander.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface RoleRepository.
 */
public interface RoleRepository extends JpaRepository<Role, Long>{
}