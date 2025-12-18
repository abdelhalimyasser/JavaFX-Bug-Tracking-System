package bug.tracker.users;

import bug.tracker.bugs.BugLevel;
import bug.tracker.bugs.BugPriority;
import bug.tracker.bugs.BugType;
import bug.tracker.csv.BugsCSV;

public class Tester extends User {

    private BugsCSV bugsData = new BugsCSV();

    public Tester(String fName, String lName, String phone, String email, String password) {
        super(fName, lName, phone, email, password, Role.TESTER);
    }

    public Tester() {
        super("default", "default", "default", "default", "default", Role.TESTER);
    }

    public void addBug(String name, String project, BugType type, BugPriority priority, BugLevel level, String desc, String date, String photo) {
        String testerName = UserSession.getCurrentUser();
        String developerName = "Unassigned";

        if (testerName == null) {
            testerName = "Unknown Tester";
        }

        bugsData.saveBug(name, project, type, priority, level, desc, date, photo, testerName, developerName);
    }
}
