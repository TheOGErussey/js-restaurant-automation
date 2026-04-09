package Employee;

public class KitchenStaff extends User {
    
    public KitchenStaff(String name, String username, String password) {
        super(name, username, password);
    }

    public void viewOrderQueue() {
        
    }
    
    public void updateOrderStatus() {
        
    }
    
    // UML has markOrderReady() but updateOrderStatus() can ( should ) do that.
    
    public void viewOrderDetails() {
        
    }
    
    // UML has removeCompletedOrder() but we can make it so it just does that itself.
        
}
