import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DeltaZone extends DeltaZoneBaseClass{
    public static void main(String[] args) throws InterruptedException, IOException, AWTException, ElementClickInterceptedException,
            NoAlertPresentException, StaleElementReferenceException, TimeoutException, SessionNotCreatedException {
        System.setProperty("webdriver.chrome.driver","D:\\Sanjai\\Apps\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver=new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.navigate().to("https://my.deltazone.dev/");

/*
//      Invalid Username pwd
        login(driver,"ragesh.1995@gmail.com","Delta,124");
        WebElement errorMsg=driver.findElement(By.xpath("//span[@id='error' and @style='color: red;']"));
        String eMsg=errorMsg.getText();
        if(eMsg.equals("Wrong email or password")) {
            System.out.println("Login failed : "+eMsg);
        }
        driver.navigate().refresh();

 */

        //Invalid Login
        login(driver,"ragesh.1995@gmail.com","Delta,124");
        driver.navigate().refresh();
        // Valid Login
        login(driver,"sanjaibala43@gmail.com","Delta,1234");
        System.out.println("After Login");
        //verifying upload file
        uploadFile(driver,"C:\\Users\\91861\\Desktop\\scrnsht1.png");

        //verifying upload Directory
        uploadDirectory(driver,"D:\\Sanjai\\Screenshot\\TC1");

        //verifying delete functionality
        delete(driver);
        //verifying search
        search(driver,"sanja");
        driver.navigate().back();

        //verifying download
        download(driver,"sanja");

       // driver.quit();

    }

}
