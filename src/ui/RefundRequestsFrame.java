package ui;

import models.*;
import models.ActivityLogger;
import models.Session;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RefundRequestsFrame extends JFrame {

    private JPanel requestsPanel;

    public RefundRequestsFrame() {
        setTitle("Refund Requests - J's Corner Restaurant");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(145, 26, 26));
        header.setPreferredSize(new Dimension(1280, 80));

        JPanel leftHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 20));
        leftHeader.setOpaque(false);

        JLabel icon = new JLabel(loadIcon("Fork.png", 40, 40));

        JLabel title = new JLabel("Refund Requests");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 26));

        leftHeader.add(icon);
        leftHeader.add(title);

        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 25));
        rightHeader.setOpaque(false);

        JLabel welcome = new JLabel("Welcome, Manager");
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel logout = new JLabel("Logout");
        logout.setForeground(Color.WHITE);
        logout.setFont(new Font("SansSerif", Font.BOLD, 14));
        logout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                showLogoutPopup();
            }
        });

        rightHeader.add(welcome);
        rightHeader.add(logout);

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
                "REARRANGE TABLES",
                "MANAGE MENU",
                "MANAGE INVENTORY",
                "GENERATE REPORTS"
        };

        for (String item : menuItems) {
            JButton btn = createSidebarButton(item);

            if (item.equals("DASHBOARD")) {
                btn.addActionListener(e -> {
                    new ManagerFrame();
                    dispose();
                });
            }

            if (item.equals("MANAGE EMPLOYEES")) {
                btn.addActionListener(e -> {
                    new ManageEmployeesFrame();
                    dispose();
                });
            }

            if (item.equals("REFUND REQUESTS")) {
                btn.setEnabled(false);
            }

            if (item.equals("REARRANGE TABLES")) {
                btn.addActionListener(e -> {
                    new ManagerFloorFrame();
                    dispose();
                });
            }

            sidebar.add(Box.createVerticalStrut(20));
            sidebar.add(btn);
        }

        // ===== MAIN CONTENT =====
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(new Color(220, 205, 185));
        content.setBorder(new EmptyBorder(25, 35, 25, 35));

        requestsPanel = new JPanel();
        requestsPanel.setBackground(new Color(220, 205, 185));
        requestsPanel.setLayout(new BoxLayout(requestsPanel, BoxLayout.Y_AXIS));

        refreshRequests();

        JScrollPane scrollPane = new JScrollPane(requestsPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(220, 205, 185));

        content.add(scrollPane, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);

        setVisible(true);
    }

    private void refreshRequests() {
        requestsPanel.removeAll();

        if (RequestManager.requests.isEmpty()) {
            JLabel none = new JLabel("No refund requests available.");
            none.setFont(new Font("SansSerif", Font.BOLD, 20));
            none.setAlignmentX(Component.LEFT_ALIGNMENT);
            requestsPanel.add(none);
        } else {
            for (RefundRequest request : RequestManager.requests) {
                requestsPanel.add(createRequestCard(request));
                requestsPanel.add(Box.createVerticalStrut(20));
            }
        }

        requestsPanel.revalidate();
        requestsPanel.repaint();
    }

    private JPanel createRequestCard(RefundRequest request) {

        // ===== OUTER (BLACK BORDER) =====
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(Color.BLACK);
        outer.setMaximumSize(new Dimension(400, 250));
        outer.setPreferredSize(new Dimension(400, 250));
        outer.setAlignmentX(Component.LEFT_ALIGNMENT);
        outer.setBorder(new EmptyBorder(12, 12, 12, 12));

        // ===== INNER CARD =====
        JPanel inner = new JPanel();
        inner.setBackground(new Color(245, 240, 235));
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBorder(new EmptyBorder(20, 25, 20, 25));

        // ===== TEXT PANEL =====
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        Font textFont = new Font("SansSerif", Font.BOLD, 18);

        JLabel tableLabel = new JLabel("Table: " + request.getTableName());
        tableLabel.setFont(textFont);
        tableLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel reasonLabel = new JLabel("Reason: " + request.getReason());
        reasonLabel.setFont(textFont);
        reasonLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel statusLabel = new JLabel("Status: " + request.getStatus());
        statusLabel.setFont(textFont);
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(tableLabel);
        textPanel.add(Box.createVerticalStrut(12));
        textPanel.add(reasonLabel);
        textPanel.add(Box.createVerticalStrut(12));
        textPanel.add(statusLabel);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        JButton detailsBtn = new JButton("View Details");
        detailsBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        detailsBtn.setBackground(new Color(200, 200, 200));
        detailsBtn.setFocusPainted(false);

        Dimension btnSize = new Dimension(180, 50);
        detailsBtn.setPreferredSize(btnSize);

        detailsBtn.addActionListener(e -> showRefundDetailsPopup(request));

        buttonPanel.add(detailsBtn);

        // ===== BUILD CARD =====
        inner.add(textPanel);
        inner.add(Box.createVerticalStrut(25));
        inner.add(buttonPanel);

        outer.add(inner, BorderLayout.CENTER);

        return outer;
    }

    private void showRefundDetailsPopup(RefundRequest request) {
        JDialog dialog = new JDialog(this, true);
        dialog.setSize(450, 380);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);

        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(145, 26, 26));
        header.setPreferredSize(new Dimension(450, 55));

        JLabel title = new JLabel("Refund Details");
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
        body.setBorder(new EmptyBorder(25, 30, 25, 30));

        Font infoFont = new Font("SansSerif", Font.BOLD, 18);

        JLabel tableLabel = new JLabel("Table: " + request.getTableName());
        tableLabel.setFont(infoFont);
        tableLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel reasonLabel = new JLabel("Reason: " + request.getReason());
        reasonLabel.setFont(infoFont);
        reasonLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel statusLabel = new JLabel("Status: " + request.getStatus());
        statusLabel.setFont(infoFont);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== TOP ROW (Back + Deny) =====
        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 0));
        topRow.setOpaque(false);

        // ===== BOTTOM ROW (Approve) =====
        JPanel bottomRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomRow.setOpaque(false);

        // ===== BUTTONS =====
        JButton backBtn = createRoundedButton("Back");
        JButton denyBtn = createRoundedButton("Deny");
        JButton approveBtn = createRoundedButton("Approve");

        Dimension btnSize = new Dimension(140, 50);

        backBtn.setPreferredSize(btnSize);
        denyBtn.setPreferredSize(btnSize);
        approveBtn.setPreferredSize(btnSize);

        // ===== ACTIONS =====
        backBtn.addActionListener(e -> dialog.dispose());

        denyBtn.addActionListener(e -> {
            request.deny();
            Employee user = Session.getUser();
            if (user != null) {
                ActivityLogger.log(user.getName() + " DENIED refund for table " + request.getTableName());
            }
            RequestManager.requests.remove(request);
            dialog.dispose();
            refreshRequests();
        });

        approveBtn.addActionListener(e -> {
            request.approve();
            Employee user = Session.getUser();
            if (user != null) {
                ActivityLogger.log(user.getName() + " APPROVED refund for table " + request.getTableName());
            }
            RequestManager.requests.remove(request);
            dialog.dispose();
            refreshRequests();
        });

        // ===== ADD BUTTONS TO ROWS =====
        topRow.add(backBtn);
        topRow.add(denyBtn);

        bottomRow.add(approveBtn);

        // ===== BUILD =====
        body.add(tableLabel);
        body.add(Box.createVerticalStrut(15));
        body.add(reasonLabel);
        body.add(Box.createVerticalStrut(15));
        body.add(statusLabel);
        body.add(Box.createVerticalStrut(35));  // space above buttons
        body.add(topRow);
        body.add(Box.createVerticalStrut(15));  // space between rows
        body.add(bottomRow);

        main.add(header, BorderLayout.NORTH);
        main.add(body, BorderLayout.CENTER);

        dialog.add(main);
        dialog.setVisible(true);
    }

    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);

        Color normal = new Color(120, 20, 20);
        Color hover = new Color(170, 40, 40);

        btn.setMaximumSize(new Dimension(200, 50));
        btn.setBackground(normal);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (btn.isEnabled()) btn.setBackground(hover);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btn.isEnabled()) btn.setBackground(normal);
            }
        });

        return btn;
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(145, 26, 26));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);

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

    private ImageIcon loadIcon(String fileName, int w, int h) {
        ImageIcon icon = new ImageIcon("src/icons/" + fileName);
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private void showLogoutPopup() {
        JDialog dialog = new JDialog(this, true);
        dialog.setSize(420, 220);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);

        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

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
        cancelButton.setMaximumSize(btnSize);
        logoutButton.setPreferredSize(btnSize);
        logoutButton.setMaximumSize(btnSize);

        cancelButton.addActionListener(e -> dialog.dispose());
        logoutButton.addActionListener(e -> {
            Employee user = Session.getUser();

            if (user != null) {
                ActivityLogger.log(user.getName() + " logged out");
            }

            Session.clear();

            new LoginFrame();
            dispose();
        });

        buttonRow.add(cancelButton);
        buttonRow.add(logoutButton);

        body.add(Box.createVerticalStrut(28));
        body.add(message);
        body.add(Box.createVerticalStrut(30));
        body.add(buttonRow);
        body.add(Box.createVerticalStrut(20));

        main.add(header, BorderLayout.NORTH);
        main.add(body, BorderLayout.CENTER);

        dialog.add(main);
        dialog.setVisible(true);
    }
}
