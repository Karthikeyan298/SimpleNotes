package com.wander.controllers;

import com.wander.entities.User;
import com.wander.exceptions.InvalidUserDetailsException;
import com.wander.services.UserServiceImpl;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * The Class UserController.
 */
@Controller
public class UserController {

	/** The user service. */
	@Autowired
	private UserServiceImpl userService;

	/**
	 * Registration.
	 *
	 * @param flashAttr the flash attr
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/user/register")
	public String registration(@ModelAttribute("flashAttr") String flashAttr,Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("error", flashAttr);
		return "register";
	}

	/**
	 * Registration.
	 *
	 * @param userForm the user form
	 * @param model the model
	 * @param redir the redir
	 * @return the string
	 */
	@PostMapping("/user/create")
	public String registration(@ModelAttribute("userForm") User userForm, Model model, RedirectAttributes redir) {
		model.addAttribute("error", "");
		try {
			validateUserParams(userForm);
			userService.saveUser(userForm);
			return "redirect:/welcome";
		} catch (InvalidUserDetailsException e) {
			e.printStackTrace();
			redir.addFlashAttribute("flashAttr",e.getErrorMsg());
	        return "redirect:/user/register";
		}
	}

	/**
	 * Login.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

	/**
	 * Validate user params.
	 *
	 * @param user the user
	 * @throws InvalidUserDetailsException the invalid user details exception
	 */
	public void validateUserParams(User user) throws InvalidUserDetailsException {

		if (user != null) {
			if (user.getUsername() == null)
				throw new InvalidUserDetailsException("Not a valid username");
			if (user.getPassword().length() < 8)
				throw new InvalidUserDetailsException("Not a valid password");
			if (user.getEmail() != null) {
				String regex = "[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
				Pattern pattern = Pattern.compile(regex);
				if (!pattern.matcher(user.getEmail()).matches())
					throw new InvalidUserDetailsException("Not a valid emailId");
			    if (userService.findByEmail(user.getEmail()) != null ) 
			    	throw new InvalidUserDetailsException("Already registered emailId");
			} else {
				throw new InvalidUserDetailsException("Not a valid emailId");
			}
			
		} else {
			throw new InvalidUserDetailsException("Not a valid user");
		}
	}
}
