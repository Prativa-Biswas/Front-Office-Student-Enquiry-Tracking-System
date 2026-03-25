package com.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.portal.dto.RegistrationForm;

import ch.qos.logback.core.model.Model;


@Controller
public class UserController {
	
	@GetMapping("/login")
	public String getLoginPage(Model model) {
		
		
		return "login";
	}
	
	@GetMapping("/singup")
	public String getSignUpPage(@ModelAttribute("user") RegistrationForm user, Model model) {
		
		user= new RegistrationForm();
		
		return "signup";
	}
	
	@GetMapping("/unlock")
	public String getunlocPage() {
		
		
		return "signup";
	}

}
