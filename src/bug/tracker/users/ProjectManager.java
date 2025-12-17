package bug.tracker.users;

import bug.tracker.csv.BugsCSV;
import bug.tracker.bugs.Bug;
import bug.tracker.users.Role;
import java.util.ArrayList;

public class ProjectManager extends User {

    private BugsCSV bugsData = new BugsCSV();

    public ProjectManager(String fName, String lName, String phone, String email, String password) {
        super(fName, lName, phone, email, password, Role.PROJECT_MANAGER);
    }

    public ProjectManager() {
        super("default", "default", "default", "default", "default", Role.PROJECT_MANAGER);
    }

    public ArrayList<Bug> getAllBugs() {
        return bugsData.loadBugs();
    }
}
