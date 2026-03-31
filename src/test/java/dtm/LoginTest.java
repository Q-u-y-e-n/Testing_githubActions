package dtm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    @BeforeMethod
    public void setUp() {
        // Gọi DriverFactory để đảm bảo chạy được trên GitHub Actions
    	DriverFactory.initDriver("chrome");
        driver = DriverFactory.getDriver();
        // Cấu hình Explicit Wait (tối đa 10 giây)
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com");
    }

    @Test(description = "testLoginSuccess: Nhập user/pass hợp lệ", groups = {"smoke", "regression"})
    public void testLoginSuccess() {
        WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        userField.sendKeys("standard_user");
        
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Chờ URL thay đổi
        wait.until(ExpectedConditions.urlContains("/inventory.html"));
        
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        Assert.assertTrue(errorMsg.getText().contains("Username is required"), 
                "Không hiển thị thông báo lỗi 'Username is required'!");
    }

    @Test(description = "testLoginEmptyPassword: Bỏ trống password")
    public void testLoginEmptyPassword() {
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

    @AfterMethod
    public void tearDown() { 
        if (driver != null) {
            driver.quit();
        }
    }
}