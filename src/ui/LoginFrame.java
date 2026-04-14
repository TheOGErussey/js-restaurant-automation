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

        // ===== HEADER (TOP BAR WITH ICON) =====
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 25));
        header.setBackground(new Color(128, 0, 0));
        header.setPreferredSize(new Dimension(1280, 120));

        JLabel logo = new JLabel(loadIcon("Fork.png", 60, 60));

        JLabel title = new JLabel("J’s Corner Restaurant");
        title.setFont(new Font("Serif", Font.BOLD, 40));
        title.setForeground(new Color(245, 230, 211));

        header.add(logo);
        header.add(title);

        // ===== BACKGROUND =====
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(new Color(222, 208, 187));

        // ===== LOGIN CARD =====
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(450, 420));
        card.setBackground(new Color(245, 240, 235));
        card.setBorder(BorderFactory.createLineBorder(new Color(60, 40, 20), 8));

        // ===== CARD HEADER =====
        JPanel cardHeader = new JPanel();
        cardHeader.setBackground(new Color(128, 0, 0));
        cardHeader.setPreferredSize(new Dimension(450, 70));

        JLabel loginTitle = new JLabel("Login");
        loginTitle.setFont(new Font("Serif", Font.BOLD, 26));
        loginTitle.setForeground(new Color(245, 230, 211));

        cardHeader.add(loginTitle);

        // ===== FORM =====
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(new Color(245, 240, 235));

        form.add(Box.createVerticalStrut(30));

        // USERNAME ROW
        JPanel userRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        userRow.setOpaque(false);

        JLabel userLabel = new JLabel("Employee ID:");
        userLabel.setFont(new Font("Serif", Font.PLAIN, 18));

        JTextField usernameField = new JTextField(15);
        usernameField.setPreferredSize(new Dimension(200, 30));

        userRow.add(userLabel);
        userRow.add(usernameField);

        // PASSWORD ROW
        JPanel passRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        passRow.setOpaque(false);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Serif", Font.PLAIN, 18));

        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setPreferredSize(new Dimension(200, 30));

        passRow.add(passLabel);
        passRow.add(passwordField);

        form.add(userRow);
        form.add(Box.createVerticalStrut(25));
        form.add(passRow);

        form.add(Box.createVerticalStrut(40));

        // ===== BUTTONS =====
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonRow.setOpaque(false);

        JButton cancelButton = createRoundedButton("Cancel");
        JButton loginButton = createRoundedButton("Login");

        buttonRow.add(cancelButton);
        buttonRow.add(loginButton);

        form.add(buttonRow);

        // ===== BUILD CARD =====
        card.add(cardHeader, BorderLayout.NORTH);
        card.add(form, BorderLayout.CENTER);

        background.add(card);

        main.add(header, BorderLayout.NORTH);
        main.add(background, BorderLayout.CENTER);

        add(main);

        // ===== BUTTON ACTIONS =====
        cancelButton.addActionListener(e -> {
            new HomeFrame();
            dispose();
        });

        loginButton.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

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

    // ===== ICON LOADER =====
    private ImageIcon loadIcon(String fileName, int width, int height) {
        ImageIcon icon = new ImageIcon("src/icons/" + fileName);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // ===== ROUNDED BUTTON =====
    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 20));
        button.setForeground(new Color(245, 230, 211));
        button.setBackground(new Color(128, 0, 0));
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
