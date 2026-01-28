package com.qa.swaglabs.pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasePage {
	
	protected WebDriver driver;
	protected WebDriverWait wait;

	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public boolean isRedirectedTo(String expectedUrlPart) {
		return wait.until(ExpectedConditions.urlContains(expectedUrlPart));
	}
	
	public void handleAlertIfPresent() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			
		} catch (NoAlertPresentException e) {
			log.debug("No alert present. Continuing execution.");
		}
	}
}
