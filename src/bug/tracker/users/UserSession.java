package bug.tracker.users;

public class UserSession {

    private static String currentUserEmail;
    private static String currentUserName;

    public static void setSession(String email, String name) {
        currentUserEmail = email;
        currentUserName = name;
    }

    public static String getEmail() {
        return currentUserEmail;
    }

    public static String getCurrentUser() {
        return currentUserName;
    }

    public static void cleanSession() {
        currentUserEmail = null;
        currentUserName = null;
    }
}
