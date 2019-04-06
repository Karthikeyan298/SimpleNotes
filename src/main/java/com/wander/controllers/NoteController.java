package com.wander.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.wander.entities.Note;
import com.wander.entities.User;
import com.wander.exceptions.ResourceNotFoundException;
import com.wander.services.NotesService;
import com.wander.services.UserService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Controller
@Slf4j
public class NoteController {
	
	@Autowired
	NotesService notesService;
	
	@Autowired
	UserService userService;

	@PostMapping(value="/notes/create")
    public String addNotes(@ModelAttribute("newNote") Note newNote, BindingResult bindingResult) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth);
		String email = auth.getName();
		log.info("Current user email: {}", email);
		
		notesService.addNotes(newNote, email);
		return "redirect:/welcome";		
	}
	
	@PostMapping(value="/notes/update/{id}")
    public String updateNotes(@PathVariable(value = "id") Long noteId,
    		@ModelAttribute("note") Note note) throws ResourceNotFoundException {
		notesService.updateNotes(noteId, note);
		return "redirect:/welcome";		
	}
	
	@PostMapping(value="/notes/delete/{id}")
    public String deleteNotes(@PathVariable(value = "id") Long noteId) {
		
		try {
			notesService.deleteNotes(noteId);
		}
		catch (ResourceNotFoundException e) {
			log.error("Unable to find the resourceId {}", noteId);
		}
		return "redirect:/welcome";		
	}
	
	@GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		log.debug("Current user email {}", email);
		User user = userService.findByEmail(email);
		
    	List<Note> notes = notesService.findByUser(user);
    	model.addAttribute("notes", notes);
    	
    	model.addAttribute("appName", "SimpleNotes");
        return "welcome";
    }
	
}
