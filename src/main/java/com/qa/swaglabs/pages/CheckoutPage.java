package com.qa.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage{

	private By firstName = By.id("first-name");
	private By lastName = By.id("last-name");
	private By postalCode = By.id("postal-code");
	private By cancelButton = By.id("cancel");
	private By continueButton = By.id("continue");
	private By errorMessage = By.cssSelector("[data-test='error']");
	private By successMessage = By.cssSelector("[data-test='complete-header']");
			
	public CheckoutPage(WebDriver driver) {
		super(driver);
	}
	
	//There is a better way to do it 
	public void fillForm(String first, String last, String code) {
		driver.findElement(firstName).sendKeys(first);
		driver.findElement(lastName).sendKeys(last);
		driver.findElement(postalCode).sendKeys(code);
	}
	
	public void clickContinueButton() {
		driver.findElement(continueButton).click();
	}
	
	public void clickCancelButton() {
		driver.findElement(cancelButton).click();
	}
	
	public String getErrorMessage() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
	}
	
	public String getSuccessMessage() {
    	return driver.findElement(successMessage).getText();
    }
}
