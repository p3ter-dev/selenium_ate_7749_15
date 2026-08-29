package com.aau.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for ParaBank Accounts Overview and Navigation Dashboard.
 */
public class AccountsOverviewPage extends BasePage {

    public static final String PAGE_URL = "https://parabank.parasoft.com/parabank/overview.htm";

    private final By overviewTitle = By.xpath("//h1[@class='title' and contains(text(),'Accounts Overview')]");
    private final By welcomeBanner = By.cssSelector("p.smallText");
    private final By accountTable = By.id("accountTable");
    private final By transferFundsLink = By.xpath("//a[contains(@href, 'transfer.htm')]");
    private final By logoutLink = By.xpath("//a[contains(@href, 'logout.htm')]");

    public AccountsOverviewPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isDisplayed(overviewTitle) || isDisplayed(welcomeBanner);
    }

    public String getWelcomeBannerText() {
        return getText(welcomeBanner);
    }

    public boolean isAccountTableDisplayed() {
        return isDisplayed(accountTable);
    }

    public TransferFundsPage clickTransferFunds() {
        click(transferFundsLink);
        return new TransferFundsPage(driver);
    }

    public LoginPage clickLogout() {
        click(logoutLink);
        return new LoginPage(driver);
    }
}