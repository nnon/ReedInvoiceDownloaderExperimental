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
        System.setProperty("webdriver.chrome.driver", ExtractExe.chromedriver.getRawPath());
        UserInterface.getLogin();
        if (!WebDriver.logIn()){
            System.out.println("Invalid login");
            System.exit(0);
        }
        WebDriver.navigateToInvoices();



        //WebDriver.closeDriver();
    }
}
