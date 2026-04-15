package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CreateOrderFrame extends JFrame {

    private JPanel itemPanel;
    private DefaultTableModel tableModel;
    private String tableName;
    private String status;

    public CreateOrderFrame(String tableName, String status) {

        this.tableName = tableName;
        this.status = status;

        setTitle("Create Order - J's Corner Restaurant");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(128, 0, 0));
        header.setPreferredSize(new Dimension(1280, 100));

        JPanel leftHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 25));
        leftHeader.setOpaque(false);

        JLabel title = new JLabel("Create Order");
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(new Color(245, 230, 211));

        leftHeader.add(title);

        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 25));
        rightHeader.setOpaque(false);

        JLabel welcome = new JLabel("Welcome, Wait Staff");
        welcome.setForeground(Color.WHITE);

        JLabel logout = new JLabel("Logout");
        logout.setForeground(Color.WHITE);

        rightHeader.add(welcome);
        rightHeader.add(logout);

        header.add(leftHeader, BorderLayout.WEST);
        header.add(rightHeader, BorderLayout.EAST);

        // ===== LEFT PANEL (TABLE) =====
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(222, 208, 187));
        leftPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        leftPanel.setPreferredSize(new Dimension(350, 600));

        JLabel tableLabel = new JLabel("Table: " + tableName);
        tableLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        tableModel = new DefaultTableModel(
                new Object[]{"Qty", "Item", "Price"}, 0
        );

        JTable orderTable = new JTable(tableModel);
        orderTable.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setPreferredSize(new Dimension(300, 150));

        JTextArea notes = new JTextArea(6, 20);
        notes.setBorder(BorderFactory.createTitledBorder("Notes"));

        JButton backBtn = createRoundedButton("Cancel");
        backBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        backBtn.addActionListener(e -> {
            new ManageOrderFrame(tableName, status);
            dispose();
        });

        leftPanel.add(tableLabel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(scrollPane);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(notes);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(backBtn);

        // ===== CATEGORY PANEL =====
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
        categoryPanel.setBackground(new Color(222, 208, 187));
        categoryPanel.setPreferredSize(new Dimension(220, 600));

        String[] categories = {
                "Appetizers", "Salads", "Entrees",
                "Sides", "Sandwiches", "Burgers", "Beverages"
        };

        for (String cat : categories) {
            JButton btn = createRoundedButton(cat);

            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(180, 60));

            btn.addActionListener(e -> loadItems(cat));

            categoryPanel.add(Box.createVerticalStrut(15));
            categoryPanel.add(btn);
        }

        // ===== ITEM PANEL =====
        itemPanel = new JPanel(new GridLayout(3, 3, 25, 25));
        itemPanel.setBackground(new Color(245, 240, 235));
        itemPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // ===== CENTER WRAPPER =====
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.add(categoryPanel, BorderLayout.WEST);
        centerWrapper.add(itemPanel, BorderLayout.CENTER);

        // ===== BUILD =====
        main.add(header, BorderLayout.NORTH);
        main.add(leftPanel, BorderLayout.WEST);
        main.add(centerWrapper, BorderLayout.CENTER);

        add(main);
        setVisible(true);
    }

    // ===== LOAD ITEMS =====
    private void loadItems(String category) {

        itemPanel.removeAll();

        String[] items;

        switch (category) {
            case "Appetizers":
                items = new String[]{"Shrimp & Grits", "Caribbean Chicken"};
                break;

            case "Salads":
                items = new String[]{"Caesar Salad", "House Salad"};
                break;

            case "Entrees":
                items = new String[]{
                        "Sweet Tea Fried Chicken",
                        "New York Strip Steak",
                        "Grilled Porkchops",
                        "Seared Tuna",
                        "Captain Crunch Chicken",
                        "Grouper Fingers",
                        "Mac & Cheese Bar"
                };
                break;

            case "Beverages":
                items = new String[]{"Tea", "Soda", "Water"};
                break;

            default:
                items = new String[]{"Item 1", "Item 2"};
        }

        for (String item : items) {

            JButton btn = createRoundedButton(item);
            btn.setPreferredSize(new Dimension(160, 120));
            btn.setFont(new Font("SansSerif", Font.BOLD, 14));

            btn.addActionListener(e -> addItemToTable(item));

            itemPanel.add(btn);
        }

        itemPanel.revalidate();
        itemPanel.repaint();
    }

    // ===== ADD ITEM =====
    private void addItemToTable(String item) {

        double price = getPrice(item);

        tableModel.addRow(new Object[]{1, item, price});

        System.out.println("Order added: " + item);
    }

    // ===== PRICE =====
    private double getPrice(String item) {
        switch (item) {
            case "Shrimp & Grits": return 14.99;
            case "Sweet Tea Fried Chicken": return 12.99;
            case "New York Strip Steak": return 19.99;
            case "Seared Tuna": return 17.99;
            case "Mac & Cheese Bar": return 6.99;
            default: return 9.99;
        }
    }

    // ===== BUTTON STYLE =====
    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
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
}
