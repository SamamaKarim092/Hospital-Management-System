package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HeaderPanel extends JPanel {
    private JLabel timeLabel;
    private JLabel dateLabel;
    private JLabel userLabel;
    private Timer timer;
    private String userName;

    public HeaderPanel(String userName) {
        this.userName = userName;
        setLayout(null);
        setBackground(Color.WHITE);
        setBounds(420, 20, 1000, 50);
        setupComponents();
        startClock();
    }

    private void setupComponents() {
        // Welcome text
        JLabel welcomeLabel = new JLabel("Welcome to Hospital Management System");
        welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(72, 123, 191));
        welcomeLabel.setBounds(20, 5, 600, 40);
        add(welcomeLabel);

        // User name
        userLabel = new JLabel("User: " + userName);
        userLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        userLabel.setForeground(new Color(72, 123, 191));
        userLabel.setBounds(550, 15, 200, 20);
        add(userLabel);

        // Time
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        timeLabel.setForeground(new Color(72, 123, 191));
        timeLabel.setBounds(900, 5, 150, 20);
        add(timeLabel);

        // Date
        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        dateLabel.setForeground(new Color(72, 123, 191));
        dateLabel.setBounds(900, 25, 150, 20);
        add(dateLabel);
    }

    private void startClock() {
        updateDateTime(); // Initial update
        timer = new Timer(1000, e -> updateDateTime());
        timer.start();
    }

    private void updateDateTime() {
        LocalDateTime now = LocalDateTime.now();
        timeLabel.setText(now.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        dateLabel.setText(now.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        if (timer != null) {
            timer.stop();
        }
    }
}