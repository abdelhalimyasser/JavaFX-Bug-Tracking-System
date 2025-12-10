public class SignIn {

    private UserSystem system;

    public SignIn(UserSystem system) {
        this.system = system;
    }

    public boolean loginUser(String email , String password) {
        if (system.isRight(email, password)) {
            System.out.println("Welcome " + system.getFirstName(email) + "!");
            System.out.println("----------------------\n");
            return true;
        } else {
            System.out.println("Invalid username or password.\n");
            return false;
        }
    }
}
