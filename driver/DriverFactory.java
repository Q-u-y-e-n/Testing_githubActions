public class DriverFactory {
    public static WebDriver createDriver(String browser) {
        // GitHub Actions tự động đặt biến môi trường CI=true khi chạy pipeline
        boolean isCI = System.getenv("CI") != null; [cite: 86]

        return switch (browser.toLowerCase()) {
            case "firefox" -> createFirefoxDriver(isCI); [cite: 87]
            default -> createChromeDriver(isCI); [cite: 87]
        };
    }

    private static WebDriver createChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions(); [cite: 92]
        
        if (headless) { [cite: 93]
            // Các đối số bắt buộc để chạy ổn định trên Linux CI [cite: 97]
            options.addArguments("--headless=new"); [cite: 94]
            options.addArguments("--no-sandbox"); [cite: 95]
            options.addArguments("--disable-dev-shm-usage"); // Tránh lỗi thiếu bộ nhớ (OOM) [cite: 98]
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