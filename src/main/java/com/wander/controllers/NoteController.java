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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wander.entities.Note;
import com.wander.entities.User;
import com.wander.exceptions.ResourceNotFoundException;
import com.wander.services.NoteServiceImpl;
import com.wander.services.UserServiceImpl;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * The Class NoteController.
 */
@Controller

/** The Constant log. */
@Slf4j
public class NoteController {
	
	/** The notes service. */
	@Autowired
	NoteServiceImpl notesService;
	
	/** The user service. */
	@Autowired
	UserServiceImpl userService;

	/**
	 * Adds the notes.
	 *
	 * @param newNote the new note
	 * @param bindingResult the binding result
	 * @return the string
	 */
	@PostMapping(value="/notes/create")
    public String addNotes(@ModelAttribute("newNote") Note newNote, RedirectAttributes redir) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		log.info("Current user email: {}", email);
		if (isValidNote(newNote) == "")
			notesService.addNotes(newNote, email);
		else
			redir.addFlashAttribute("flashAttr", isValidNote(newNote));
		return "redirect:/welcome";		
	}
	
	/**
	 * Update notes.
	 *
	 * @param noteId the note id
	 * @param note the note
	 * @return the string
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@PostMapping(value="/notes/update/{id}")
    public String updateNotes(@PathVariable(value = "id") Long noteId,
    		@ModelAttribute("note") Note note) throws ResourceNotFoundException {
		notesService.updateNotes(noteId, note);
		return "redirect:/welcome";		
	}
	
	/**
	 * Delete notes.
	 *
	 * @param noteId the note id
	 * @return the string
	 */
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
	
	/**
	 * Welcome.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping({"/", "/welcome"})
    public String welcome(@ModelAttribute("flashAttr") String error, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		log.debug("Current user email {}", email);
		User user = userService.findByEmail(email);
		
    	List<Note> notes = notesService.findByUser(user);
    	model.addAttribute("notes", notes);
    	model.addAttribute("error", error);
    	
    	model.addAttribute("username", user.getUsername());
        return "welcome";
    }
	
	private String isValidNote(Note note) {
		if (note.getTitle() == "")
			return "Title is empty!!!";
		return "";
	}
	
}
