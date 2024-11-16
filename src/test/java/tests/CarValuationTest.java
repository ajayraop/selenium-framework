package tests;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.FileParser;
import com.aventstack.extentreports.ExtentTest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CarValuationTest extends BaseTest {
    private static final String testDataFolderPath = "src/test/resources/testData/";

    // Use FileParser to load car registrations from a file and provide them to the parameterized test
    public static Stream<String> loadCarRegistrations() throws IOException {
        List<String> regNumbers = FileParser.getRegistrationNumbers(testDataFolderPath + "car_input.txt");
        return regNumbers.stream();
    }

    // Parameterized test that runs for each car registration
    @ParameterizedTest
    @MethodSource("loadCarRegistrations")
    public void testCarValuation(String carRegistration) throws IOException {
        ExtentTest test = extent.createTest("Car Valuation Test for Registration: " + carRegistration);
        Map<String, String[]> expectedData = FileParser.getExpectedData(testDataFolderPath + "car_output.txt");
        String[] actualCarDetails = new String[0];

        try {
            test.info("Running test for car registration: " + carRegistration);
            currentPage.acceptCookies();

            // Generate random mileage
            int randomMileage = (int) (Math.random() * 100000);
            test.info("Testing with mileage: " + randomMileage);

            currentPage.searchCar(carRegistration, randomMileage);

            // Wait for results to load and process the output
            currentPage.waitForLoadingImageToAppearAndDisappear();

            String[] expectedCarDetails = expectedData.get(carRegistration);
            actualCarDetails = currentPage.getCarDetails().get(carRegistration);
            Assertions.assertArrayEquals(expectedCarDetails, actualCarDetails, "The car registration data is not correct!");

            test.pass("Car details match for registration: " + carRegistration);
            test.info("Car details: " + String.join(", ", actualCarDetails));
        } catch (AssertionError e) {
            test.fail("Assertion failed: " + e.getMessage());
            takeScreenshot(carRegistration, test);
            throw e;
        } catch (Exception e) {
            test.fail("Unexpected error: " + e.getMessage());
            takeScreenshot(carRegistration, test);
            throw e;
        }
    }

    private void takeScreenshot(String carRegistration, ExtentTest test) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            File destFile = new File(screenshotsFolder + "/screenshot_" + carRegistration + ".png");
            FileUtils.copyFile(srcFile, destFile);
            test.addScreenCaptureFromPath(destFile.getAbsolutePath()); // Add screenshot to report
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
