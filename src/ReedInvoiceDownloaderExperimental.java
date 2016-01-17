import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import javax.swing.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ReedInvoiceDownloaderExperimental {

    public static MainFrame mf;

    //main method
    public static void main(final String[] args)
            throws URISyntaxException,
            ZipException,
            IOException {
        //try to use default system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //get system type - used to determine which chromedriver file to be extracted
        os = System.getProperty("os.name");
        //get location of jar file - used to populate invoice location dialog
        jarURI = getJarURI();
        //extract chromedriver - returns false if correct chromedriver executable for system type is available
        boolean systemEligible = extractChromeDriver();
        //set chromedriver location
        System.setProperty("webdriver.chrome.driver", chromedriver.getRawPath());
        driver = new ChromeDriver();
        //commence UI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ReedInvoiceDownloaderExperimental.mf = new MainFrame();
                mf.setVisible(true);
            }
        });
        if (!systemEligible) {mf.updateStatus("Ineligible system type: " + os);};
    }

    //called by login button using UI parameters
    public static void login(String payroll, String surname, char[] password){
        //User.createUser(payroll, surname, password);
        mf.updateStatus("Attempting to log in to Reed ...");
        //navigate to Reed
        driver.get("http://candidate.reed.co.uk");
        //enter user details
        driver.findElement(By.name("payroll")).sendKeys(payroll);
        driver.findElement(By.name("surname")).sendKeys(surname);
        driver.findElement(By.name("PIN")).sendKeys(new String(password));
        driver.findElement(By.name("submit")).click();
        //if URL does't change, login has been unsuccessful
        if (driver.getCurrentUrl().equals("http://candidate.reed.co.uk/candlogin.asp")) {
            mf.updateStatus("Invalid login");
        } else {
            mf.updateStatus("Login successful");
            //locate invoice page
            navigateToInvoices();
            //populate the list of invoice dates available
            mf.updateStatus(getInvList() + " invoices available for download");
            //enable the download button
            mf.enableDownload();
        }
    }

    //download all relevant invoices
    public static void download(){

    }

    //safely shut down
    public static void close(){
        closeDriver();
        System.exit(0);
    }

//    webdriver

    private static ChromeDriver driver;
    private static Select dropdown;
    private static String invHome;
    private static ArrayList<String> invDates;

    //navigate to invoice page - used first time only
    public static void navigateToInvoices(){
        driver.findElement(By.linkText("invoices")).click();
    }

    //initialise the contents of the dropdown
    private static void getDropdown(){
        dropdown = new Select(driver.findElement(By.name("submitted")));
    }

    //navigate to invoice page - used after first time
    private static void navigateToInvHome(){
        driver.navigate().to(invHome);
    }

    //populate list of invoice dates, return the size of the list
    public static int getInvList(){
        invHome = driver.getCurrentUrl();
        //select the dropdown
        getDropdown();
        //get dropdown contents
        List<WebElement> invoices = dropdown.getOptions();
        invDates = new ArrayList<>();
        for (WebElement we : invoices){
            //add date to list where not null and not a descriptor
            if (we.getText() != null && !we.getText().equals("Select a timesheet")){
                //temporary code for testing purposes
                if (invDates.size() < 2) {
                    invDates.add(we.getText());
                }
            }
        }
        return invDates.size();
    }

    //get list of invoices already downloaded
    public static void getLocalInvList(){

    }

    //export invoices
    public static void exportInv() throws IOException{
        //website date format
        SimpleDateFormat invDateFormat = new SimpleDateFormat("dd MMMM yyyyy");
        //output date format
        SimpleDateFormat outDateFormat = new SimpleDateFormat("yyyyMMdd");
        String outputDateStr;
        for (String invDate : invDates){
            dropdown.selectByVisibleText(invDate);
            try {
                outputDateStr = outDateFormat.format(invDateFormat.parse(invDate));
                File scrFile = driver.getScreenshotAs(OutputType.FILE);
                System.out.println(new File(jarURI).getParent() + File.separator + "ReedInvoice" + outputDateStr + ".png");
                FileUtils.copyFile(scrFile, new File(new File(jarURI).getParent() + File.separator + "ReedInvoice" + outputDateStr + ".png"));
                navigateToInvHome();
                getDropdown();
            } catch (ParseException e){
                e.printStackTrace();
            }

        }
    }

    //close webdriver
    public static void closeDriver() {
        driver.close();
        driver.quit();
    }

//    extract exe

    public static String os;
    public static URI chromedriver;
    public static URI jarURI;

    //extract relevant chromedriver executable as a temporary file
    public static boolean extractChromeDriver()
            throws URISyntaxException,
            ZipException,
            IOException {
        if (os.equals("Linux")) {
            chromedriver = getFile(jarURI, "chromedriverLinux"); //linux
            return true;
        } else if (os.equals("Mac OS X")) {
            chromedriver = getFile(jarURI, "chromedriverMac"); //mac
            return true;
        } else {
            String osName[] = os.split(" ");
            if (osName[0].equals("Windows")) {
                chromedriver = getFile(jarURI, "chromedriver.exe"); //windows
                return true;
            } else {
                return false;
            }
        }
    }

    //return the URI of the jar file
    private static URI getJarURI()
            throws URISyntaxException {
        final ProtectionDomain domain;
        final CodeSource source;
        final URL url;
        final URI uri;

        domain = ReedInvoiceDownloaderExperimental.class.getProtectionDomain();
        source = domain.getCodeSource();
        url = source.getLocation();
        uri = url.toURI();

        return (uri);
    }

    //return the URI of the extracted chromedriver executable
    private static URI getFile(final URI where,
                               final String fileName)
            throws ZipException,
            IOException {
        final File location;
        final URI fileURI;

        location = new File(where);

        // not in a JAR, just return the path on disk
        if (location.isDirectory()) {
            fileURI = URI.create(where.toString() + fileName);
        } else {
            final ZipFile zipFile;

            zipFile = new ZipFile(location);

            try {
                fileURI = extract(zipFile, fileName);
            } finally {
                zipFile.close();
            }
        }

        return (fileURI);
    }

    //extract chromedriver
    private static URI extract(final ZipFile zipFile,
                               final String fileName)
            throws IOException {
        final File tempFile;
        final ZipEntry entry;
        final InputStream zipStream;
        OutputStream fileStream;

        tempFile = File.createTempFile(fileName, Long.toString(System.currentTimeMillis()));
        tempFile.deleteOnExit();
        tempFile.setExecutable(true);
        entry = zipFile.getEntry(fileName);

        if (entry == null) {
            throw new FileNotFoundException("cannot find file: " + fileName + " in archive: " + zipFile.getName());
        }

        zipStream = zipFile.getInputStream(entry);
        fileStream = null;

        try {
            final byte[] buf;
            int i;

            fileStream = new FileOutputStream(tempFile);
            buf = new byte[1024];

            while ((i = zipStream.read(buf)) != -1) {
                fileStream.write(buf, 0, i);
            }
        } finally {
            close(zipStream);
            close(fileStream);
        }
        return (tempFile.toURI());
    }

    //close file/zip stream
    private static void close(final Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
