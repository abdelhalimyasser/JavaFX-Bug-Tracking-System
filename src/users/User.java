package users;

public class User {
    // firstName,lastName,password,email,role
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String role;

    public User(String firstName, String lastName, String password, String email, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String toCsvFormat() {
        return firstName + "," + lastName + "," + password + "," + email + "," + role;
    }

    public static User fromCsvFormat(String csvLine) {
        String[] fields = csvLine.split(",");
        String firstName = fields[0];
        String lastName = fields[1];
        String password = fields[2];
        String email = fields[3];
        String role = fields[4];
//        User user = new User(firstName, lastName, password, email, role);
//        return user;
        return new User(firstName, lastName, password, email, role);
    }

    @Override
    public boolean equals(Object obj) {
        return this.email.equals(((User) obj).email);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                "}\n";
    }
}
