package com.qa.swaglabs.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	protected WebDriver driver;
	protected String url = "https://www.saucedemo.com/";
	
	@Parameters("browser")
	@BeforeMethod(alwaysRun = true)
	public void setup(@Optional("chrome") String browser) {
		
		switch (browser.toLowerCase()) {
			case "firefox":
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				firefoxOptions.addArguments("--headless");
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver(firefoxOptions);
				break;
				
			case "chrome":
				default:
					ChromeOptions chromeOptions = new ChromeOptions();
					chromeOptions.addArguments("--headless=new");
					chromeOptions.addArguments("--no-sandbox");
					chromeOptions.addArguments("--disable-dev-shm-usage");
					WebDriverManager.chromedriver().setup();
					driver = new ChromeDriver(chromeOptions);
					break;
		}
		
		driver.manage().window().maximize();
		driver.get(url);
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		if (driver != null) {
			driver.quit();			
		}
	}
}
