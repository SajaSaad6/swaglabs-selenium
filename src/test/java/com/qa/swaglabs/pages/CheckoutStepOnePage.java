package com.qa.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutStepOnePage extends BasePage{

	private By firstName = By.id("first-name");
	private By lastName = By.id("last-name");
	private By postalCode = By.id("postal-code");
	private By cancelButton = By.id("cancel");
	private By continueButton = By.id("continue");
	private By errorMessage = By.cssSelector("[data-test='error']");
	private By successMessage = By.cssSelector("[data-test='complete-header']");
			
	public CheckoutStepOnePage(WebDriver driver) {
		super(driver);
	}
	
	public void fillForm(String first, String last, String code) {
		enterText(firstName, first);
		enterText(lastName, last);
		enterText(postalCode, code);
	}
	
	public void clickContinueButton() {
		clickElement(continueButton);
	}
	
	public void clickCancelButton() {
		clickElement(cancelButton);
	}
	
	public String getErrorMessage() {
		return getText(errorMessage);
	}
	
	public String getSuccessMessage() {
    	return getText(successMessage);
    }
	
}
