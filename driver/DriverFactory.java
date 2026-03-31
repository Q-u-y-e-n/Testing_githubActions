package dtm; // Đảm bảo đúng package của bạn

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {
    // Sửa 'void' thành 'WebDriver' [cite: 85]
    public static WebDriver initDriver(String browser) {
        boolean isCI = System.getenv("CI") != null; [cite: 86]

        // Thêm 'return' vào trước switch để trả về kết quả từ các hàm con [cite: 87]
        return switch (browser.toLowerCase()) {
            case "firefox" -> createFirefoxDriver(isCI); [cite: 87]
            default -> createChromeDriver(isCI); [cite: 87]
        };
    }

    private static WebDriver createChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions(); [cite: 92]
        if (headless) { [cite: 93]
            options.addArguments("--headless=new"); [cite: 94]
            options.addArguments("--no-sandbox"); [cite: 95]
            options.addArguments("--disable-dev-shm-usage"); [cite: 98]
            options.addArguments("--window-size=1920,1080"); [cite: 99]
        } else {
            options.addArguments("--start-maximized"); [cite: 102]
        }
        WebDriverManager.chromedriver().setup(); [cite: 103]
        return new ChromeDriver(options); [cite: 104]
    }

    private static WebDriver createFirefoxDriver(boolean headless) {
        FirefoxOptions options = new FirefoxOptions(); [cite: 106]
        if (headless) {
            options.addArguments("-headless"); [cite: 107]
        }
        WebDriverManager.firefoxdriver().setup(); [cite: 108]
        return new FirefoxDriver(options); [cite: 113]
    }
}