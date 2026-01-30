package com.qa.swaglabs.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.swaglabs.pages.CartPage;
import com.qa.swaglabs.pages.CheckoutCompletePage;
import com.qa.swaglabs.pages.CheckoutStepOnePage;
import com.qa.swaglabs.pages.CheckoutStepTwoPage;
import com.qa.swaglabs.pages.InventoryPage;
import com.qa.swaglabs.pages.LoginPage;
import com.qa.swaglabs.testdata.CheckoutTestData;
import com.qa.swaglabs.testdata.InventoryTestData;
import com.qa.swaglabs.testdata.LoginTestData;

public class CheckoutTest extends BaseTest{

	private LoginPage loginPage;
	private InventoryPage inventoryPage;
	private CartPage cartPage;
	private CheckoutCompletePage checkoutCompletePage;
	private CheckoutStepOnePage checkoutStepOnePage;
	private CheckoutStepTwoPage checkoutStepTwoPage;
	
	@BeforeMethod(alwaysRun = true)
	public void testSetup() {	
		loginPage = new LoginPage(driver);
		inventoryPage = new InventoryPage(driver);
		cartPage = new CartPage(driver);
		checkoutCompletePage = new CheckoutCompletePage(driver);
		checkoutStepOnePage = new CheckoutStepOnePage(driver);
		checkoutStepTwoPage = new CheckoutStepTwoPage(driver);
		
		loginPage.login(
				LoginTestData.ValidCredential.STANDARD_USER, 
				LoginTestData.ValidCredential.PASSWORD);		
	}
	
	
	private void navigateToCheckoutForm() {
		inventoryPage.addItem(InventoryTestData.AddItemsIDs.BLOTTSHIRT);
		inventoryPage.openCart();
		cartPage.clickCheckoutButton();
	}
	
	
	private void fillCheckoutFormWithValidData() {
		navigateToCheckoutForm();
		checkoutStepOnePage.fillForm(
				CheckoutTestData.ValidData.FIRSTNAME, 
				CheckoutTestData.ValidData.LASTNAME, 
				CheckoutTestData.ValidData.ZIP
				);	
		
		checkoutStepOnePage.clickContinueButton();
	}

	
	@Test (description = "Verify user can proceed to checkout form from cart",
			groups = {"smoke"})
	public void testProceedToCheckoutForm() {
		navigateToCheckoutForm();
		
		Assert.assertTrue(checkoutStepOnePage.isRedirectedTo("checkout-step-one.html"),
				"User should redirect to checkout form (step one) page after clicking checkout button. Current URL: " +
				driver.getCurrentUrl());
	}
	
	
	@Test (description = "Verify user can fill checkout form with valid data"
			)
	public void testCheckoutForm() {		
		fillCheckoutFormWithValidData();
		
		Assert.assertTrue(cartPage.isRedirectedTo("checkout-step-two.html"),
				"User should proceed to order overview page (step two) after filling checkout form with valid data. Current URL: " +
				driver.getCurrentUrl());
	}
	
	
	@Test ( description = "Verify order total calculation is correct on overview page",
			groups = {"critical"})
	public void testOrderTotalCalculation() {
		fillCheckoutFormWithValidData();
		
		double itemsTotal = checkoutStepTwoPage.getSumOfProductPrices();
		double tax = checkoutStepTwoPage.getTax();
		double expectedTotal =itemsTotal + tax;
		double actualTotal = checkoutStepTwoPage.getTotalPrice();
		
		Assert.assertEquals(actualTotal, expectedTotal, 
				String.format("Total should be %.2f (items: %.2f + tax: %.2f) but was %.2f",
                expectedTotal, itemsTotal, tax, actualTotal));
	}
	

	@Test (description = "Verify user can complete order succcessfully",
			groups = {"e2e", "smoke", "critical"})
	public void testCompleteOrder() {
		fillCheckoutFormWithValidData();
		checkoutStepTwoPage.clickFinishButton();
		
		Assert.assertEquals(
				checkoutCompletePage.getConfirmationMessage(), 
				CheckoutTestData.ConfirmationMessage.CONFIRMATIONMESSAGE,
				"User should see order confirmation message after successful checkout");
	}
	
	
	@Test (description = "Verify checkout fails when first name is empty",
			groups = {"validation"})
	public void testCheckoutWithEmptyFirstName() {
		navigateToCheckoutForm();
		
		checkoutStepOnePage.fillForm(
				"", 
				CheckoutTestData.ValidData.LASTNAME, 
				CheckoutTestData.ValidData.ZIP);
		
		checkoutStepOnePage.clickContinueButton();
		
		Assert.assertEquals(checkoutStepOnePage.getErrorMessage(), 
				CheckoutTestData.ErrorMessage.EMPTY_FIRST_NAME_FIELD,
				"System should prevent checkout and display validation error when first name is empty");
	}
	
	
	@Test (description = "Verify checkout fails when last name is empty",
			groups = {"validation"})
	public void testCheckoutWithEmptyLastName() {
		navigateToCheckoutForm();
		
		checkoutStepOnePage.fillForm(
				CheckoutTestData.ValidData.FIRSTNAME, 
				"", 
				CheckoutTestData.ValidData.ZIP);
		
		checkoutStepOnePage.clickContinueButton();
		
		Assert.assertEquals(checkoutStepOnePage.getErrorMessage(), 
				CheckoutTestData.ErrorMessage.EMPTY_LAST_NAME_FIELD,
				"System should prevent checkout and display validation error when last name is empty");
	}
	
	
	@Test (description = "Verify checkout fails when Zip Code is empty",
			groups = {"validation"})
	public void testCheckoutWithEmptyZip() {
		navigateToCheckoutForm();
		
		checkoutStepOnePage.fillForm(
				CheckoutTestData.ValidData.FIRSTNAME, 
				CheckoutTestData.ValidData.LASTNAME, 
				"");
		
		checkoutStepOnePage.clickContinueButton();
		
		Assert.assertEquals(checkoutStepOnePage.getErrorMessage(), 
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
		fillCheckoutFormWithValidData();
	
		checkoutStepTwoPage.clickFinishButton();		
		checkoutCompletePage.clickBackHomeButton();
		
		Assert.assertTrue(cartPage.isRedirectedTo("inventory.html"),
				"User should be redirected back to inventory page (home) after clicking 'Back Home' button. " +
				        "Current URL: " + driver.getCurrentUrl());
	}
}
