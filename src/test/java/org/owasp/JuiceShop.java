package org.owasp;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;

import static java.util.Map.entry;
import static org.owasp.JuiceShop.Item.*;

public class JuiceShop {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Map<Item, Integer> itemPositions = Map.ofEntries(
            entry(APPLE_JUICE, 1),
            entry(APPLE_POMACE, 2),
            entry(BANANA_JUICE, 3),
            entry(SALESMAN_ARTWORK, 4),
            entry(CARROT_JUICE, 5),
            entry(EGGFRUIT_JUICE, 6),
            entry(FRUIT_PRESS, 7),
            entry(GREEN_SMOOTHIE, 8),
            entry(PERMAFROST_BADGE, 9),
            entry(LEMON_JUICE, 10),
            entry(MELON_BIKE, 11),
            entry(FACE_MASK, 12)
    );

    public JuiceShop(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(4));
    }

    public void addToCart(Item item) {
        driver.findElement(By.cssSelector(".logo"));
        WebElement itemCard = getItemCardElement(item);
        waitAndClick(itemCard.findElement(By.cssSelector("button")));
    }

    public void removeItem(Item item) {
        driver.findElement(By.cssSelector("[aria-label='Show the shopping cart']")).click();
        WebElement itemRow = getItemRow(item.name());
        itemRow.findElement(By.cssSelector(".mat-column-remove")).click();
    }

    private void waitAndClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    private WebElement getItemRow(String item) {
        return driver.findElement(cartByXpath(item));
    }

    public By cartByXpath(String item) {
        return By.xpath(String.format("//mat-cell[contains(text(),%s)]/parent::mat-row", item));
    }

    public BigDecimal getPrice(Item item) {
        WebElement itemCard = getItemCardElement(item);
        String price = itemCard.findElement(By.cssSelector(".item-price > span")).getText();
        return new BigDecimal(price.substring(0, price.length() - 1));
    }

    private WebElement getItemCardElement(Item item) {
        int pos = itemPositions.get(item);
        return driver.findElement(By.cssSelector(String.format("mat-grid-tile:nth-child(%d) > div > mat-card", pos)));
    }

    enum Item {
        APPLE_JUICE("Apple Juice (1000ml)"),
        APPLE_POMACE("Apple Pomace"),
        BANANA_JUICE("Banana Juice (1000ml)"),
        SALESMAN_ARTWORK("Best Juice Shop Salesman Artwork"),
        CARROT_JUICE("Carrot Juice (1000ml)"),
        EGGFRUIT_JUICE("Eggfruit Juice (500ml)"),
        FRUIT_PRESS("Fruit Press"),
        GREEN_SMOOTHIE("Green Smoothie"),
        PERMAFROST_BADGE("Juice Shop \"Permafrost\" 2020 Edition"),
        LEMON_JUICE("Lemon Juice (500ml)"),
        MELON_BIKE("Melon Bike (Comeback-Product 2018 Edition)"),
        FACE_MASK("OWASP Juice Shop \"King of the Hill\" Facemask");

        Item(String title) {
        }
    }
}