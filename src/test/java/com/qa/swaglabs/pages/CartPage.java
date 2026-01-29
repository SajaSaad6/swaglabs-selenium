package com.qa.swaglabs.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage{

	public CartPage(WebDriver driver) {
		super(driver);
	}

	private By continueShoppingButton = By.id("continue-shopping");
	private By checkoutButton = By.id("checkout");
	
	private By productCard = By.cssSelector("[data-test=\"inventory-item\"]");
	
	
	public void continueShopping() {
		wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton)).click();
	}
	
	public void clickCheckoutButton() {
		wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
	}
	
	public List<WebElement> getProductsInCart() {
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productCard));
		return driver.findElements(productCard);
	}
	
	public void removeProductFromCart(String itemId) {
		wait.until(ExpectedConditions.elementToBeClickable(By.id(itemId))).click();
	}
	
	public void wiatForCartPage() {
		wait.until(ExpectedConditions.urlContains("cart"));
	}
}
