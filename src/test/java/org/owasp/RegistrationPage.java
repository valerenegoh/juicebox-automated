package org.owasp;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

import java.time.Duration;

public class RegistrationPage {
    private final WebDriver driver;
    private final FakerData faker;
    @FindBy(id = "emailControl")
    private WebElement email;
    @FindBy(id = "passwordControl")
    private WebElement password;
    @FindBy(id = "repeatPasswordControl")
    private WebElement repeatPassword;
    @FindBy(name = "securityQuestion")
    private WebElement securityQuestion;
    @FindBy(xpath = "//mat-option/span[contains(text(),\"Mother's maiden name?\")]")
    private WebElement securityQuestionOption;
    @FindBy(id = "securityAnswerControl")
    private WebElement securityAnswer;
    @FindBy(id = "registerButton")
    private WebElement registerButton;

    public RegistrationPage(WebDriver driver, FakerData faker) {
        this.driver = driver;
        this.faker = faker;
        PageFactory.initElements(driver, this);
    }

    public void navigate() {
        driver.navigate().to("http://localhost:3000/#/register");
    }

    public void submit() {
        email.sendKeys(faker.getEmail());
        password.sendKeys(faker.getPassword());
        repeatPassword.sendKeys(faker.getPassword());

        securityQuestion.click();
        securityQuestionOption.click();
        securityAnswer.sendKeys("beautiful");

        registerButton.click();
    }
}
