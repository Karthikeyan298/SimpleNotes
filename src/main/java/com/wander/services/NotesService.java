package com.wander.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wander.entities.Note;
import com.wander.exceptions.ResourceNotFoundException;
import com.wander.repositories.NoteRepository;

@Service
public class NotesService {
	
	@Autowired
	NoteRepository noteRepository;
	

    public Note addNotes(Note note) {
		return noteRepository.save(note);		
	}
	
    public Note updateNotes(Long noteId, Note noteDetails) throws ResourceNotFoundException {
    	Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        note.setTitle(noteDetails.getTitle());
        note.setDescription(noteDetails.getDescription());

        Note updatedNote = noteRepository.save(note);
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
	
}
