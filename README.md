# swaglabs-selenium
This project contains automated UI tests for the Swag Labs web application using Selenium WebDriver, TestNG, and the Page Object Model (POM) design pattern.

## Project Goals:
- Validate core user flows (authentication, product listing, cart, checkout)
- Support regression, smoke, and end-to-end (E2E) testing
- Ensure tests are maintainable, reusable, and scalable

# Tech Stack
- Java
- Selenium WebDriver
- TestNG
- Maven
- Eclipse IDE
- Chrome/Firefox Drivers
- GitHub Actions (CI)

# Test Types Covered
- Smoke Tests
- Regression Tests
- End-to_End (E2E) Tests

# Project Structure
- src/main/java → Page Objects 
- src/test/java → Test cases & Test data
- pom.xml → Maven dependencies
- testng.xml → Test suite configuration

# Test Suites
- testing-critical.xml
- testing-cross-browser.xml
- testing-e2e.xml
- testing-regression.xml
- testing-smoke.xml
- testing-validation.xml

# How to Run Tests
## Run all tests
mvn test

## Run a specific test suite
mvn test -DsuiteXmlFile=testing-smoke.xml

