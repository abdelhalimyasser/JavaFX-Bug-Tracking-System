public class SignUp {

    private UserSystem user;

    public SignUp(UserSystem user) {
        this.user = user;
    }

    public boolean registerUser(String firstName , String lastName , String phoneNumber , String email , String role , String password) {
        if (user.isTaken(email)) {
            System.out.println("Email already exists. Try another one.\n");
            return false;
        }

        if (!user.isValidEmail(email)) {
            System.out.println("Invalid email format.\n");
            return false;
        }

        if (!user.isValidPassword(password)) {
            System.out.println("Password must be at least 6 characters and contain no spaces.\n");
            return false;
        }

//        System.out.print("Role\n1.Tester\n2.Developer\n3.Project Manager\n4.Admin\nChoose the number of Role: ");
//        int roleChoice = cin.nextInt();
//        cin.nextLine();

//        String role = switch (roleChoice){
//            case 1 -> "Tester";
//            case 2 -> "Developer";
//            case 3 -> "Project Manager";
//            case 4 -> "Admin";
//            default -> null;
//        };

        user.saveUser(firstName, lastName, phoneNumber , email, password, role);

        System.out.println("\nRegistration completed successfully!");
        System.out.println("----------------------\n");
        return true;
    }
}
