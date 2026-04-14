package ui;

import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JFrame {

    public HomeFrame() {
        setTitle("J's Corner Restaurant");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout());

        // ===== HEADER =====
        JPanel header = new JPanel(new GridBagLayout());
        header.setBackground(new Color(128, 0, 0));
        header.setPreferredSize(new Dimension(1280, 120));

        JLabel logo = new JLabel(loadIcon("/icons/Fork.png", 55, 55));

        JLabel title = new JLabel("J’s Corner Restaurant");
        title.setFont(new Font("Serif", Font.BOLD, 40));
        title.setForeground(new Color(245, 230, 211));

        JPanel titleGroup = new JPanel();
        titleGroup.setOpaque(false);
        titleGroup.add(logo);
        titleGroup.add(Box.createHorizontalStrut(15));
        titleGroup.add(title);

        header.add(titleGroup);

        // ===== CENTER =====
        JPanel center = new JPanel();
        center.setBackground(new Color(222, 208, 187));
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        center.add(Box.createVerticalStrut(60));

        // ===== ADDRESS =====
        JLabel address = new JLabel("680 Arntson Dr., Marietta, GA");
        address.setFont(new Font("Serif", Font.PLAIN, 28));
        address.setIcon(loadIcon("/icons/Pin.png", 60, 70));
        address.setIconTextGap(20);
        address.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== HOURS =====
        JLabel hours = new JLabel("Monday - Saturday: 11AM - 9:30PM");
        hours.setFont(new Font("Serif", Font.PLAIN, 28));
        hours.setIcon(loadIcon("/icons/Clock.png", 60, 60));
        hours.setIconTextGap(20);
        hours.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== PHONE =====
        JLabel phone = new JLabel("(470) 555-1212");
        phone.setFont(new Font("Serif", Font.PLAIN, 28));
        phone.setIcon(loadIcon("/icons/Phone.png", 49, 49));
        phone.setIconTextGap(20);
        phone.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== BUTTONS =====
        JButton loginButton = createRoundedButton("Login");
        JButton exitButton = createRoundedButton("Exit");
        loginButton.setPreferredSize(new Dimension(200, 70));
        exitButton.setPreferredSize(new Dimension(200, 70));

        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== ADD COMPONENTS =====
        center.add(address);
        center.add(Box.createVerticalStrut(5));

        center.add(hours);
        center.add(Box.createVerticalStrut(5));

        center.add(phone);

        // move buttons DOWN
        center.add(Box.createVerticalStrut(70));

        center.add(loginButton);
        center.add(Box.createVerticalStrut(25));
        center.add(exitButton);

        main.add(header, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);

        add(main);

        // ===== BUTTON ACTIONS =====
        loginButton.addActionListener(e -> {
            new LoginFrame(); // open login screen
            dispose();        // close home screen
        });

        exitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    // ===== ICON LOADER =====
    private ImageIcon loadIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // ===== ROUNDED BUTTON =====
    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);

        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(145, 26, 26));

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);

        // FORCE SIZE HERE
        button.setPreferredSize(new Dimension(200, 65));
        button.setMinimumSize(new Dimension(200, 65));
        button.setMaximumSize(new Dimension(200, 65));

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
