package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

public class ConfusedPage extends BasePage {
    //    TODO - locators need to be updated for Confused.com site

    public ConfusedPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void acceptCookies() {
        // TODO - Implement the acceptCookies method
    }

    @Override
    public void searchCar(String reg, int mileage) {
        // TODO - Implement the searchCar method with the given reg and mileage
    }

    @Override
    public void waitForLoadingImageToAppearAndDisappear() {
        // TODO - Implement the waitForLoadingImageToAppearAndDisappear method
    }

    @Override
    public Map<String, String[]> getCarDetails() {
        // TODO - Implement the getCarDetails method to return actual car details
        return new HashMap<>() {
        };
    }
}

