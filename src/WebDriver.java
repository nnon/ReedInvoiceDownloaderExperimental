import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;


public class WebDriver {

    public static ChromeDriver driver;

    public static boolean logIn(){
        driver = new ChromeDriver();
        driver.get("http://candidate.reed.co.uk");
        return true;
    }
}
