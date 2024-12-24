package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Reception extends JFrame {
    private JPanel leftPanel;
    private JPanel rightPanel;

    // Declaring the function that are required
    Reception() {
        setupPanels();
        setupHeaderAndLogo();
        setupFeatureSection();
        setupButtons();
        setupMainFrame();
    }

    // Making the panel function which contain left and right panel
    private void setupPanels() {
        leftPanel = createPanel(0, 0, 400, 1525, Color.WHITE);
        rightPanel = createPanel(400, 0, 1525, 1525, new Color(109, 164, 170));
        add(leftPanel);
        add(rightPanel);
    }
    // Making the panel function which contain left and right panel
    private JPanel createPanel(int x, int y, int width, int height, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(x, y, width, height);
        panel.setBackground(color);
        return panel;
    }

    // Setting up the Header and logo of Management System
    private void setupHeaderAndLogo() {
        addStyledLabel("   Hospital Management System", 20, 20, 350, 40,
                new Font("Tahoma", Font.BOLD, 20), Color.white, new Color(72, 123, 191));

        addImage("icon/Ambulance + sign.jpg", 50, 40, 250, 200);
    }

    // Setting up the Features of Management System
    private void setupFeatureSection() {
        addStyledLabel("   Features", 20, 250, 300, 40,
                new Font("Tahoma", Font.BOLD, 20), Color.white, new Color(72, 123, 191));

        addStyledLabel("   Patient", 8, 300, 75, 40,
                new Font("Tahoma", Font.PLAIN, 15), Color.GRAY, Color.WHITE);

        addStyledLabel("   Department", 8, 425, 100, 40,
                new Font("Tahoma", Font.PLAIN, 15), Color.GRAY, Color.WHITE);
    }


    private void addStyledLabel(String text, int x, int y, int width, int height,
                                Font font, Color foreground, Color background) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setFont(font);
        label.setForeground(foreground);
        label.setBackground(background);
        label.setOpaque(true);
        leftPanel.add(label);
    }

    // Add Image Function
    private void addImage(String path, int x, int y, int width, int height) {
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(path));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        JLabel label = new JLabel(new ImageIcon(img));
        label.setBounds(x, y, width, height);
        leftPanel.add(label);
    }

    // Add Button Function
    private void setupButtons() {
        // Patient Section Buttons
        createStyledButton("Add new Patient", "icon/Add patient button.png", 15, 340, e -> new NEW_PATIENT());
        createStyledButton("Patient Info", "icon/Patient info button.png", 205, 340, e -> {});
        createStyledButton("Update Patient Info", "icon/Update patient button.png", 15, 380, e -> {});
        createStyledButton("Patient Discharge", "icon/Patient discharge button.png", 205, 380, e -> {});

        // Department Section Buttons
        createStyledButton("Search Room", "icon/Search room button.png", 15, 468, e -> {});
        createStyledButton("Available Room", "icon/Available room button.png", 205, 468, e -> new RoomAvailability()
        );
        createStyledButton("Department Info", "icon/Department Info button.png", 15, 508, e -> {});
        createStyledButton("Employee Info", "icon/Emplyoee info button.png", 205, 508, e -> {});
        createStyledButton("Ambulance Detail", "icon/Ambulance detail button.png", 15, 550, e -> {});

        // Logout Button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(15, 725, 180, 30);
        logoutBtn.setBackground(Color.RED);
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 12));
        logoutBtn.addActionListener(e -> {});

        // Add hover effect to logout button
        logoutBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logoutBtn.setBackground(new Color(178, 34, 34));  // Darker red on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logoutBtn.setBackground(Color.RED);  // Original red color
            }
        });
        leftPanel.add(logoutBtn);
    }

    private void createStyledButton(String text, String iconPath, int x, int y,
                                    java.awt.event.ActionListener action) {
        AnimatedButton button = new AnimatedButton(text, iconPath);
        button.setBounds(x, y, 180, 30);
        button.addActionListener(action);
        leftPanel.add(button);
    }

    private void addHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255, 223, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.YELLOW);
            }
        });
    }

    private void setupMainFrame() {
        setSize(1950, 1090);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Reception();
    }
}