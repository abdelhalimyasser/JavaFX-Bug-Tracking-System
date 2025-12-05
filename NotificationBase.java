/**
 * Abstract base notification class. Implements the interface to provide shared state.
 */
public abstract class NotificationBase implements NotificationSender {
    protected String systemName;

    public NotificationBase(String systemName) {
        this.systemName = systemName;
    }

    public abstract void sendAssignmentNotification(Bug bug, String assignedBy);
    public abstract void sendCompletionNotification(Bug bug, String completedBy);
}
