package com.qa.swaglabs.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.swaglabs.pages.LoginPage;
import com.qa.swaglabs.testdata.LoginTestData;

public class LoginTest extends BaseTest{

	private LoginPage loginPage;
	
	@BeforeMethod(alwaysRun = true)
	public void testSetup() {
		loginPage = new LoginPage(driver);
	}
	
	
	@Test (description = "Verify sucessful login with valid credentials",
			groups = {"smoke", "critical"})
	public void testLoginWithValidCredentials() {
		loginPage.login(
				LoginTestData.ValidCredential.STANDARD_USER,
				LoginTestData.ValidCredential.PASSWORD);
		
		loginPage.handleAlertIfPresent();
		
		Assert.assertTrue(loginPage.isRedirectedTo("inventory"), 
				"User should redirected to inventory page after successful login. Current URL: " +
						driver.getCurrentUrl());
	}
	
	
	@Test (description = "Verify login fails when username field is empty",
			groups = {"validation"})
	public void testLoginWithEmptyUsernameField() {
		loginPage.login(
				"",
				LoginTestData.ValidCredential.PASSWORD);
		
		Assert.assertEquals(loginPage.getErrorMessage(), 
				LoginTestData.ErrorMessages.EMPTY_USERNAME, 
				"Error message should indicate username is required");	
	}
	
	
	@Test (description = "Verify login fails password field is empty",
			groups = {"validation"})
	public void testLoginWithEmptyPasswordField() {
		loginPage.login(
				LoginTestData.ValidCredential.STANDARD_USER,
				"");
		
		Assert.assertEquals(loginPage.getErrorMessage(), 
				LoginTestData.ErrorMessages.EMPTY_PASSWORD,
				"Error message should indicate password is required.");	
	}
	
	
	@Test (description = "Verify login fails with invalid password",
			groups = {"validation"})
	public void testLoginWithInvalidPassword() {
		loginPage.login(
				LoginTestData.ValidCredential.STANDARD_USER, 
				LoginTestData.InvalidCredential.PASSWORD);
		
		Assert.assertEquals(loginPage.getErrorMessage(), 
				LoginTestData.ErrorMessages.INVALID_CREDENTIAL,
				"System should display generic error message for invalid password (security best practice");	
	}
	
	
	@Test (description = "Verify blocked user can't login",
			groups = {"validation"})
	public void testLoginWithLockedUser() {
		loginPage.login(
				LoginTestData.ValidCredential.LOCKED_OUTUSER, 
				LoginTestData.ValidCredential.PASSWORD);
		
		Assert.assertEquals(loginPage.getErrorMessage(), 
				LoginTestData.ErrorMessages.LOCKED_USER,
				"Locked user should be prevented from logging in with appropriate error message");	
	}
	
	
	@Test (description = "Verify login fails with invalid credentials",
			groups = {"validation"})
	public void testLoginWithInvalidCredentials() {
		loginPage.login(
				LoginTestData.InvalidCredential.USERNAME, 
				LoginTestData.InvalidCredential.PASSWORD);
		
		Assert.assertEquals(loginPage.getErrorMessage(), 
							LoginTestData.ErrorMessages.INVALID_CREDENTIAL, 
							"System should reject completely invalid credentials");	
	}
	
	@AfterMethod(alwaysRun = true)
	public void teardown() {
		super.tearDown();
	}
}
