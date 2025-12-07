package users;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    private static final String CSV_FILE_PATH = "./user.csv";
    private Map<String, User> userMap;

    public UserService() {
        userMap = new HashMap<>();
        try {
            loadAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean createUser(User user) {
        if (userMap.get(user.getEmail()) != null) {
            return false;
        }

        userMap.put(user.getEmail(), user);
        try {
            updateCsvFile();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createUser(String firstName, String lastName, String email, String password, String role) {
        User user = new User(firstName, lastName, password, email, role);
        return createUser(user);
    }

    public boolean updateFirstName(String firstName, String email) {
        User user = userMap.get(email);
        if (user == null) {
            return false;
        }

        user.setFirstName(firstName);
        userMap.put(email, user);
        try {
            updateCsvFile();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateLastName(String lastName, String email) {
        User user = userMap.get(email);
        if (user == null) {
            return false;
        }

        user.setLastName(lastName);
        userMap.put(email, user);
        try {
            updateCsvFile();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRole(String role, String email) {
        User user = userMap.get(email);
        if (user == null) {
            return false;
        }

        user.setRole(role);
        userMap.put(email, user);
        try {
            updateCsvFile();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePassword(String email, String oldPassword, String newPassword) {
        User user = userMap.get(email);
        if (user == null) {
            return false;
        }

        if (!oldPassword.equals(user.getPassword())) {
            return false;
        }
        user.setPassword(newPassword);
        userMap.put(email, user);
        try {
            updateCsvFile();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserByEmail(String email) {
        return userMap.get(email);
    }

    public List<User> getAllUsers() {
        return userMap.values().stream().toList();
    }

    public boolean deleteUser(String email) {
        User user = userMap.remove(email);
        if (user == null) {
            return false;
        }
        return true;
    }

    private void loadAllUsers() throws IOException, URISyntaxException {
        File file = new File(CSV_FILE_PATH);
        if (!file.exists()) {
            boolean isCreated = file.createNewFile();
            System.out.println("User file created: " + isCreated);
            return;
        }
        List<String> lines = Files.readAllLines(Path.of(CSV_FILE_PATH));
        for(String line : lines) {
            User user = User.fromCsvFormat(line);
            userMap.put(user.getEmail(), user);
        }
    }

    private void updateCsvFile() throws FileNotFoundException {
        String lines = "";
        List<User> userList = userMap.values().stream().toList();
        for (User user : userList) {
            String line = user.toCsvFormat();
            lines += line;
            lines += "\n";
        }
        File file = new File(CSV_FILE_PATH);
        FileOutputStream fos = new FileOutputStream(file, false);
        PrintWriter printWriter = new PrintWriter(fos);
        printWriter.print(lines);
        printWriter.flush();
        printWriter.close();
    }
}
