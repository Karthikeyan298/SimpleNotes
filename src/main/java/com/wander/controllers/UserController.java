package com.wander.controllers;

import com.wander.entities.User;
import com.wander.exceptions.InvalidUserDetailsException;
import com.wander.services.UserService;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/user/register")
	public String registration(@ModelAttribute("flashAttr") String flashAttr,Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("error", flashAttr);
		return "register";
	}

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
	        return "redirect:/register";
		}
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

	public void validateUserParams(User user) throws InvalidUserDetailsException {

		if (user != null) {
			if (user.getUsername() == null)
				throw new InvalidUserDetailsException("Not a valid username");
			if (user.getPassword().length() < 8 && user.getPassword() != user.getPasswordConfirm())
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
