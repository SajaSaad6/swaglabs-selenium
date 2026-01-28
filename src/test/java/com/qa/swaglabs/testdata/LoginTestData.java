package com.qa.swaglabs.testdata;

public class LoginTestData {

	public static class ValidCredential {
		public static final String STANDARD_USER = "standard_user";
		public static final String LOCKED_OUTUSER = "locked_out_user";
		public static final String PROBLEM_USER = "problem_user";
		public static final String ERROR_USER = "error_user";
		public static final String VISUAL_USER = "visual_user";
		public static final String PASSWORD = "secret_sauce";
	}
	
	public static class InvalidCredential {
		public static final String USERNAME = "InvalidUser";
		public static final String PASSWORD = "invalidPassword";
	}
	
	public static class ErrorMessages {
		public static final String EMPTY_USERNAME = "Epic sadface: Username is required";
		public static final String EMPTY_PASSWORD = "Epic sadface: Password is required";
		public static final String INVALID_CREDENTIAL = "Epic sadface: Username and password do not match any user in this service";
		public static final String LOCKED_USER = "Epic sadface: Sorry, this user has been locked out.";
	}
}
