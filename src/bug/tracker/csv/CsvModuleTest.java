package bug.tracker.csv;

import java.io.File;
import java.util.ArrayList;
import bug.tracker.users.User;
import bug.tracker.bugs.Bug;

public class CsvModuleTest {
    // Match the file paths used in the classes
    private static final String USER_FILE = "csvfiles" + File.separator + "users.csv";
    private static final String BUG_FILE = "csvfiles" + File.separator + "bugs.csv";

    public static void main(String[] args) {
        System.out.println("Starting CSV Module Test...");
        
        // Ensure directory exists for test execution
        new File("csvfiles").mkdirs();

        testUsersCSV();
        testBugsCSV();

        System.out.println("\nAll tests finished.");
    }

    private static void testUsersCSV() {
        System.out.println("\n--- Testing UsersCSV ---");
        UsersCSV usersCsv = new UsersCSV();
        
        // Clean up before test
        new File(USER_FILE).delete();

        // Test Save User
        System.out.println("Testing saveUser...");
        usersCsv.saveUser("John", "Doe", "1234567890", "john@example.com", "password123", "ADMIN");
        boolean exists = usersCsv.isUser("john@example.com");
        if (exists) System.out.println("PASS: User saved and found.");
        else System.out.println("FAIL: User not found after save.");

        // Test Load Users
        System.out.println("Testing loadUsers...");
        try {
            ArrayList<User> users = usersCsv.loadUsers();
            // Check list size
            if (!users.isEmpty() && users.get(0).getEmail().equals("john@example.com")) {
                System.out.println("PASS: Users loaded correctly.");
            } else {
                System.out.println("FAIL: User load mismatch. Count: " + users.size());
            }
        } catch (Exception e) {
            System.out.println("WARN: loadUsers failed. " + e.getMessage());
            e.printStackTrace();
        }

        // Test Validation
        if (usersCsv.loginValidate("john@example.com", "password123")) {
            System.out.println("PASS: Login validation working.");
        } else System.out.println("FAIL: Login validation.");

        // Test Delete
        System.out.println("Testing deleteUser...");
        usersCsv.deleteUser("john@example.com");
        if (!usersCsv.isUser("john@example.com")) {
            System.out.println("PASS: User deleted.");
        } else System.out.println("FAIL: User still exists.");
    }

    private static void testBugsCSV() {
        System.out.println("\n--- Testing BugsCSV ---");
        BugsCSV bugsCsv = new BugsCSV();

        // Clean up before test
        new File(BUG_FILE).delete();

        // Test Save Bug (Note: Comma in description to test sanitizer)
        System.out.println("Testing saveBug...");
        bugsCsv.saveBug("Login Crash", "Crash on login, prevents access", "High", "Open", "Bug", "john@example.com", "dev1");
        
        // Test Load Bugs
        System.out.println("Testing loadBugs...");
        ArrayList<Bug> bugs = bugsCsv.loadBugs();
        
        if (!bugs.isEmpty() && bugs.get(0).getTitle().equals("Login Crash")) {
            System.out.println("PASS: Bug saved and loaded. ID: " + bugs.get(0).getId());
            // Verify sanitizer worked (comma replaced by semicolon)
            if (bugs.get(0).getDescription().contains(";")) {
                System.out.println("PASS: Comma sanitization worked.");
            }
        } else {
            System.out.println("FAIL: Bug load mismatch or empty.");
        }

        // Test Update Bug
        if (!bugs.isEmpty()) {
            System.out.println("Testing updateBug...");
            int id = bugs.get(0).getId();
            bugsCsv.updateBug(id, "Login Crash Updated", "Crash fixed", "Medium", "In Progress", "Bug", "john@example.com", "dev1");
            bugs = bugsCsv.loadBugs();
            if (bugs.get(0).getTitle().equals("Login Crash Updated") && bugs.get(0).getStatus().equals("In Progress")) {
                System.out.println("PASS: Bug updated.");
            } else {
                System.out.println("FAIL: Bug update failed. Title: " + bugs.get(0).getTitle());
            }

            // Test Delete Bug
            System.out.println("Testing deleteBug...");
            bugsCsv.deleteBug(id);
            bugs = bugsCsv.loadBugs();
            if (bugs.isEmpty()) {
                System.out.println("PASS: Bug deleted.");
            } else {
                System.out.println("FAIL: Bug not deleted.");
            }
        }
    }
}