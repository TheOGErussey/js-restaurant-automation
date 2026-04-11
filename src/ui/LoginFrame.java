package ui;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Login - J's Corner Restaurant");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout());

        // ===== HEADER =====
        JPanel header = new JPanel(new GridBagLayout());
        header.setBackground(new Color(128, 0, 0));
        header.setPreferredSize(new Dimension(1280, 120));

        JLabel title = new JLabel("Login");
        title.setFont(new Font("Segoe UI", Font.BOLD, 40));
        title.setForeground(new Color(245, 230, 211));

        header.add(title);

        // ===== CENTER =====
        JPanel center = new JPanel();
        center.setBackground(new Color(222, 208, 187));
        center.setLayout(new GridBagLayout());

        JPanel form = new JPanel();
        form.setBackground(new Color(222, 208, 187));
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        // ===== USERNAME =====
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(300, 40));
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        // ===== PASSWORD =====
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(300, 40));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        // ===== BUTTONS =====
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonRow.setBackground(new Color(222, 208, 187));

        JButton cancelButton = createRoundedButton("Cancel");
        JButton loginButton = createRoundedButton("Login");

        buttonRow.add(cancelButton);
        buttonRow.add(loginButton);

        // ===== ADD COMPONENTS =====
        form.add(userLabel);
        form.add(Box.createVerticalStrut(10));
        form.add(usernameField);

        form.add(Box.createVerticalStrut(25));

        form.add(passLabel);
        form.add(Box.createVerticalStrut(10));
        form.add(passwordField);

        form.add(Box.createVerticalStrut(30));
        form.add(buttonRow);

        center.add(form);

        main.add(header, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);

        add(main);

        // ===== BUTTON ACTIONS =====

        cancelButton.addActionListener(e -> {
            new HomeFrame();
            dispose();
        });

        loginButton.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            // TEMP login 
            if (user.equals("admin") && pass.equals("admin123")) {
                JOptionPane.showMessageDialog(this, "Manager Login Success");
            } else if (user.equals("waiter") && pass.equals("123")) {
                JOptionPane.showMessageDialog(this, "Waiter Login Success");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            }
        });

        setVisible(true);
    }

    // ===== ROUNDED BUTTON =====
    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(160, 0, 0));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(180, 60));

        button.setContentAreaFilled(false);
        button.setOpaque(false);

        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(button.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 40, 40);

                super.paint(g, c);
            }
        });

        return button;
    }
}
