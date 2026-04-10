package Main;

import javax.swing.*;
import java.awt.*;

public class Resturant {
    
    // Default width, height
    public static final int DEF_W = 350, DEF_H = 300;
    
    // Colors from our canva
    public static final Color PEACH = new Color(245, 230, 211), 
                              MAROON   = new Color(122,  30,  30);
        
    public static void main(String[] args) {
        
       JFrame mainFrame = new JFrame("Main Frame");
        
        /* Admin User, keep this here */
        Employee.User admin = new Employee.Manager("Admin", "Admin", "123");
        
        // Users for testing
        Employee.User testWaitStaff     = new Employee.WaitStaff("Sara",      "Sara1314",     "1314"),
                      testBusBoy        = new Employee.Busboy("Joe Schmo",    "BusBoyJoe",    "JoePassword"),
                      testKitchenStaff  = new Employee.KitchenStaff("Bob",    "Chef Bob",     "password123");
        
        welcomeScreen(mainFrame);
    }
    
    private static void welcomeScreen(JFrame frame) {
        final int X_PAD = 40, Y_PAD = 40;
        
        frame.setTitle("Welcome");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(DEF_W, DEF_H);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(MAROON);
        JLabel title = new JLabel("J's Corner Resturant");
        title.setBackground(MAROON);
        title.setForeground(PEACH);
        title.setFont(new Font("SansSerif", Font.BOLD, 20)); // Bigger font
        titlePanel.add(title);
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, frame.getHeight() / 4)); // Makes the section smaller
        
        JPanel labelPanel = makeGridPanel(3, 1); // 3 Labels
        labelPanel.add(makeCenteredLabel("📌 680 Arntson Dr, Marietta, GA"));
        labelPanel.add(makeCenteredLabel("⏲ Monday - Saturday: 11AM-9:30PM"));
        labelPanel.add(makeCenteredLabel("☎ (470) 555-1212"));
        
        JPanel optionsPanel = makeGridPanel(2, 1); // 2 Buttons
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(Y_PAD/2, Y_PAD/2, X_PAD/2, X_PAD/2)); 

        JButton login = makeButton("Login"), exit = makeButton("Exit");
        optionsPanel.add(login); optionsPanel.add(exit);
        
        frame.add(titlePanel);
        frame.add(labelPanel);
        frame.add(optionsPanel);
        
        frame.setVisible(true);
        
        // On press promt for user and password & attempt login
        login.addActionListener( e -> login(frame) );
        // On press exit
        exit.addActionListener( e -> frame.dispose() );
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
        frame.setSize(DEF_W*2,DEF_H*2);
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
        welcomeScreen(frame);
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Admin / Manager Functions ">
    
    private static JPanel getAdminOptions(Employee.Manager admin) {
        final int ROWS = 3, COLS = 3;
        JPanel buttonPanel = makeGridPanel(ROWS, COLS);
        
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
        final int ROWS = 3, COLS = 2;
        JPanel buttonPanel = makeGridPanel(ROWS, COLS);
        
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
        final int ROWS = 2, COLS = 2;
        JPanel buttonPanel = makeGridPanel(ROWS, COLS);
        
        JButton viewTableStatus = new JButton("View Table Status");
        JButton updateTableStatus = new JButton("Update Table Status"); 
        
        buttonPanel.add(viewTableStatus); buttonPanel.add(updateTableStatus);
        
        return buttonPanel;
    }
    
    private static JPanel getKitchenStaffOptions(Employee.KitchenStaff kitchenStaff) {
        final int ROWS = 2, COLS = 3;
        JPanel buttonPanel = makeGridPanel(ROWS, COLS);
        
        JButton viewOrderQ = new JButton("View Order Queue");
        JButton updateOrderStatus = new JButton("Update Order Status"); 
        JButton viewOrderDetails = new JButton("View Order Details");
        
        buttonPanel.add(viewOrderQ); buttonPanel.add(updateOrderStatus); buttonPanel.add(viewOrderDetails);
        
        return buttonPanel;
        
    }
    
    /**
     * Makes a stylized JButton in line with our stylization.
     * 
     * @param name Text that will be on the button.
     * @return The stylized JButton.
     */
    public static JButton makeButton(String name) {
        JButton butt = new JButton(name);
        butt.setBackground(MAROON); // Button color
        butt.setForeground(PEACH);  // Text color
        butt.setFocusPainted(false);
        
        return butt;
    }
    /**
     * Makes a stylized JLabel in line with our stylization.
     * 
     * @param text Text that the label with display.
     * @return The stylized JLabel;
     */
    public static JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Main.Resturant.MAROON);
        
        return label;
    }
    
    public static JLabel makeCenteredLabel(String text) {
        JLabel label = makeLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        
        return label;
    }
    
    public static JPanel makeGridPanel(int r, int c) {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(r, c));
        p.setBackground(PEACH);
        
        return p;
    }
}