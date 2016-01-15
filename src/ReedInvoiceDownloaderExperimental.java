import build.tools.javazic.Main;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.zip.ZipException;

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
        }catch (InstantiationException ex) {
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

        if (!ExtractExe.extractChromeDriver()) {
            mf.updateStatus("Ineligible system type: " + ExtractExe.os);

        }
        System.setProperty("webdriver.chrome.driver", ExtractExe.chromedriver.getRawPath());
//        if (args.length == 3){
//
//        } else {
//            UserInterface.getLogin();
//        }
//        if (!WebDriver.logIn()){
//            System.out.println("Invalid login");
//            System.exit(0);
//        }
//        WebDriver.navigateToInvoices();
//        WebDriver.getInvList();
//        WebDriver.exportInv();
    }

    public static void login(String payroll, String surname, char[] password){
        UserInterface.getLogin(payroll, surname, password);
        mf.updateStatus("Attempting to log in to Reed ...");
        if (!WebDriver.logIn()){
            mf.updateStatus("Invalid login");
            return;
        } else {

        }


    }

    public static void download(){

    }

    public static void close(){
        WebDriver.closeDriver();
        System.exit(0);
    }
}
