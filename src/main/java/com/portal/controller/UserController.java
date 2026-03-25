package com.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.portal.dto.RegistrationForm;
import com.portal.service.UserDetailsService;

@Controller
public class UserController {
	
	@Autowired
	private UserDetailsService service;
	
	@GetMapping("/signup")
	public String getSignUpPage(Model model) {

       model.addAttribute("user", new RegistrationForm());
	    return "signup";
	}
	
	@PostMapping("/signup")
	public String signUpHandler(@ModelAttribute("user") RegistrationForm user, Model model) {
		
		Boolean registration = service.userRegistration(user);
		if(registration) {
			
			model.addAttribute("successMsg","Registration Success , check your Mail for Furture process");
		}		
		else{
			model.addAttribute("errorMsg","Already have Account with this email");
		}
		
		return "signup";
	}
	
	
	@GetMapping("/login")
	public String getLoginPage(Model model) {
		
		
		return "login";
	}
	
	
	
	@GetMapping("/unlock/email/{email}")
	public String getunlocPage(@PathVariable String email) {
		
		
		return "unlock";
	}

}
