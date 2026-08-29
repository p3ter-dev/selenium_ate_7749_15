# Selenium End-to-End Automated Testing Suite & Report

**Course:** Software Testing and Validation  
**Institution:** Addis Ababa University, School of Information Technology and Engineering  
**Student ID:** `ATE/7749/15`  
**Target Application:** [ParaBank](https://parabank.parasoft.com/parabank/index.htm)  
**Submission Package:** `selenium_ate_7749_15.zip` and `selenium_ate_7749_15.pdf`  
**GitHub Repository:** `https://github.com/peter/selenium_ate_7749_15`

---

## 1. Project Overview & Requirements Fulfillment

This repository contains an end-to-end automated testing suite written in **Java 21**, **Selenium WebDriver 4.28**, and **JUnit 5.11** against ParaBank. It strictly fulfills all eight required testing rubrics (T1–T8):

| Requirement ID | Description | Implementation Details |
| :--- | :--- | :--- |
| **T1: Smoke Test** | Navigation & Page Load Verification | Asserts page title `ParaBank \| Welcome \| Online Banking`, logo presence, and caption banner visibility upon accessing `/index.htm`. |
| **T2: Locators** | Diverse Element Locator Strategies | Employs `By.name`, `By.cssSelector`, `By.className`, `By.id`, and `By.xpath` (with attribute matching). Zero positional XPaths. |
| **T3: Positive Path** | Complete End-to-End User Flow | Dynamic user registration &rarr; verification &rarr; dashboard navigation &rarr; AJAX fund transfer ($150.00) &rarr; assertions on confirmation header and balance. |
| **T4: Negative Path** | Invalid Auth & Form Submission | Validates error messaging on incorrect credentials (`The username and password could not be verified.`) and blank registration form submissions (`First name is required.`, `Username is required.`). |
| **T5: Explicit Waits** | Synchronization (0 `Thread.sleep`) | Leverages `WebDriverWait` with `ExpectedConditions.visibilityOfElementLocated` and custom predicate checks for AJAX dropdown option population and transfer confirmation. |
| **T6: Parameterized EP** | Data-Driven Equivalence Partitioning | `@ParameterizedTest` with `@MethodSource` evaluating 5 equivalence partitions across valid, non-existent, empty username, empty password, and blank credential combinations. |
| **T7: Page Object Model** | Full POM Encapsulation | Encapsulates UI components and intention-revealing methods inside `BasePage`, `LoginPage`, `RegisterPage`, `AccountsOverviewPage`, and `TransferFundsPage`. |
| **T8: Test Lifecycle** | JUnit 5 `@BeforeEach` / `@AfterEach` | Spawns a fresh `ChromeDriver` instance per test in `@BeforeEach` and cleanly terminates it via `driver.quit()` in `@AfterEach`. Fully runnable via `mvn test`. |

---

## 2. Directory Structure

```text
selenium_ate_7749_15/
├── pom.xml
├── README.md
├── generate_report.py
├── selenium_ate_7749_15.pdf
├── selenium_ate_7749_15.zip
└── src/
    ├── main/
    │   └── java/
    │       └── com/aau/testing/pages/
    │           ├── BasePage.java
    │           ├── LoginPage.java
    │           ├── RegisterPage.java
    │           ├── AccountsOverviewPage.java
    │           └── TransferFundsPage.java
    └── test/
        └── java/
            └── com/aau/testing/
                ├── BaseTest.java
                └── ParaBankAutomationTests.java
```

---

## 3. How to Run the Tests

### Prerequisites
- **Java JDK 17 or 21+** installed and configured on `PATH`.
- **Apache Maven 3.8+** installed.
- **Google Chrome** browser installed.

### Command-Line Execution

Run the complete test suite in headless mode (default):
```bash
mvn clean test
```

Run tests with visible browser UI window:
```bash
mvn test -Dheadless=false
```

Run a specific test case:
```bash
mvn test -Dtest=ParaBankAutomationTests#testT3_PositivePathEndToEndFlow
```

---

## 4. Test Report & Submission Details

- **Report PDF:** `selenium_ate_7749_15.pdf` (2-page report including site justification, test matrix table, Equivalence Partitioning design table, usability defects, and green execution log).
- **Archive:** `selenium_ate_7749_15.zip`
