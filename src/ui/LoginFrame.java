package ui;

import models.Employee;
import models.EmployeeFileHandler;
import models.ActivityLogger;
import models.Session;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JPasswordField passwordField;
    private boolean passwordVisible = false;
    private JPanel errorContainer;

    public LoginFrame() {
        setTitle("Login - J's Corner Restaurant");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout());

        // ===== HEADER =====
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 30));
        header.setBackground(new Color(128, 0, 0));
        header.setPreferredSize(new Dimension(1280, 120));

        JLabel logo = new JLabel(loadIcon("Fork.png", 55, 55));

        JLabel title = new JLabel("J’s Corner Restaurant");
        title.setFont(new Font("Serif", Font.BOLD, 40));
        title.setForeground(new Color(245, 230, 211));

        header.add(logo);
        header.add(title);

        // ===== BACKGROUND =====
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(new Color(222, 208, 187));

        // ===== CARD =====
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(420, 420));
        card.setBackground(new Color(245, 240, 235));
        card.setBorder(BorderFactory.createLineBorder(new Color(60, 40, 20), 10));

        // ===== CARD HEADER =====
        JPanel cardHeader = new JPanel(new GridBagLayout());
        cardHeader.setBackground(new Color(145, 26, 26));
        cardHeader.setPreferredSize(new Dimension(420, 80));

        JLabel loginTitle = new JLabel("Login");
        loginTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        loginTitle.setForeground(Color.WHITE);

        cardHeader.add(loginTitle);

        // ===== FORM =====
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(new Color(245, 240, 235));
        form.setBorder(new EmptyBorder(30, 30, 30, 30));
        form.setAlignmentY(Component.TOP_ALIGNMENT);

        // ===== USER ROW =====
        JPanel userRow = new JPanel(new GridLayout(1, 2, 10, 0));
        userRow.setOpaque(false);
        userRow.setMaximumSize(new Dimension(420, 50));

        JLabel userLabel = new JLabel("Employee ID:");
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        JTextField usernameField = new JTextField();
        usernameField.setBackground(new Color(230, 230, 230));
        usernameField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        usernameField.setPreferredSize(new Dimension(300, 50));
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 18));

        userRow.add(userLabel);
        userRow.add(usernameField);

        // ===== PASSWORD ROW =====
        JPanel passRow = new JPanel(new GridLayout(1, 2, 10, 0));
        passRow.setOpaque(false);
        passRow.setMaximumSize(new Dimension(420, 50));

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(new Color(230, 230, 230));
        passwordPanel.setPreferredSize(new Dimension(300, 50));

        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        passwordField.setBackground(new Color(230, 230, 230));
        passwordField.setEchoChar('*');
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 18)); // bigger text

        JButton eyeButton = new JButton(loadIcon("Eye.png", 18, 18));
        eyeButton.setPreferredSize(new Dimension(30, 30));
        eyeButton.setBorderPainted(false);
        eyeButton.setContentAreaFilled(false);
        eyeButton.addActionListener(e -> togglePassword());

        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.add(eyeButton, BorderLayout.EAST);

        passRow.add(passLabel);
        passRow.add(passwordPanel);

        // ===== BUTTONS =====
        JPanel buttonRow = new JPanel();
        buttonRow.setOpaque(false);
        buttonRow.setLayout(new BoxLayout(buttonRow, BoxLayout.X_AXIS));

        // center alignment
        buttonRow.add(Box.createHorizontalGlue());

        JButton cancelButton = createRoundedButton("Cancel");
        JButton loginButton = createRoundedButton("Login");

        // size
        cancelButton.setMinimumSize(new Dimension(160, 60));
        cancelButton.setPreferredSize(new Dimension(160, 60));
        cancelButton.setMaximumSize(new Dimension(160, 60));

        loginButton.setMinimumSize(new Dimension(160, 60));
        loginButton.setPreferredSize(new Dimension(160, 60));
        loginButton.setMaximumSize(new Dimension(160, 60));

        // spacing between buttons
        buttonRow.add(cancelButton);
        buttonRow.add(Box.createHorizontalStrut(20));
        buttonRow.add(loginButton);

        buttonRow.add(Box.createHorizontalGlue());

        // ===== ADD FORM =====
        errorContainer = new JPanel();
        errorContainer.setLayout(new BoxLayout(errorContainer, BoxLayout.Y_AXIS));
        errorContainer.setOpaque(false);

        form.add(errorContainer);
        form.add(Box.createVerticalStrut(10));
        form.add(userRow);
        form.add(Box.createVerticalStrut(25));
        form.add(passRow);
        form.add(Box.createVerticalStrut(50));
        form.add(buttonRow);

        // ===== BUILD =====
        card.add(cardHeader, BorderLayout.NORTH);
        card.add(form, BorderLayout.CENTER);

        background.add(card);

        main.add(header, BorderLayout.NORTH);
        main.add(background, BorderLayout.CENTER);

        add(main);

        // ===== ACTIONS =====
        cancelButton.addActionListener(e -> {
            new HomeFrame();
            dispose();
        });

        loginButton.addActionListener(e -> {

            String usernameInput = usernameField.getText().trim();
            String passwordInput = new String(passwordField.getPassword()).trim();

            for (Employee emp : EmployeeFileHandler.loadEmployees()) {

                // ONLY ALLOW WAITER + MANAGER FOR REQUIREMENTS
                if (!emp.getRole().equalsIgnoreCase("Waiter") &&
                        !emp.getRole().equalsIgnoreCase("Manager")) {
                    continue;
                }

                if (emp.getId().equalsIgnoreCase(usernameInput) &&
                        emp.getPassword().equals(passwordInput)) {
                    Session.setUser(emp);
                    ActivityLogger.log(emp.getName() + " logged in");
                    if (emp.getRole().equalsIgnoreCase("Waiter")) {
                        new ClockInFrame(emp);
                    }
                    else if (emp.getRole().equalsIgnoreCase("Manager")) {
                        new ManagerFrame();
                    }

                    dispose();
                    return;
                }
            }

            showErrorPopup("Invalid login.");
        });

        setVisible(true);
    }

    private void togglePassword() {
        if (passwordVisible) {
            // hide password
            passwordField.setEchoChar('*');
            passwordVisible = false;
        } else {
            // show password
            passwordField.setEchoChar((char) 0);
            passwordVisible = true;
        }
    }

    private ImageIcon loadIcon(String fileName, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/" + fileName));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(145, 26, 26));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);

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

    private void showErrorPopup(String message) {

        JDialog dialog = new JDialog(this, true);
        dialog.setSize(420, 220);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);

        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(145, 26, 26));
        header.setPreferredSize(new Dimension(420, 55));

        JLabel title = new JLabel("Error");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setBorder(new EmptyBorder(0, 15, 0, 0));

        JLabel warningIcon = new JLabel(loadIcon("Warning.png", 24, 24));
        warningIcon.setBorder(new EmptyBorder(0, 0, 0, 15));

        header.add(title, BorderLayout.WEST);
        header.add(warningIcon, BorderLayout.EAST);

        JPanel body = new JPanel();
        body.setBackground(new Color(245, 240, 235));
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        JLabel text = new JLabel(message);
        text.setFont(new Font("SansSerif", Font.PLAIN, 18));
        text.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton okButton = createRoundedButton("OK");
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        Dimension okSize = new Dimension(140, 55);
        okButton.setPreferredSize(okSize);
        okButton.setMinimumSize(okSize);
        okButton.setMaximumSize(okSize);

        body.add(Box.createVerticalStrut(28));
        body.add(text);
        body.add(Box.createVerticalStrut(35));
        body.add(okButton);
        body.add(Box.createVerticalStrut(20));

        okButton.addActionListener(e -> dialog.dispose());

        main.add(header, BorderLayout.NORTH);
        main.add(body, BorderLayout.CENTER);

        dialog.add(main);
        dialog.setVisible(true);
    }
}
