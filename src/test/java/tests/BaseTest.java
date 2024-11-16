package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.AutoTraderPage;
import pages.BasePage;
import pages.ConfusedPage;
import utils.PropertiesLoader;
import pages.WeBuyAnyCarPage;

import java.io.File;
import java.io.IOException;

public class BaseTest {
    protected static WebDriver driver;
    protected static ExtentReports extent;
    protected static final String screenshotsFolder = "screenshots";
    private static String baseUrl;
    protected static BasePage currentPage;

    private enum Site {
        WEBUYANYCAR("webuyanycar", WeBuyAnyCarPage.class),
        CONFUSED("confused", ConfusedPage.class),
        AUTOTRADER("autotrader", AutoTraderPage.class);

        private final String siteName;
        private final Class<? extends BasePage> pageClass;

        Site(String siteName, Class<? extends BasePage> pageClass) {
            this.siteName = siteName;
            this.pageClass = pageClass;
        }

        public String getSiteName() {
            return siteName;
        }

        public Class<? extends BasePage> getPageClass() {
            return pageClass;
        }

        public static Site fromString(String siteName) {
            for (Site site : Site.values()) {
                if (site.getSiteName().equalsIgnoreCase(siteName)) {
                    return site;
                }
            }
            throw new IllegalArgumentException("Invalid site specified: " + siteName);
        }
    }

    @BeforeAll
    public static void beforeAllTests() throws IOException {
        // Delete the screenshots folder if it exists
        File screenshotsDir = new File(screenshotsFolder);
        if (screenshotsDir.exists()) {
            FileUtils.deleteDirectory(screenshotsDir);
        }

        // Load properties using the PropertiesLoader class
        PropertiesLoader propertiesLoader = new PropertiesLoader("config");
        baseUrl = propertiesLoader.getBaseUrl();
    }

    @BeforeEach
    public void setUp() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);

        String site = System.getProperty("site");

        // Initialize the appropriate Page Object dynamically using the enum
        Site siteEnum = Site.fromString(site);
        try {
            currentPage = siteEnum.getPageClass().getConstructor(WebDriver.class).newInstance(driver);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error initializing page for site: " + site, e);
        }

        if (extent == null) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("extent_report.html");
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterAll
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
}
