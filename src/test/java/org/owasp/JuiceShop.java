package org.owasp;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;

import static java.util.Map.entry;

public class JuiceShop {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final Map<String, Integer> itemPositions = Map.ofEntries(
            entry("AppleJuice", 1),
            entry("ApplePomace", 2),
            entry("BananaJuice", 3),
            entry("SalesmanArtwork", 4),
            entry("CarrotJuice", 5),
            entry("EggfruitJuice", 6),
            entry("FruitPress", 7),
            entry("GreenSmoothie", 8),
            entry("PermafrostBadge", 9),
            entry("LemonJuice", 10),
            entry("MelonBike", 11),
            entry("FaceMask", 12)
    );

    public JuiceShop(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(4));
    }

    public void addToCart(String item) {
        WebElement itemCard = getWebElement(item);
        wait.until(ExpectedConditions.elementToBeClickable(itemCard.findElement(By.cssSelector("button")))).click();
    }

    private WebElement getWebElement(String item) {
        int pos = itemPositions.get(item);
        return driver.findElement(By.cssSelector(String.format("mat-grid-tile:nth-child(%d) > div > mat-card", pos)));
    }
}