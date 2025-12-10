import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class UserSystem {

    private final String FILE_NAME = "users.csv";

    public UserSystem() {
        File file = new File(FILE_NAME);
        try {
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidEmail(String email) {
        if(email == null) return false;
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex, email);
    }

    public boolean isValidPassword(String password) {
        if(password == null || password.length() < 6 || password.contains(" ")) return false;
        String regex = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{6,}$";
        return Pattern.matches(regex, password);
    }

    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isTaken(String email) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[2].equals(email)) { // email index 2
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isRight(String email, String password) {
        String hashed = hashPassword(password);
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 &&
                        parts[2].equals(email) &&
                        parts[3].equals(hashed)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void saveUser(String firstName, String lastName, String phoneNumber , String email, String password, String role) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            String hashed = hashPassword(password);
            pw.println(firstName + "," + lastName + "," + ","  + phoneNumber + "," + email + "," + hashed + "," + role);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFirstName(String email) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[2].equals(email)) {
                    return parts[0];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
