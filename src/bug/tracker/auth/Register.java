package bug.tracker.auth;

import bug.tracker.csv.UsersCSV;

public class Register {
    public boolean registerUser(String firstName, String lastName, String phoneNumber, String email, String role, String password) {
        if (!Validator.isValidName(firstName) || !Validator.isValidName(lastName))
            return false;
        if (!Validator.isValidPhone(phoneNumber))
            return false;
        if (!Validator.isValidEmail(email))
            return false;
        if (!Validator.isValidPassword(password))
            return false;

        UsersCSV usersCsv = new UsersCSV();

        if (usersCsv.isUser(email))
            return false;

        String hashedPassword = Validator.hashPassword(password);
        if (hashedPassword == null) 
            return false;

        usersCsv.saveUser(firstName, lastName, phoneNumber, email, hashedPassword, role);
        return true;
    }
}