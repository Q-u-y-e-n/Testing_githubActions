package dtm;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class TitleTest {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Thiết lập ChromeDriver tự động
        WebDriverManager.chromedriver().setup(); // [cite: 74]
        driver = new ChromeDriver(); // [cite: 79]
        driver.manage().window().maximize(); // [cite: 80]
        driver.get("https://www.saucedemo.com"); // [cite: 81]
    }

    @Test(description = "Kiem thu tieu de trang chu")
    public void testTitle() {
        String expectedTitle = "Swag Labs"; // [cite: 85]
        String actualTitle = driver.getTitle(); // [cite: 86]
        Assert.assertEquals(actualTitle, expectedTitle, "Tieu de trang khong dung!"); // [cite: 88]
    }

    @Test(description = "Kiem thu URL trang chu")
    public void testURL() {
        String actualUrl = driver.getCurrentUrl(); // [cite: 92]
        Assert.assertTrue(actualUrl.contains("saucedemo"), "URL khong hop le!"); // [cite: 93]
    }

    // --- PHẦN YÊU CẦU LÀM THÊM [cite: 98, 99] ---

    @Test(description = "Kiem thu nguon trang (page source)")
    public void testPageSource() {
        // Lấy toàn bộ mã nguồn HTML của trang
        String pageSource = driver.getPageSource();
        // Kiểm tra xem mã nguồn có chứa thẻ div id="root" hoặc một class đặc trưng nào đó không
        Assert.assertTrue(pageSource.contains("login_wrapper"), "Nguon trang khong hop le hoac khong chua form dang nhap!");
    }

    @Test(description = "Kiem thu form dang nhap co hien thi hay khong")
    public void testLoginFormDisplayed() {
        // Tìm nút Login bằng ID
        WebElement loginButton = driver.findElement(By.id("login-button"));
        // Kiểm tra phần tử có đang hiển thị trên giao diện không [cite: 57]
        Assert.assertTrue(loginButton.isDisplayed(), "Nut dang nhap khong hien thi tren man hinh!");
    }

    @AfterMethod
    public void tearDown() {
        // Đóng toàn bộ session trình duyệt sau khi test xong
        driver.quit(); // [cite: 96]
    }
}