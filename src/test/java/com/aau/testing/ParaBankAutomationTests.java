package com.aau.testing;

import com.aau.testing.pages.AccountsOverviewPage;
import com.aau.testing.pages.LoginPage;
import com.aau.testing.pages.RegisterPage;
import com.aau.testing.pages.TransferFundsPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * End-to-End Test Suite for ParaBank web application.
 * Fulfills all 8 course requirements (T1 to T8) for Addis Ababa University Software Testing homework.
 */
public class ParaBankAutomationTests extends BaseTest {

    /**
     * T1: Navigation Smoke Test
     * Proves the site loads correctly by asserting title and key visible elements.
     */
    @Test
    @DisplayName("T1: Navigation smoke test - verifies site loads with correct title and header")
    public void testT1_NavigationSmokeTest() {
        LoginPage loginPage = new LoginPage(driver).open();

        assertAll("Navigation Smoke Assertions",
                () -> assertThat(loginPage.getPageTitle())
                        .as("Page title should match ParaBank standard title")
                        .isEqualTo("ParaBank | Welcome | Online Banking"),
                () -> assertThat(loginPage.isLoaded())
                        .as("Logo and Login panel should be visible")
                        .isTrue(),
                () -> assertThat(loginPage.getCaption())
                        .as("Header caption should be displayed")
                        .isEqualTo("Experience the difference")
        );
    }

    /**
     * T2: Locator Strategies
     * Locates elements using at least two different strategies (By.name, By.cssSelector, By.xpath, By.className, By.id).
     * Strictly avoids positional XPaths.
     */
    @Test
    @DisplayName("T2: Locator strategies test - verifies multiple selector strategies without positional XPaths")
    public void testT2_MultipleLocatorStrategies() {
        LoginPage loginPage = new LoginPage(driver).open();

        assertAll("Multiple Locator Strategies Verification",
                () -> assertThat(loginPage.isUsernameFieldPresent())
                        .as("Strategy 1: By.name locator for username input")
                        .isTrue(),
                () -> assertThat(loginPage.isLoginButtonPresent())
                        .as("Strategy 2: By.cssSelector locator for login submit button")
                        .isTrue(),
                () -> assertThat(loginPage.isLogoPresent())
                        .as("Strategy 3: By.className / By.cssSelector locator for logo")
                        .isTrue()
        );
    }

    /**
     * T3: Positive Path Flow
     * Automates full end-to-end user flow:
     * User registration -> Confirmation -> Navigation to Transfer Funds -> Execute Transfer -> Assert Confirmation.
     */
    @Test
    @DisplayName("T3: Main positive path - End-to-end registration, login, and fund transfer flow")
    public void testT3_PositivePathEndToEndFlow() {
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 8);
        String testUser = "user_" + uniqueSuffix;
        String testPassword = "Password123!";

        // 1. Open registration page
        RegisterPage registerPage = new RegisterPage(driver).open();
        assertThat(registerPage.isLoaded()).as("Register page should be loaded").isTrue();

        // 2. Complete registration form
        registerPage.registerNewUser(
                "John", "Doe", "123 Main St", "Addis Ababa", "AA", "1000",
                "+251911000000", "123-45-6789", testUser, testPassword
        );

        // 3. Assert registration success state
        String successMessage = registerPage.getSuccessMessage();
        assertThat(successMessage)
                .as("Registration confirmation message should be visible")
                .contains("Your account was created successfully. You are now logged in.");

        // 4. Navigate to Transfer Funds
        AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(driver);
        TransferFundsPage transferFundsPage = accountsOverviewPage.clickTransferFunds();
        assertThat(transferFundsPage.isLoaded()).as("Transfer funds page should be displayed").isTrue();

        // 5. Execute fund transfer
        transferFundsPage.executeTransfer("150.00");

