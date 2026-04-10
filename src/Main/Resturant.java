package Main;

import javax.swing.*;
import java.awt.*;

public class Resturant {
    
    // Default width, height
    public static final int defW = 300, defH = 200;
        
    public static void main(String[] args) {
        
       JFrame mainFrame = new JFrame("Main Frame");
        
        /* Admin User, keep this here */
        Employee.User admin = new Employee.Manager("Admin", "Admin", "123");
        
        // Users for testing
        Employee.User testWaitStaff = new Employee.WaitStaff("Sara",      "Sara1314",     "1314"),
                testBusBoy          = new Employee.Busboy("Joe Schmo",    "BusBoyJoe",    "JoePassword"),
                testKitchenStaff    = new Employee.KitchenStaff("Bob",    "Chef Bob",     "password123");
        
        loginScreen(mainFrame);
    }
    
    private static void loginScreen(JFrame frame) {
        
        JPanel loginPanel = new JPanel();

        frame.setTitle("Welcome");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(defW, defH);
        frame.getContentPane().setBackground(new Color(245, 230, 211));

        JButton login = new JButton("Login");
                
        loginPanel.add(login);
        
        frame.add(loginPanel);
        frame.setVisible(true);
        
        // On press promt for user and password & attempt login
        login.addActionListener( e -> login(frame) );
    }
    
    private static void login(JFrame frame) {
        Employee.User user = Employee.User.login();
        if (user == null) return;
        frame.dispose();
        System.out.println("Logged in"); 
        loggedOn(frame, user);
    }
    
    private static void loggedOn(JFrame frame, Employee.User user) {
        frame.getContentPane().removeAll();
        frame.setSize(defW*2,defH*2);
        frame.setTitle("Logged in as: " + user.getName());
        
        JPanel buttonPanel = new JPanel();
        
             if (user instanceof Employee.Manager manager) buttonPanel = getAdminOptions(manager);
        else if (user instanceof Employee.WaitStaff waiter) getWaitStaffOptions(waiter);
        else if (user instanceof Employee.Busboy busBoy) getBusBoyOptions(busBoy);
        else if (user instanceof Employee.KitchenStaff kitchenStaff) getKitchenStaffOptions(kitchenStaff);
        else System.err.println("Unrecognized role ... ");
        
        // Universal Options
        JButton editAccount = new JButton("Edit Account");
        JButton logOff = new JButton("Log off");
        
        buttonPanel.add(editAccount);
        buttonPanel.add(logOff);

        logOff.addActionListener( e -> logOff(frame) );
        
        frame.add(buttonPanel);
        
        frame.setVisible(true);
    }
   
    private static void logOff(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.dispose();
        loginScreen(frame);
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Admin / Manager Functions ">
    
    private static JPanel getAdminOptions(Employee.Manager admin) {
        int rows = 3, cols = 3;
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(rows, cols));
        
        JButton createEmployee = new JButton("Create Employee");
        createEmployee.addActionListener(e -> admin.createEmployee());
        JButton editEmployee = new JButton("Edit Employee");
        JButton deleteEmployee = new JButton("Delete Employee");
        
        JButton approveRefund = new JButton("Approve Refund");
        JButton generateReport = new JButton("Generate Report");
        
        JButton updateMenu = new JButton("Update Menu");
        JButton updateInventory = new JButton("Update Inventory");
        
        buttonPanel.add(createEmployee); buttonPanel.add(editEmployee); buttonPanel.add(deleteEmployee);
        
        buttonPanel.add(approveRefund); buttonPanel.add(generateReport);
        
        buttonPanel.add(updateMenu); buttonPanel.add(updateInventory);
        
        return buttonPanel;
    }
    
    //</editor-fold>
   
    private static JPanel getWaitStaffOptions(Employee.WaitStaff waiter) {
        int rows = 3, cols = 2;

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(rows, cols));
        
        JButton createOrder = new JButton("Create order");
        JButton editOrder = new JButton("Update Order");
        
        JButton addItem = new JButton("Add Item");
        JButton requestRefund = new JButton("Request Refund");
        
        JButton viewTableStatus = new JButton("View Table Status");
        JButton updateTableStatus = new JButton("Update Table Status");
        
        buttonPanel.add(createOrder); buttonPanel.add(editOrder);
        
        buttonPanel.add(addItem); buttonPanel.add(requestRefund);
        
        buttonPanel.add(viewTableStatus); buttonPanel.add(updateTableStatus);
        
        return buttonPanel;
    }
    
    private static JPanel getBusBoyOptions(Employee.Busboy busBoy) {
        int rows = 2, cols = 2;

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(rows, cols));
        
        JButton viewTableStatus = new JButton("View Table Status");
        JButton updateTableStatus = new JButton("Update Table Status"); 
        
        buttonPanel.add(viewTableStatus); buttonPanel.add(updateTableStatus);
        
        return buttonPanel;
    }
    
    private static JPanel getKitchenStaffOptions(Employee.KitchenStaff kitchenStaff) {
        
        int rows = 2, cols = 3;

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(rows, cols));
        
        JButton viewOrderQ = new JButton("View Order Queue");
        JButton updateOrderStatus = new JButton("Update Order Status"); 
        JButton viewOrderDetails = new JButton("View Order Details");
        
        buttonPanel.add(viewOrderQ); buttonPanel.add(updateOrderStatus); buttonPanel.add(viewOrderDetails);
        
        return buttonPanel;
        
    }

}