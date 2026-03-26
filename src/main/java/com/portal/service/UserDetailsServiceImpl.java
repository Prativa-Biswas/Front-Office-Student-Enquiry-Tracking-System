package com.portal.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.dto.LoginForm;
import com.portal.dto.RegistrationForm;
import com.portal.dto.UnlockAccountForm;
import com.portal.enitiy.UserDetails;
import com.portal.repository.UserDetailsRepository;
import com.portal.utils.EmailUtils;
import com.portal.utils.PasswordUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDetailsRepository userRepo;
	
	@Autowired
	private EmailUtils emailSender;
	
	@Override
	public Boolean userRegistration(RegistrationForm regForm) {
		
		String email = regForm.getEmail();
		
		// TODO: Validate email is unique
		UserDetails userByEmail = userRepo.getByEmail(email); 
		if(userByEmail!=null) {
			return false;
		}
		
		// TODO: Copy form data into entity
		UserDetails user = new  UserDetails();
		BeanUtils.copyProperties(regForm,user);
		
		// TODO: Create randomPassword
		String password = PasswordUtils.generateRandomPassword();
		user.setPassword(password);
		
		// TODO: Set Account as LOCKED at first
		     user.setAccStatus("LOACKED");
		     
	    // TODO: save data to table
		     userRepo.save(user);
		     
		// TODO: Send mail to Unlock account
		     String subject= "Unlock The Account ";
		     StringBuffer body= new StringBuffer("");
		     body.append("<h1>Use Below Temporary password to unlock your account </h1>");
		     body.append("Temporarary password: "+password);
		     body.append("<br>");
		     body.append("<a href=\"http://localhost:8080/unlock?email=" + email + "\">Click here to unlock your Account</a>");		     
		     emailSender.sendMail(email, subject, body.toString());
		     
		return true;
	}

	
	@Override
	public String userAccountUnlock(UnlockAccountForm accForm) {
		
		// TODO: Check user by email
		    UserDetails user = userRepo.getByEmail(accForm.getEmail());
		    if(user==null)
		    {
		    	return "Account not found with this email";	    }
		    
		    if("UNLOCKED".equalsIgnoreCase(user.getAccStatus()))
		    {
		    	return "Account is Already Unlock";
		    }
		    
		 // TODO: check temporary password & set entity object
		    if(!user.getPassword().equals(accForm.getTemporaryPassword()))
		    {
		    	return "Temporaray password is  Incorrect";	    }
		    
			// TODO: Save Object   
		    	String newPassword = accForm.getNewPassword();
		    	user.setAccStatus("UNLOCKED");
		    	user.setPassword(newPassword);
		    	userRepo.save(user);
		   	
		return "SUCCESS";
	}

	
	
	@Override
	public String userLogin(LoginForm login) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public String ForgotPassword(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
