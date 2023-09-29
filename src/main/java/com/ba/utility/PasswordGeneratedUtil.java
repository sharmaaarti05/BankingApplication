package com.ba.utility;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;

public class PasswordGeneratedUtil {
	private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
	private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String DIGITS = "0123456789";
	private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:'\",.<>?";
	
	
	

	public static String generateRandomPassword(int length) {

		// Create a StringBuilder to build the password
		StringBuilder passwordBuilder = new StringBuilder();

		// Create a SecureRandom object for generating random numbers
		SecureRandom random = new SecureRandom();

		// Use at least one character from each character set
		passwordBuilder.append(LOWERCASE_CHARACTERS.charAt(random.nextInt(LOWERCASE_CHARACTERS.length())));
		passwordBuilder.append(UPPERCASE_CHARACTERS.charAt(random.nextInt(UPPERCASE_CHARACTERS.length())));
		passwordBuilder.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
		passwordBuilder.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

		// Generate the remaining characters randomly
		for (int i = 4; i < length; i++) {
			String characterSet = getRandomCharacterSet(random);
			passwordBuilder.append(characterSet.charAt(random.nextInt(characterSet.length())));
		}

		// Shuffle the characters to randomize the password
		String password = shuffleString(passwordBuilder.toString());

		return password;
	}

	private static String getRandomCharacterSet(SecureRandom random) {
		String[] characterSets = { LOWERCASE_CHARACTERS, UPPERCASE_CHARACTERS, DIGITS, SPECIAL_CHARACTERS };
		return characterSets[random.nextInt(characterSets.length)];
	}

	private static String shuffleString(String input) {
		char[] characters = input.toCharArray();
		for (int i = characters.length - 1; i > 0; i--) {
			int index = 0;
			try {
				index = SecureRandom.getInstanceStrong().nextInt(i + 1);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			char temp = characters[index];
			characters[index] = characters[i];
			characters[i] = temp;
		}
		return new String(characters);
	}
}
