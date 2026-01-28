package com.qa.swaglabs.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OverviewPage extends BasePage{

	public OverviewPage(WebDriver driver) {
		super(driver);
	}
	
	private By cancelButton = By.id("cancel");
	private By finishButton = By.id("finish");
	private By tax = By.cssSelector("[data-test=\"tax-label\"]");
	private By total = By.cssSelector("[data-test=\"total-label\"]");
	private By products = By.cssSelector("[data-test=\"inventory-item\"]");
	private By productPrice = By.cssSelector("[data-test=\"inventory-item-price\"]");
	
	public void clickCancelButton() {
		driver.findElement(cancelButton).click();
	}
	
	public void clickFinishButton() {
		driver.findElement(finishButton).click();
	}
	
	public Double getTax() {
		String taxText = driver.findElement(tax).getText();
		taxText = taxText.replace("Tax: ", "").trim();
		
		return Double.parseDouble(taxText.replace("$", ""));
	}
	
	public Double getTotalPrice() {
		String totalText = driver.findElement(total).getText();
		totalText = totalText.replace("Total: ", "").trim();
		
		return Double.parseDouble(totalText.replace("$", ""));
	}
	
	public Double getProductPrices() {
		List<WebElement> cards = driver.findElements(products);
		Double sumPrices = 0.0;
		
		for (WebElement card : cards) {
			Double price = Double.parseDouble(card.findElement(productPrice).getText().replace("$", ""));
			sumPrices = price + sumPrices;
		}
		
		return sumPrices;
	}
}
