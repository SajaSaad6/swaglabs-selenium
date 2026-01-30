package com.qa.swaglabs.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutStepTwoPage extends BasePage{

	public CheckoutStepTwoPage(WebDriver driver) {
		super(driver);
	}
	
	private By cancelButton = By.id("cancel");
	private By finishButton = By.id("finish");
	private By tax = By.cssSelector("[data-test=\"tax-label\"]");
	private By total = By.cssSelector("[data-test=\"total-label\"]");
	private By products = By.cssSelector("[data-test=\"inventory-item\"]");
	private By productPrice = By.cssSelector("[data-test=\"inventory-item-price\"]");
	
	public void clickCancelButton() {
		clickElement(cancelButton);
	}
	
	public void clickFinishButton() {
		clickElement(finishButton);
	}
	
	public Double getTax() {
		String taxText = getText(tax);
		taxText = taxText.replace("Tax: ", "").trim();
		
		return Double.parseDouble(taxText.replace("$", ""));
	}
	
	public Double getTotalPrice() {
		String totalText = getText(total);
		totalText = totalText.replace("Total: ", "").trim();
		
		return Double.parseDouble(totalText.replace("$", ""));
	}
	
	public Double getSumOfProductPrices() {
		List<WebElement> cards = waitVisibilityOfAllElement(products);
		double sumPrices = 0.0;
		
		for (WebElement card : cards) {
			Double price = Double.parseDouble(card.findElement(productPrice).getText().replace("$", ""));
			sumPrices = price + sumPrices;
		}
		
		return round(sumPrices);
	}
	
	private double round(Double value) {
		return Math.round(value * 100.0) /100.0;
	}
}
