import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.SystemClock;

import java.util.ArrayList;
import java.util.List;


public class WebDriver {

    public static ChromeDriver driver;

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

    public static Select getDropdown(){
        return new Select(driver.findElement(By.name("submitted")));
    }

    public static void getInvoices(){
        String invHome = driver.getCurrentUrl();
        Select dropdown = getDropdown();
        List<WebElement> invoices = dropdown.getOptions();
        System.out.printf("%s invoices available", invoices.size());
        ArrayList<String> invoiceDates = new ArrayList<>();
        for (WebElement we : invoices){
            if (we.getText() != null && !we.getText().equals("Select a timesheet")){
                invoiceDates.add(we.getText());
            }
        }
        for (String invDate : invoiceDates){
            System.out.println(invDate);
            dropdown.selectByVisibleText(invDate);
            driver.navigate().to(invHome);
            dropdown = getDropdown();
        }
    }

    public static void closeDriver() {
        driver.close();
        driver.quit();
    }
}
