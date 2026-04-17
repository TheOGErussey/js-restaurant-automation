package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import models.OrderItem;
import models.TableInfo;
import models.Order;
import models.RefundRequest;
import models.RequestManager;

public class ManageOrderFrame extends JFrame {

    private JLabel selectedTableLabel;
    private JLabel statusLabel;
    private JButton selectedButton = null;
    private TableInfo table;

    private final Color OPEN = new Color(110, 200, 80);
    private final Color OCCUPIED = new Color(240, 200, 80);
    private final Color DIRTY = new Color(230, 50, 50);

    public ManageOrderFrame(TableInfo table) {
        setTitle("Manage Order - J's Corner Restaurant");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout());

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(128, 0, 0));
        header.setPreferredSize(new Dimension(1280, 100));

        JPanel leftHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 20));
        leftHeader.setOpaque(false);

        JLabel logo = new JLabel(loadIcon("Fork.png", 45, 45));
        JLabel title = new JLabel("Manage Order");
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(new Color(245, 230, 211));

        leftHeader.add(logo);
        leftHeader.add(title);

        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 25));
        rightHeader.setOpaque(false);

        JLabel welcome = new JLabel("Welcome, Wait Staff");
        welcome.setForeground(Color.WHITE);

        JLabel logout = new JLabel("Logout");
        logout.setForeground(Color.WHITE);
        logout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                showLogoutPopup();
            }
        });

        rightHeader.add(welcome);
        rightHeader.add(logout);

        header.add(leftHeader, BorderLayout.WEST);
        header.add(rightHeader, BorderLayout.EAST);

        // ===== TABLE AREA =====
        JPanel tablePanel = new JPanel(new GridBagLayout());
        tablePanel.setBackground(new Color(222, 208, 187));
        tablePanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        // LEFT SIDE
        addTable(tablePanel, "A1", OPEN, 0, 0, gbc);
        addTable(tablePanel, "B1", OPEN, 1, 0, gbc);

        addTable(tablePanel, "A2", OPEN, 0, 1, gbc);
        addTable(tablePanel, "B2", OCCUPIED, 1, 1, gbc);

        addTable(tablePanel, "A3", DIRTY, 0, 2, gbc);
        addTable(tablePanel, "B3", OPEN, 1, 2, gbc);

        addTable(tablePanel, "A4", OCCUPIED, 0, 3, gbc);
        addTable(tablePanel, "B4", OCCUPIED, 1, 3, gbc);

        addTable(tablePanel, "A5", DIRTY, 0, 4, gbc);
        addTable(tablePanel, "B5", OPEN, 1, 4, gbc);

        addTable(tablePanel, "A6", OPEN, 0, 5, gbc);
        addTable(tablePanel, "B6", OPEN, 1, 5, gbc);

        // ===== CENTER BLOCK =====
        JPanel centerBlock = new JPanel();
        centerBlock.setBackground(Color.LIGHT_GRAY);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipadx = 220;
        gbc.ipady = 240;

        tablePanel.add(centerBlock, gbc);

        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.ipadx = 0;
        gbc.ipady = 0;

        // MIDDLE BOTTOM
        addTable(tablePanel, "C5", DIRTY, 2, 4, gbc);
        addTable(tablePanel, "D5", OCCUPIED, 3, 4, gbc);

        addTable(tablePanel, "C6", OPEN, 2, 5, gbc);
        addTable(tablePanel, "D6", OCCUPIED, 3, 5, gbc);

        // RIGHT SIDE
        addTable(tablePanel, "E1", OPEN, 4, 0, gbc);
        addTable(tablePanel, "F1", OPEN, 5, 0, gbc);

        addTable(tablePanel, "E2", OPEN, 4, 1, gbc);
        addTable(tablePanel, "F2", OPEN, 5, 1, gbc);

        addTable(tablePanel, "E3", DIRTY, 4, 2, gbc);
        addTable(tablePanel, "F3", OPEN, 5, 2, gbc);

        addTable(tablePanel, "E4", OCCUPIED, 4, 3, gbc);
        addTable(tablePanel, "F4", OPEN, 5, 3, gbc);

        addTable(tablePanel, "E5", DIRTY, 4, 4, gbc);
        addTable(tablePanel, "F5", OPEN, 5, 4, gbc);

        addTable(tablePanel, "E6", OPEN, 4, 5, gbc);
        addTable(tablePanel, "F6", OPEN, 5, 5, gbc);

        // ===== SIDE PANEL =====
        JPanel sideWrapper = new JPanel(new GridBagLayout());
        sideWrapper.setBackground(new Color(222, 208, 187));

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(245, 240, 235));
        sidePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 6));
        sidePanel.setPreferredSize(new Dimension(360, 520));

        selectedTableLabel = new JLabel("Table Selected: " + table.getTableName());
        selectedTableLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        selectedTableLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        statusLabel = new JLabel("Status: " + table.getStatus());
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== LOAD ORDER =====
        if (table.getCurrentOrder() != null) {

            Order order = table.getCurrentOrder();
        }

        // BUTTONS
        JButton createOrderBtn = createRoundedButton("Create Order");
        createOrderBtn.addActionListener(e -> {
            new CreateOrderFrame(table);
            dispose();
        });

        JButton updateOrderBtn = createRoundedButton("Update Order");
        JButton orderStatusBtn = createRoundedButton("Order Status");
        JButton checkoutBtn = createRoundedButton("Checkout");
        JButton cancelBtn = createRoundedButton("Cancel");
        JButton refundBtn = createRoundedButton("Request Refund");

        refundBtn.addActionListener(e -> {

            if (table.getCurrentOrder() == null) {
                showNoOrderRefundError();
                return;
            }

            String reason = JOptionPane.showInputDialog("Enter refund reason:");

            if (reason == null || reason.trim().isEmpty()) {
                showRefundReasonError();
                return;
            }

            RefundRequest request = new RefundRequest(
                    table.getTableName(),
                    reason
            );

            RequestManager.requests.add(request);

            showRefundSuccessPopup();
        });

        cancelBtn.addActionListener(e -> {
            new WaitStaffFloorFrame();
            dispose();
        });

        Dimension btnSize = new Dimension(160, 50);

        JButton[] buttons = {
                createOrderBtn, updateOrderBtn, orderStatusBtn, checkoutBtn, refundBtn, cancelBtn
        };

        for (JButton b : buttons) {
            b.setPreferredSize(btnSize);
            b.setMinimumSize(btnSize);
            b.setMaximumSize(btnSize);
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        // LAYOUT
        sidePanel.add(Box.createVerticalStrut(30));
        sidePanel.add(selectedTableLabel);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(statusLabel);
        sidePanel.add(Box.createVerticalStrut(30));

        sidePanel.add(createOrderBtn);
        sidePanel.add(Box.createVerticalStrut(15));

        sidePanel.add(updateOrderBtn);
        sidePanel.add(Box.createVerticalStrut(15));

        sidePanel.add(orderStatusBtn);
        sidePanel.add(Box.createVerticalStrut(15));

        sidePanel.add(checkoutBtn);
        sidePanel.add(Box.createVerticalStrut(15));

        sidePanel.add(cancelBtn);
        sidePanel.add(Box.createVerticalStrut(15));

        sidePanel.add(refundBtn);

        sidePanel.add(Box.createVerticalGlue());

        sideWrapper.add(sidePanel);

        // ===== BUILD =====
        main.add(header, BorderLayout.NORTH);
        main.add(tablePanel, BorderLayout.CENTER);
        main.add(sideWrapper, BorderLayout.EAST);

        add(main);
        setVisible(true);
    }

    // ===== TABLE METHOD =====
    private void addTable(JPanel panel, String name, Color color, int x, int y, GridBagConstraints gbc) {
        JButton btn = new JButton(name);

        Dimension size = new Dimension(85, 75);
        btn.setPreferredSize(size);
        btn.setMinimumSize(size);
        btn.setMaximumSize(size);

        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));

        btn.addActionListener(e -> {

            if (selectedButton != null) {
                selectedButton.setBackground(color);
            }

            selectedButton = btn;
            btn.setBackground(Color.LIGHT_GRAY);

            selectedTableLabel.setText("Table Selected: " + name);

            if (color.equals(OPEN)) statusLabel.setText("Status: Open");
            else if (color.equals(OCCUPIED)) statusLabel.setText("Status: Occupied");
            else statusLabel.setText("Status: Dirty");
        });

        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(btn, gbc);
    }

    private ImageIcon loadIcon(String fileName, int w, int h) {
        ImageIcon icon = new ImageIcon("src/icons/" + fileName);
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 18));

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

        JButton cancelBtn = createRoundedButton("Cancel");
        JButton logoutBtn = createRoundedButton("Logout");

        Dimension size = new Dimension(140, 55);

        cancelBtn.setPreferredSize(size);
        cancelBtn.setMinimumSize(size);
        cancelBtn.setMaximumSize(size);

        logoutBtn.setPreferredSize(size);
        logoutBtn.setMinimumSize(size);
        logoutBtn.setMaximumSize(size);

        cancelBtn.addActionListener(e -> dialog.dispose());

        logoutBtn.addActionListener(e -> {
            dialog.dispose();
            new LoginFrame();
            dispose();
        });

        buttonRow.add(cancelBtn);
        buttonRow.add(logoutBtn);

        body.add(Box.createVerticalStrut(30));
        body.add(message);
        body.add(Box.createVerticalStrut(30));
        body.add(buttonRow);

        main.add(header, BorderLayout.NORTH);
        main.add(body, BorderLayout.CENTER);

        dialog.add(main);
        dialog.setVisible(true);
    }

    private void showNoOrderRefundError() {

        JDialog dialog = new JDialog(this, true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(145, 26, 26));
        header.setPreferredSize(new Dimension(400, 50));

        JLabel title = new JLabel("Error");
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

        JLabel message = new JLabel("No order to refund.");
        message.setFont(new Font("SansSerif", Font.PLAIN, 18));
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton okBtn = createRoundedButton("OK");

        Dimension size = new Dimension(140, 45);
        okBtn.setPreferredSize(size);
        okBtn.setMaximumSize(size);
        okBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        okBtn.addActionListener(e -> dialog.dispose());

        body.add(Box.createVerticalStrut(25));
        body.add(message);
        body.add(Box.createVerticalStrut(25));
        body.add(okBtn);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(body, BorderLayout.CENTER);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    // CURRENT WORK IN PROGRESS
    private void showRefundReasonError() {

        JDialog dialog = new JDialog(this, true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(145, 26, 26));
        header.setPreferredSize(new Dimension(400, 50));

        JLabel title = new JLabel("Error");
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

        JLabel message = new JLabel("Refund reason is required.");
        message.setFont(new Font("SansSerif", Font.PLAIN, 18));
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton okBtn = createRoundedButton("OK");

        Dimension size = new Dimension(140, 45);
        okBtn.setPreferredSize(size);
        okBtn.setMaximumSize(size);
        okBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        okBtn.addActionListener(e -> dialog.dispose());

        body.add(Box.createVerticalStrut(25));
        body.add(message);
        body.add(Box.createVerticalStrut(25));
        body.add(okBtn);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(body, BorderLayout.CENTER);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    private void showRefundSuccessPopup() {

        JDialog dialog = new JDialog(this, true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(145, 26, 26));
        header.setPreferredSize(new Dimension(400, 50));

        JLabel title = new JLabel("Success");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setBorder(new EmptyBorder(0, 15, 0, 0));

        JLabel icon = new JLabel(loadIcon("Check.png", 24, 24));
        icon.setBorder(new EmptyBorder(0, 0, 0, 15));

        header.add(title, BorderLayout.WEST);
        header.add(icon, BorderLayout.EAST);

        JPanel body = new JPanel();
        body.setBackground(new Color(245, 240, 235));
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        JLabel message = new JLabel("Refund request sent.");
        message.setFont(new Font("SansSerif", Font.PLAIN, 18));
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton okBtn = createRoundedButton("OK");

        Dimension size = new Dimension(140, 45);
        okBtn.setPreferredSize(size);
        okBtn.setMaximumSize(size);
        okBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        okBtn.addActionListener(e -> dialog.dispose());

        body.add(Box.createVerticalStrut(25));
        body.add(message);
        body.add(Box.createVerticalStrut(25));
        body.add(okBtn);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(body, BorderLayout.CENTER);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
}
