package com.qa.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductDetailPage extends BasePage{

	public ProductDetailPage(WebDriver driver) {
		super(driver);
	}
	
	private By addToCartButton = By.id("add-to-cart");
	private By backToProductsButton = By.id("back-to-products");
	private By productName = By.cssSelector("[data-test=\"inventory-item-name\"]");
	private By productDetail = By.cssSelector("[data-test=\"inventory-item-desc\"]");
	private By productPrice = By.cssSelector("[data-test=\"inventory-item-price\"]");
		
	
	public void backToProducts() {
		wait.until(ExpectedConditions.elementToBeClickable(backToProductsButton)).click();
	}
	
	public String getProductName() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
	}

	public String getProductDetail() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(productDetail)).getText();
	}
	
	public Double getProductPrice() {
		return Double.parseDouble(wait.until(ExpectedConditions.visibilityOfElementLocated(productPrice)).getText());
	}
	
	public void addProductToCart() {
		wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
	}
	
}
