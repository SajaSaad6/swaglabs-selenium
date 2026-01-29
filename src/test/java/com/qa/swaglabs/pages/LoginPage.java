package com.qa.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class LoginPage extends BasePage{
	
	private By loginButton = By.id("login-button");
	private By usernameField = By.id("user-name");
	private By passwordField = By.id("password");
	private By errorMessage = By.xpath("//h3[@data-test='error']");
	
	public LoginPage(WebDriver driver) {
		super(driver);		
	}
	
	public void enterUsername(String name) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(name);
	}
	
	public void enterPassword(String password) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
	}
	
	public void login(String username, String password) {
		enterUsername(username);
		enterPassword(password);
		wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
	}
	
	public String getErrorMessage() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
	}
	
	
}
