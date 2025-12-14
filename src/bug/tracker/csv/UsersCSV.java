package bug.tracker.csv;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import bug.tracker.users.User;

public class UsersCSV extends CSV {
    // Fixed path usage to be consistent
    private final String FILE_NAME = "csvfiles" + File.separator + "users.csv";
    private ArrayList<User> users = new ArrayList<>();

    // Helper to ensure file and folder exist
    private void ensureFileExists() {
        File file = new File(FILE_NAME);
        try {
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs(); // Create "csvfiles" directory if missing
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String firstName, String lastName, String phoneNumber, String email, String password, String role) {
        ensureFileExists();
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            // Sanitize inputs to prevent CSV breakage
            String line = String.join(",", 
                sanitize(firstName), 
                sanitize(lastName), 
                sanitize(phoneNumber), 
                sanitize(email), 
                password, // Password/Hash usually doesn't need sanitizing, but be careful with commas
                sanitize(role)
            );
            pw.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(String email, String firstName, String lastName, String phoneNumber, String password, String role) {
        ensureFileExists();
        ArrayList<String> lines = new ArrayList<>();
        boolean found = false;
        
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[3].equalsIgnoreCase(email)) {
                    // Update the line
                    String updatedLine = String.join(",", 
                        sanitize(firstName), 
                        sanitize(lastName), 
                        sanitize(phoneNumber), 
                        sanitize(email), 
                        password, 
                        sanitize(role)
                    );
                    lines.add(updatedLine);
                    found = true;
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (found) {
            rewriteFile(lines);
        }
    }

    @Override
    public void deleteUser(String email) {
        ensureFileExists();
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                // Use equalsIgnoreCase for emails
                if (parts.length >= 4 && !parts[3].equalsIgnoreCase(email)) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        rewriteFile(lines);
    }

    private void rewriteFile(ArrayList<String> lines) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (String line : lines) {
                pw.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isUser(String email) {
        ensureFileExists();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[3].equalsIgnoreCase(email.trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean loginValidate(String email, String password) {
        ensureFileExists();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    // Check email (case insensitive) and password (case sensitive)
                    if (parts[3].equalsIgnoreCase(email) && parts[4].equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<User> loadUsers() {
        ensureFileExists();
        users.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    bug.tracker.users.Role roleEnum = null;
                    try {
                        roleEnum = bug.tracker.users.Role.valueOf(parts[5].toUpperCase()); 
                    } catch (Exception e) {
                        // Fallback or log if role is invalid
                        System.err.println("Invalid role found: " + parts[5]);
                    }
                    // Assuming User constructor matches
                    User user = new User(parts[0], parts[1], parts[2], parts[3], parts[4], roleEnum);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    // --- Unimplemented Methods (Interface Segregation issues) ---
    @Override 
    public void saveBug(String t, String d, String p, String s, String ty, String r, String a) { throw new UnsupportedOperationException("UsersCSV cannot save bugs"); }
    @Override 
    public void updateBug(int id, String t, String d, String p, String s, String ty, String r, String a) { throw new UnsupportedOperationException("UsersCSV cannot update bugs"); }
    @Override 
    public void deleteBug(int id) { throw new UnsupportedOperationException("UsersCSV cannot delete bugs"); }
    @Override 
    public ArrayList<bug.tracker.bugs.Bug> loadBugs() { return new ArrayList<>(); }
    
    // Kept these stubbed as returning false/null is safer than crashing for getters
    @Override 
    public boolean registerValidate(String fn, String ln, String pn, String em, String pw, String ro) { 
        return !isUser(em); 
    }
    
    @Override 
    public boolean passwordReset(String firstName, String lastName, String phoneNumber, String email, String newPassword) { 
        ensureFileExists();
        ArrayList<String> lines = new ArrayList<>();
        boolean foundAndVerified = false;

        try (BufferedReader br = new BufferedReader(new FileReader("csvfiles" + File.separator + "users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                
                // Check if this is the target email (Case Insensitive)
                if (parts.length >= 6 && parts[3].equalsIgnoreCase(email)) {
                    
                    // SECURITY CHECK: Verify Name and Phone match before resetting
                    if (parts[0].equalsIgnoreCase(firstName) &&
                        parts[1].equalsIgnoreCase(lastName) &&
                        parts[2].equals(phoneNumber)) {
                        
                        // Create updated line with NEW PASSWORD (index 4)
                        // Structure: First, Last, Phone, Email, Pass, Role
                        String updatedLine = String.join(",", 
                            parts[0], 
                            parts[1], 
                            parts[2], 
                            parts[3], 
                            newPassword, // Insert new hashed password here
                            parts[5]
                        );
                        lines.add(updatedLine);
                        foundAndVerified = true;
                    } else {
                        // Email matched, but security questions failed. Do not update.
                        lines.add(line);
                        System.out.println("DEBUG: Security verification failed for " + email);
                    }
                } else {
                    // Not the target user, keep line as is
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (foundAndVerified) {
            rewriteFile(lines); // Helper method you already have in UsersCSV
            return true;
        }
        
        return false; 
    }

    @Override 
    public String getRole(String email) {
        ensureFileExists();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6 && parts[3].equalsIgnoreCase(email)) return parts[5];
            }
        } catch (IOException e) { e.printStackTrace(); }
        return null;
    }
    @Override 
    public String getRole(int id) { return null; }
}