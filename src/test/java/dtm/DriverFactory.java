package dtm;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    private static final boolean IS_CI = System.getenv("CI") != null;

    public static WebDriver initDriver(String browser) {
        if (browser == null || browser.trim().isEmpty()) {
            browser = "chrome";
        }
        if (browser.trim().toLowerCase().equals("firefox")) {
            return createFirefoxDriver(IS_CI);
        } else {
            return createChromeDriver(IS_CI);
        }
    }

    public static WebDriver initDriver() {
        // Đọc -Dbrowser=chrome/firefox từ lệnh mvn
        String browser = System.getProperty("browser", "chrome");
        return initDriver(browser);
    }

    private static WebDriver createChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--remote-allow-origins=*");
        } else {
            options.addArguments("--start-maximized");
        }

        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless");
        }
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver(options);
    }
    public static void quitDriver(WebDriver driver) {
        if (driver != null) {
            driver.quit();
        }
    }
}