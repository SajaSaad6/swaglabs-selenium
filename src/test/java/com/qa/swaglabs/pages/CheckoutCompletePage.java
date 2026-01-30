package com.qa.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends BasePage {

	public CheckoutCompletePage(WebDriver driver) {
		super(driver);
	}
	
	private By thankMessage = By.cssSelector("[data-test=\"complete-header\"]");
	private By backHomeButton = By.id("back-to-products");
	
	public String getConfirmationMessage() {
		return getText(thankMessage);
		
	}
	
	public void clickBackHomeButton() {
		clickElement(backHomeButton);
	}

}
