import java.util.Scanner;

public class SignIn {

    private UserSystem system;
    private Scanner cin = new Scanner(System.in);

    public SignIn(UserSystem system) {
        this.system = system;
    }

    public void loginUser() {
        while (true) {
            System.out.print("Email: ");
            String email = cin.nextLine();

            System.out.print("Password: ");
            String password = cin.nextLine();

            if (system.isRight(email, password)) {
                System.out.println("Welcome " + system.getFirstName(email) + "!");
                System.out.println("----------------------\n");
                break;
            } else {
                System.out.println("Invalid username or password.\n");
            }
        }
    }
}
