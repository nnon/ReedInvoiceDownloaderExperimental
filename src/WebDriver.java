import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.SystemClock;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public static int getInvList(){
        invHome = driver.getCurrentUrl();
        getDropdown();
        List<WebElement> invoices = dropdown.getOptions();
        invDates = new ArrayList<>();
        for (WebElement we : invoices){
            if (we.getText() != null && !we.getText().equals("Select a timesheet")){
                if (invDates.size() < 2) {
                    invDates.add(we.getText());
                }
            }
        }
        return invDates.size();
    }

    public static void getLocalInvList(){

    }
    
    public static void exportInv() throws IOException{
        SimpleDateFormat invDateFormat = new SimpleDateFormat("dd MMMM yyyyy");
        SimpleDateFormat outDateFormat = new SimpleDateFormat("yyyyMMdd");
        String outputDateStr;
        for (String invDate : invDates){
            dropdown.selectByVisibleText(invDate);
            try {
                outputDateStr = outDateFormat.format(invDateFormat.parse(invDate));
                File scrFile = driver.getScreenshotAs(OutputType.FILE);
                System.out.println(new File(ExtractExe.jarURI).getParent() + File.separator + "ReedInvoice" + outputDateStr + ".png");
                FileUtils.copyFile(scrFile, new File(new File(ExtractExe.jarURI).getParent() + File.separator + "ReedInvoice" + outputDateStr + ".png"));
                navigateToInvHome();
                getDropdown();
            } catch (ParseException e){
                e.printStackTrace();
            }

        }       
    }

    public static void closeDriver() {
        driver.close();
        driver.quit();
    }
}
