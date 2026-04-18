package ui;

import models.Employee;
import models.EmployeeFileHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ManageEmployeesFrame extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private ArrayList<Employee> employees = EmployeeFileHandler.loadEmployees();


    public ManageEmployeesFrame() {

        setTitle("Manage Employees - J's Corner Restaurant");
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

        JLabel title = new JLabel("Manage Employees");
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
                "MANAGE MENU",
                "MANAGE INVENTORY",
                "GENERATE REPORTS"
        };

        for (String item : menuItems) {
            JButton btn = createSidebarButton(item);

            // DASHBOARD BUTTON
            if (item.equals("DASHBOARD")) {
                btn.addActionListener(e -> {
                    new ManagerFrame();
                    dispose();
                });
            }

            sidebar.add(Box.createVerticalStrut(20));
            sidebar.add(btn);
        }

        employees = EmployeeFileHandler.loadEmployees();

        // ===== MAIN CONTENT =====
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(new Color(220, 205, 185));
        content.setBorder(new EmptyBorder(20, 30, 20, 30));

        // TOP BAR
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 15));
        topBar.setOpaque(false);

        JButton addBtn = createRoundedButton("+ Add Employee");
        JButton editBtn = createRoundedButton("Edit");
        JButton deleteBtn = createRoundedButton("Delete");

        Dimension btnSize = new Dimension(180, 50);

        addBtn.setPreferredSize(btnSize);
        editBtn.setPreferredSize(btnSize);
        deleteBtn.setPreferredSize(btnSize);

        addBtn.setMinimumSize(btnSize);
        editBtn.setMinimumSize(btnSize);
        deleteBtn.setMinimumSize(btnSize);

        addBtn.setMaximumSize(btnSize);
        editBtn.setMaximumSize(btnSize);
        deleteBtn.setMaximumSize(btnSize);

        topBar.add(addBtn);
        topBar.add(editBtn);
        topBar.add(deleteBtn);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new Object[]{"Name", "Role", "Username", "Tables"}, 0
        );

        table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("SansSerif", Font.PLAIN, 15));
        loadTableData();

        // CENTER TEXT
        javax.swing.table.DefaultTableCellRenderer center =
                new javax.swing.table.DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        // HEADER STYLE
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(145, 26, 26));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(table);

        // ===== BUTTON ACTIONS =====

        // ADD
        addBtn.addActionListener(e -> {

            EmployeeFormFrame form = new EmployeeFormFrame(model, -1, employees);

            form.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {

                    employees = EmployeeFileHandler.loadEmployees();
                    loadTableData();
                }
            });
        });

        // EDIT
        editBtn.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row != -1) {

                EmployeeFormFrame form = new EmployeeFormFrame(model, row, employees);

                form.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {

                        employees = EmployeeFileHandler.loadEmployees();
                        loadTableData();
                    }
                });
            }
        });

        // DELETE
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                employees.remove(row);
                EmployeeFileHandler.saveEmployees(employees);
                loadTableData();
            }
        });

        content.add(topBar, BorderLayout.NORTH);
        content.add(scroll, BorderLayout.CENTER);

        // ===== ADD TO FRAME =====
        add(header, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);

        setVisible(true);
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
        btn.setBackground(new Color(120, 20, 20));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        applyHoverEffect(btn, normal, hover);

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

    private void applyHoverEffect(JButton button, Color normal, Color hover) {

        button.setBackground(normal);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hover);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normal);
            }
        });
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

    private void loadTableData() {
        model.setRowCount(0); // clear table

        for (Employee e : employees) {
            model.addRow(new Object[]{
                    e.getName(),
                    e.getRole(),
                    e.getId(),
                    e.getAssignedTables()
            });
        }
    }
}
