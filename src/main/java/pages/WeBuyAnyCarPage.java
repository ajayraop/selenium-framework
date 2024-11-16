package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

public class WeBuyAnyCarPage extends BasePage {
    private final By acceptCookies = By.id("onetrust-accept-btn-handler");
    private final By regInput = By.id("vehicleReg");
    private final By mileageInput = By.id("Mileage");
    private final By submitButton = By.id("btn-go");

    //Search Results
    private final By carDetails = By.cssSelector(".order-lg-3");
    private final By reg = By.cssSelector("div.details-vrm.ng-star-inserted");
    private final By make = By.cssSelector("div.d-table div:nth-child(1) div.value");
    private final By model = By.cssSelector("div.d-table div:nth-child(2) div.value");
    private final By year = By.cssSelector("div.d-table div:nth-child(3) div.value");
    private final By carNotFound = By.cssSelector(".text-focus.ng-star-inserted");
    private final By loading = By.cssSelector("img[loading='eager']");

    public WeBuyAnyCarPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void acceptCookies() {
        clickElement(acceptCookies);
    }

    @Override
    public void searchCar(String reg, int mileage) {
        sendKeysToElement(regInput, reg);
        sendKeysToElement(mileageInput, String.valueOf(mileage));
        clickElement(submitButton);
    }

    @Override
    public void waitForLoadingImageToAppearAndDisappear() {
        waitForImageToAppearAndDisappear(loading);
    }

    @Override
    public Map<String, String[]> getCarDetails() {
        return new HashMap<>() {{
            put(getChildElementText(carDetails, reg),
                    new String[]{
                            getChildElementText(carDetails, make),
                            getChildElementText(carDetails, model),
                            getChildElementText(carDetails, year)
                    });
        }};
    }
}

