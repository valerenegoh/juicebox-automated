package org.owasp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.*;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;


public class ChromeTest {
    private static FakerData faker = new FakerData();
    private WebDriver driver;

    @Before
    public void setup() {
        driver = WebDriverManager.chromedriver().create();
        driver.navigate().to("http://localhost:3000");
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.MILLISECONDS);
    }

    @After
    public void teardown() {
        driver.quit();
    }

    @Test
    public void registerAccountForUser() throws InterruptedException {
        closePopUp();
        navigateToLogin();
        registerUser("Mother's maiden name?", "beautiful");

        assertEquals("Registration completed successfully. You can now log in.", getSnackbarMessages());
    }

    private String getSnackbarMessages() {
        return driver.findElement(By.cssSelector(".mat-simple-snack-bar-content")).getText();
    }

    private void registerUser(String securityQuestion, String securityAnswer) throws InterruptedException {
        driver.findElement(By.id("newCustomerLink")).click();

        driver.findElement(By.id("emailControl")).sendKeys(faker.getEmail());
        driver.findElement(By.id("passwordControl")).sendKeys(faker.getPassword());
        driver.findElement(By.id("repeatPasswordControl")).sendKeys(faker.getPassword());

        driver.findElement(By.name("securityQuestion")).click();
        String xpath = String.format("//mat-option/span[contains(text(),\"%s\")]", securityQuestion);
        driver.findElement(By.xpath(xpath)).click();
        driver.findElement(By.id("securityAnswerControl")).sendKeys(securityAnswer);

        driver.findElement(By.id("registerButton")).click();

        Thread.sleep(200);
    }

    private void navigateToLogin() {
        driver.findElement(By.id("navbarAccount")).click();
        driver.findElement(By.id("navbarLoginButton")).click();
    }

    private void closePopUp() {
        driver.findElement(By.cssSelector(".close-dialog")).click();
    }

}