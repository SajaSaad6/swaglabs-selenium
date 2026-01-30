package com.qa.swaglabs.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage extends BasePage{

	public CartPage(WebDriver driver) {
		super(driver);
	}

	private By continueShoppingButton = By.id("continue-shopping");
	private By checkoutButton = By.id("checkout");
	
	private By productCard = By.cssSelector("[data-test=\"inventory-item\"]");
	
	
	public void continueShopping() {
		clickElement(continueShoppingButton);
	}
	
	public void clickCheckoutButton() {
		clickElement(checkoutButton);
	}
	
	public List<WebElement> getProductsInCart() {
		//wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productCard));
		return driver.findElements(productCard);
	}
	
	public void removeProductFromCart(String itemId) {
		By removeButton = By.id(itemId);
		waitForElement(removeButton);
	    clickElement(removeButton);
	}
}
