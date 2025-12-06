import java.util.Scanner;

public class CreateAccount {

    private UserSystem user;
    private Scanner cin = new Scanner(System.in);

    public SignUp(UserSystem user) {
        this.user = user;
    }

    public void registerUser() {
        while (true) {

            System.out.print("First Name: ");
            String firstName = cin.nextLine().trim();

            System.out.print("Last Name: ");
            String lastName = cin.nextLine().trim();

            System.out.print("Email: ");
            String email = cin.nextLine().trim();

            if (user.isTaken(email)) {
                System.out.println("Email already exists. Try another one.\n");
                continue;
            }

            if (!user.isValidEmail(email)) {
                System.out.println("Invalid email format.\n");
                continue;
            }

            System.out.print("Password: ");
            String password = cin.nextLine().trim();

            if (!user.isValidPassword(password)) {
                System.out.println("Password must be at least 6 characters and contain no spaces.\n");
                continue;
            }

            System.out.print("Role\n1.Tester\n2.Developer\n3.Project Manager\n4.Admin\nChoose the number of Role: ");
            int roleChoice = cin.nextInt();
            cin.nextLine();

            String role = switch (roleChoice){
                case 1 -> "Tester";
                case 2 -> "Developer";
                case 3 -> "Project Manager";
                case 4 -> "Admin";
                default -> null;
            };

            if(role == null){
                System.out.println("Choose the correct role.\n");
                continue;
            }

            user.saveUser(firstName, lastName, email, password, role);

            System.out.println("\nRegistration completed successfully!");
            System.out.println("----------------------\n");
            break;
        }
    }
}
