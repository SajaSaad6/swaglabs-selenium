package com.qa.swaglabs.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.swaglabs.pages.CartPage;
import com.qa.swaglabs.pages.CheckoutCompletePage;
import com.qa.swaglabs.pages.CheckoutPage;
import com.qa.swaglabs.pages.InventoryPage;
import com.qa.swaglabs.pages.LoginPage;
import com.qa.swaglabs.pages.OverviewPage;
import com.qa.swaglabs.testdata.CheckoutTestData;
import com.qa.swaglabs.testdata.InventoryTestData;
import com.qa.swaglabs.testdata.LoginTestData;

public class CheckoutTest extends BaseTest{

	private LoginPage loginPage;
	private InventoryPage inventoryPage;
	private CartPage cartPage;
	private CheckoutCompletePage checkoutCompletePage;
	private CheckoutPage checkoutPage;
	private OverviewPage overviewPage;
	
	@BeforeMethod(alwaysRun = true)
	public void testSetup() {	
		loginPage = new LoginPage(driver);
		inventoryPage = new InventoryPage(driver);
		cartPage = new CartPage(driver);
		checkoutCompletePage = new CheckoutCompletePage(driver);
		checkoutPage = new CheckoutPage(driver);
		overviewPage = new OverviewPage(driver);
		
		loginPage.login(
				LoginTestData.ValidCredential.STANDARD_USER, 
				LoginTestData.ValidCredential.PASSWORD);
		
		loginPage.handleAlertIfPresent();		
	}
	
	
	private void navigateToCheckoutForm() {
		inventoryPage.addItem(InventoryTestData.AddItemsIDs.FLEECEJACKET);
		inventoryPage.openCart();
		cartPage.wiatForCartPage();
		cartPage.clickCheckoutButton();
		checkoutPage.waitForCheckoutInformationPage();
	}
	
	
	private void fillCheckoutFormWithValidData() {
		checkoutPage.fillForm(
				CheckoutTestData.ValidData.FIRSTNAME, 
				CheckoutTestData.ValidData.LASTNAME, 
				CheckoutTestData.ValidData.ZIP
				);	
		
		checkoutPage.clickContinueButton();
	}

	
	@Test (description = "Verify user can proceed to checkout form from cart",
			groups = {"smoke"})
	public void testProceedToCheckoutForm() {
		navigateToCheckoutForm();
		
		Assert.assertTrue(checkoutPage.isRedirectedTo("checkout-step-one.html"),
				"User should redirect to checkout form (step one) page after clicking checkout button. Current URL: " +
				driver.getCurrentUrl());
	}
	
	
	@Test (description = "Verify user can fill checkout form with valid data"
			)
	public void testCheckoutForm() {
		navigateToCheckoutForm();
		fillCheckoutFormWithValidData();
		
		Assert.assertTrue(cartPage.isRedirectedTo("checkout-step-two.html"),
				"User should proceed to order overview page (step two) after filling checkout form with valid data. Current URL: " +
				driver.getCurrentUrl());
	}
	
	
	@Test ( description = "Verify order total calculation is correct on overview page",
			groups = {"critical"})
	public void testOrderTotalCalculation() {
		navigateToCheckoutForm();
		fillCheckoutFormWithValidData();
		
		double itemsTotal = overviewPage.getProductPrices();
		double tax = overviewPage.getTax();
		double expectedTotal =itemsTotal + tax;
		double actualTotal = overviewPage.getTotalPrice();
		
		Assert.assertEquals(actualTotal, expectedTotal, 
				String.format("Total should be %.2f (items: %.2f + tax: %.2f) but was %.2f",
                expectedTotal, itemsTotal, tax, actualTotal));
	}
	

	@Test (description = "Verify user can complete order succcessfully",
			groups = {"e2e", "smoke", "critical"})
	public void testCompleteOrder() {
		navigateToCheckoutForm();
		fillCheckoutFormWithValidData();
		overviewPage.clickFinishButton();
		
		Assert.assertEquals(
				checkoutCompletePage.getConfirmationMessage(), 
				CheckoutTestData.ConfirmationMessage.CONFIRMATIONMESSAGE,
				"User should see order confirmation message after successful checkout");
	}
	
	
	@Test (description = "Verify checkout fails when first name is empty",
			groups = {"validation"})
	public void testCheckoutWithEmptyFirstName() {
		navigateToCheckoutForm();
		
		checkoutPage.fillForm(
				"", 
				CheckoutTestData.ValidData.LASTNAME, 
				CheckoutTestData.ValidData.ZIP);
		
		checkoutPage.clickContinueButton();
		
		Assert.assertEquals(checkoutPage.getErrorMessage(), 
				CheckoutTestData.ErrorMessage.EMPTY_FIRST_NAME_FIELD,
				"System should prevent checkout and display validation error when first name is empty");
	}
	
	
	@Test (description = "Verify checkout fails when last name is empty",
			groups = {"validation"})
	public void testCheckoutWithEmptyLastName() {
		navigateToCheckoutForm();
		
		checkoutPage.fillForm(
				CheckoutTestData.ValidData.FIRSTNAME, 
				"", 
				CheckoutTestData.ValidData.ZIP);
		
		checkoutPage.clickContinueButton();
		
		Assert.assertEquals(checkoutPage.getErrorMessage(), 
				CheckoutTestData.ErrorMessage.EMPTY_LAST_NAME_FIELD,
				"System should prevent checkout and display validation error when last name is empty");
	}
	
	
	@Test (description = "Verify checkout fails when Zip Code is empty",
			groups = {"validation"})
	public void testCheckoutWithEmptyZip() {
		navigateToCheckoutForm();
		
		checkoutPage.fillForm(
				CheckoutTestData.ValidData.FIRSTNAME, 
				CheckoutTestData.ValidData.LASTNAME, 
				"");
		
		checkoutPage.clickContinueButton();
		
		Assert.assertEquals(checkoutPage.getErrorMessage(), 
				CheckoutTestData.ErrorMessage.EMPTY_POSTAL_CODE_FIELD,
				"System should prevent checkout and display validation error when Zip Code is empty");
	}
	
	
	@Test (description = "Verify checkout button behavior with empty cart", 
			groups = {"cart", "critical"})
	public void testCheckoutWithEmptyCart() {
		inventoryPage.openCart();
		
		cartPage.clickCheckoutButton();
		boolean isStillOnCartPage = driver.getCurrentUrl().contains("cart.html");
        boolean isOnCheckoutPage = driver.getCurrentUrl().contains("checkout");
        
        Assert.assertTrue(isStillOnCartPage || isOnCheckoutPage,
        		String.format(
        	            "System should handle empty cart checkout gracefully by either: " +
        	            "(1) keeping user on cart page, or (2) allowing checkout with empty cart. " +
        	            "Current URL: %s",
        	            driver.getCurrentUrl()));
	}
	
	
	@Test (description = "Verify user can return to homepage after completing order",
	        groups = {"navigation"})
	public void testReturnToHomepageAfterOrder() {
		navigateToCheckoutForm();
		fillCheckoutFormWithValidData();		
		overviewPage.clickFinishButton();
		checkoutCompletePage.clickBackHomeButton();
		
		Assert.assertTrue(cartPage.isRedirectedTo("inventory.html"),
				"User should be redirected back to inventory page (home) after clicking 'Back Home' button. " +
				        "Current URL: " + driver.getCurrentUrl());
	}
	
	
	@AfterMethod(alwaysRun = true)
	public void testTeardown() {
		super.tearDown();
	}
	
}
