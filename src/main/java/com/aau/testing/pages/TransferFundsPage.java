package com.aau.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Page Object for ParaBank Transfer Funds Page.
 * Demonstrates explicit wait synchronization (T5) on asynchronous AJAX
 * completion.
 */
public class TransferFundsPage extends BasePage {

    public static final String PAGE_URL = "https://parabank.parasoft.com/parabank/transfer.htm";

    private final By amountInput = By.id("amount");
    private final By fromAccountDropdown = By.id("fromAccountId");
    private final By toAccountDropdown = By.id("toAccountId");
    private final By transferButton = By.cssSelector("input[value='Transfer']");

    // Dynamic AJAX result elements
    private final By transferCompleteTitle = By.xpath("//h1[@class='title' and contains(text(),'Transfer Complete!')]");
    private final By transferAmountResult = By.id("amountResult");

    public TransferFundsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isDisplayed(amountInput) && isDisplayed(transferButton);
    }

    public void enterAmount(String amount) {
        type(amountInput, amount);
    }

    public void selectFromAccountByIndex(int index) {
        waitForVisibility(fromAccountDropdown);
        Select select = new Select(driver.findElement(fromAccountDropdown));
        select.selectByIndex(index);
    }

    public void selectToAccountByIndex(int index) {
        waitForVisibility(toAccountDropdown);
        Select select = new Select(driver.findElement(toAccountDropdown));
        select.selectByIndex(index);
    }

    public void clickTransfer() {
        click(transferButton);
    }

    /**
     * Executes complete transfer and uses explicit wait (T5) on dynamic AJAX
     * response.
     */
    public void executeTransfer(String amount) {
        // Explicitly wait for account options to load via AJAX into dropdown
        wait.until(d -> {
            try {
                Select select = new Select(d.findElement(fromAccountDropdown));
                return select.getOptions().size() >= 1;
            } catch (Exception e) {
                return false;
            }
        });
        enterAmount(amount);
        clickTransfer();
    }

    /**
     * Explicit wait synchronization ensuring AJAX request finished without
     * Thread.sleep (T5).
     */
    public boolean waitForTransferComplete() {
        try {
            waitForVisibility(transferCompleteTitle);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getTransferCompleteHeader() {
        return getText(transferCompleteTitle);
    }

    public String getTransferredAmountResult() {
        return getText(transferAmountResult);
    }
}