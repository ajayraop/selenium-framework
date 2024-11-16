package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {
    public static List<String> getRegistrationNumbers(String filePath) throws IOException {
        List<String> regNumbers = new ArrayList<>();
        String regex = "\\b[A-Z]{2}\\d{2}\\s?[A-Z]{3}\\b"; // Registration number pattern
        Pattern pattern = Pattern.compile(regex);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    regNumbers.add(matcher.group().replaceAll("\\s", ""));
                }
            }
        }
        return regNumbers;
    }

    public static Map<String, String[]> getExpectedData(String filePath) throws IOException {
        Map<String, String[]> expectedData = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip any empty lines or lines that are not in the expected format
                if (line.trim().isEmpty()) {
                    continue;
                }
                // Split the line at the first comma to separate registration number and details
                String[] parts = line.split(",", 2);

                if (parts.length == 2) {
                    String reg = parts[0].trim();
                    String details = parts[1].trim();

                    String[] detailParts = details.split(",");

                    // store the registration number and car details
                    expectedData.put(reg, detailParts);
                }
            }
        }
        return expectedData;
    }
}
