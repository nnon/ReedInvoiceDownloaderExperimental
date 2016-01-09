import java.io.IOException;
import java.net.URISyntaxException;
import java.util.zip.ZipException;

public class ReedInvoiceDownloaderExperimental {

    public static void main(final String[] args)
            throws URISyntaxException,
            ZipException,
            IOException {
        if (!ExtractExe.extractChromeDriver()) {
            System.out.println("Ineligible system type: " + ExtractExe.os);
            System.exit(0);
        }
        System.out.println(ExtractExe.chromedriver.getRawPath());
        System.setProperty("webdriver.chrome.driver", "/home/nnon/IdeaProjects/ReedInvoiceDownloader/chromedriverLinux");//ExtractExe.chromedriver.getRawPath());
        UserInterface.getLogin();
        System.out.println("Payroll ID is " + User.payroll);
        System.out.println("Surname is " + User.surname);
        System.out.println("Password is " + User.getPassword());
        //Add webdriver
        boolean b = WebDriver.logIn();
    }
}
