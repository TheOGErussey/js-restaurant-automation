package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ClockInFrame extends JFrame {

    private JLabel statusLabel;
    private boolean isClockedIn = false;

    public ClockInFrame() {

        setTitle("Wait Staff - J's Corner Restaurant");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout());

        // ===== HEADER =====
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 30));
        header.setBackground(new Color(128, 0, 0));
        header.setPreferredSize(new Dimension(1280, 120));

        JLabel logo = new JLabel(loadIcon("Fork.png", 55, 55));

        JLabel title = new JLabel("J’s Corner Restaurant");
        title.setFont(new Font("Serif", Font.BOLD, 40));
        title.setForeground(new Color(245, 230, 211));

        header.add(logo);
        header.add(title);

        // ===== BACKGROUND =====
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(new Color(222, 208, 187));

        // ===== CARD =====
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(420, 420));
        card.setBackground(new Color(245, 240, 235));
        card.setBorder(BorderFactory.createLineBorder(new Color(60, 40, 20), 10));

        // ===== CARD HEADER =====
        JPanel cardHeader = new JPanel(new GridBagLayout());
        cardHeader.setBackground(new Color(145, 26, 26));
        cardHeader.setPreferredSize(new Dimension(420, 80));

        JLabel welcomeLabel = new JLabel("Welcome, Wait Staff");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.WHITE);

        cardHeader.add(welcomeLabel);

        // ===== FORM AREA =====
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(new Color(245, 240, 235));
        form.setBorder(new EmptyBorder(30, 30, 30, 30));

        // ===== STATUS ROW =====
        JPanel statusRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        statusRow.setOpaque(false);

        JLabel clockIcon = new JLabel(loadIcon("Clock.png", 30, 30));

        statusLabel = new JLabel("Current Status: Not Clocked In");
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        statusRow.add(clockIcon);
        statusRow.add(statusLabel);

        // ===== BUTTON =====
        JButton clockButton = createRoundedButton("Clock In");
        JButton backButton = createRoundedButton("Back");
        clockButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // spacing
        form.add(Box.createVerticalStrut(40));
        form.add(statusRow);
        form.add(Box.createVerticalStrut(125));

        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonWrapper.setOpaque(false);

        JPanel buttonRow = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonRow.setOpaque(false);
        buttonRow.setPreferredSize(new Dimension(340, 60)); // fits inside card

        buttonRow.add(backButton);
        buttonRow.add(clockButton);

        buttonWrapper.add(buttonRow);

        form.add(buttonWrapper);

        form.add(Box.createVerticalGlue());

        // ===== BUILD =====
        card.add(cardHeader, BorderLayout.NORTH);
        card.add(form, BorderLayout.CENTER);

        background.add(card);

        main.add(header, BorderLayout.NORTH);
        main.add(background, BorderLayout.CENTER);

        add(main);

        // ===== ACTION =====
        clockButton.addActionListener(e -> {
            if (!isClockedIn) {
                isClockedIn = true;
                statusLabel.setText("Current Status: Clocked In");
                clockButton.setText("Clock In");

                showSuccessPopup();

            } else {
                isClockedIn = false;
                statusLabel.setText("Current Status: Not Clocked In");
                clockButton.setText("Clock In");
            }
        });

        backButton.addActionListener(e -> {
            new LoginFrame();
        });

        setVisible(true);
    }

    // ===== ICON LOADER =====
    private ImageIcon loadIcon(String fileName, int w, int h) {
        ImageIcon icon = new ImageIcon("src/icons/" + fileName);
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
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

        button.setPreferredSize(new Dimension(160, 60));
        button.setMinimumSize(new Dimension(160, 60));
        button.setMaximumSize(new Dimension(160, 60));

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

    private void showSuccessPopup() {

        JDialog dialog = new JDialog(this, true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);

        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(145, 26, 26));
        header.setPreferredSize(new Dimension(400, 60));

        JLabel title = new JLabel("Success");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setBorder(new EmptyBorder(0, 15, 0, 0));

        JLabel checkIcon = new JLabel(loadIcon("Check.png", 30, 30));
        checkIcon.setBorder(new EmptyBorder(0, 0, 0, 15));

        header.add(title, BorderLayout.WEST);
        header.add(checkIcon, BorderLayout.EAST);

        // ===== BODY =====
        JPanel body = new JPanel();
        body.setBackground(new Color(245, 240, 235));
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        JLabel message = new JLabel("Clock in successful.");
        message.setFont(new Font("SansSerif", Font.PLAIN, 18));
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton okButton = createRoundedButton("OK");
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        body.add(Box.createVerticalStrut(30));
        body.add(message);
        body.add(Box.createVerticalStrut(50));
        body.add(okButton);

        // ===== ACTION =====
        okButton.addActionListener(e -> {
            dialog.dispose();
            new WaitStaffFloorFrame();
        });

        // ===== BUILD =====
        main.add(header, BorderLayout.NORTH);
        main.add(body, BorderLayout.CENTER);

        dialog.add(main);
        dialog.setVisible(true);
    }
}
