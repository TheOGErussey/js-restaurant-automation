package ui;

import models.Employee;
import models.EmployeeFileHandler;
import models.ActivityLogger;
import models.Session;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EmployeeFormFrame extends JFrame {

    private java.util.ArrayList<Employee> employees;

    public EmployeeFormFrame(DefaultTableModel model, int row, java.util.ArrayList<Employee> employees) {

        this.employees = employees;
        boolean isEdit = row >= 0;

        setTitle(isEdit ? "Edit Employee" : "Add Employee");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(145, 26, 26));
        header.setPreferredSize(new Dimension(1280, 80));

        JPanel leftHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 20));
        leftHeader.setOpaque(false);

        JLabel icon = new JLabel(loadIcon("Fork.png", 40, 40));

        JLabel title = new JLabel(isEdit ? "Edit Employee" : "Add Employee");
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
        logout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                showLogoutPopup();
            }
        });
        logout.setForeground(Color.WHITE);
        logout.setFont(new Font("SansSerif", Font.BOLD, 14));

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
                "DASHBOARD", "REFUND REQUESTS", "MANAGE EMPLOYEES",
                "MANAGE MENU", "MANAGE INVENTORY", "GENERATE REPORTS"
        };

        for (String item : menuItems) {
            JButton btn = createSidebarButton(item);

            if (item.equals("DASHBOARD")) {
                btn.addActionListener(e -> {
                    new ManagerFrame();
                    dispose();
                });
            }

            sidebar.add(Box.createVerticalStrut(20));
            sidebar.add(btn);
        }

        // ===== MAIN CONTENT =====
        JPanel content = new JPanel();
        content.setBackground(new Color(220, 205, 185));
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel sectionTitle = new JLabel("Employee Details");
        sectionTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        sectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(Box.createVerticalStrut(20));
        content.add(sectionTitle);
        content.add(Box.createVerticalStrut(20));

        // ===== CARD =====
        JPanel card = new JPanel();
        card.setBackground(new Color(245, 240, 235));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(30, 50, 30, 50));
        card.setMaximumSize(new Dimension(650, 500));
        card.setPreferredSize(new Dimension(650, 500));

        // ===== FIELDS =====
        JTextField nameField = new JTextField();
        JTextField usernameField = new JTextField();
        JTextField tablesField = new JTextField();

        String[] roles = {"Waiter", "Cook", "Busboy", "Manager"};
        JComboBox<String> roleBox = new JComboBox<>(roles);

        // PREFILL IF EDIT
        if (isEdit) {
            nameField.setText((String) model.getValueAt(row, 0));
            roleBox.setSelectedItem(model.getValueAt(row, 1));
            usernameField.setText((String) model.getValueAt(row, 2));
            tablesField.setText((String) model.getValueAt(row, 3));
        }

        card.add(createField("Name:", nameField));
        card.add(createField("Role:", roleBox));
        card.add(createField("Username:", usernameField));
        card.add(createField("Tables:", tablesField));

        card.add(Box.createVerticalStrut(20));

        // ===== BUTTONS =====
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        btnPanel.setOpaque(false);

        JButton cancelBtn = createRoundedButton("Cancel");
        JButton saveBtn = createRoundedButton("Save");

        btnPanel.add(cancelBtn);
        btnPanel.add(saveBtn);

        card.add(btnPanel);

        content.add(card);

        // ===== ACTIONS =====
        cancelBtn.addActionListener(e -> dispose());

        saveBtn.addActionListener(e -> {

            String name = nameField.getText().trim();
            String role = (String) roleBox.getSelectedItem();
            String username = usernameField.getText().trim();
            String tables = tablesField.getText().trim();

            // ===== EMPTY FIELD CHECK =====
            if (name.isEmpty() || username.isEmpty()) {
                showStyledPopup("Error", "Please fill in all required fields.");
                return;
            }

            // ===== TABLE RULE =====
            if (!role.equals("Waiter") && !tables.isEmpty()) {
                showStyledPopup("Error", "Only waiters can have assigned tables.");
                return;
            }

            // ===== AUTO CLEAR TABLES IF NOT WAITER =====
            if (!role.equals("Waiter")) {
                tables = "-";
            }

            // ===== SAVE DATA =====
            if (isEdit) {
                Employee oldEmp = employees.get(row);

                String password = oldEmp.getPassword();
                boolean authorized = oldEmp.isAuthorized();

                Employee updated = new Employee(
                        name,
                        username,
                        role,
                        tables,
                        authorized,
                        password
                );

                employees.set(row, updated);
                ActivityLogger.log("Employee " + name + " was EDITED");

            } else {

                Employee newEmp = new Employee(
                        name,
                        username,
                        role,
                        tables,
                        false,
                        "123"
                );

                employees.add(newEmp);
                ActivityLogger.log("Employee " + name + " was ADDED");
            }

            EmployeeFileHandler.saveEmployees(employees);
            dispose();
        });

        add(header, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);

        setVisible(true);
    }

    // ===== FIELD STYLE =====
    private JPanel createField(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));

        field.setPreferredSize(new Dimension(300, 40));

        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        panel.add(Box.createVerticalStrut(15), BorderLayout.SOUTH);

        return panel;
    }

    // ===== BUTTON STYLE =====
    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(145, 26, 26));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setPreferredSize(new Dimension(180, 55));

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

        // HOVER EFFECT
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(hover);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(normal);
            }
        });

        return btn;
    }

    private ImageIcon loadIcon(String fileName, int w, int h) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icons/" + fileName));
            Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return null;
        }
    }

    private void showStyledPopup(String titleText, String messageText) {

        JDialog dialog = new JDialog(this, true);
        dialog.setSize(420, 220);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);

        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(145, 26, 26));
        header.setPreferredSize(new Dimension(420, 50));

        JLabel title = new JLabel(titleText);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setBorder(new EmptyBorder(0, 15, 0, 0));

        // WARNING ICON
        JLabel icon = new JLabel(loadIcon("Warning.png", 24, 24));
        icon.setBorder(new EmptyBorder(0, 0, 0, 15));

        header.add(title, BorderLayout.WEST);
        header.add(icon, BorderLayout.EAST);

        // ===== BODY =====
        JPanel body = new JPanel();
        body.setBackground(new Color(245, 240, 235));
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        JLabel message = new JLabel(messageText);
        message.setFont(new Font("SansSerif", Font.PLAIN, 16));
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton okBtn = createRoundedButton("OK");
        okBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        okBtn.setMaximumSize(new Dimension(180, 55));

        okBtn.addActionListener(e -> dialog.dispose());

        body.add(Box.createVerticalStrut(30));
        body.add(message);
        body.add(Box.createVerticalStrut(25));
        body.add(okBtn);
        body.add(Box.createVerticalStrut(20));

        main.add(header, BorderLayout.NORTH);
        main.add(body, BorderLayout.CENTER);

        dialog.add(main);
        dialog.setVisible(true);
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
            Employee user = Session.getUser();

            if (user != null) {
                ActivityLogger.log(user.getName() + " logged out");
            }

            Session.clear();

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
