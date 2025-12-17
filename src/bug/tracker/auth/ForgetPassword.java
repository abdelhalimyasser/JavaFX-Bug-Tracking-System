package bug.tracker.auth;

import bug.tracker.csv.UsersCSV;

public class ForgetPassword {

    public boolean resetPassword(String firstName, String lastName, String phoneNumber, String email, String newPassword) {
        if (!Validator.isValidPassword(newPassword)) {
            return false;
        }

        UsersCSV usersCsv = new UsersCSV();

        if (!usersCsv.isUser(email)) {
            return false;
        }

        String hashedNewPassword = Validator.hashPassword(newPassword);
        boolean success = usersCsv.passwordReset(firstName, lastName, phoneNumber, email, hashedNewPassword);

        return success;
    }
}
