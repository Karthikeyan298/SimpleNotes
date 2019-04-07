package com.wander.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wander.entities.Note;
import com.wander.entities.User;
import com.wander.exceptions.ResourceNotFoundException;
import com.wander.repositories.NoteRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class NoteServiceImpl.
 */
@Service

/** The Constant log. */
@Slf4j
public class NoteServiceImpl implements NoteService{
	
	/** The note repository. */
	@Autowired
	NoteRepository noteRepository;
	
	/** The user service. */
	@Autowired
	UserServiceImpl userService;
	
    /* (non-Javadoc)
     * @see com.wander.services.NoteService#addNotes(com.wander.entities.Note, java.lang.String)
     */
    public Note addNotes(Note note, String email) {
    	User user = userService.findByEmail(email);
    	note.setUser(user);
		return noteRepository.save(note);		
	}
	
    /* (non-Javadoc)
     * @see com.wander.services.NoteService#updateNotes(java.lang.Long, com.wander.entities.Note)
     */
    public Note updateNotes(Long noteId, Note noteDetails) throws ResourceNotFoundException {
    	Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        note.setTitle(noteDetails.getTitle());
        note.setDescription(noteDetails.getDescription());

        Note updatedNote = noteRepository.save(note);
        log.debug("{} Note updated successfully", noteDetails.getTitle());
        
        return updatedNote;		
	}
	
    /* (non-Javadoc)
     * @see com.wander.services.NoteService#viewAllNotes()
     */
    public List<Note> viewAllNotes() {
		return noteRepository.findAll();		
	}
	
    /* (non-Javadoc)
     * @see com.wander.services.NoteService#deleteNotes(java.lang.Long)
     */
    public String deleteNotes(Long noteId) throws ResourceNotFoundException {
    	Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        noteRepository.delete(note);
		return "success";	
	}

	/* (non-Javadoc)
	 * @see com.wander.services.NoteService#findById(java.lang.Long)
	 */
	public Note findById(Long noteId) throws ResourceNotFoundException {
		return noteRepository.findById(noteId)
	            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
	}
	
	/* (non-Javadoc)
	 * @see com.wander.services.NoteService#findByUser(com.wander.entities.User)
	 */
	public List<Note> findByUser(User user) {
		return noteRepository.findByUser(user);
	}
	
}
