package hospital.management.system;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import net.proteanit.sql.DbUtils;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;

public class RoomAvailability extends JFrame {

    // Static method to get the total count of occupied rooms
    public static int getOccupiedRoomCount() {
        int occupiedCount = 0;
        try {
            // Establishing the connection
            conn c = new conn();

            // Execute query to get count of occupied rooms
            // Assuming 'Room' table has an 'Availability' column where 'Occupied' means the room is in use
            String query = "SELECT COUNT(*) AS count FROM Room WHERE Availability = 'Occupied'";
            ResultSet resultSet = c.statement.executeQuery(query);

            if (resultSet.next()) {
                occupiedCount = resultSet.getInt("count");
            }

            // Log the count for verification
            System.out.println("Current occupied room count: " + occupiedCount);

        } catch (Exception e) {
            // Handle and log any exception
            e.printStackTrace();
        }

        // Return the count of occupied rooms
        return occupiedCount;
    }

    // Add this field declaration
    private MediaPlayer mediaPlayer;
    JTable table;
    JLabel label1;

    RoomAvailability() {

        // Add new Room Panel
        JPanel panel = new JPanel();
        panel.setBounds(5, 8, 840, 585);
        panel.setBackground(new Color(72, 123, 191));
        panel.setLayout(null);
        add(panel);

        // Setting up video player
        try {
            // Create JavaFX panel for video
            JFXPanel videoPanel = new JFXPanel();
            videoPanel.setBounds(550, 150, 250, 300);
            panel.add(videoPanel);

            // Initialize and setup media player in JavaFX thread
            javafx.application.Platform.runLater(() -> {
                try {
                    URL videoFileUrl = getClass().getResource("/icon/Room.mp4");
                    if (videoFileUrl != null) {
                        Media media = new Media(videoFileUrl.toURI().toString());
                        mediaPlayer = new MediaPlayer(media);
                        mediaPlayer.setAutoPlay(true); // Auto start playing
                        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Set to loop indefinitely

                        // Add error handling
                        mediaPlayer.setOnError(() -> {
                            System.out.println("Media Player Error: " + mediaPlayer.getError().toString());
                        });

                        // Add a listener for when the media ends
                        mediaPlayer.setOnEndOfMedia(() -> {
                            mediaPlayer.seek(javafx.util.Duration.ZERO); // Return to start
                            mediaPlayer.play(); // Play again
                        });

                        MediaView mediaView = new MediaView(mediaPlayer);

                        // Fit video to panel size
                        mediaView.setFitWidth(600);
                        mediaView.setFitHeight(600);
                        mediaView.setPreserveRatio(true);

                        // Create scene and add media view
                        StackPane root = new StackPane();
                        root.getChildren().add(mediaView);
                        Scene scene = new Scene(root);
                        videoPanel.setScene(scene);

                        // Start playing
                        mediaPlayer.play();
                    } else {
                        System.err.println("Video file not found in resources folder: /icon/Room.mp4");
                        // You might want to add a fallback image here
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error initializing video player: " + e.getMessage());
        }

        // Add window closing event to properly dispose of the MediaPlayer
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (mediaPlayer != null) {
                    mediaPlayer.dispose();
                }
            }
        });

        // Table Setup
        JTable table = new JTable();
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setForeground(Color.WHITE);
        table.setBackground(new Color(72, 123, 191));
        table.setRowHeight(30); // Set row height for better visibility
        table.setShowGrid(false); // Disable grid lines

        // Table Header Styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Tahoma", Font.BOLD, 16));
        header.setBackground(Color.white);
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false); // Disable column reordering

        // Custom Cell Renderer for Alignment and Appearance
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER); // Center alignment for cells
        table.setDefaultRenderer(Object.class, renderer);

        // Wrap Table in ScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 140, 500, 300);
        scrollPane.setBackground(new Color(72, 123, 191));
        scrollPane.setBorder(null); // Optional: Remove border for a flat look
        panel.add(scrollPane);

        // Fetch Data from Database
        try {
            conn c = new conn();
            String q = "select * from Room";
            ResultSet resultSet = c.statement.executeQuery(q);
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Room Availability Heading
        JLabel heading = new JLabel("Room Availability");
        heading.setBounds(12, 15, 250, 45);
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Tahoma" , Font.BOLD , 25));
        panel.add(heading);

        // Room No Heading
        JLabel label1 = new JLabel("Room No");
        label1.setBounds(12, 105, 80, 15);
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("Tahoma" , Font.BOLD , 16));
        panel.add(label1);

        // Availability Heading
        JLabel label2 = new JLabel("Availability");
        label2.setBounds(135, 105, 90, 15);
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("Tahoma" , Font.BOLD , 16));
        panel.add(label2);

        // Price Heading
        JLabel label3 = new JLabel("Price");
        label3.setBounds(280, 105, 80, 15);
        label3.setForeground(Color.WHITE);
        label3.setFont(new Font("Tahoma" , Font.BOLD , 16));
        panel.add(label3);

        // Room Type Heading
        JLabel label4 = new JLabel("Room Type");
        label4.setBounds(382, 105, 90, 15);
        label4.setForeground(Color.WHITE);
        label4.setFont(new Font("Tahoma" , Font.BOLD , 16));
        panel.add(label4);

        // Modern back button with animation
        JButton back = new JButton("Back");
        back.setBounds(20, 500, 120, 40);
        back.setFont(new Font("Segoe UI", Font.BOLD, 14));
        back.setFocusPainted(false);
        back.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        back.setBackground(new Color(72, 123, 191));
        back.setForeground(Color.WHITE);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Smooth hover effect
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            Timer timer;
            float alpha = 0;

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (timer != null && timer.isRunning()) timer.stop();
                timer = new Timer(10, evt -> {
                    alpha += 0.1f;
                    if (alpha >= 1) {
                        alpha = 1;
                        ((Timer)evt.getSource()).stop();
                    }
                    back.setBackground(interpolateColor(new Color(72, 123, 191), Color.WHITE, alpha));
                    back.setForeground(interpolateColor(Color.WHITE, new Color(72, 123, 191), alpha));
                });
                timer.start();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (timer != null && timer.isRunning()) timer.stop();
                timer = new Timer(10, evt -> {
                    alpha -= 0.1f;
                    if (alpha <= 0) {
                        alpha = 0;
                        ((Timer)evt.getSource()).stop();
                    }
                    back.setBackground(interpolateColor(new Color(72, 123, 191), Color.WHITE, alpha));
                    back.setForeground(interpolateColor(Color.WHITE, new Color(72, 123, 191), alpha));
                });
                timer.start();
            }

            private Color interpolateColor(Color c1, Color c2, float ratio) {
                int red = (int)(c1.getRed() * (1 - ratio) + c2.getRed() * ratio);
                int green = (int)(c1.getGreen() * (1 - ratio) + c2.getGreen() * ratio);
                int blue = (int)(c1.getBlue() * (1 - ratio) + c2.getBlue() * ratio);
                return new Color(red, green, blue);
            }
        });

        back.addActionListener(e -> setVisible(false));
        panel.add(back);


        // Room Panel
        setUndecorated(true);
        setSize(850, 600);
        setLayout(null);
        setLocation(530, 130);
        setVisible(true);
    }

    public static void main(String[] args) {
        new RoomAvailability();
    }
}