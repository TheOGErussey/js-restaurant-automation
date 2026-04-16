package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ManagerFrame extends JFrame {

    public ManagerFrame() {

        setTitle("Manager Dashboard - J's Corner Restaurant");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(145, 26, 26));
        header.setPreferredSize(new Dimension(1100, 80));

        // ===== LEFT SIDE (ICON + TITLE) =====
        JPanel leftHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 20));
        leftHeader.setOpaque(false);

        JLabel icon = new JLabel(loadIcon("Fork.png", 40, 40));

        JLabel title = new JLabel("Manager Dashboard");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 26));

        leftHeader.add(icon);
        leftHeader.add(title);

        // ===== RIGHT SIDE (WELCOME + LOGOUT) =====
        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 25));
        rightHeader.setOpaque(false);

        JLabel welcome = new JLabel("Welcome, Manager");
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel logout = new JLabel("Logout");
        logout.setForeground(Color.WHITE);
        logout.setFont(new Font("SansSerif", Font.BOLD, 14));

        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                showLogoutPopup();
            }
        });

        rightHeader.add(welcome);
        rightHeader.add(logout);

        // ===== ADD TO HEADER =====
        header.add(leftHeader, BorderLayout.WEST);
        header.add(rightHeader, BorderLayout.EAST);

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(120, 20, 20));
        sidebar.setPreferredSize(new Dimension(220, 600));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        String[] menuItems = {
                "DASHBOARD",
                "REFUND REQUESTS",
                "MANAGE EMPLOYEES",
                "MANAGE MENU",
                "MANAGE INVENTORY",
                "GENERATE REPORTS"
        };

        for (String item : menuItems) {
            JButton btn = createSidebarButton(item);

            // ONLY THIS BUTTON WORKS FOR REQUIREMENTS
            if (item.equals("MANAGE EMPLOYEES")) {
                btn.addActionListener(e -> new ManageEmployeesFrame());
            }

            sidebar.add(Box.createVerticalStrut(20));
            sidebar.add(btn);
        }

        //===== CENTER =====
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(new Color(220, 205, 185));

        // ===== DASHBOARD CARDS =====
        JPanel dashboard = new JPanel(new GridLayout(2, 2, 30, 30));
        dashboard.setBackground(new Color(220, 205, 185));
        dashboard.setBorder(new EmptyBorder(30, 30, 30, 30));

        dashboard.add(createDashboardCard("Total Revenue", "$12,450"));
        dashboard.add(createDashboardCard("Orders Today", "42"));
        dashboard.add(createDashboardCard("Pending Requests", "5"));
        dashboard.add(createDashboardCard("Inventory Alerts", "2"));

        content.add(dashboard, BorderLayout.CENTER);

        //===== ADD TO FRAME =====
        add(header, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createDashboardCard(String title, String value) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(245, 240, 235));
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));


        card.setPreferredSize(new Dimension(190, 110));
        card.setMaximumSize(new Dimension(190, 110));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Serif", Font.BOLD, 26));
        valueLabel.setForeground(new Color(145, 26, 26));
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(valueLabel);
        card.add(Box.createVerticalGlue());

        return card;
    }

    // ===== SIDEBAR BUTTON STYLE =====
    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(200, 50));
        btn.setBackground(new Color(120, 20, 20));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }

    // ===== ICON LOADER =====
    private ImageIcon loadIcon(String fileName, int w, int h) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icons/" + fileName));
            Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return null;
        }
    }

    // ROUNDED BUTTONS
    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(145, 26, 26));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setPreferredSize(new Dimension(180, 60));

        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(button.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 40, 40);
                super.paint(g, c);
            }
        });

        return button;
    }

    private void showLogoutPopup() {
        JDialog dialog = new JDialog(this, true);
        dialog.setSize(420, 220);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);

        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(145, 26, 26));
        header.setPreferredSize(new Dimension(420, 55));

        JLabel title = new JLabel("Logout");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setBorder(new EmptyBorder(0, 15, 0, 0));

        JLabel icon = new JLabel(loadIcon("Warning.png", 24, 24));
        icon.setBorder(new EmptyBorder(0, 0, 0, 15));

        header.add(title, BorderLayout.WEST);
        header.add(icon, BorderLayout.EAST);

        // ===== BODY =====
        JPanel body = new JPanel();
        body.setBackground(new Color(245, 240, 235));
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        JLabel message = new JLabel("Are you sure you want to logout?");
        message.setFont(new Font("SansSerif", Font.PLAIN, 18));
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonRow.setOpaque(false);

        JButton cancelButton = createRoundedButton("Cancel");
        JButton logoutButton = createRoundedButton("Logout");

        Dimension btnSize = new Dimension(140, 55);

        cancelButton.setPreferredSize(btnSize);
        cancelButton.setMinimumSize(btnSize);
        cancelButton.setMaximumSize(btnSize);

        logoutButton.setPreferredSize(btnSize);
        logoutButton.setMinimumSize(btnSize);
        logoutButton.setMaximumSize(btnSize);

        buttonRow.add(cancelButton);
        buttonRow.add(logoutButton);

        body.add(Box.createVerticalStrut(28));
        body.add(message);
        body.add(Box.createVerticalStrut(30));
        body.add(buttonRow);
        body.add(Box.createVerticalStrut(20));

        // ===== ACTIONS =====
        cancelButton.addActionListener(e -> dialog.dispose());

        logoutButton.addActionListener(e -> {
            dialog.dispose();
            new LoginFrame();
            dispose();
        });

        // ===== BUILD =====
        main.add(header, BorderLayout.NORTH);
        main.add(body, BorderLayout.CENTER);

        dialog.add(main);
        dialog.setVisible(true);
    }
}
