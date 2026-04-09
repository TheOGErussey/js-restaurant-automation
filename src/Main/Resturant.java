package Main;

import javax.swing.*;
import java.awt.*;

public class Resturant {
    
    // Default width, height
    public static final int defW = 300, defH = 200;
    
    private static JFrame mainFrame;
    
    public static void main(String[] args) {
        
        /* Admin User, keep this here */
        Employee.User admin = new Employee.Manager("Admin", "Admin", "123");
        
        // Users for testing
        Employee.User testWaitStaff = new Employee.WaitStaff("Sara",         "Sara1314",     "1314"),
                testBusBoy          = new Employee.Busboy("Joe Schmo",    "BusBoyJoe",    "JoePassword"),
                testKitchenStaff    = new Employee.KitchenStaff("Bob",          "Chef Bob",     "password123");
        
        loginScreen();
    }
    
    private static void loginScreen() {
        // All is a Temporary GUI 
        
        mainFrame = new JFrame("Login Screen");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(defW, defH);

        JButton login = new JButton("Login");
                
        mainFrame.add(login);
        
        mainFrame.setVisible(true);
        
        // On press promt for user and password & attempt login
        login.addActionListener(e -> login() );
    }
    
    private static void login() {
        Employee.User user = Employee.User.login();
        if (user == null) return;
        mainFrame.dispose();
        System.out.println("Logged in");
        loggedOn(user);
    }
    
    private static void loggedOn(Employee.User user) {
        mainFrame = new JFrame("Logged in as: " + user.getName());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(defW * 2, defH * 2);
                
        if (user instanceof Employee.Manager manager) addAdminOptions(manager);
        else if (user instanceof Employee.WaitStaff waiter) addWaitStaffOptions(waiter);
        else if (user instanceof Employee.Busboy busBoy) addBusBoyOptions(busBoy);
        else if (user instanceof Employee.KitchenStaff kitchenStaff) addKitchenStaffOptions(kitchenStaff);
        else System.err.println("Unrecognized role ... ");
        
        // Universal Options
        JButton editAccount = new JButton("Edit Account");
        JButton logOff = new JButton("Log off");
        
        mainFrame.add(editAccount);
        mainFrame.add(logOff);
        
        logOff.addActionListener(e -> logOff());
        
        mainFrame.setVisible(true);
    }
   
    private static void logOff() {
        mainFrame.dispose();
        loginScreen();
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Admin / Manager Functions ">
    
    private static void addAdminOptions(Employee.Manager admin) {
        int rows = 3, cols = 3;

        mainFrame.setLayout(new GridLayout(rows, cols));
        
        JButton createEmployee = new JButton("Create Employee");
        createEmployee.addActionListener(e -> admin.createEmployee());
        JButton editEmployee = new JButton("Edit Employee");
        JButton deleteEmployee = new JButton("Delete Employee");
        
        JButton approveRefund = new JButton("Approve Refund");
        JButton generateReport = new JButton("Generate Report");
        
        JButton updateMenu = new JButton("Update Menu");
        JButton updateInventory = new JButton("Update Inventory");
        
        mainFrame.add(createEmployee); mainFrame.add(editEmployee); mainFrame.add(deleteEmployee);
        
        mainFrame.add(approveRefund); mainFrame.add(generateReport);
        
        mainFrame.add(updateMenu); mainFrame.add(updateInventory);
    }
    
    //</editor-fold>
   
    private static void addWaitStaffOptions(Employee.WaitStaff waiter) {
        int rows = 3, cols = 2;

        mainFrame.setLayout(new GridLayout(rows, cols));
        
        JButton createOrder = new JButton("Create order");
        JButton editOrder = new JButton("Update Order");
        
        JButton addItem = new JButton("Add Item");
        JButton requestRefund = new JButton("Request Refund");
        
        JButton viewTableStatus = new JButton("View Table Status");
        JButton updateTableStatus = new JButton("Update Table Status");
        
        mainFrame.add(createOrder); mainFrame.add(editOrder);
        
        mainFrame.add(addItem); mainFrame.add(requestRefund);
        
        mainFrame.add(viewTableStatus); mainFrame.add(updateTableStatus);
    }
    
    private static void addBusBoyOptions(Employee.Busboy busBoy) {
        int rows = 2, cols = 2;

        mainFrame.setLayout(new GridLayout(rows, cols));
        
        JButton viewTableStatus = new JButton("View Table Status");
        JButton updateTableStatus = new JButton("Update Table Status"); 
        
        mainFrame.add(viewTableStatus); mainFrame.add(updateTableStatus);
    }
    
    private static void addKitchenStaffOptions(Employee.KitchenStaff kitchenStaff) {
        
        int rows = 2, cols = 3;

        mainFrame.setLayout(new GridLayout(rows, cols));
        
        JButton viewOrderQ = new JButton("View Order Queue");
        JButton updateOrderStatus = new JButton("Update Order Status"); 
        JButton viewOrderDetails = new JButton("View Order Details");
        
        mainFrame.add(viewOrderQ); mainFrame.add(updateOrderStatus); mainFrame.add(viewOrderDetails);
        
    }

}