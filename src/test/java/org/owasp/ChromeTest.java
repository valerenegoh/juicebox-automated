package org.owasp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.openqa.selenium.*;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
class ChromeTest {
    private static FakerData faker;
    private static JuiceShop juiceShop;
    private static WebDriver driver;

    @BeforeAll
    static void setup() {
        driver = WebDriverManager.chromedriver().create();
        driver.navigate().to("http://localhost:3000");
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.MILLISECONDS);

        faker = new FakerData();
        juiceShop = new JuiceShop(driver);
    }

    @AfterAll
    static void teardown() {
        driver.quit();
    }

    @Test
    @Order(1)
    void createUserAccount() throws InterruptedException {
        closePopUp();
        navigateToLogin();
        submitUserRegistrationForm("Mother's maiden name?", "beautiful");

        Thread.sleep(200);
        assertEquals("Registration completed successfully. You can now log in.", getSnackbarMessageAndClose());
    }

    @Test
    @Order(2)
    void loginToAccount() {
        navigateToLogin();
        submitUserLoginForm();

        driver.findElement(By.id("navbarAccount")).click();
        assertTrue(driver.findElement(By.id("navbarLogoutButton")).isDisplayed());
    }

    @Test
    @Order(3)
    void addItemToCart() {
        removeOverlay();
        juiceShop.addToCart("AppleJuice");

        assertEquals("Placed Apple Juice (1000ml) into basket.", getSnackbarMessageAndClose());
    }

    private void removeOverlay() {
        driver.findElement(By.cssSelector(".cdk-overlay-backdrop")).click();
    }

    private void submitUserLoginForm() {
        driver.findElement(By.id("email")).sendKeys(faker.getEmail());
        driver.findElement(By.id("password")).sendKeys(faker.getPassword());
        driver.findElement(By.id("loginButton")).click();
    }

    private String getSnackbarMessageAndClose() {
        String snackbar = driver.findElement(By.cssSelector(".mat-simple-snack-bar-content")).getText();
        driver.findElement(By.cssSelector(".mat-simple-snackbar-action")).click();
        return snackbar;
    }

    private void submitUserRegistrationForm(String securityQuestion, String securityAnswer) {
        driver.findElement(By.id("newCustomerLink")).click();

        driver.findElement(By.id("emailControl")).sendKeys(faker.getEmail());
        driver.findElement(By.id("passwordControl")).sendKeys(faker.getPassword());
        driver.findElement(By.id("repeatPasswordControl")).sendKeys(faker.getPassword());

        driver.findElement(By.name("securityQuestion")).click();
        String xpath = String.format("//mat-option/span[contains(text(),\"%s\")]", securityQuestion);
        driver.findElement(By.xpath(xpath)).click();
        driver.findElement(By.id("securityAnswerControl")).sendKeys(securityAnswer);

        driver.findElement(By.id("registerButton")).click();
    }

    private void navigateToLogin() {
        driver.findElement(By.id("navbarAccount")).click();
        driver.findElement(By.id("navbarLoginButton")).click();
    }

    private void closePopUp() {
        driver.findElement(By.cssSelector(".close-dialog")).click();
    }
}