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
		clickElement(backToProductsButton);
	}
	
	public String getProductName() {
		return getText(productName);
	}

	public String getProductDetail() {
		return getText(productDetail);
	}
	
	public Double getProductPrice() {
		return Double.parseDouble(getText(productPrice));
	}
	
	public void addProductToCart() {
		clickElement(addToCartButton);
	}
	
}
