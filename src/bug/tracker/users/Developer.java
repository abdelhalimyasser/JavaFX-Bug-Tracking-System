package bug.tracker.users;

import bug.tracker.csv.BugsCSV;
import bug.tracker.users.UserSession;

public class Developer extends User {
    
    private BugsCSV bugsData = new BugsCSV();

    public Developer(String fName, String lName, String phone, String email, String password) {
        super(fName, lName, phone, email, password, Role.DEVELOPER);
    }

    public Developer() {
        super("default", "default", "default", "default", "default", Role.DEVELOPER);
    }

    public void updateBugStatus(int bugId, boolean newStatus) {
        String developerName = UserSession.getCurrentUser();
        
        if (developerName == null)
            developerName = "Unknown Developer";

        bugsData.updateBugStatus(bugId, newStatus, developerName);
    }
}