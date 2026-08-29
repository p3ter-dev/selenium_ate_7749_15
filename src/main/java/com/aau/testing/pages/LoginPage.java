package com.aau.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for ParaBank Login Panel and Home Page.
 * Demonstrates multiple locator strategies: By.name, By.cssSelector,
 * By.className, By.xpath, By.linkText.
 */
public class LoginPage extends BasePage {

    public static final String PAGE_URL = "https://parabank.parasoft.com/parabank/index.htm";

    // Locators using diverse strategies (T2) without positional indices
    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By loginButton = By.cssSelector("input[value='Log In']");
    private final By registerLink = By.xpath("//a[contains(@href, 'register.htm')]");
    private final By errorMessage = By.className("error");
    private final By logoImage = By.cssSelector("img.logo");
    private final By captionText = By.className("caption");
    private final By loginPanelHeader = By.xpath("//h2[text()='Customer Login']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(PAGE_URL);
        return this;
    }

    public boolean isLoaded() {
        return isDisplayed(logoImage) && isDisplayed(loginPanelHeader);
    }

    public String getCaption() {
        return getText(captionText);
    }

    public void enterUsername(String username) {
        type(usernameField, username);
    }

    public void enterPassword(String password) {
        type(passwordField, password);
    }

    public void clickLogin() {
        click(loginButton);
    }

    public AccountsOverviewPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        return new AccountsOverviewPage(driver);
    }

    public RegisterPage clickRegister() {
        click(registerLink);
        return new RegisterPage(driver);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }

    // Direct element verification methods for locator strategy tests (T2)
    public boolean isUsernameFieldPresent() {
        return isDisplayed(usernameField);
    }

    public boolean isLoginButtonPresent() {
        return isDisplayed(loginButton);
    }

    public boolean isLogoPresent() {
        return isDisplayed(logoImage);
    }
}
