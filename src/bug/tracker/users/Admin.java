package bug.tracker.users;

public class Admin extends User {
    public Admin(String fName, String lName, String phone, String email, String password) {
        super(fName, lName, phone, email, password, Role.ADMIN);
    }

    public Admin() {
        super("default", "default", "default", "default", "default", Role.ADMIN);
    }
}
