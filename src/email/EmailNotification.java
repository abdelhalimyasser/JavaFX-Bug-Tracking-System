/**
 * Concrete EmailNotification demonstrating Polymorphism: different notification types could implement same interface.
 * This implementation simulates sending by printing formatted templates to the console.
 */
public class EmailNotification extends NotificationBase {

    public EmailNotification(String systemName) {
        super(systemName);
    }

    @Override
    public void sendAssignmentNotification(Bug bug, String assignedBy) {
        String template = String.join("\n",
                "To: %s",
                "Subject: [%s] Bug #%d Assigned - %s",
                "Time: %s",
                "",
                "Hello %s,",
                "",
                "Bug #%d - \"%s\" has been assigned to you by %s.",
                "Project: %s",
                "Priority: %s",
                "Due: %s",
                "",
                "Regards,",
                "%s"
        );
        String to = bug.getAssignedTo() == null || bug.getAssignedTo().isEmpty() ? "unassigned@local" : bug.getAssignedTo() + "@example.com";
        String out = String.format(template,
                to,
                systemName,
                bug.getId(),
                bug.getTitle(),
                DateTimeUtil.format(bug.getCreatedAt()),
                bug.getAssignedTo() == null || bug.getAssignedTo().isEmpty() ? "Developer" : bug.getAssignedTo(),
                bug.getId(),
                bug.getTitle(),
                assignedBy,
                bug.getProjectName(),
                bug.getPriority(),
                DateTimeUtil.formatOpt(bug.getDueDate()),
                systemName
        );
        System.out.println("=== SIMULATED EMAIL (Assignment) ===");
        System.out.println(out);
    }

    @Override
    public void sendCompletionNotification(Bug bug, String completedBy) {
        String template = String.join("\n",
                "To: %s",
                "Subject: [%s] Bug #%d Completed - %s",
                "Time: %s",
                "",
                "Hello Tester,",
                "",
                "Bug #%d - \"%s\" has been marked as COMPLETED by %s.",
                "Status: %s",
                "Project: %s",
                "",
                "Please verify and close the bug if resolved.",
                "",
                "Regards,",
                "%s"
        );
        String to = "tester@example.com";
        String out = String.format(template,
                to,
                systemName,
                bug.getId(),
                bug.getTitle(),
                DateTimeUtil.format(bug.getCreatedAt()),
                bug.getId(),
                bug.getTitle(),
                completedBy,
                bug.getStatus(),
                bug.getProjectName(),
                systemName
        );
        System.out.println("=== SIMULATED EMAIL (Completion) ===");
        System.out.println(out);
    }
}
