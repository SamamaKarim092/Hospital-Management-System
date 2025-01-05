package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.net.URL;

public class Login extends JFrame implements ActionListener {

    // Declaring the Fields
    JTextField textField;
    JPasswordField jPasswordField;
    JCheckBox showPassword;
    JButton b1, b2;
    private MediaPlayer mediaPlayer;

    Login() {
        // Initialize JavaFX toolkit
        JFXPanel fxPanel = new JFXPanel();

        // Text AdminPanel
        JLabel admin = new JLabel("AdminPanel");
        admin.setBounds(100, 10, 130, 30);
        admin.setFont(new Font("Tahoma", Font.BOLD, 20));
        admin.setForeground(Color.white);
        add(admin);

        // Text Username
        JLabel newLabel = new JLabel("Username");
        newLabel.setBounds(40, 60, 100, 30);
        newLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        newLabel.setForeground(Color.white);
        add(newLabel);

        // Text Password
        JLabel password = new JLabel("Password");
        password.setBounds(40, 110, 100, 30);
        password.setFont(new Font("Tahoma", Font.BOLD, 16));
        password.setForeground(Color.white);
        add(password);

        // Textbox Username
        textField = new JTextField();
        textField.setBounds(150, 60, 150, 30);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textField.setBackground(new Color(255, 179, 0));
        add(textField);

        // Textbox Password
        jPasswordField = new JPasswordField();
        jPasswordField.setBounds(150, 110, 150, 30);
        jPasswordField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        jPasswordField.setBackground(new Color(255, 179, 0));
        add(jPasswordField);

        // Show Password Checkbox
        showPassword = new JCheckBox("Show Password");
        showPassword.setBounds(35, 160, 150, 20);
        showPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        showPassword.setBackground(new Color(72, 124, 191));
        showPassword.setForeground(Color.white);
        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                jPasswordField.setEchoChar((char) 0);
            } else {
                jPasswordField.setEchoChar('•');
            }
        });
        add(showPassword);

        // Setting up video player
        try {
            // Create JavaFX panel for video
            JFXPanel videoPanel = new JFXPanel();
            videoPanel.setBounds(365, -30, 300, 300);
            add(videoPanel);

            // Initialize and setup media player in JavaFX thread
            javafx.application.Platform.runLater(() -> {
                try {
                    URL videoFileUrl = getClass().getResource("/icon/Hospital.mp4");
                    if (videoFileUrl != null) {
                        Media media = new Media(videoFileUrl.toURI().toString());
                        mediaPlayer = new MediaPlayer(media);
                        mediaPlayer.setAutoPlay(true);
                        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

                        // Add error handling
                        mediaPlayer.setOnError(() -> {
                            System.out.println("Media Player Error: " + mediaPlayer.getError().toString());
                        });

                        MediaView mediaView = new MediaView(mediaPlayer);

                        // Fit video to panel size
                        mediaView.setFitWidth(350);
                        mediaView.setFitHeight(300);
                        mediaView.setPreserveRatio(true);

                        // Create scene and add media view
                        StackPane root = new StackPane();
                        root.getChildren().add(mediaView);
                        Scene scene = new Scene(root);
                        videoPanel.setScene(scene);

                        // Start playing
                        mediaPlayer.play();
                    } else {
                        System.err.println("Video file not found in resources folder: /icon/Hospital.mp4");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error initializing video player: " + e.getMessage());
        }

        // Creating a Login Button
        b1 = new JButton("Login");
        b1.setBounds(40, 200, 120, 30);
        b1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        b1.setBackground(Color.black);
        b1.setForeground(Color.white);
        b1.addActionListener(this);
        addHoverEffect(b1, new Color(0, 128, 0), Color.black);
        add(b1);

        // Creating a Cancel Button
        b2 = new JButton("Cancel");
        b2.setBounds(180, 200, 120, 30);
        b2.setFont(new Font("Tahoma", Font.PLAIN, 15));
        b2.setBackground(Color.black);
        b2.setForeground(Color.white);
        b2.addActionListener(this);
        addHoverEffect(b2, new Color(255, 0, 0), Color.black);
        add(b2);

        // Add window closing event to properly dispose of the MediaPlayer
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (mediaPlayer != null) {
                    mediaPlayer.dispose();
                }
            }
        });

        // Makes a Login Panel
        getContentPane().setBackground(new Color(72, 124, 191));
        setSize(700, 300);
        setLocation(400, 270);
        setLayout(null);
        setVisible(true);
        setTitle("Hospital Management System - Login");
    }

    // Method to add hover effect
    private void addHoverEffect(JButton button, Color hoverBackgroundColor, Color originalBackgroundColor) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverBackgroundColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalBackgroundColor);
            }
        });
    }

    // Hash password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            try {
                String user = textField.getText();
                String pass = new String(jPasswordField.getPassword());
                String hashedPassword = hashPassword(pass);

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management_system", "root", "12345");
                Statement statement = conn.createStatement();

                String q = "SELECT * FROM login WHERE ID = '" + user + "' AND PW = '" + hashedPassword + "'";
                ResultSet resultSet = statement.executeQuery(q);

                if (resultSet.next()) {
                    // Use the ID as the username since that's what we have
                    String userName = resultSet.getString("ID");
                    JOptionPane.showMessageDialog(this, "Login Successful!");
                    setVisible(false);
                    new Reception(userName);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Credentials.");
                }

                conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        } else if (e.getSource() == b2) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}