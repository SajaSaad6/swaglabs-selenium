package com.qa.swaglabs.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.swaglabs.pages.CartPage;
import com.qa.swaglabs.pages.InventoryPage;
import com.qa.swaglabs.pages.LoginPage;
import com.qa.swaglabs.pages.ProductDetailPage;
import com.qa.swaglabs.testdata.InventoryTestData;
import com.qa.swaglabs.testdata.LoginTestData;

public class PLPTest extends BaseTest{
	
	private InventoryPage inventoryPage;
	private LoginPage loginPage;
	private CartPage cartPage;
	private ProductDetailPage productDetailPage;

	
	@BeforeMethod(alwaysRun = true)
	public void testSetup() {		
		inventoryPage = new InventoryPage(driver);
		loginPage = new LoginPage(driver);
		cartPage = new CartPage(driver);		
		productDetailPage = new ProductDetailPage(driver);
		
		loginPage.login(
				LoginTestData.ValidCredential.STANDARD_USER, 
				LoginTestData.ValidCredential.PASSWORD);
		
		loginPage.handleAlertIfPresent();
	}

	
	@Test(description = "Verify product list page loads correctly",
			groups = {"smoke", "navigation"})
	public void testInventoryPageLoadsCorrectly() {
		int productCount = inventoryPage.getProducts().size();
		Assert.assertTrue(inventoryPage.getProducts().size() > 0, 
				String.format(
			            "Product list page should display at least 1 product. Found: %d products",
			            productCount
			        ));
	}

	
	@Test (description = "Verify sort products by name in ascending alphabetical oreder (A to Z)",
			groups = {"sorting"})
	public void testSortByNameA_Z() {
		
		inventoryPage.sortByValue(InventoryTestData.SortData.NAME_A_Z);
		
		List<String> actual = inventoryPage.getAllProductNames();
		List<String> expected = new ArrayList<>(actual);
		Collections.sort(expected);
		
		Assert.assertEquals(actual,  expected, 
				"Products should be sorted alphabetically A-Z");
	}

	
	@Test (description = "Verify sort products by price in ascending oreder (High to Low)",
			groups = {"sorting"})
	public void testSortByPrice_Low() {
	
		inventoryPage.sortByValue(InventoryTestData.SortData.PRICE_HIGH);
		
		List<Double> actual = inventoryPage.getAllProductPrices();
		List<Double> expected = new ArrayList<>(actual);
		expected.sort(Collections.reverseOrder());
		
		Assert.assertEquals(actual,  expected,
				"Products should be sorted by price from highest to lowest");
	}

	
	@Test (description = "Verify user can add a product to the cart from the Products List Page",
			groups = {"smoke"})
	public void testAddProductToCart() {
		inventoryPage.addItem(InventoryTestData.AddItemsIDs.BIKELIGHT);		
		inventoryPage.addItem(InventoryTestData.AddItemsIDs.FLEECEJACKET);		
		inventoryPage.addItem(InventoryTestData.AddItemsIDs.REDSHIRT);
		
		
		Assert.assertEquals(inventoryPage.getCartBadgeCount(), 3,
				"Cart badge should display the same items number of added products");
	}
	
	@Test (description = "Verify user can remove a product by clicking remove button")
	public void testRemoveProductFromCart() {		
		inventoryPage.addItem(InventoryTestData.AddItemsIDs.BIKELIGHT);
		inventoryPage.addItem(InventoryTestData.AddItemsIDs.FLEECEJACKET);
		inventoryPage.addItem(InventoryTestData.AddItemsIDs.REDSHIRT);
		
		inventoryPage.addItem(InventoryTestData.RemoveItemsIDs.FLEECEJACKET);
	
		Assert.assertEquals(inventoryPage.getCartBadgeCount(), 2,
				"Cart badge should display the same items number of added products");
	}
	
	
	@Test (description = "Verify user can navigate to the cart page",
			groups = {"navigation", "smoke"})
	public void testOpenCart() {
		inventoryPage.addItem(InventoryTestData.AddItemsIDs.BIKELIGHT);
		inventoryPage.addItem(InventoryTestData.AddItemsIDs.FLEECEJACKET);
		
		inventoryPage.openCart();
			
		cartPage.removeProductFromCart(InventoryTestData.RemoveItemsIDs.BIKELIGHT);
		
		Assert.assertEquals(cartPage.getProductsInCart().size(), 1, 
				"User should be able to remove items from the cart");
	}
	
	@Test (description = "Verify user can navigate to product detail page",
			groups = {"navigation", "smoke"})
	public void testOpenPDP() {
		inventoryPage.goToProductPage(InventoryTestData.ItemsIDs.ITEM2);
		
		Assert.assertEquals(productDetailPage.getProductName(), 
				InventoryTestData.ProductsName.ITEM2,
				"Product detail page should display the selected product");
	}
	
	 @Test (description = "Verify user can add a product to the cart from PDP",
			 groups = {"smoke"})
	public void testAddProductFromPDP() {
		inventoryPage.goToProductPage(InventoryTestData.ItemsIDs.ITEM4);

		int badgeCount = inventoryPage.getCartBadgeCount();
		
		productDetailPage.addProductToCart();
				
		Assert.assertEquals(inventoryPage.getCartBadgeCount(), badgeCount + 1,
				"Cart badge should increment by 1 after adding product from PDP");
	}

	
	 @AfterMethod(alwaysRun = true)
		public void teardown() {
			super.tearDown();
		}
}
