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

        JLabel title = new JLabel("Create Order", loadIcon("Fork.png", 45, 45), JLabel.LEFT);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(new Color(245, 230, 211));

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

        // ===== LEFT PANEL (TABLE) =====
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(222, 208, 187));
        leftPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        leftPanel.setPreferredSize(new Dimension(420, 600));

        JLabel tableLabel = new JLabel("Table: " + tableName);
        tableLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        tableModel = new DefaultTableModel(
                new Object[]{"Qty", "Item", "Price"}, 0
        );

        JTable orderTable = new JTable(tableModel);
        orderTable.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setPreferredSize(new Dimension(360, 180));

        JTextArea notes = new JTextArea(6, 20);
        notes.setBorder(BorderFactory.createTitledBorder("Notes"));

        JButton cancelBtn = createRoundedButton("Cancel");
        JButton sendBtn = createRoundedButton("Send");

        Dimension buttonSize = new Dimension(140, 50);

        cancelBtn.setPreferredSize(buttonSize);
        cancelBtn.setMaximumSize(buttonSize);
        cancelBtn.setMinimumSize(buttonSize);

        sendBtn.setPreferredSize(buttonSize);
        sendBtn.setMaximumSize(buttonSize);
        sendBtn.setMinimumSize(buttonSize);

        // This damn button row
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        buttonRow.setMaximumSize(new Dimension(350, 70));
        buttonRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonRow.setOpaque(false);

        buttonRow.add(cancelBtn);  // LEFT
        buttonRow.add(sendBtn);    // RIGHT

        cancelBtn.addActionListener(e -> {
            new ManageOrderFrame(tableName, status);
            dispose();
        });

        sendBtn.addActionListener(e -> {

            if (tableModel.getRowCount() == 0) {
                showEmptyOrderError();
                return;
            }

            showSendPopup();
        });

        tableLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        notes.setAlignmentX(Component.LEFT_ALIGNMENT);

        // scroll pane
        scrollPane.setPreferredSize(new Dimension(360, 220));
        scrollPane.setMaximumSize(new Dimension(360, 220));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane notesScroll = new JScrollPane(notes);
        notesScroll.setPreferredSize(new Dimension(360, 180));
        notesScroll.setMaximumSize(new Dimension(360, 180));
        notesScroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftPanel.add(tableLabel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(scrollPane);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(notesScroll);
        leftPanel.add(Box.createVerticalStrut(60));
        leftPanel.add(buttonRow);

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
            btn.setFont(new Font("SansSerif", Font.BOLD, 18));

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
                items = new String[]{
                        "Chicken Nachos",
                        "Pork Nachos",
                        "Sliders (Pork/Chicken)",
                        "Catfish Bites",
                        "Fried Veggies"
                };
                break;

            case "Salads":
                items = new String[]{
                        "House Salad",
                        "Wedge Salad",
                        "Caesar Salad",
                        "Sweet Potato Chicken Salad"
                };
                break;

            case "Entrees":
                items = new String[]{
                        "Shrimp & Grits",
                        "Sweet Tea Fried Chicken",
                        "Caribbean Chicken",
                        "Grilled Pork Chops",
                        "New York Strip Steak",
                        "Seared Tuna",
                        "Captain Crunch Chicken Tenders",
                        "Grouper Fingers",
                        "Mac & Cheese Bar"
                };
                break;

            case "Sides":
                items = new String[]{
                        "Curly Fries",
                        "Wing Chips",
                        "Sweet Potato Fries",
                        "Cabbage Slaw",
                        "Cheese Grits",
                        "Mashed Potatoes",
                        "Mac & Cheese",
                        "Vegetables",
                        "Baked Beans"
                };
                break;

            case "Sandwiches":
                items = new String[]{
                        "Grilled Cheese",
                        "Chicken BLT&A",
                        "Philly",
                        "Club",
                        "Meatball Sub"
                };
                break;

            case "Burgers":
                items = new String[]{
                        "Bacon Cheeseburger",
                        "Carolina Burger",
                        "Portobello Burger",
                        "Vegan Boca Burger"
                };
                break;

            case "Beverages":
                items = new String[]{
                        "Tea",
                        "Coke",
                        "Diet Coke",
                        "Sprite",
                        "Water",
                        "Lemonade",
                        "Orange Juice"
                };
                break;

            default:
                items = new String[]{"No Items"};
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

        // Check if item already exists
        for (int i = 0; i < tableModel.getRowCount(); i++) {

            String existingItem = (String) tableModel.getValueAt(i, 1);

            if (existingItem.equals(item)) {

                int qty = (int) tableModel.getValueAt(i, 0);
                tableModel.setValueAt(qty + 1, i, 0);

                return; // stop here, don't add new row
            }
        }

        // If item not found, add new row
        tableModel.addRow(new Object[]{
                1,
                item,
                String.format("%.2f", price)
        });

        System.out.println("Order added: " + item);
    }

    // ===== PRICE =====
    private double getPrice(String item) {
        switch (item) {
            case "Chicken Nachos": return 8.50;
            case "Pork Nachos": return 8.50;
            case "Sliders (Pork/Chicken)": return 5.00;
            case "Catfish Bites": return 6.50;
            case "Fried Veggies": return 6.50;

            case "House Salad": return 7.50;
            case "Wedge Salad": return 7.50;
            case "Caesar Salad": return 7.50;
            case "Sweet Potato Chicken Salad": return 11.50;

            case "Shrimp & Grits": return 13.50;
            case "Sweet Tea Fried Chicken": return 11.50;
            case "Caribbean Chicken": return 11.50;
            case "Grilled Pork Chops": return 11.00;
            case "New York Strip Steak": return 17.00;
            case "Seared Tuna": return 15.00;
            case "Captain Crunch Chicken Tenders": return 11.50;
            case "Grouper Fingers": return 11.50;
            case "Mac & Cheese Bar": return 8.50;

            case "Curly Fries": return 2.50;
            case "Wing Chips": return 2.50;
            case "Sweet Potato Fries": return 2.50;
            case "Cabbage Slaw": return 2.50;
            case "Cheese Grits": return 2.50;
            case "Mashed Potatoes": return 2.50;
            case "Mac & Cheese": return 2.50;
            case "Vegetables": return 2.50;
            case "Baked Beans": return 2.50;

            case "Grilled Cheese": return 5.50;
            case "Chicken BLT&A": return 10.00;
            case "Philly": return 13.50;
            case "Club": return 10.00;
            case "Meatball Sub": return 10.00;

            case "Bacon Cheeseburger": return 11.00;
            case "Carolina Burger": return 11.00;
            case "Portobello Burger": return 8.50;
            case "Vegan Boca Burger": return 10.50;

            case "Tea": return 2.00;
            case "Coke": return 2.00;
            case "Diet Coke": return 2.00;
            case "Sprite": return 2.00;
            case "Water": return 2.00;
            case "Lemonade": return 2.00;
            case "Orange Juice": return 2.00;

            default: return 0.0;
        }
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

    private void showSendPopup() {

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

        JLabel message = new JLabel("Order sent to kitchen!");
        message.setFont(new Font("SansSerif", Font.PLAIN, 18));
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton okBtn = createRoundedButton("OK");

        Dimension size = new Dimension(140, 45);
        okBtn.setPreferredSize(size);
        okBtn.setMaximumSize(size);
        okBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        okBtn.addActionListener(e -> dialog.dispose());

        // spacing
        body.add(Box.createVerticalStrut(25));
        body.add(message);
        body.add(Box.createVerticalStrut(25));
        body.add(okBtn);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(body, BorderLayout.CENTER);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    private void showEmptyOrderError() {

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

        JLabel message = new JLabel("Cannot send an empty order.");
        message.setFont(new Font("SansSerif", Font.PLAIN, 18));
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton okBtn = createRoundedButton("OK");

        Dimension size = new Dimension(140, 45);
        okBtn.setPreferredSize(size);
        okBtn.setMaximumSize(size);
        okBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        okBtn.addActionListener(e -> dialog.dispose());

        // spacing
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
