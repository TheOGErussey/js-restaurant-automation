package models;

public class RefundRequest {

    private String tableName;
    private String reason;
    private String status; // Pending, Approved, Denied

    public RefundRequest(String tableName, String reason) {
        this.tableName = tableName;
        this.reason = reason;
        this.status = "Pending";
    }

    public String getTableName() {
        return tableName;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public void approve() {
        status = "Approved";
    }

    public void deny() {
        status = "Denied";
    }
}
