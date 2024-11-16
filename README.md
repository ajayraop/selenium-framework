# Car Valuation Test Automation

This project is a Maven-based automation testing framework for car valuation using Selenium WebDriver. The framework tests the car registration and valuation functionality on the "We Buy Any Car" website.

## Commands

### 1. Install Maven Dependencies without Running Tests

To install the dependencies listed in the `pom.xml` file without running the tests, use the following command:

```bash
mvn install -DskipTests
```

### 2. Running the Tests

#### Default site is set to "webuyanycar"

```bash
mvn test
```

#### set site to "webuyanycar" 

```bash
 mvn test -Dsite=webuyanycar
```

#### set site to "confused"

```bash
 mvn test -Dsite=confused
```

#### set site to "autotrader"

```bash
 mvn test -Dsite=autotrader
```

## Note

site property is case-insensitive