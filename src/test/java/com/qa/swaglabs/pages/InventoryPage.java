package com.qa.swaglabs.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class InventoryPage extends BasePage{
	
	public InventoryPage(WebDriver driver) {
		super(driver);
	}
	
	//private By burgerMenuButton = By.id("react-burger-menu-btn");
	//private By resetButton = By.id("reset_sidebar_link");
	
	private By cartBadge = By.cssSelector("[data-test='shopping-cart-badge']");
	private By cartButton = By.cssSelector("[data-test='shopping-cart-link']");
	private By sortDropdown = By.cssSelector("[data-test='product-sort-container']");
	
	private By product = By.cssSelector("[data-test=\"inventory-item\"]");
	private By productCard = By.cssSelector("[data-test='inventory-item-description']");
	private By productName = By.cssSelector("[data-test='inventory-item-name']");
	private By productPrice = By.cssSelector("[data-test='inventory-item-price']");
		
	/*
	public void resetAppState() {
		clickElement(burgerMenuButton);
		clickElement(resetButton);
	}
	*/
	public void addItem(String itemId) {
		clickElement(By.id(itemId));
	}
	
	public int getCartBadgeCount() {
		List<WebElement> badges = driver.findElements(cartBadge);
	    return badges.isEmpty() ? 0 : Integer.parseInt(badges.get(0).getText());
	}
	
	public void openCart() {
		clickElement(cartButton);
	}
	
	public void sortByValue(String value) {
		new Select(driver.findElement(sortDropdown)).selectByValue(value);
	}
	
	public List<WebElement> getProducts() {	
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(product));
	}
	
	
	public List<String> getAllProductNames() {		
		List<WebElement> cards = waitVisibilityOfAllElement(productCard);
		List<String> productNamesList = new ArrayList<>();
		
		for (WebElement card : cards) {
			String name = card.findElement(productName).getText();
			productNamesList.add(name);
		}
		return productNamesList;				
	}
	
	public List<Double> getAllProductPrices() {
		
		List<WebElement> cards = waitVisibilityOfAllElement(productCard);
		List<Double> productPricesList = new ArrayList<>();
		
		for (WebElement card : cards) {
			double price = Double.parseDouble(card.findElement(productPrice).getText().replace("$", ""));
			productPricesList.add(price);
		}
		return productPricesList;
	}
	
	public void goToProductPage(String itemId) {
		clickElement(By.id(itemId));
	}
	
	
}
