package models;

public class Session {
    private static Employee currentUser;

    public static void setUser(Employee user) {
        currentUser = user;
    }

    public static Employee getUser() {
        return currentUser;
    }

    public static void clear() {
        currentUser = null;
    }
}
