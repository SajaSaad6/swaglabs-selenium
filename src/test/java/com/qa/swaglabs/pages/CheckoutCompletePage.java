package com.qa.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutCompletePage extends BasePage {

	public CheckoutCompletePage(WebDriver driver) {
		super(driver);
	}
	
	private By thankMessage = By.cssSelector("[data-test=\"complete-header\"]");
	private By backHomeButton = By.id("back-to-products");
	
	public String getConfirmationMessage() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(thankMessage)).getText();
		
	}
	
	public void clickBackHomeButton() {
		wait.until(ExpectedConditions.elementToBeClickable(backHomeButton)).click();
	}

}
