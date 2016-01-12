import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.SystemClock;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class WebDriver {

    private static ChromeDriver driver;
    private static Select dropdown;
    private static String invHome;
    private static ArrayList<String> invDates;

    public static boolean logIn() {
        driver = new ChromeDriver();
        driver.get("http://candidate.reed.co.uk");
        driver.findElement(By.name("payroll")).sendKeys(User.payroll);
        driver.findElement(By.name("surname")).sendKeys(User.surname);
        driver.findElement(By.name("PIN")).sendKeys(User.getPassword());
        driver.findElement(By.name("submit")).click();
        if (driver.getCurrentUrl().equals("http://candidate.reed.co.uk/candlogin.asp")) {
            closeDriver();
            return false;
        } else {
            return true;
        }
    }

    public static void navigateToInvoices(){
        driver.findElement(By.linkText("invoices")).click();
    }

    private static void getDropdown(){
        dropdown = new Select(driver.findElement(By.name("submitted")));
    }

    private static void navigateToInvHome(){
        driver.navigate().to(invHome);
    }

    public static void getInvList(){
        invHome = driver.getCurrentUrl();
        getDropdown();
        List<WebElement> invoices = dropdown.getOptions();
        System.out.printf("%s invoices available", invoices.size());
        invDates = new ArrayList<>();
        for (WebElement we : invoices){
            if (we.getText() != null && !we.getText().equals("Select a timesheet")){
                if (invDates.size() < 3) {
                    invDates.add(we.getText());
                }
            }
        }
    }

    public static void getLocalInvList(){

    }
    
    public static void exportInv() throws IOException{
        for (String invDate : invDates){
            System.out.println(invDate);
            dropdown.selectByVisibleText(invDate);
            //Fix file output
            File scrFile = driver.getScreenshotAs(OutputType.FILE);
            //FileUtils.copyFile(scrFile, new File(ExtractExe.jarURI + "ReedInvoice" + invDate + ".png"));
            navigateToInvHome();
            getDropdown();
        }       
    }

    public static void closeDriver() {
        driver.close();
        driver.quit();
    }
}
