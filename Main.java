import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ensureScreenshotsFolder();

        String csvPath = "data/bugs.csv";
        BugCSVController csv = new BugCSVController(csvPath);
        EmailNotification notifier = new EmailNotification("BugTracker-PL");

        Bug newBug = new Bug(0,
                "Login page crash when password contains comma",
                "Functional",
                "High",
                "ModuleA",
                "WebsiteProject",
                LocalDateTime.now().plusDays(7),
                "Open",
                "",
                "screenshots/login_error.png"
        );

        Bug saved = csv.addBug(newBug);
        System.out.println("Added bug: " + saved);

        saved.setAssignedTo("AbderahmanAlaa");
        saved.setStatus("Assigned");
        boolean updated = csv.updateBug(saved);
        if (updated) {
            System.out.println("Assigned bug and updated CSV.");
            notifier.sendAssignmentNotification(saved, "Nada Moustafa");
        } else {
            System.err.println("Failed to update bug assignment.");
        }

        saved.setStatus("Completed");
        boolean updated2 = csv.updateBug(saved);
        if (updated2) {
            System.out.println("Updated bug as Completed.");
            notifier.sendCompletionNotification(saved, saved.getAssignedTo());
        }

        List<Bug> all = csv.getAllBugs();
        System.out.println("All bugs in CSV:");
        for (Bug b : all) {
            System.out.println(b);
        }

        boolean deleted = csv.deleteById(saved.getId());
        System.out.println("Deleted? " + deleted);

        System.out.println("Done demo.");
    }

    public static void ensureScreenshotsFolder() {
        java.io.File f = new java.io.File("screenshots");
        if (!f.exists()) {
            boolean ok = f.mkdirs();
            if (ok) {
                System.out.println("Created folder: screenshots/");
            } else {
                System.err.println("Failed to create screenshots/ (check permissions)");
            }
        } else {
            System.out.println("screenshots/ already exists");
        }
    }
}
