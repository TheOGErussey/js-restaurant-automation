package models;

public class Employee {

    private String name;
    private String id;
    private String role; // Waiter or Cook
    private String assignedTables;
    private boolean authorized;
    private String password;

    public Employee(String name, String id, String role, String assignedTables, boolean authorized, String password) {
        this.name = name;
        this.id = id;
        this.role = role;
        this.assignedTables = assignedTables;
        this.authorized = authorized;
        this.password = password;
    }

    public String getName() { return name; }
    public String getId() { return id; }
    public String getRole() { return role; }
    public String getAssignedTables() { return assignedTables; }
    public boolean isAuthorized() { return authorized; }
    public String getPassword() {return password; }

}
