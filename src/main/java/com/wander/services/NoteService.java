package com.wander.services;

import java.util.List;

import com.wander.entities.Note;
import com.wander.entities.User;
import com.wander.exceptions.ResourceNotFoundException;

/**
 * The Interface NoteService.
 */
public interface NoteService {
	
	/**
	 * Adds the notes.
	 *
	 * @param note the note
	 * @param email the email
	 * @return the note
	 */
	public Note addNotes(Note note, String email);
	
    /**
     * Update notes.
     *
     * @param noteId the note id
     * @param noteDetails the note details
     * @return the note
     * @throws ResourceNotFoundException the resource not found exception
     */
    public Note updateNotes(Long noteId, Note noteDetails) throws ResourceNotFoundException;
	
    /**
     * View all notes.
     *
     * @return the list
     */
    public List<Note> viewAllNotes();
	
    /**
     * Delete notes.
     *
     * @param noteId the note id
     * @return the string
     * @throws ResourceNotFoundException the resource not found exception
     */
    public String deleteNotes(Long noteId) throws ResourceNotFoundException;

	/**
	 * Find by id.
	 *
	 * @param noteId the note id
	 * @return the note
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	public Note findById(Long noteId) throws ResourceNotFoundException;
	
	/**
	 * Find by user.
	 *
	 * @param user the user
	 * @return the list
	 */
	public List<Note> findByUser(User user);

}
