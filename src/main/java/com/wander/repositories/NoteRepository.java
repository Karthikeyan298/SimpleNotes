package com.wander.repositories;

import com.wander.entities.Note;
import com.wander.entities.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
	List<Note> findByUser(User user);

}