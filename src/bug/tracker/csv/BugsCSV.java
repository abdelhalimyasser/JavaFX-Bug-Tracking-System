package bug.tracker.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import bug.tracker.bugs.Bug;
import bug.tracker.users.User;

public class BugsCSV extends CSV {
    private final String FILE_NAME = "csvfiles" + File.separator + "bugs.csv";

    private void ensureFileExists() {
        File file = new File(FILE_NAME);
        try {
            if (file.getParentFile() != null) file.getParentFile().mkdirs();
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getNextId() {
        int maxId = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    try {
                        int currentId = Integer.parseInt(parts[0]);
                        if (currentId > maxId) maxId = currentId;
                    } catch (NumberFormatException e) { /* ignore header or bad data */ }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxId + 1;
    }

    @Override
    public void saveBug(String title, String description, String priority, String status, String type, String reporter, String assignee) {
        ensureFileExists();
        int id = getNextId();

        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            // Sanitize all text fields to remove commas
            String line = id + "," + 
                          sanitize(title) + "," + 
                          sanitize(description) + "," + 
                          sanitize(priority) + "," + 
                          sanitize(status) + "," + 
                          sanitize(type) + "," + 
                          sanitize(reporter) + "," + 
                          sanitize(assignee);
            pw.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBug(int id, String title, String description, String priority, String status, String type, String reporter, String assignee) {
        ensureFileExists();
        ArrayList<String> lines = new ArrayList<>();
        boolean found = false;
        
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    try {
                        int currentId = Integer.parseInt(parts[0]);
                        if (currentId == id) {
                            String updated = id + "," + 
                                           sanitize(title) + "," + 
                                           sanitize(description) + "," + 
                                           sanitize(priority) + "," + 
                                           sanitize(status) + "," + 
                                           sanitize(type) + "," + 
                                           sanitize(reporter) + "," + 
                                           sanitize(assignee);
                            lines.add(updated);
                            found = true;
                        } else {
                            lines.add(line);
                        }
                    } catch (NumberFormatException e) {
                        lines.add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (found) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
                for (String line : lines) pw.println(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteBug(int id) {
        ensureFileExists();
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    try {
                        int currentId = Integer.parseInt(parts[0]);
                        if (currentId != id) lines.add(line);
                    } catch (NumberFormatException e) {
                        lines.add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (String line : lines) pw.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Bug> loadBugs() {
        ensureFileExists();
        ArrayList<Bug> bugs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        bugs.add(new Bug(id, parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7]));
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid bug row: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bugs;
    }

    @Override 
    public void saveUser(String f, String l, String p, String e, String pass, String r) { throw new UnsupportedOperationException(); }
    @Override 
    public void updateUser(String e, String f, String l, String p, String pass, String r) { throw new UnsupportedOperationException(); }
    @Override 
    public void deleteUser(String email) { throw new UnsupportedOperationException(); }
    @Override 
    public boolean isUser(String email) { return false; }
    @Override 
    public boolean loginValidate(String email, String password) { return false; }
    @Override 
    public boolean registerValidate(String f, String l, String p, String e, String pass, String r) { return false; }
    @Override 
    public boolean passwordReset(String f, String l, String p, String e, String pass) { return false; }
    @Override 
    public String getRole(String email) { return null; }
    @Override 
    public String getRole(int id) { return null; }
    @Override 
    public ArrayList<User> loadUsers() { return new ArrayList<>(); }
}