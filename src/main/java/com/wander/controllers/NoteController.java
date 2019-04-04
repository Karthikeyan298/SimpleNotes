package com.wander.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.wander.entities.Note;
import com.wander.exceptions.ResourceNotFoundException;
import com.wander.services.NotesService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class NoteController {
	
	@Autowired
	NotesService notesService;
	
//	@RequestMapping("/login")
//    public String validateLogin(HttpServletRequest request) {
//		return null;		
//	}
//	
//	@RequestMapping("/signup")
//    public String addUser(HttpServletRequest request) {
//		return null;		
//	}
	
	@RequestMapping(value="notes/index", method=RequestMethod.GET)
    public String main(Model model) {
        model.addAttribute("message", "This is karthikeyan");
        model.addAttribute("tasks", "While ago sprinig app");

        return "index"; 
    }
	
	@RequestMapping(value="/notes/create", method=RequestMethod.POST)
    public Note addNotes(@Valid @RequestBody Note note) {
		return notesService.addNotes(note);		
	}
	
	@RequestMapping(value="/notes/{id}", method=RequestMethod.PUT)
    public Note updateNotes(@PathVariable(value = "id") Long noteId,
            @Valid @RequestBody Note noteDetails) throws ResourceNotFoundException {
		return notesService.updateNotes(noteId, noteDetails);		
	}
	
	@RequestMapping(value="/notes/view", method=RequestMethod.GET)
    public List<Note> viewAllNotes(HttpServletRequest request) {
		return notesService.viewAllNotes();		
	}
	
	@RequestMapping(value="/notes/delete", method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteNotes(@PathVariable(value = "id") Long noteId) throws ResourceNotFoundException {
		if (notesService.deleteNotes(noteId) == "success") {
			return ResponseEntity.ok().build();
		}
		return null;		
	}
	
	@RequestMapping(value="/notes/{id}", method=RequestMethod.GET)
	public Note getNoteById(@PathVariable(value = "id") Long noteId) throws ResourceNotFoundException {
	    return notesService.findById(noteId);
	            
	}
	
}
