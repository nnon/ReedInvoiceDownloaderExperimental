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

    public static void main(final String[] args)
            throws URISyntaxException,
            ZipException,
            IOException {

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

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ReedInvoiceDownloaderExperimental.mf = new MainFrame();
                mf.setVisible(true);
            }
        });

        if (!extractChromeDriver()) {
            mf.updateStatus("Ineligible system type: " + os);

        }
        System.setProperty("webdriver.chrome.driver", chromedriver.getRawPath());
//        if (args.length == 3){
//
//        } else {
//            UserInterface.getLogin();
//        }

//        exportInv();
    }

    public static void login(String payroll, String surname, char[] password){
        UserInterface.getLogin(payroll, surname, password);
        mf.updateStatus("Attempting to log in to Reed ...");
        if (!logIn()){
            mf.updateStatus("Invalid login");
            return;
        } else {
            navigateToInvoices();
            mf.updateStatus(getInvList() + " invoices available for download");
            mf.enableDownload();
        }
    }

    public static void download(){

    }

    public static void close(){
        closeDriver();
        System.exit(0);
    }

//    webdriver

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
                System.out.println(new File(jarURI).getParent() + File.separator + "ReedInvoice" + outputDateStr + ".png");
                FileUtils.copyFile(scrFile, new File(new File(jarURI).getParent() + File.separator + "ReedInvoice" + outputDateStr + ".png"));
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

//    extract exe

    public static String os;
    public static URI chromedriver;
    public static URI jarURI;

    public static boolean extractChromeDriver()
            throws URISyntaxException,
            ZipException,
            IOException {
        os = System.getProperty("os.name");
        jarURI = getJarURI();
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
