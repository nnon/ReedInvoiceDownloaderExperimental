import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.server.SystemClock;


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

    public static void closeDriver() {
        driver.close();
        driver.quit();
    }
}