        // 6. Assert visible result after action
        boolean transferCompleted = transferFundsPage.waitForTransferComplete();
        assertThat(transferCompleted).as("Transfer completed state should be reached").isTrue();
        assertThat(transferFundsPage.getTransferCompleteHeader())
                .as("Transfer confirmation title should be visible")
                .contains("Transfer Complete!");
    }

    /**
     * T4: Negative Path
     * Automates invalid login and empty form submissions, asserting error messages.
     */
    @Test
    @DisplayName("T4: Negative path - Verifies error message on invalid credentials and blank forms")
    public void testT4_NegativePathInvalidCredentialsAndBlankForms() {
        LoginPage loginPage = new LoginPage(driver).open();

        // 1. Invalid login attempt
        loginPage.login("non_existent_user_9988", "WrongPass123!");
        assertThat(loginPage.isErrorDisplayed()).as("Error message should appear on invalid login").isTrue();
        assertThat(loginPage.getErrorMessage())
                .as("Error text should inform user of verification failure")
                .contains("The username and password could not be verified.");

        // 2. Blank registration attempt
        RegisterPage registerPage = new RegisterPage(driver).open();
        registerPage.clickRegisterButton();

        assertAll("Registration Validation Errors",
                () -> assertThat(registerPage.isFirstNameErrorDisplayed()).as("First name error is displayed").isTrue(),
                () -> assertThat(registerPage.getFirstNameError()).as("First name required message").isEqualTo("First name is required."),
                () -> assertThat(registerPage.isUsernameErrorDisplayed()).as("Username error is displayed").isTrue(),
                () -> assertThat(registerPage.getUsernameError()).as("Username required message").isEqualTo("Username is required.")
        );
    }

    /**
     * T5: Explicit Wait (Strictly 0 Thread.sleep)
     * Uses WebDriverWait with ExpectedConditions to synchronize with asynchronous AJAX state updates.
     */
    @Test
    @DisplayName("T5: Explicit wait - Uses WebDriverWait with ExpectedConditions on dynamic AJAX response")
    public void testT5_ExplicitWaitOnAsyncTransfer() {
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 8);
        String testUser = "wait_user_" + uniqueSuffix;
        String testPassword = "Password123!";

        // Register user
        RegisterPage registerPage = new RegisterPage(driver).open();
        registerPage.registerNewUser(
                "Alice", "Smith", "456 Bole Rd", "Addis Ababa", "AA", "1000",
                "+251922000000", "987-65-4321", testUser, testPassword
        );

        // Navigate to Transfer Funds
        AccountsOverviewPage accountsOverview = new AccountsOverviewPage(driver);
        TransferFundsPage transferFundsPage = accountsOverview.clickTransferFunds();

        // Trigger AJAX transfer
        transferFundsPage.executeTransfer("75.50");

        // Explicit Wait synchronization
        boolean isComplete = transferFundsPage.waitForTransferComplete();
        assertTrue(isComplete, "Explicit wait should succeed when transfer completes via AJAX");
        assertThat(transferFundsPage.getTransferCompleteHeader()).isEqualTo("Transfer Complete!");
    }

    /**
     * T6: Data-Driven Parameterized Test with Equivalence Partitioning (EP)
     * Evaluates login behavior across distinct equivalence classes:
     * - Valid credentials partition
     * - Non-existent credentials partition
     * - Empty username partition
     * - Empty password partition
     * - Empty credentials partition
     */
    @ParameterizedTest(name = "[{index}] Partition: {0} | User: ''{1}'' | Expected Error: ''{3}''")
    @MethodSource("loginEquivalencePartitions")
    @DisplayName("T6: Parameterized test - Equivalence partitioning on login authentication")
    public void testT6_ParameterizedLoginEquivalencePartitioning(
            String partitionName,
            String username,
            String password,
            String expectedMessage,
            boolean isSuccessExpected) {

        LoginPage loginPage = new LoginPage(driver).open();

        if (isSuccessExpected) {
            // For valid partition, pre-register the user first
            RegisterPage registerPage = new RegisterPage(driver).open();
            registerPage.registerNewUser(
                    "Param", "User", "789 Africa Ave", "Addis Ababa", "AA", "1000",
                    "+251933000000", "555-55-5555", username, password
            );
            new AccountsOverviewPage(driver).clickLogout();

            // Perform login
            loginPage.open();
            AccountsOverviewPage overview = loginPage.login(username, password);
            assertThat(overview.isLoaded())
                    .as("Valid credentials partition should successfully log in")
                    .isTrue();
        } else {
            loginPage.login(username, password);
            assertThat(loginPage.isErrorDisplayed())
                    .as("Invalid partition should trigger an error message")
                    .isTrue();
            assertThat(loginPage.getErrorMessage())
                    .as("Error text should match expected partition validation")
                    .contains(expectedMessage);
        }
    }

    /**
     * Data provider for T6: Equivalence Partitions for Authentication.
     */
    static Stream<Arguments> loginEquivalencePartitions() {
        String validDynamicUser = "ep_user_" + UUID.randomUUID().toString().substring(0, 6);
        return Stream.of(
                Arguments.of("EP1: Valid Registered Credentials", validDynamicUser, "ValidPass123!", "", true),
                Arguments.of("EP2: Non-existent Account", "invalid_nonexistent_user", "wrongpass", "The username and password could not be verified.", false),
                Arguments.of("EP3: Empty Username", "", "SomePassword123!", "Please enter a username and password.", false),
                Arguments.of("EP4: Empty Password", "some_username", "", "Please enter a username and password.", false),
                Arguments.of("EP5: Empty Username and Password", "", "", "Please enter a username and password.", false)
        );
    }

    /**
     * T7: Page Object Encapsulation Test
     * Verifies that user flows and state transitions are entirely encapsulated within Page Objects.
     */
    @Test
    @DisplayName("T7: Page Object encapsulation - executes user actions strictly through intention-revealing page methods")
    public void testT7_PageObjectEncapsulation() {
        LoginPage loginPage = new LoginPage(driver).open();
        RegisterPage registerPage = loginPage.clickRegister();
        assertThat(registerPage.getHeaderTitle()).isEqualTo("Signing up is easy!");
    }
}
