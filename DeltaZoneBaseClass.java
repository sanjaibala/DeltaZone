import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DeltaZoneBaseClass {
    public static void login(WebDriver driver, String email, String password) {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        WebElement emailLogin = driver.findElement(By.id("login-email"));
        emailLogin.clear();
        emailLogin.sendKeys(email);
        WebElement passwordLogin = driver.findElement(By.id("login-password"));
        passwordLogin.clear();
        passwordLogin.sendKeys(password);
        brklinks(driver);
        driver.findElement(By.id("submit-login")).click();
        //        driver.findElement(By.id("username")).sendKeys(email);
        //        driver.findElement(By.id("password")).sendKeys(password);
        //        driver.findElement(By.xpath("//button[@name='action']")).click();
    }

    public static void brklinks(WebDriver driver) {
        List<WebElement> links = driver.findElements(By.tagName("a"));
        for (WebElement link : links) {
            try {
                String lnk = link.getAttribute("href");

                URL url = new URL(lnk);
                HttpsURLConnection hURLConnection = (HttpsURLConnection) url.openConnection();
                hURLConnection.connect();
                int resCode = hURLConnection.getResponseCode();
                if (resCode != 200) {
                    System.out.println("Broken link :" + lnk);
                }
            } catch (Exception e) {

            }
        }
    }

    public static void clickUpload(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebElement uploadInnerBtnEnabled = driver.findElement(By.xpath("//span[@class='Actions']/button[@class='upload-button']"));
        //WebElement uploadInnerBtnDisabled= driver.findElement(By.xpath("//span[@class='Actions']/button[@class='upload-button' and @disabled]"));

        WebDriverWait exWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        exWait.until((ExpectedConditions.elementToBeClickable(uploadInnerBtnEnabled)));
        uploadInnerBtnEnabled.click();


        exWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='dialog']//span[text()='ok']")));
        driver.findElement(By.xpath("//div[@role='dialog']//span[text()='ok']")).click();
        brklinks(driver);

    }

    public static void uploadFile(WebDriver driver, String path) throws AWTException, NoSuchElementException, ElementClickInterceptedException {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.linkText("File Manager")).click();
        brklinks(driver);

        //Click Upload
        WebElement uploadBtn = driver.findElement(By.xpath("//button[@class='MuiButtonBase-root MuiButton-root MuiButton-contained MuiButton-containedPrimary']"));
        uploadBtn.click();
        brklinks(driver);

        //upload file
        WebElement uploadFile = driver.findElement(By.xpath("//div[@class='drop-zone '][1]"));
        uploadFile.click();
        brklinks(driver);

        Robot robot = new Robot();
        StringSelection str = new StringSelection(path);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        clickUpload(driver);
    }

    public static void uploadDirectory(WebDriver driver, String path) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.linkText("File Manager")).click();

        //Click Upload
        WebElement uploadBtn = driver.findElement(By.xpath("//button[@class='MuiButtonBase-root MuiButton-root MuiButton-contained MuiButton-containedPrimary']"));
        uploadBtn.click();

        WebElement uploadDirectory = driver.findElement(By.xpath("//div[@class='drop-zone '][2]/input"));
        uploadDirectory.sendKeys(path);
        brklinks(driver);
        Thread.sleep(1000);

        clickUpload(driver);
    }

    public static void search(WebDriver driver, String searchable) {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.linkText("Search")).click();
        brklinks(driver);
        driver.navigate().refresh();

        driver.findElement(By.xpath("//input[@placeholder='Search...']")).sendKeys(searchable, Keys.ENTER);
        WebElement listbox = driver.findElement(By.xpath("//div[@class='ui item dropdown']"));
        if (listbox.isDisplayed()) {
            System.out.println("File found");
        } else {
            System.out.println("File is not available");
        }
    }

    public static void download(WebDriver driver, String searchable) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        search(driver, searchable);
        WebElement listbox = driver.findElement(By.xpath("//div[@class='ui item dropdown']"));
        listbox.click();
        // download
        WebElement download = driver.findElement(By.xpath("(//div[@role='option'])[1]"));
        download.click();
        //Thread.sleep(15000);
        driver.navigate().back();
        driver.navigate().forward();
        System.out.println("downloaded");

    }
    public static void delete(WebDriver driver) throws ElementClickInterceptedException {
        // Delete
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebElement fileManagerBtn = driver.findElement(By.linkText("File Manager"));
        WebDriverWait exWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        exWait.until((ExpectedConditions.elementToBeClickable(fileManagerBtn)));
        fileManagerBtn.click();

        WebElement firstCheckbx = driver.findElement(By.xpath("(//div[@class='ui fitted checkbox file-manager-check-box'])[2]"));
        firstCheckbx.click();
        WebElement actionBtn = driver.findElement(By.xpath("//*[local-name()='svg'][@class='MuiSvgIcon-root']"));
        actionBtn.click();
        WebElement deleteBtn = driver.findElement(By.xpath("(//li[@class='MuiButtonBase-root MuiListItem-root MuiMenuItem-root MuiMenuItem-gutters MuiListItem-gutters MuiListItem-button'])[3]"));
        deleteBtn.click();
        driver.switchTo().alert().accept();

        driver.findElement(By.xpath("//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text']")).click();
        System.out.println("deleted");


    }


}
