package org.owasp.ui;

import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

@Getter
public class LoginPage {
    private final WebDriver driver;
    private final FakerData faker;
    @FindBy(id = "email")
    private WebElement email;
    @FindBy(id = "password")
    private WebElement password;
    @FindBy(id = "loginButton")
    private WebElement loginButton;
    @FindBy(id = "navbarAccount")
    private WebElement navbarAccount;
    @FindBy(id = "navbarLogoutButton")
    private WebElement navbarLogout;

    public LoginPage(WebDriver driver, FakerData faker) {
        this.driver = driver;
        this.faker = faker;
        PageFactory.initElements(driver, this);
    }

    public void navigate() {
        driver.navigate().to("http://localhost:3000/#/login");
    }

    public void submit() {
        email.sendKeys(faker.getEmail());
        password.sendKeys(faker.getPassword());
        loginButton.click();
    }
}
