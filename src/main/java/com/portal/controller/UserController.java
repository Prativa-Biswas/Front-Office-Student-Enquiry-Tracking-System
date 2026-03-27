package com.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.portal.dto.LoginForm;
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
			
		UnlockAccountForm unlockForm = new UnlockAccountForm();
		unlockForm.setEmail(email);
		model.addAttribute("unlockForm",unlockForm);
				
		return "unlock";
	}
	
	@PostMapping("/unlock")
	public String unlockHandler( @ModelAttribute("unlockForm") UnlockAccountForm unlockForm, Model model) {
		
		// TODO: Check new password = confirm password
	    if(unlockForm.getNewPassword().equals(unlockForm.getConfirmPassword()))
	    {
			String msg = service.userAccountUnlock(unlockForm);
			
					if(msg.contains("SUCCESS"))			{
						model.addAttribute("message","Account Unlocked");			}
					else {
					model.addAttribute("errorMsg",msg);	 			}
	    }
	    else
	    {
	        model.addAttribute("errorMsg","New password and confirm password mismatch");
	    }
				
		return "unlock";
	}
	
	@GetMapping("/login")
	public String getLoginPage(Model model) {
		
		model.addAttribute("loginForm", new LoginForm());
		
		return "login";
	}
	
	@PostMapping("/login")
	public String Logine( @ModelAttribute LoginForm loginForm, Model model) {
		
		String loginMessage = service.userLogin(loginForm);
		
		if(loginMessage.contains("SUCCESS")) {
			
			return "redirect:/dashboard";		}	
		
		model.addAttribute("errorMsg",loginMessage);
		
		return "login";
	}
	
	
	@GetMapping("/forgotPwd")
	public String getFogotPwdPage() {
		
		
		return "forgotPwd";
	}
	
	
	
	@PostMapping("/forgotPwd")
	public String FogotPassword(@RequestParam("email") String email, Model model) {
		
		Boolean isAvailable = service.ForgotPassword(email);
		
		if(isAvailable) {
			model.addAttribute("successMsg","Kindly check your email to retrieve your password.");
		}
		else
		{
			model.addAttribute("errMsg","No user account exists for the provided email address.");
		}
		return "forgotPwd";
	}
	

}
