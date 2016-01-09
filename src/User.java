/**
 * Created by nnon on 07/01/16.
 */
public class User {

    public static String payroll;
    public static String surname;
    private static char[] password;

    public static void createUser(String pay,
                String sur,
                char[] pass){
        payroll = pay;
        surname = sur;
        password = pass;
    }

    public static String getPassword(){
        return new String(password);
    }
}
