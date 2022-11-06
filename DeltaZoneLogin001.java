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


public class DeltaZoneLogin001 {
    public static String btnPrefix="//li[@role='menuitem' and text()='";
    public static void signUp(WebDriver driver, String email, String password) {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.findElement(By.partialLinkText("Sign")).click();

        WebElement emailSignUp = driver.findElement(By.id("signup-email"));
        emailSignUp.clear();
        emailSignUp.sendKeys(email);
        WebElement passwordSignUp = driver.findElement(By.id("signup-password"));
        passwordSignUp.clear();
        passwordSignUp.sendKeys(password);
        WebElement signUp = driver.findElement(By.id("submit-signup"));
        signUp.click();
/*
        driver.findElement(By.xpath("//button[@class='ui button bread-crumbs-button1']"));

        driver.findElement(By.xpath("(//button[@class='ui medium button bread-crumbs-button'])[1]")).click();
        Alert alrt = driver.switchTo().alert();
        System.out.println(alrt.getText());
        alrt.accept();

 */

        // brklinks(driver);
    }


    public static void login(WebDriver driver, String email, String password) {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.navigate().to("https://my.deltazone.dev/");
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

    public static void clickFileManager(WebDriver driver){

        driver.findElement(By.linkText("File Manager")).click();
    }

    public static void clickUpload(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebElement uploadInnerBtnEnabled = driver.findElement(By.xpath("//span[@class='Actions']/button[@class='upload-button']"));

        WebDriverWait exWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        exWait.until((ExpectedConditions.elementToBeClickable(uploadInnerBtnEnabled)));
        uploadInnerBtnEnabled.click();

        exWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='dialog']//span[text()='ok']")));
        driver.findElement(By.xpath("//div[@role='dialog']//span[text()='ok']")).click();
        brklinks(driver);

    }

    public static void uploadFile(WebDriver driver, String path) throws AWTException, NoSuchElementException, ElementClickInterceptedException {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        clickFileManager(driver);
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
        clickFileManager(driver);

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

        driver.navigate().back();
        driver.navigate().forward();

    }

    public static void dropdown(WebDriver driver, String actionToPerform) throws InterruptedException {

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        WebElement actionBtn = driver.findElement(By.xpath("//*[local-name()='svg'][@class='MuiSvgIcon-root']"));
        actionBtn.click();
        WebElement actToPerfomBtn = driver.findElement(By.xpath(btnPrefix+actionToPerform+"']"));
        WebDriverWait exWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        exWait.until(ExpectedConditions.elementToBeClickable(actToPerfomBtn));
        actToPerfomBtn.click();

       /* List<WebElement> dd = driver.findElements(By.xpath("//ul[@id='split-button-menu']/li"));
        for (WebElement d : dd) {
            //System.out.println(d);
            String action = d.getText();
            System.out.println(action);

            if (action.contains or .equals(actionToPerform)) {
                d.click();
                break;
            }

        }

        */

    }

    public static void delete(WebDriver driver) throws ElementClickInterceptedException, InterruptedException {
        // Delete
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        clickFileManager(driver);

        WebElement firstCheckbx = driver.findElement(By.xpath("(//div[@class='ui fitted checkbox file-manager-check-box'])[2]"));
        firstCheckbx.click();
        Thread.sleep(15000);

        dropdown(driver,"Delete");
        driver.switchTo().alert().accept();

        driver.findElement(By.xpath("//button[contains(@class,'MuiButtonBase')]")).click();
        System.out.println("deleted");


    }

    public static void revert(WebDriver driver)throws ElementClickInterceptedException, InterruptedException{
        // Deleting All files to revert back
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        clickFileManager(driver);

        WebElement selectAllCheckbx = driver.findElement(By.xpath("(//div[@class='ui fitted checkbox file-manager-check-box'])[1]"));
        selectAllCheckbx.click();

        Thread.sleep(5000);

        dropdown(driver, "Delete");
        driver.switchTo().alert().accept();

        driver.findElement(By.xpath("//button[contains(@class,'MuiButtonBase')]")).click();
            System.out.println("All deleted");
       }
    public static void rename(WebDriver driver, String name) throws InterruptedException {

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        clickFileManager(driver);

        WebElement firstCheckbx = driver.findElement(By.xpath("(//div[@class='ui fitted checkbox file-manager-check-box'])[2]"));
        firstCheckbx.click();
        Thread.sleep(15000);

        dropdown(driver,"Rename");

        WebElement nameTxtBx = driver.findElement(By.id("rename-textbox"));
        String oldFileName=nameTxtBx.getAttribute("value");
        System.out.println("File or Directory Name  - "+oldFileName);
        String extentions;
        if(oldFileName.contains(".")){
            extentions= oldFileName.split("\\.")[1];
            System.out.println(extentions);
            nameTxtBx.clear();
            nameTxtBx.sendKeys(name+"."+extentions);
            }
        else {
            nameTxtBx.clear();
            nameTxtBx.sendKeys(name);
        }

        WebElement submitBtn = driver.findElement(By.xpath("//div[@class='MuiDialogActions-root MuiDialogActions-spacing']/button[@class='ui button btn-color']"));
        submitBtn.click();
        driver.switchTo().alert().accept();

        System.out.println("renamed");
        driver.navigate().refresh();
        Thread.sleep(10000);

    }

    public static void addDirectory(WebDriver driver, String directoryName) throws ElementClickInterceptedException, InterruptedException {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        clickFileManager(driver);
        dropdown(driver,"Add Directory");

        WebElement addDirectoryNameTxtbx = driver.findElement(By.xpath("//input[@placeholder='Directory Name']"));
        addDirectoryNameTxtbx.sendKeys(directoryName);

        WebElement submitBtn = driver.findElement(By.xpath("//div[@class='MuiDialogActions-root MuiDialogActions-spacing']/button[@class='ui button btn-color']"));
        submitBtn.click();

        driver.findElement(By.xpath("//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text']")).click();
        System.out.println("directory added");
    }

    public static void moveTo(WebDriver driver, String directoryName) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        clickFileManager(driver);

        WebElement secondCheckbx = driver.findElement(By.xpath("(//div[@class='ui fitted checkbox file-manager-check-box'])[3]"));
        secondCheckbx.click();
        Thread.sleep(15000);

        dropdown(driver,"Move");
        WebElement select = driver.findElement(By.xpath("//div[contains(@class,'Dropdown') and text()='Select...']"));
        select.click();
        String sufix= directoryName+"/']";
        WebElement dName=driver.findElement(By.xpath("//div[contains(@class,'Dropdown') and text()='"+sufix));
        dName.click();
        WebElement submitBtn = driver.findElement(By.xpath("//button[contains(@class,'ui button') and text()='Submit']"));
        submitBtn.click();

        Alert alrt=driver.switchTo().alert();
        System.out.println(alrt.getText());
        alrt.accept();

        }

   public static void logOut(WebDriver driver){
        driver.findElement(By.xpath("//img[contains(@class,'ui avatar image')]")).click();
        driver.findElement(By.xpath("//div[text()='Logout']")).click();
        driver.quit();
       System.out.println("Log out");

   }


// Need to check why null can't be passed then how to test blank value
//        login(driver,null,null);
//        WebElement requiredErrorMsg=driver.findElement(By.xpath("//span[@id='error' and @style='color: red;']"));
//        String reqMsg=requiredErrorMsg.getText();
//        if(reqMsg.equals("Email and Password Required")) {
//            System.out.println(" error shown : "+reqMsg);
//        }



}
