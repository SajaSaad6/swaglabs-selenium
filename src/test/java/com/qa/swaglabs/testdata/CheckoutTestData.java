package com.qa.swaglabs.testdata;

public class CheckoutTestData {

	public static class ValidData {
		public static final String FIRSTNAME = "Sally";
		public static final String LASTNAME = "Smith";
		public static final String ZIP = "12345";
	}
	
	public static class ErrorMessage {
		public static final String EMPTY_FIRST_NAME_FIELD = "Error: First Name is required";
		public static final String EMPTY_LAST_NAME_FIELD = "Error: Last Name is required";
		public static final String EMPTY_POSTAL_CODE_FIELD = "Error: Postal Code is required";
	}
	
	public static class ConfirmationMessage {
		public static final String CONFIRMATIONMESSAGE = "Thank you for your order!";
	}
}