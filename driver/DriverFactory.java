import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {
    
	// Đảm bảo tên hàm trong Test class gọi đúng hàm này
	public static WebDriver initDriver(String browser) { 
	    boolean isCI = System.getenv("CI") != null; [cite: 86]

	    return switch (browser.toLowerCase()) {
	        case "firefox" -> createFirefoxDriver(isCI); [cite: 87]
	        default -> createChromeDriver(isCI); [cite: 87]
	    };
	}
    private static WebDriver createChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions(); [cite: 92]
        
        if (headless) { [cite: 93]
            // Chế độ không màn hình (Headless) bắt buộc trên Linux CI [cite: 83, 97]
            options.addArguments("--headless=new"); // Chrome 112+ [cite: 94, 96]
            options.addArguments("--no-sandbox"); // Bắt buộc trên Linux [cite: 95, 97]
            options.addArguments("--disable-dev-shm-usage"); // Tránh lỗi Out of Memory (OOM) [cite: 98, 121]
            options.addArguments("--window-size=1920,1080"); // Thiết lập kích thước màn hình giả lập [cite: 99]
        } else {
            options.addArguments("--start-maximized"); // Chạy bình thường ở máy local [cite: 102]
        }
        
        WebDriverManager.chromedriver().setup(); [cite: 103]
        return new ChromeDriver(options); [cite: 104]
    }

    private static WebDriver createFirefoxDriver(boolean headless) {
        FirefoxOptions options = new FirefoxOptions(); [cite: 106]
        
        if (headless) {
            options.addArguments("-headless"); // Chế độ không màn hình cho Firefox [cite: 107, 165]
        }
        
        WebDriverManager.firefoxdriver().setup(); [cite: 108]
        return new FirefoxDriver(options); [cite: 113]
    }
}