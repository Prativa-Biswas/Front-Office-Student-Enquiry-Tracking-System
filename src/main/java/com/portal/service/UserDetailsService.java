package com.portal.service;

import com.portal.dto.LoginForm;
import com.portal.dto.RegistrationForm;
import com.portal.dto.UnlockAccountForm;

public interface UserDetailsService {

	public Boolean userRegistration(RegistrationForm regForm);
	public String userLogin(LoginForm login);
	public String userAccountUnlock(UnlockAccountForm accForm);
	public String ForgotPassword(String email);
}
