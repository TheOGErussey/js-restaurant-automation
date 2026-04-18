package models;

public class RefundRequest {

    private TableInfo table;
    private String reason;
    private String status;

    public RefundRequest(TableInfo table, String reason) {
        this.table = table;
        this.reason = reason;
        this.status = "Pending";
    }

    public TableInfo getTable() {
        return table;
    }

    public String getTableName() {
        return table.getTableName();
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public void approve() {
        status = "Approved";

        // AUTO CLEAR ORDER
        table.clearOrder();
    }

    public void deny() {
        status = "Denied";

        table.clearOrder();
    }
}
