package com.qa.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
		driver.findElement(backToProductsButton).click();
	}
	
	public String getProductName() {
		return driver.findElement(productName).getText();
	}

	public String getProductDetail() {
		return driver.findElement(productDetail).getText();
	}
	
	public Double getProductPrice() {
		return Double.parseDouble(driver.findElement(productPrice).getText());
	}
	
	public void addProductToCart() {
		driver.findElement(addToCartButton).click();
	}
	
}
