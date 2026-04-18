package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import models.TableInfo;
import models.TableManager;
import models.TableRegistry;

public class ManagerFloorFrame extends JFrame {

    private JLabel selectedTableLabel;
    private JLabel statusLabel;
    private JLabel selectedTablesLabel;

    private JButton selectedTableButton = null;

    private final Color OPEN = new Color(110, 200, 80);
    private final Color OCCUPIED = new Color(240, 200, 80);
    private final Color DIRTY = new Color(230, 50, 50);

    private final Map<JButton, String> tableStatuses = new HashMap<>();
    private final Map<JButton, String> tableNames = new HashMap<>();
    private final Map<JButton, TableInfo> tableMap = new HashMap<>();

    private ArrayList<TableInfo> selectedTables = new ArrayList<>();

    public ManagerFloorFrame() {
        setTitle("Manager - J's Corner Restaurant");
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
        JLabel title = new JLabel("Rearrange Tables");
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(new Color(245, 230, 211));

        leftHeader.add(logo);
        leftHeader.add(title);

        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 25));
        rightHeader.setOpaque(false);

        JLabel welcome = new JLabel("Welcome, Manager");
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


        selectedTablesLabel = new JLabel("Selected Table/s: None");
        selectedTablesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectedTablesLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        statusLabel = new JLabel("Status: ");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        JButton addSeatsBtn = createRoundedButton("Add Seats");
        JButton joinTablesBtn = createRoundedButton("Join Tables");
        JButton cancelBtn = createRoundedButton("Cancel");

        addSeatsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        joinTablesBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension btnSize = new Dimension(160, 60);

        addSeatsBtn.setPreferredSize(btnSize);
        addSeatsBtn.setMinimumSize(btnSize);
        addSeatsBtn.setMaximumSize(btnSize);

        joinTablesBtn.setPreferredSize(btnSize);
        joinTablesBtn.setMinimumSize(btnSize);
        joinTablesBtn.setMaximumSize(btnSize);

        cancelBtn.setPreferredSize(btnSize);
        cancelBtn.setMinimumSize(btnSize);
        cancelBtn.setMaximumSize(btnSize);

        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(selectedTablesLabel);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(statusLabel);
        sidePanel.add(Box.createVerticalStrut(40));
        sidePanel.add(addSeatsBtn);
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(joinTablesBtn);
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(cancelBtn);

        sideWrapper.add(sidePanel);

        // ===== ACTIONS =====
        addSeatsBtn.addActionListener(e -> {

            if (selectedTables.size() != 1) {
                showErrorPopup("Select one table.");
                return;
            }

            showAddSeatsPopup();
        });

        cancelBtn.addActionListener(e -> {

            selectedTables.clear();

            // remove highlights from all buttons
            for (JButton b : tableMap.keySet()) {
                b.setBorder(null);
            }

            updateSelectedTablesLabel();
        });

        joinTablesBtn.addActionListener(e -> {

            if (selectedTables.size() < 2) {
                showErrorPopup("Select at least two tables.");
                return;
            }

            TableInfo primaryTable = selectedTables.get(0);

            for (int i = 1; i < selectedTables.size(); i++) {
                primaryTable.joinWith(selectedTables.get(i));
            }

            showJoinTablesSuccessPopup();
            selectedTables.clear();
            updateSelectedTablesLabel();
            refreshTableButtons();

        });

        // ===== BUILD =====
        main.add(header, BorderLayout.NORTH);
        main.add(tablePanel, BorderLayout.CENTER);
        main.add(sideWrapper, BorderLayout.EAST);

        add(main);
        setVisible(true);
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

        // ===== CREATE TABLE OBJECT =====
        TableInfo table = new TableInfo(name);
        table.setStatus(status);

        TableRegistry.tables.add(table);
        TableManager.tables.add(table);
        tableMap.put(btn, table);

        // ===== KEEP EXISTING MAPS =====
        tableStatuses.put(btn, status);
        tableNames.put(btn, name);

        btn.addActionListener(e -> {

            TableInfo selectedTable = tableMap.get(btn);

            if (selectedTables.contains(selectedTable)) {
                selectedTables.remove(selectedTable);
                btn.setBorder(null);
            } else {
                selectedTables.add(selectedTable);
                btn.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
            }

            updateSelectedTablesLabel();
        });

        // ===== ADD TO PANEL =====
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

    private void showAddSeatsSuccessPopup() {

        JDialog dialog = new JDialog(this, true);
        dialog.setSize(420, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(145, 26, 26));
        header.setPreferredSize(new Dimension(420, 55));

        JLabel title = new JLabel("Success");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setBorder(new EmptyBorder(0, 15, 0, 0));

        JLabel icon = new JLabel(loadIcon("Check.png", 24, 24));
        icon.setBorder(new EmptyBorder(0, 0, 0, 15));

        header.add(title, BorderLayout.WEST);
        header.add(icon, BorderLayout.EAST);

        // ===== BODY =====
        JPanel body = new JPanel();
        body.setBackground(new Color(245, 240, 235));
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        JLabel message = new JLabel("Seats added successfully.");
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

    private void showJoinTablesSuccessPopup() {

        JDialog dialog = new JDialog(this, true);
        dialog.setSize(420, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(145, 26, 26));
        header.setPreferredSize(new Dimension(420, 55));

        JLabel title = new JLabel("Success");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setBorder(new EmptyBorder(0, 15, 0, 0));

        JLabel icon = new JLabel(loadIcon("Check.png", 24, 24));
        icon.setBorder(new EmptyBorder(0, 0, 0, 15));

        header.add(title, BorderLayout.WEST);
        header.add(icon, BorderLayout.EAST);

        // ===== BODY =====
        JPanel body = new JPanel();
        body.setBackground(new Color(245, 240, 235));
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        JLabel message = new JLabel("Tables joined successfully.");
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

    private void updateSelectedTablesLabel() {
        if (selectedTables.isEmpty()) {
            selectedTablesLabel.setText("Selected Tables: None");
            return;
        }

        StringBuilder sb = new StringBuilder("Selected Tables: ");
        for (int i = 0; i < selectedTables.size(); i++) {
            sb.append(selectedTables.get(i).getTableName());
            if (i < selectedTables.size() - 1) {
                sb.append(", ");
            }
        }

        selectedTablesLabel.setText(sb.toString());
    }

    private void refreshTableButtons() {

        for (Map.Entry<JButton, TableInfo> entry : tableMap.entrySet()) {

            JButton btn = entry.getKey();
            TableInfo table = entry.getValue();

            // Update name
            btn.setText(table.getTableName());

            // Update color
            btn.setBackground(getColorForStatus(table.getStatus()));
        }
    }

    private void showAddSeatsPopup() {

        JDialog dialog = new JDialog(this, true);
        dialog.setSize(420, 260);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);

        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(145, 26, 26));
        header.setPreferredSize(new Dimension(420, 55));

        JLabel title = new JLabel("Add Seats");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setBorder(new EmptyBorder(0, 15, 0, 0));

        JLabel icon = new JLabel(loadIcon("Fork.png", 24, 24));
        icon.setBorder(new EmptyBorder(0, 0, 0, 15));

        header.add(title, BorderLayout.WEST);
        header.add(icon, BorderLayout.EAST);

        // ===== BODY =====
        JPanel body = new JPanel();
        body.setBackground(new Color(245, 240, 235));
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Enter number of seats:");
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField input = new JTextField();
        input.setMaximumSize(new Dimension(200, 35));
        input.setFont(new Font("SansSerif", Font.PLAIN, 18));
        input.setHorizontalAlignment(JTextField.CENTER);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonRow.setOpaque(false);

        JButton cancelBtn = createRoundedButton("Cancel");
        JButton confirmBtn = createRoundedButton("Add");

        Dimension btnSize = new Dimension(140, 50);
        cancelBtn.setPreferredSize(btnSize);
        confirmBtn.setPreferredSize(btnSize);

        // ===== ACTIONS =====
        cancelBtn.addActionListener(e -> dialog.dispose());

        confirmBtn.addActionListener(e -> {
            try {
                int seats = Integer.parseInt(input.getText());

                if (seats <= 0) {
                    showErrorPopup("Seats must be greater than 0.");
                    return;
                }

                selectedTables.get(0).addSeats(seats);

                dialog.dispose();
                showAddSeatsSuccessPopup();

            } catch (Exception ex) {
                showErrorPopup("Invalid number.");
            }
        });

        buttonRow.add(cancelBtn);
        buttonRow.add(confirmBtn);

        body.add(Box.createVerticalStrut(20));
        body.add(label);
        body.add(Box.createVerticalStrut(15));
        body.add(input);
        body.add(Box.createVerticalStrut(25));
        body.add(buttonRow);
        body.add(Box.createVerticalStrut(20));

        main.add(header, BorderLayout.NORTH);
        main.add(body, BorderLayout.CENTER);

        dialog.add(main);
        dialog.setVisible(true);
    }
}