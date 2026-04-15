package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class WaitStaffFloorFrame extends JFrame {

    private JLabel selectedTableLabel;
    private JLabel statusLabel;

    private JButton selectedTableButton = null;

    private final Color OPEN = new Color(110, 200, 80);
    private final Color OCCUPIED = new Color(240, 200, 80);
    private final Color DIRTY = new Color(230, 50, 50);

    private final Map<JButton, String> tableStatuses = new HashMap<>();
    private final Map<JButton, String> tableNames = new HashMap<>();

    public WaitStaffFloorFrame() {
        setTitle("Wait Staff - J's Corner Restaurant");
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
        JLabel title = new JLabel("Waiter Dashboard");
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

        // LEFT SIDE (A + B)
        addTable(tablePanel, "A1", "Open", 0, 0, gbc);
        addTable(tablePanel, "B1", "Open", 1, 0, gbc);

        addTable(tablePanel, "A2", "Open", 0, 1, gbc);
        addTable(tablePanel, "B2", "Occupied", 1, 1, gbc);

        addTable(tablePanel, "A3", "Dirty", 0, 2, gbc);
        addTable(tablePanel, "B3", "Open", 1, 2, gbc);

        addTable(tablePanel, "A4", "Occupied", 0, 3, gbc);
        addTable(tablePanel, "B4", "Occupied", 1, 3, gbc);

        addTable(tablePanel, "A5", "Dirty", 0, 4, gbc);
        addTable(tablePanel, "B5", "Open", 1, 4, gbc);

        addTable(tablePanel, "A6", "Open", 0, 5, gbc);
        addTable(tablePanel, "B6", "Open", 1, 5, gbc);

        // ===== CENTER GREY BLOCK =====
        JPanel centerBlock = new JPanel();
        centerBlock.setBackground(Color.LIGHT_GRAY);
        centerBlock.setPreferredSize(new Dimension(260, 260));

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipadx = 220;
        gbc.ipady = 220;

        tablePanel.add(centerBlock, gbc);

        // reset constraints after center block
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.fill = GridBagConstraints.NONE;

        // MIDDLE BOTTOM (C + D)
        addTable(tablePanel, "C5", "Dirty", 2, 4, gbc);
        addTable(tablePanel, "D5", "Occupied", 3, 4, gbc);

        addTable(tablePanel, "C6", "Open", 2, 5, gbc);
        addTable(tablePanel, "D6", "Occupied", 3, 5, gbc);

        // RIGHT SIDE (E + F)
        addTable(tablePanel, "E1", "Open", 4, 0, gbc);
        addTable(tablePanel, "F1", "Open", 5, 0, gbc);

        addTable(tablePanel, "E2", "Open", 4, 1, gbc);
        addTable(tablePanel, "F2", "Open", 5, 1, gbc);

        addTable(tablePanel, "E3", "Dirty", 4, 2, gbc);
        addTable(tablePanel, "F3", "Open", 5, 2, gbc);

        addTable(tablePanel, "E4", "Occupied", 4, 3, gbc);
        addTable(tablePanel, "F4", "Open", 5, 3, gbc);

        addTable(tablePanel, "E5", "Dirty", 4, 4, gbc);
        addTable(tablePanel, "F5", "Open", 5, 4, gbc);

        addTable(tablePanel, "E6", "Open", 4, 5, gbc);
        addTable(tablePanel, "F6", "Open", 5, 5, gbc);

        // ===== SIDE PANEL =====
        JPanel sideWrapper = new JPanel(new GridBagLayout());
        sideWrapper.setBackground(new Color(222, 208, 187));

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(245, 240, 235));
        sidePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 6));
        sidePanel.setPreferredSize(new Dimension(320, 420));

        selectedTableLabel = new JLabel("Table Selected: None");
        selectedTableLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectedTableLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        statusLabel = new JLabel("Status: ");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        JButton updateBtn = createRoundedButton("Update Status");
        JButton orderBtn = createRoundedButton("Manage Order");
        JButton cancelBtn = createRoundedButton("Cancel");

        updateBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        orderBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension btnSize = new Dimension(160, 60);

        updateBtn.setPreferredSize(btnSize);
        updateBtn.setMinimumSize(btnSize);
        updateBtn.setMaximumSize(btnSize);

        orderBtn.setPreferredSize(btnSize);
        orderBtn.setMinimumSize(btnSize);
        orderBtn.setMaximumSize(btnSize);

        cancelBtn.setPreferredSize(btnSize);
        cancelBtn.setMinimumSize(btnSize);
        cancelBtn.setMaximumSize(btnSize);

        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(selectedTableLabel);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(statusLabel);
        sidePanel.add(Box.createVerticalStrut(40));
        sidePanel.add(updateBtn);
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(orderBtn);
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(cancelBtn);

        sideWrapper.add(sidePanel);

        // ===== ACTIONS =====
        orderBtn.addActionListener(e -> {

            if (selectedTableButton == null) {
                showErrorPopup("Please select a table first.");
                return;
            }

            String currentStatus = tableStatuses.get(selectedTableButton);

            if ("Dirty".equals(currentStatus)) {
                showErrorPopup("Cannot manage order for a dirty table.");
                return;
            }

            // GET TABLE NAME + STATUS
            String tableName = selectedTableLabel.getText().replace("Table Selected: ", "");
            String status = currentStatus;

            // PASS INTO NEXT SCREEN
            new ManageOrderFrame(tableName, status);
            dispose();
        });

        cancelBtn.addActionListener(e -> {
            selectedTableButton = null;
            selectedTableLabel.setText("Table Selected: None");
            statusLabel.setText("Status: ");
        });

        updateBtn.addActionListener(e -> {
            if (selectedTableButton == null) {
                showErrorPopup("Please select a table first.");
                return;
            }

            String currentStatus = tableStatuses.get(selectedTableButton);

            if ("Occupied".equals(currentStatus)) {
                tableStatuses.put(selectedTableButton, "Dirty");
                selectedTableButton.setBackground(DIRTY);
                statusLabel.setText("Status: Dirty");
            } else {
                showErrorPopup("Only occupied tables can be marked dirty.");
            }
        });

        // ===== BUILD =====
        main.add(header, BorderLayout.NORTH);
        main.add(tablePanel, BorderLayout.CENTER);
        main.add(sideWrapper, BorderLayout.EAST);

        add(main);
        setVisible(true);
    }

    public WaitStaffFloorFrame(String tableName, String status) {
        this();

        selectedTableLabel.setText("Table Selected: " + tableName);
        statusLabel.setText("Status: " + status);
    }

    // ===== TABLE METHOD =====
    private void addTable(JPanel panel, String name, String status, int x, int y, GridBagConstraints gbc) {
        JButton btn = new JButton(name);
        Dimension size = new Dimension(85, 75);

        btn.setFont(new Font("SansSerif", Font.BOLD, 18));

        btn.setPreferredSize(size);
        btn.setMinimumSize(size);
        btn.setMaximumSize(size);
        btn.setBackground(getColorForStatus(status));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);

        tableStatuses.put(btn, status);
        tableNames.put(btn, name);

        btn.addActionListener(e -> {
            selectedTableButton = btn;

            selectedTableLabel.setText("Table Selected: " + name);
            statusLabel.setText("Status: " + tableStatuses.get(btn));
        });

        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(btn, gbc);
    }

    private Color getColorForStatus(String status) {
        switch (status) {
            case "Open":
                return OPEN;
            case "Occupied":
                return OCCUPIED;
            case "Dirty":
                return DIRTY;
            default:
                return OPEN;
        }
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

    private ImageIcon loadIcon(String fileName, int w, int h) {
        ImageIcon icon = new ImageIcon("src/icons/" + fileName);
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
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
