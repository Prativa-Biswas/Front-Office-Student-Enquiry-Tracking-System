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
	
	/**
	 * Handles request to display the user registration (signup) page.
	 * 
	 * This method initializes a RegistrationForm object and adds it to the model
	 * for form binding in the signup UI.
	 * 
	 * @param model Model object used to pass data to the view
	 * @return String - returns the signup page
	 */
	@GetMapping("/signup")
	public String getSignUpPage(Model model) {

       model.addAttribute("user", new RegistrationForm());
	    return "signup";
	}
	
	
	/**
	 * Handles submission of the user registration form.
	 * 
	 * This method processes user registration by sending form data to the service layer.
	 * It returns success or error messages based on whether the registration is successful
	 * or if the user already exists with the given email.
	 * 
	 * @param user RegistrationForm object containing user input data
	 * @param model Model object used to pass response messages to the view
	 * @return String - returns the signup page with status message
	 */	
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
	
	
	/**
	 * Handles request to display the account unlock page.
	 * 
	 * This method receives the user's email as a request parameter,
	 * initializes the UnlockAccountForm with the email, and adds it
	 * to the model for form binding in the UI.
	 * 
	 * @param email Email ID of the user whose account needs to be unlocked
	 * @param model Model object used to pass data to the view
	 * @return String - returns the unlock page
	 */
	
	@GetMapping("/unlock")
	public String getunlockPage(@RequestParam("email") String email , Model model) {
			
		UnlockAccountForm unlockForm = new UnlockAccountForm();
		unlockForm.setEmail(email);
		model.addAttribute("unlockForm",unlockForm);
				
		return "unlock";
	}
	
	/**
	 * Handles submission of the account unlock form.
	 * 
	 * This method validates that the new password and confirm password match.
	 * If valid, it calls the service layer to unlock the user account.
	 * Based on the result, it sends success or error messages back to the UI.
	 * 
	 * @param unlockForm UnlockAccountForm object containing user input data
	 * @param model Model object used to pass response messages to the view
	 * @return String - returns the unlock page with status message
	 */
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
	
	
	/**
	 * Handles request to display the login page.
	 * 
	 * This method initializes a LoginForm object and adds it to the model
	 * for form binding in the login UI.
	 * 
	 * @param model Model object used to pass data to the view
	 * @return String - returns the login page
	 */
	@GetMapping("/login")
	public String getLoginPage(Model model) {
		
		model.addAttribute("loginForm", new LoginForm());
		
		return "login";
	}
	
	/**
	 * Handles submission of the login form.
	 * 
	 * This method authenticates the user by passing login credentials
	 * to the service layer. If authentication is successful, the user
	 * is redirected to the dashboard page; otherwise, an error message
	 * is displayed on the login page.
	 * 
	 * @param loginForm LoginForm object containing user credentials
	 * @param model Model object used to pass error messages to the view
	 * @return String - redirects to dashboard on success or returns login page on failure
	 */
	@PostMapping("/login")
	public String Logine( @ModelAttribute LoginForm loginForm, Model model) {
		
		String loginMessage = service.userLogin(loginForm);
		
		if(loginMessage.contains("SUCCESS")) {
			
			return "redirect:/dashboard";		}	
		
		model.addAttribute("errorMsg",loginMessage);
		
		return "login";
	}
	
	/**
	 * Handles request to display the Forgot Password page.
	 * 
	 * This method simply returns the forgot password view where
	 * the user can enter their registered email address.
	 * 
	 * @return String - returns the forgotPwd page
	 */
	
	@GetMapping("/forgotPwd")
	public String getFogotPwdPage() {
				
		return "forgotPwd";
	}
	
	
	/**
	 * Handles submission of the Forgot Password form.
	 * 
	 * This method checks if a user exists with the provided email.
	 * If available, it triggers sending the existing password to the user's email.
	 * Otherwise, it returns an error message indicating no account found.
	 * 
	 * @param email Registered email address of the user
	 * @param model Model object used to pass response messages to the view
	 * @return String - returns the forgotPwd page with status message
	 */
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
