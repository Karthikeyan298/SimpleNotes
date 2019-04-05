package com.wander.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wander.entities.Note;
import com.wander.entities.User;
import com.wander.exceptions.ResourceNotFoundException;
import com.wander.repositories.NoteRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotesService {
	
	@Autowired
	NoteRepository noteRepository;
	
	@Autowired
	UserService userService;
	
    public Note addNotes(Note note, String email) {
    	User user = userService.findByEmail(email);
    	note.setUser(user);
		return noteRepository.save(note);		
	}
	
    public Note updateNotes(Long noteId, Note noteDetails) throws ResourceNotFoundException {
    	Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        note.setTitle(noteDetails.getTitle());
        note.setDescription(noteDetails.getDescription());

        Note updatedNote = noteRepository.save(note);
        log.debug("{} Note updated successfully", noteDetails.getTitle());
        
        return updatedNote;		
	}
	
    public List<Note> viewAllNotes() {
		return noteRepository.findAll();		
	}
	
    public String deleteNotes(Long noteId) throws ResourceNotFoundException {
    	Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        noteRepository.delete(note);
		return "success";	
	}

	public Note findById(Long noteId) throws ResourceNotFoundException {
		return noteRepository.findById(noteId)
	            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
	}
	
	public List<Note> findByUser(User user) {
		return noteRepository.findByUser(user);
	}
	
}
