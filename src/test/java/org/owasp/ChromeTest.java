package org.owasp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.owasp.JuiceShop.Item.*;

@TestMethodOrder(OrderAnnotation.class)
class ChromeTest {
    private static FakerData faker;
    private static JuiceShop juiceShop;
    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    static void setup() {
        driver = WebDriverManager.chromedriver().create();
        driver.navigate().to("http://localhost:3000");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        wait = new WebDriverWait(driver, Duration.ofSeconds(4));

        faker = new FakerData();
        juiceShop = new JuiceShop(driver);

        closePopUp();
    }

    @AfterAll
    static void teardown() {
        driver.quit();
    }

    @Test
    @Order(1)
    void createUserAccount() {
        getSnackbarMessageAndClose();

        RegistrationPage registrationPage = new RegistrationPage(driver, faker);
        registrationPage.navigate();
        registrationPage.submit();

        assertEquals("Registration completed successfully. You can now log in.", getSnackbarMessageAndClose());
    }

    @Test
    @Order(2)
    void loginToAccount() {
        LoginPage loginPage = new LoginPage(driver, faker);
        loginPage.navigate();
        loginPage.submit();

        loginPage.getNavbarAccount().click();
        assertTrue(loginPage.getNavbarLogout().isDisplayed());
    }

    @Test
    @Order(3)
    void addItemToCart() {
        removeOverlay();
        juiceShop.addToCart(APPLE_JUICE);

        assertEquals("Placed Apple Juice (1000ml) into basket.", getSnackbarMessageAndClose());
    }

    @Test
    @Order(4)
    void removeItemFromCart() throws InterruptedException {
        juiceShop.removeItem(APPLE_JUICE);

        Thread.sleep(200);
        List<WebElement> appleJuice = driver.findElements(juiceShop.cartByXpath(APPLE_JUICE.name()));
        assertEquals(0, appleJuice.size());
    }

    private void removeOverlay() {
        driver.findElement(By.cssSelector(".cdk-overlay-backdrop")).click();
    }

    private String getSnackbarMessageAndClose() {
        wait.until(ExpectedConditions.visibilityOf(getSnackbarElement()));
        String snackbar = getSnackbarElement().getText();
        driver.findElement(By.cssSelector(".mat-simple-snackbar-action")).click();
        return snackbar;
    }

    private WebElement getSnackbarElement() {
        return driver.findElement(By.cssSelector(".mat-simple-snack-bar-content"));
    }

    private static void closePopUp() {
        driver.findElement(By.cssSelector(".close-dialog")).click();
    }
}