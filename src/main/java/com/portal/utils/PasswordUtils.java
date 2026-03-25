package com.portal.utils;

import java.security.SecureRandom;
import org.apache.commons.text.RandomStringGenerator;

public class PasswordUtils {
	

	public static String generateRandomPassword() {
		/*
				String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
				String pwd = RandomStringUtils.random( 6, characters );*/		

		    SecureRandom secureRandom = new SecureRandom();

		    RandomStringGenerator generator = new RandomStringGenerator.Builder()
		            .withinRange('0', 'z')
		            .filteredBy(Character::isLetterOrDigit)
		            .usingRandom(secureRandom::nextInt)
		            .build();

		    return generator.generate(6);
		}

}
