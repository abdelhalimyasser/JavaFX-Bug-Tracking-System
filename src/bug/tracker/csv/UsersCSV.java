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

    private final String FILE_NAME = "csvfiles" + File.separator + "users.csv";
    private ArrayList<User> users = new ArrayList<>();

    // Helper function to ensure file and folder exist
    private void ensureFileExists() {
        File file = new File(FILE_NAME);
        try {
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
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
            String line = String.join(",", sanitize(firstName), sanitize(lastName), sanitize(phoneNumber), sanitize(email), password, sanitize(role));
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
                    String updatedLine = String.join(",", sanitize(firstName), sanitize(lastName), sanitize(phoneNumber), sanitize(email), password, sanitize(role));
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
                        System.err.println("Invalid role found: " + parts[5]);
                    }

                    User user = new User(parts[0], parts[1], parts[2], parts[3], parts[4], roleEnum);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

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

                if (parts.length >= 6 && parts[3].equalsIgnoreCase(email)) {
                    if (parts[0].equalsIgnoreCase(firstName)
                            && parts[1].equalsIgnoreCase(lastName)
                            && parts[2].equals(phoneNumber)) {

                        String updatedLine = String.join(",", parts[0], parts[1], parts[2], parts[3], newPassword, parts[5]);
                        lines.add(updatedLine);
                        foundAndVerified = true;
                    } else {
                        lines.add(line);
                        System.out.println("DEBUG: Security verification failed for " + email);
                    }
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (foundAndVerified) {
            rewriteFile(lines);
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
                if (parts.length >= 6 && parts[3].equalsIgnoreCase(email)) {
                    return parts[5];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getName(String email) {
        ensureFileExists();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6 && parts[3].equalsIgnoreCase(email)) {
                    return parts[0] + " " + parts[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getRole(int id) {
        return null;
    }

    // Bug methods
    @Override
    public void saveBug(String t, String d, String p, String s, String ty, String r, String a) {
        throw new UnsupportedOperationException("UsersCSV cannot save bugs");
    }

    @Override
    public void updateBug(int id, String t, String d, String p, String s, String ty, String r, String a) {
        throw new UnsupportedOperationException("UsersCSV cannot update bugs");
    }

    @Override
    public void deleteBug(int id) {
        throw new UnsupportedOperationException("UsersCSV cannot delete bugs");
    }

    @Override
    public ArrayList<bug.tracker.bugs.Bug> loadBugs() {
        return new ArrayList<>();
    }
}
