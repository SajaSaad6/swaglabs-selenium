package com.qa.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class LoginPage extends BasePage{
	
	private By loginButton = By.id("login-button");
	private By usernameField = By.id("user-name");
	private By passwordField = By.id("password");
	private By errorMessage = By.xpath("//h3[@data-test='error']");
	
	public LoginPage(WebDriver driver) {
		super(driver);		
	}
	
	public void enterUsername(String name) {
		enterText(usernameField, name);
	}
	
	public void enterPassword(String password) {
		enterText(passwordField, password);
	}
	
	public void login(String username, String password) {
		enterUsername(username);
		enterPassword(password);
		clickElement(loginButton);
	}
	
	public String getErrorMessage() {
		return getText(errorMessage);
	}
	
	
}
