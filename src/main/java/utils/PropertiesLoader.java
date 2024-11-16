package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    private final Properties properties;
    private static final String DEFAULT_URL = "https://www.webuyanycar.com";
    private static final String CONFIG_FOLDER = "src/test/resources/config/";

    public PropertiesLoader(String propertiesFileName) {
        properties = new Properties();
        String filePath = CONFIG_FOLDER + propertiesFileName + ".properties";

        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Error loading properties file: " + e.getMessage());
        }
    }

    public String getBaseUrl() {
        String siteName = getSystemPropertyIgnoreCase("site", "webuyanycar");
        System.setProperty("site", siteName);
        String baseUrl = properties.getProperty(siteName);
        if (baseUrl == null || baseUrl.isEmpty()) {
            System.out.println("Base URL not found in properties file. Using default: " + DEFAULT_URL);
            return DEFAULT_URL;
        }
        return baseUrl;
    }

    public static String getSystemPropertyIgnoreCase(String key, String defaultValue) {
        for (String propertyKey : System.getProperties().stringPropertyNames()) {
            if (propertyKey.equalsIgnoreCase(key)) {
                return System.getProperty(propertyKey);
            }
        }
        return defaultValue;
    }
}
