package models;

public class TableInfo {

    private String tableName;
    private String status; // Open, Occupied, Dirty
    private String assignedWaiter;
    private int seats;

    // LINK TO ORDER
    private Order currentOrder;

    // ===== CONSTRUCTOR =====
    public TableInfo(String tableName) {
        this.tableName = tableName;
        this.status = "Open";
        this.assignedWaiter = "";
        this.currentOrder = null;
        this.seats = 4;
    }

    // ===== GETTERS =====
    public String getTableName() {
        return tableName;
    }

    public String getStatus() {
        return status;
    }

    public String getAssignedWaiter() {
        return assignedWaiter;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public int getSeats() {
        return seats;
    }

    // ===== SETTERS =====
    public void setStatus(String status) {
        this.status = status;
    }

    public void setAssignedWaiter(String assignedWaiter) {
        this.assignedWaiter = assignedWaiter;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    // ===== HELPER METHODS =====

    // Start a new order for this table
    public void startOrder() {
        this.currentOrder = new Order();
        this.status = "Occupied";
    }

    // Clear order when sent to kitchen
    public void clearOrder() {
        this.currentOrder = null;
        this.status = "Dirty";
    }

    public void addSeats(int amount) {
        seats += amount;
    }

    public void joinWith(TableInfo otherTable) {
        this.seats += otherTable.seats;
        this.tableName = this.tableName + "+" + otherTable.tableName;

        otherTable.seats = 0;
        otherTable.status = "Joined";
    }
}
