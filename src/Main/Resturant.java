package Main;

import javax.swing.*;
import java.awt.*;

public class Resturant {
    
    // Default width, height
    public static final int defW = 300, defH = 200;
    
    private static JFrame mainFrame;
    
    public static void main(String[] args) {
        
        /* Admin User, keep this here */
        Employee.User admin = new Employee.User("Admin", "Admin", "123", "Admin");
        
        // Users for testing
        Employee.User testWaitStaff = new Employee.User("Sara",         "Sara1314",     "1314",         "WaitStaff"),
                testBusBoy          = new Employee.User("Joe Schmo",    "BusBoyJoe",    "JoePassword",  "BusBoy"),
                testKitchenStaff    = new Employee.User("Bob",          "Chef Bob",     "password123",  "KitchenStaff");
        
        run();
    }
    
    private static void run() {
        // All is a Temporary GUI 
        
        mainFrame = new JFrame("Login Screen");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(defW, defH);

        JButton login = new JButton("Login");
                
        mainFrame.add(login);
        
        mainFrame.setVisible(true);
        
        // On press promt for user and password & attempt login
        login.addActionListener((java.awt.event.ActionEvent e) -> { logOn(); } );
    }
    
    private static void logOn() {
        Employee.User user = Employee.User.login();
        
        if (user == null) return;
         
        mainFrame.dispose();
        
        System.out.println("Logged in");
        
        mainFrame = new JFrame("Logged in as: " + user.getName());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(defW * 2, defH * 2);
        
        switch (user.getRole()) {
            case "Manager" :
            case "Admin" : addAdminOptions(mainFrame); 
                break;
            case "WaitStaff" : addWaitStaffOptions(mainFrame); break;
            case "BusBoy" : addBusBoyOptions(mainFrame); break;
            case "KitchenStaff" : addKitchenStaffOptions(mainFrame); break;
            default : System.err.println(user.getRole() + " does not exist!");
        }
        
        // Default Options
        JButton editAccount = new JButton("Edit Account");
        JButton logOff = new JButton("Log off");
        
        mainFrame.add(editAccount);
        mainFrame.add(logOff);
        
        logOff.addActionListener(e -> logOff());
        
        mainFrame.setVisible(true);
    }
   
    private static void logOff() {
        mainFrame.dispose();
        run();
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Add Options ">
    
    private static void addAdminOptions(JFrame f) {
        int rows = 3, cols = 3;

        mainFrame.setLayout(new GridLayout(rows, cols));
        
        JButton createEmployee = new JButton("Create Employee");
        JButton editEmployee = new JButton("Edit Employee");
        JButton deleteEmployee = new JButton("Delete Employee");
        
        JButton approveRefund = new JButton("Approve Refund");
        JButton generateReport = new JButton("Generate Report");
        
        JButton updateMenu = new JButton("Update Menu");
        JButton updateInventory = new JButton("Update Inventory");
        
        f.add(createEmployee); f.add(editEmployee); f.add(deleteEmployee);
        
        f.add(approveRefund); f.add(generateReport);
        
        f.add(updateMenu); f.add(updateInventory);
    }
   
    private static void addWaitStaffOptions(JFrame f) {
        int rows = 3, cols = 2;

        mainFrame.setLayout(new GridLayout(rows, cols));
        
        JButton createOrder = new JButton("Create order");
        JButton editOrder = new JButton("Update Order");
        
        JButton addItem = new JButton("Add Item");
        JButton requestRefund = new JButton("Request Refund");
        
        JButton viewTableStatus = new JButton("View Table Status");
        JButton updateTableStatus = new JButton("Update Table Status");
        
        f.add(createOrder); f.add(editOrder);
        
        f.add(addItem); f.add(requestRefund);
        
        f.add(viewTableStatus); f.add(updateTableStatus);
    }
    
    private static void addBusBoyOptions(JFrame f) {
        int rows = 2, cols = 2;

        mainFrame.setLayout(new GridLayout(rows, cols));
        
        JButton viewTableStatus = new JButton("View Table Status");
        JButton updateTableStatus = new JButton("Update Table Status"); 
        
        f.add(viewTableStatus); f.add(updateTableStatus);
    }
    
    private static void addKitchenStaffOptions(JFrame f) {
        
        int rows = 2, cols = 3;

        mainFrame.setLayout(new GridLayout(rows, cols));
        
        JButton viewOrderQ = new JButton("View Order Queue");
        JButton updateOrderStatus = new JButton("Update Order Status"); 
        JButton viewOrderDetails = new JButton("View Order Details");
        
        f.add(viewOrderQ); f.add(updateOrderStatus); f.add(viewOrderDetails);
        
    }
    
    //</editor-fold>
}