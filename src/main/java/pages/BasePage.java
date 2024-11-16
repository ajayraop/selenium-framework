package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.Map;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Default wait time
    }

    public abstract void acceptCookies();

    public abstract void searchCar(String reg, int mileage);

    public abstract void waitForLoadingImageToAppearAndDisappear();

    public abstract Map<String, String[]> getCarDetails();

    public WebElement fluentWait(By locator) {
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))  // Max wait time
                .pollingEvery(Duration.ofMillis(500))  // Interval to check for element
                .ignoring(NoSuchElementException.class)  // Ignore if element is not found immediately
                .ignoring(ElementNotInteractableException.class); // Ignore if element is not interactable
        return fluentWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement waitForElementToBeVisible(By locator) {
        return fluentWait(locator);
    }

    public WebElement waitForElementToBeClickable(By locator) {
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotInteractableException.class);

        return fluentWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void clickElement(By locator) {
        WebElement element = waitForElementToBeClickable(locator);
        element.click();
    }

    public void sendKeysToElement(By locator, String text) {
        WebElement element = waitForElementToBeVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    public String getChildElementText(By parentLocator, By childLocator) {
        WebElement parentElement = waitForElementToBeVisible(parentLocator);
        return parentElement.findElement(childLocator).getText();
    }

    public void waitForImageToAppearAndDisappear(By locator) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            wait.until(ExpectedConditions.invisibilityOf(element));
            System.out.println("element with locator " + locator + " has disappeared");
        } catch (TimeoutException e) {
            System.out.println("Timed out waiting for the element with locator " + locator + "to appear/disappear");
        }
    }
}

