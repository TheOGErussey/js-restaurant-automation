package Main;

import javax.swing.*;
import java.awt.*;

public class Resturant {
    
    // Default width, height
    public static final int defW = 300, defH = 200;
    
    private static JFrame mainFrame;
    
    public static void main(String[] args) {
        
        // Default User
        Employee.User defualtUser = new Employee.User("Admin", "Admin", "123", "Admin");
        
        // All is a Temporary GUI 
        
        mainFrame = new JFrame("Temp Start");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(defW, defH);

        JButton button = new JButton("Start");
        mainFrame.add(button); 
        
        mainFrame.setVisible(true);
        
        // On press promt for user and password & attempt login
        button.addActionListener((java.awt.event.ActionEvent e) -> { initialLogin(); } );
    }
    
    private static void initialLogin() {
        Employee.User user = Employee.User.login();
        
        mainFrame.dispose();
        
        System.out.println("Logged in");
        
        mainFrame = new JFrame("Logged in as: " + user.getName());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(defW * 2, defH * 2);
        
        int rows = 3;
        int cols = 3;

        mainFrame.setLayout(new GridLayout(rows, cols));

        for (int i = 1; i <= rows * cols; i++) {
            JButton button = new JButton("Button " + i);
            mainFrame.add(button);
        }
        
        mainFrame.setVisible(true);
    }
    
}