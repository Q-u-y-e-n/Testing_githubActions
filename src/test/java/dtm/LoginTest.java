package dtm;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {
    WebDriver driver;
    WebDriverWait wait;

    // Yêu cầu 1: Dùng @BeforeMethod để mở trình duyệt mới cho MỖI test case
 // Sửa lại method setUp()
    @BeforeMethod
    public void setUp() {
        // Gọi DriverFactory khởi tạo Chrome
        DriverFactory.initDriver("chrome");
        // Lấy driver từ ThreadLocal ra để dùng
        driver = DriverFactory.getDriver();
        
        // Cấu hình Explicit Wait (tối đa 10 giây)
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com");
    }

    @Test(description = "testLoginSuccess: Nhập user/pass hợp lệ", groups = {"smoke", "regression"})
    public void testLoginSuccess() {
 
        // Yêu cầu 2: Dùng Explicit Wait thay vì Thread.sleep()
        WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        userField.sendKeys("standard_user");
        
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Chờ URL thay đổi
        wait.until(ExpectedConditions.urlContains("/inventory.html"));
        
        // Yêu cầu 3: Mỗi Assert phải có thông báo lỗi
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"), 
                "Đăng nhập thành công nhưng không chuyển hướng sang /inventory.html!");
        System.out.println("Chay LoginTest -> testLoginSuccess (Nhóm: smoke, regression)");
     
    }

    @Test(description = "testLoginWrongPassword: Nhập sai mật khẩu", groups = {"regression"})

    public void testLoginWrongPassword() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name"))).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("wrong_password123");
        driver.findElement(By.id("login-button")).click();

        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        Assert.assertTrue(errorMsg.getText().contains("Username and password do not match"), 
                "Không hiển thị đúng thông báo lỗi sai mật khẩu!");
        System.out.println("Chay LoginTest -> testLoginWrongPassword (Nhóm: regression)");
    }

    @Test(description = "testLoginEmptyUsername: Bỏ trống username")
    public void testLoginEmptyUsername() {
        // Chỉ nhập password, bỏ trống username
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        Assert.assertTrue(errorMsg.getText().contains("Username is required"), 
                "Không hiển thị thông báo lỗi 'Username is required'!");
    }

    @Test(description = "testLoginEmptyPassword: Bỏ trống password")
    public void testLoginEmptyPassword() {
        // Chỉ nhập username, bỏ trống password
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name"))).sendKeys("standard_user");
        driver.findElement(By.id("login-button")).click();

        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        Assert.assertTrue(errorMsg.getText().contains("Password is required"), 
                "Không hiển thị thông báo lỗi 'Password is required'!");
    }

    @Test(description = "testLoginLockedUser: Dùng tài khoản bị khóa")
    public void testLoginLockedUser() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name"))).sendKeys("locked_out_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        Assert.assertTrue(errorMsg.getText().contains("Sorry, this user has been locked out"), 
                "Không hiển thị thông báo lỗi tài khoản bị khóa!");
    }

    // Yêu cầu 1: Dùng @AfterMethod để đóng trình duyệt sau mỗi test
  
    @AfterMethod
    public void tearDown() throws InterruptedException { // Nhớ thêm throws InterruptedException nhé
        // TẠM THỜI: Dừng luồng này lại 5 giây (5000 milliseconds) để kịp chụp ảnh
        Thread.sleep(5000); 
        
        // Sau 5 giây, trình duyệt mới bị đóng
        DriverFactory.quitDriver();
    }
}