package Employee;

import static Main.Resturant.makeButton;
import static Main.Resturant.makeGridPanel;
import javax.swing.*;
import java.awt.*;

public class User {

    // All employees
    private static java.util.List<User> employees = new java.util.ArrayList<>(1);
    
    private int nextID = 0;
    private int userID;
    private String name, username, password;
    
    public User(String name, String username, String password) {
        this.name = name; this.username = username; this.password = password;
        userID = nextID++;
        
        employees.add(this);
    }
    
    public static User login() {
        final int X_PAD = 40, Y_PAD = 20;
        
        JDialog loginDialog = new JDialog((Frame) null, "Login", true); 
        loginDialog.setSize(Main.Resturant.DEF_W, (int) (Main.Resturant.DEF_H / 1.25));
        loginDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginDialog.setLayout(new BoxLayout(loginDialog.getContentPane(), BoxLayout.Y_AXIS));

        // 2 by 2 grid
        // userLabel, userInput
        // passLabel, passInput
        
        JPanel inputPanel = Main.Resturant.makeGridPanel(2, 2);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(Y_PAD/2, Y_PAD/2, X_PAD/2, X_PAD/2)); 

        JLabel userLabel = Main.Resturant.makeLabel("Username:", 20);
        JTextField userField = new JTextField();
        
        JLabel passLabel = Main.Resturant.makeLabel("Password:", 20);
        JPasswordField passField = new JPasswordField();

        inputPanel.add(userLabel); inputPanel.add(userField);
        inputPanel.add(passLabel); inputPanel.add(passField);

        loginDialog.add(Main.Resturant.getHeaderPanel(15, 15));
        loginDialog.add(inputPanel, BorderLayout.CENTER);

        final User[] result = { null };

        // Add the confirmation button
        JPanel optionsPanel = makeGridPanel(2, 1); // 2 Buttons
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(Y_PAD/2, Y_PAD/2, X_PAD/2, X_PAD/2)); 

        JButton login = makeButton("Login"), exit = makeButton("Exit");
        optionsPanel.add(login); optionsPanel.add(exit);
        
        loginDialog.add(optionsPanel);

        login.addActionListener((java.awt.event.ActionEvent e) -> {
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
