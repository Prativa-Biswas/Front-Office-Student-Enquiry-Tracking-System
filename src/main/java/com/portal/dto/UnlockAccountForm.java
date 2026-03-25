package com.portal.dto;

import lombok.Data;

@Data
public class UnlockAccountForm {
	
	private String emil;
	private String temporaryPaasword;
	private String  newPassword;
	private String  confirmPassword;


}
