package dtm;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartTest {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
    	String browserName = System.getProperty("browser", "chrome");
        
        // Khởi tạo trình duyệt dựa trên biến nhận được
        driver = DriverFactory.initDriver(browserName);
        driver.get("https://www.google.com"); // Mở Google cho dễ phân biệt
    }

    @Test
    public void testAddProductToCart() {
        System.out.println("Chay CartTest -> testAddProductToCart");
    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        Thread.sleep(5000);
        DriverFactory.quitDriver(driver); // ✅ Truyền driver vào
    }
}