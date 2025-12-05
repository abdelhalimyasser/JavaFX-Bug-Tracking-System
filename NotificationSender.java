/**
 * Interface demonstrating Interfaces & Polymorphism.
 */
public interface NotificationSender {
    void sendAssignmentNotification(Bug bug, String assignedBy);
    void sendCompletionNotification(Bug bug, String completedBy);
}
