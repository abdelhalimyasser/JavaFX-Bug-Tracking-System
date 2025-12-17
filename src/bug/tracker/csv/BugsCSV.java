package bug.tracker.csv;

import java.io.*;
import java.util.ArrayList;
import bug.tracker.bugs.Bug;
import bug.tracker.bugs.BugLevel;
import bug.tracker.bugs.BugPriority;
import bug.tracker.bugs.BugType;
import bug.tracker.users.User;

public class BugsCSV extends CSV {

    private final String FILE_NAME = "csvfiles" + File.separator + "bugs.csv";

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
                        if (currentId > maxId) {
                            maxId = currentId;
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxId + 1;
    }

    public void saveBug(String name, String project, BugType type, BugPriority priority, BugLevel level, String desc, String date, String photo, String tester, String developer) {
        ensureFileExists();
        int id = getNextId();
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            String line = id + "," + sanitize(name) + "," + sanitize(project) + "," + type + "," + priority + "," + level + "," + sanitize(desc) + "," + date + "," + sanitize(photo) + "," + sanitize(tester) + "," + sanitize(developer) + "," + "false";
            pw.println(line);
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
                if (parts.length >= 12) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        String proj = parts[2];
                        BugType type = BugType.valueOf(parts[3]);
                        BugPriority prio = BugPriority.valueOf(parts[4]);
                        BugLevel level = BugLevel.valueOf(parts[5]);
                        String desc = parts[6];
                        String date = parts[7];
                        String photo = parts[8];
                        String tester = parts[9];
                        String developer = parts[10];
                        boolean status = Boolean.parseBoolean(parts[11]);

                        Bug bug = new Bug(id, name, proj, type, prio, level, desc, date, photo, tester, developer);
                        bug.setBugStatus(status);
                        bugs.add(bug);
                    } catch (Exception e) {
                        System.err.println("Skipping invalid bug row: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bugs;
    }

    public void updateBugStatus(int bugId, boolean newStatus, String developerName) {
        ArrayList<Bug> bugs = loadBugs();
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Bug b : bugs) {
                if (b.getBugID() == bugId) {
                    b.setBugStatus(newStatus);
                    b.setDeveloper(developerName);
                }
                writeBugLine(pw, b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateFullBug(Bug updatedBug) {
        ArrayList<Bug> bugs = loadBugs();
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Bug b : bugs) {
                if (b.getBugID() == updatedBug.getBugID()) {
                    writeBugLine(pw, updatedBug);
                } else {
                    writeBugLine(pw, b);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeBugLine(PrintWriter pw, Bug b) {
        String line = b.getBugID() + "," + sanitize(b.getBugName()) + "," + sanitize(b.getProjectName()) + "," + b.getBugType() + "," + b.getBugPriority() + "," + b.getBugLevel() + "," + sanitize(b.getBugDescription()) + "," + b.getBugDate() + "," + sanitize(b.getBugPhoto()) + "," + sanitize(b.getTester()) + "," + sanitize(b.getDeveloper()) + "," + b.getBugStatus();
        pw.println(line);
    }

    @Override
    public void updateBug(int id, String t, String d, String p, String s, String ty, String r, String a) {
    }

    @Override
    public void deleteBug(int id) {
        ArrayList<Bug> bugs = loadBugs();
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Bug b : bugs) {
                if (b.getBugID() != id) {
                    writeBugLine(pw, b);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Users Functions
    @Override
    public void saveBug(String t, String d, String p, String s, String ty, String r, String a) {
    }

    @Override
    public void saveUser(String f, String l, String p, String e, String pass, String r) {
        throw new UnsupportedOperationException("BugsCSV does not support saveUser");
    }

    @Override
    public void updateUser(String e, String f, String l, String p, String pass, String r) {
        throw new UnsupportedOperationException("BugsCSV does not support updateUser");
    }

    @Override
    public void deleteUser(String e) {
        throw new UnsupportedOperationException("BugsCSV does not support deleteUser");
    }

    @Override
    public boolean isUser(String e) {
        throw new UnsupportedOperationException("BugsCSV does not support isUser");
    }

    @Override
    public boolean loginValidate(String e, String p) {
        throw new UnsupportedOperationException("BugsCSV does not support loginValidate");
    }

    @Override
    public boolean registerValidate(String f, String l, String p, String e, String pass, String r) {
        throw new UnsupportedOperationException("BugsCSV does not support registerValidate");
    }

    @Override
    public boolean passwordReset(String f, String l, String p, String e, String pass) {
        throw new UnsupportedOperationException("BugsCSV does not support passwordReset");
    }

    @Override
    public String getRole(String e) {
        throw new UnsupportedOperationException("BugsCSV does not support getRole");
    }

    @Override
    public String getRole(int id) {
        throw new UnsupportedOperationException("BugsCSV does not support getRole");
    }

    @Override
    public ArrayList<User> loadUsers() {
        throw new UnsupportedOperationException("BugsCSV does not support loadUsers");
    }
}
