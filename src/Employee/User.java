package Employee;

import javax.swing.*;
import java.awt.*;

public class User {

    // All employees
    private static java.util.List<User> employees = new java.util.ArrayList<>(1);
    
    private int nextID = 0;
    private int userID;
    private String name, username, password, role;
    
    public User(String name, String username, String password, String role) {
        this.name = name; this.username = username; this.password = password; this.role = role;
        userID = nextID++;
        
        employees.add(this);
    }
    
    public static User login() {
        JDialog loginDialog = new JDialog((Frame) null, "Login", true); // modal
        loginDialog.setSize(300, 150);
        loginDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 2 by 2 grid
        // userLabel, userInput
        // passLabel, passInput
        
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        panel.add(userLabel); panel.add(userField);
        panel.add(passLabel); panel.add(passField);

        loginDialog.add(panel, BorderLayout.CENTER);

        final User[] result = { null };

        // Add the confirmation button
        JButton loginButton = new JButton("Login");
        loginDialog.add(loginButton, BorderLayout.SOUTH);

        loginButton.addActionListener((java.awt.event.ActionEvent e) -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            

            if (User.userDoesExist(username)) {
                User user = User.getUser(username);

                if (user.password.equals(password)) {
                    result[0] = user;
                    loginDialog.dispose();
                } else { System.err.println("Password is incorrect!"); }
            } else { System.err.println("Username does not exist!"); }
        });

        loginDialog.setVisible(true);

        return result[0];
    }

    public void logout() {
        
    }
    
    public String getName() { return name; }
    
    private static boolean userDoesExist(String usernameToCheck) {
        for (User user : employees) if (user.username.equals(usernameToCheck)) return true; 
        return false;
    }
    
    private static User getUser(String username) {
        for (User user : employees) if (user.username.equals(username)) return user;
        return null;
    }
}
