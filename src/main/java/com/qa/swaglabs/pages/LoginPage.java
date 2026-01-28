package com.qa.swaglabs.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
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
		driver.findElement(usernameField).sendKeys(name);
	}
	
	public void enterPassword(String password) {
		driver.findElement(passwordField).sendKeys(password);
	}
	
	public void login(String username, String password) {
		enterUsername(username);
		enterPassword(password);
		driver.findElement(loginButton).click();
	}
	
	public String getErrorMessage() {
		return driver.findElement(errorMessage).getText();
	}
	
	public void handleAlertIfPresent() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			
		} catch (NoAlertPresentException e) {
			
		}
	}
}
