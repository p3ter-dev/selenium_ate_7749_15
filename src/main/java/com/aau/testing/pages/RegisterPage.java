package com.aau.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for ParaBank User Registration Page.
 * Encapsulates form inputs, validations, and submission actions.
 */
public class RegisterPage extends BasePage {

    public static final String PAGE_URL = "https://parabank.parasoft.com/parabank/register.htm";

    // Form input locators using By.id (T2)
    private final By firstNameField = By.id("customer.firstName");
    private final By lastNameField = By.id("customer.lastName");
    private final By addressField = By.id("customer.address.street");
    private final By cityField = By.id("customer.address.city");
    private final By stateField = By.id("customer.address.state");
    private final By zipCodeField = By.id("customer.address.zipCode");
    private final By phoneField = By.id("customer.phoneNumber");
    private final By ssnField = By.id("customer.ssn");
    private final By usernameField = By.id("customer.username");
    private final By passwordField = By.id("customer.password");
    private final By confirmPasswordField = By.id("repeatedPassword");
    private final By registerButton = By.cssSelector("input[value='Register']");

    // Success and header elements
    private final By pageHeader = By.cssSelector("h1.title");
    private final By successParagraph = By.xpath("//div[@id='rightPanel']/p");

    // Validation error locators
    private final By firstNameError = By.id("customer.firstName.errors");
    private final By lastNameError = By.id("customer.lastName.errors");
    private final By addressError = By.id("customer.address.street.errors");
    private final By cityError = By.id("customer.address.city.errors");
    private final By stateError = By.id("customer.address.state.errors");
    private final By zipCodeError = By.id("customer.address.zipCode.errors");
    private final By ssnError = By.id("customer.ssn.errors");
    private final By usernameError = By.id("customer.username.errors");
    private final By passwordError = By.id("customer.password.errors");
    private final By confirmPasswordError = By.id("repeatedPassword.errors");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public RegisterPage open() {
        driver.get(PAGE_URL);
        return this;
    }

    public boolean isLoaded() {
        return isDisplayed(pageHeader) && isDisplayed(registerButton);
    }

    public void fillRegistrationForm(String firstName, String lastName, String address,
                                     String city, String state, String zipCode,
                                     String phone, String ssn, String username,
                                     String password, String confirmPassword) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(addressField, address);
        type(cityField, city);
        type(stateField, state);
        type(zipCodeField, zipCode);
        type(phoneField, phone);
        type(ssnField, ssn);
        type(usernameField, username);
        type(passwordField, password);
        type(confirmPasswordField, confirmPassword);
    }

    public void clickRegisterButton() {
        click(registerButton);
    }

    public void registerNewUser(String firstName, String lastName, String address,
                                String city, String state, String zipCode,
                                String phone, String ssn, String username,
                                String password) {
        fillRegistrationForm(firstName, lastName, address, city, state, zipCode,
                phone, ssn, username, password, password);
        clickRegisterButton();
    }

    public String getHeaderTitle() {
        return getText(pageHeader);
    }

    public String getSuccessMessage() {
        return getText(successParagraph);
    }

    // Validation error getters
    public String getFirstNameError() {
        return getText(firstNameError);
    }

    public String getLastNameError() {
        return getText(lastNameError);
    }

    public String getAddressError() {
        return getText(addressError);
    }

    public String getCityError() {
        return getText(cityError);
    }

    public String getStateError() {
        return getText(stateError);
    }

    public String getZipCodeError() {
        return getText(zipCodeError);
    }

    public String getSsnError() {
        return getText(ssnError);
    }

    public String getUsernameError() {
        return getText(usernameError);
    }

    public String getPasswordError() {
        return getText(passwordError);
    }

    public String getConfirmPasswordError() {
        return getText(confirmPasswordError);
    }

    public boolean isFirstNameErrorDisplayed() {
        return isDisplayed(firstNameError);
    }

    public boolean isUsernameErrorDisplayed() {
        return isDisplayed(usernameError);
    }
}
