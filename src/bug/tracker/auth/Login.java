package bug.tracker.auth;

import bug.tracker.csv.UsersCSV;

public class Login {

    public String loginUser(String email, String password) {
        if (email == null || password == null) {
            return null;
        }

        UsersCSV usersCsv = new UsersCSV();

        String hashedPassword = Validator.hashPassword(password);
        boolean isValid = usersCsv.loginValidate(email, hashedPassword);

        if (isValid) {
            return usersCsv.getRole(email);
        }

        return null;
    }
}
