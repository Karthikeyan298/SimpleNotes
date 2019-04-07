package com.wander.services;

import java.util.List;

import com.wander.entities.Note;
import com.wander.entities.User;
import com.wander.exceptions.ResourceNotFoundException;

public interface NoteService {
	
	public Note addNotes(Note note, String email);
	
    public Note updateNotes(Long noteId, Note noteDetails) throws ResourceNotFoundException;
	
    public List<Note> viewAllNotes();
	
    public String deleteNotes(Long noteId) throws ResourceNotFoundException;

	public Note findById(Long noteId) throws ResourceNotFoundException;
	
	public List<Note> findByUser(User user);

}
