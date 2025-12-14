package bug.tracker.users;

public class User {
    private int id;
    private String fName;
    private String lName;
    private String phone;
    private String email;
    private String password;
    private Role role;

    public User(String fName, String lName, String phone, String email, String password, Role role) {
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }
    public void setLName(String lName) {
        this.lName = lName;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    // getters
    public int getId() {
        return id;
    }

    public String getFName() {
        return fName;
    }
    public String getLName() {
        return lName;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User ID:" + id + ", First Name:" + fName + ", Last Name:" + lName + "\nPhone:" + phone + ", Email:" + email + "\nRole:" + role;
    }
}
