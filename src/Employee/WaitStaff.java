package Employee;

public class WaitStaff extends User {

    private java.util.List<Misc.Table> assignedTables = new java.util.ArrayList<>();
    
    public WaitStaff(String name, String username, String password) {
        super(name, username, password);
    }
    
    public void createOrder() {
        
    }
    
    public void updateOrder() {
        
    }
    
    public void addItem() {
        
    }
    
    public void requestRefund() {
        
    }
    
    public void viewTableStatus() {
        
    }
    
    // UML has markTableClean() but updateTableStatus() can ( should ) do that.
    
}
