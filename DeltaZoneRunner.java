import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DeltaZoneRunner extends DeltaZoneLogin001{
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
       //Sign up element verification
        signUp(driver,"sanjaibala043@gmail.com","Delta@,124");

        //Invalid Login
        login(driver,"ragesh.1995@gmail.com","Delta,124");
        driver.navigate().refresh();

        // Valid Login
        login(driver,"sanjaibala43@gmail.com","Delta,1234");

        //verifying upload file
        uploadFile(driver,"C:\\Users\\91861\\Desktop\\scrnsht1.png");

        //rename the file
        rename(driver,"screenshotA");

        //verifying delete functionality
        delete(driver);

        uploadFile(driver,"C:\\Users\\91861\\Desktop\\scrnsht1.png");

        //add directory
        addDirectory(driver,"SAN");

        //rename the directory
        rename(driver,"SANJAI");

        //move to directory
        moveTo(driver,"SANJAI");

        //verifying upload Directory
        uploadDirectory(driver,"D:\\Sanjai\\Screenshot\\TC1");

        //verifying search
        search(driver,"sanja");
        driver.navigate().back();

        //verifying download
        download(driver,"sanja");

        // revert -> deleting all files
        revert(driver);

        //Log out
        logOut(driver);

        // Need to verify addDirectory, rename, delete ,move within "Directory" ----------pending
        // at end page should be "File manager" - Done
        // rename directory and file - Done
        // null for login - Need to check why null can't be passed then how to test blank value
        // sign up      --------------------- issue or not "wrong mail and password" if same mail used again
        // log out- Done




    }

}
