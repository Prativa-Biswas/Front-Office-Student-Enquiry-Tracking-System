package com.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.portal.dto.RegistrationForm;
import com.portal.dto.UnlockAccountForm;
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
	
	
	@GetMapping("/unlock")
	public String getunlockPage(@RequestParam("email") String email , Model model) {
		
		model.addAttribute("email",email);
				
		return "unlock";
	}
	
	@PostMapping("/unlock")
	public String unlockHandler(UnlockAccountForm form, Model model) {
		
		String msg = service.userAccountUnlock(form);
		model.addAttribute("message",msg);
				
		return "unlock";
	}
	
	@GetMapping("/login")
	public String getLoginPage(Model model) {
		
		
		return "login";
	}
	
	
	
	

}
