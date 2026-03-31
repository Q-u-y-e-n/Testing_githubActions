package dtm;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartTest {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        DriverFactory.initDriver("chrome");
        driver = DriverFactory.getDriver();
        driver.get("https://www.google.com"); // Mở Google cho dễ phân biệt
    }

    @Test
    public void testAddProductToCart() {
        System.out.println("Chay CartTest -> testAddProductToCart");
    }

    @AfterMethod
    public void tearDown() throws InterruptedException { // Nhớ thêm throws InterruptedException nhé
        // TẠM THỜI: Dừng luồng này lại 5 giây (5000 milliseconds) để kịp chụp ảnh
        Thread.sleep(5000); 
        
        // Sau 5 giây, trình duyệt mới bị đóng
        DriverFactory.quitDriver();
    }
}