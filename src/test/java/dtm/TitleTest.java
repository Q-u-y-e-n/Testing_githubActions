package dtm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TitleTest {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Đồng bộ sử dụng DriverFactory để kế thừa cấu hình CI
    	driver = DriverFactory.initDriver("chrome");
   
        driver.get("https://www.saucedemo.com"); 
    }

    @Test(description = "Kiem thu tieu de trang chu")
    public void testTitle() {
        String expectedTitle = "Swag Labs"; 
        String actualTitle = driver.getTitle(); 
        Assert.assertEquals(actualTitle, expectedTitle, "Tieu de trang khong dung!"); 
    }

    @Test(description = "Kiem thu URL trang chu")
    public void testURL() {
        String actualUrl = driver.getCurrentUrl(); 
        Assert.assertTrue(actualUrl.contains("saucedemo"), "URL khong hop le!"); 
    }

    @Test(description = "Kiem thu nguon trang (page source)")
    public void testPageSource() {
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("login_wrapper"), "Nguon trang khong hop le hoac khong chua form dang nhap!");
    }

    @Test(description = "Kiem thu form dang nhap co hien thi hay khong")
    public void testLoginFormDisplayed() {
        WebElement loginButton = driver.findElement(By.id("login-button"));
        Assert.assertTrue(loginButton.isDisplayed(), "Nut dang nhap khong hien thi tren man hinh!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit(); 
        }
    }
}