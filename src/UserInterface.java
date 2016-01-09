import java.io.Console;
import java.util.Scanner;
//import java.util.Scanner;

public class UserInterface {

    public static void getLogin(){
        //String login;
        Console console = System.console();
        if (console == null) {
            getLoginScanner();
        } else {
            getLoginConsole(console);
        }
        //return login;
    }

    private static void getLoginConsole(Console console){
        System.out.println("Reed Invoice Downloader");
        System.out.println("=======================");
        System.out.print("Payroll number: ");
        String payroll = console.readLine();
        System.out.print("Surname: ");
        String surname = console.readLine();
        System.out.print("Password: ");
        char[] password = console.readPassword();
        User.createUser(payroll, surname, password);
    }

    private static void getLoginScanner(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Reed Invoice Downloader");
        System.out.println("=======================");
        System.out.print("Payroll number: ");
        String payroll = scanner.nextLine();
        System.out.print("Surname: ");
        String surname = scanner.nextLine();
        System.out.print("Password: ");
        char[] password = scanner.nextLine().toCharArray();
        User.createUser(payroll, surname, password);
    }
}
