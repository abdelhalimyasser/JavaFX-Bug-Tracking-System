package bug.tracker.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import bug.tracker.users.User;
import bug.tracker.bugs.Bug;

public abstract class CSV {
    // Abstract methods to be implemented by children
    public abstract void saveUser(String firstName, String lastName, String phoneNumber, String email, String password, String role);
    public abstract void saveBug(String title, String description, String priority, String status, String type, String reporter, String assignee);

    public abstract void updateUser(String email, String firstName, String lastName, String phoneNumber, String password, String role);
    public abstract void updateBug(int id, String title, String description, String priority, String status, String type, String reporter, String assignee);

    public abstract void deleteUser(String email);
    public abstract void deleteBug(int id);

    public abstract boolean isUser(String email);
    public abstract boolean loginValidate(String email, String password);
    public abstract boolean registerValidate(String firstName, String lastName, String phoneNumber, String email, String password, String role);
    public abstract boolean passwordReset(String firstName, String lastName, String phoneNumber, String email, String password);
    
    public abstract String getRole(String email);
    
    // Changed to Integer to allow returning null if not found
    public abstract String getRole(int id);

    public abstract ArrayList<User> loadUsers();
    public abstract ArrayList<Bug> loadBugs();

    // Helper to prevent CSV corruption
    protected String sanitize(String input) {
        if (input == null) return "";
        // Replace commas with semicolons to prevent split(",") errors
        return input.replace(",", ";").trim(); 
    }

    public boolean clear(String fileName){
        try (FileWriter fw = new FileWriter(fileName, false)) {
            fw.write("");
            fw.flush();
            return true;
        } catch (IOException e) {
            System.err.println("Error clearing file: " + e.getMessage());
            return false;
        }
    }
}