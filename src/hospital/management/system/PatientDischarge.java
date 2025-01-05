package hospital.management.system;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.*;

public class PatientDischarge extends JFrame {
    private MediaPlayer mediaPlayer;

    PatientDischarge() {
        // Main Panel with gradient background
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                Color color1 = new Color(72, 123, 191);
                Color color2 = new Color(38, 85, 145);
                GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        panel.setBounds(5, 8, 940, 635);
        panel.setLayout(null);
        add(panel);

        // Setting up video player
        try {
            JFXPanel videoPanel = new JFXPanel();
            videoPanel.setBounds(550, 150, 200, 200);
            panel.add(videoPanel);

            javafx.application.Platform.runLater(() -> {
                try {
                    URL videoFileUrl = getClass().getResource("/icon/Patient.mp4");
                    if (videoFileUrl != null) {
                        Media media = new Media(videoFileUrl.toURI().toString());
                        mediaPlayer = new MediaPlayer(media);
                        mediaPlayer.setAutoPlay(true);
                        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

                        mediaPlayer.setOnError(() -> {
                            System.out.println("Media Player Error: " + mediaPlayer.getError().toString());
                        });

                        mediaPlayer.setOnEndOfMedia(() -> {
                            mediaPlayer.seek(javafx.util.Duration.ZERO);
                            mediaPlayer.play();
                        });

                        MediaView mediaView = new MediaView(mediaPlayer);
                        mediaView.setFitWidth(300);
                        mediaView.setFitHeight(300);
                        mediaView.setPreserveRatio(true);

                        StackPane root = new StackPane();
                        root.getChildren().add(mediaView);
                        Scene scene = new Scene(root);
                        videoPanel.setScene(scene);
                        mediaPlayer.play();
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Window closing event
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (mediaPlayer != null) {
                    mediaPlayer.dispose();
                }
            }
        });

        // Patient Discharge Heading
        JLabel discharge = new JLabel("Patient Discharge");
        discharge.setBounds(25, 25, 300, 45);
        discharge.setForeground(Color.WHITE);
        discharge.setFont(new Font("Tahoma", Font.BOLD, 24));
        panel.add(discharge);

        // Details Panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(null);
        detailsPanel.setBounds(40, 90, 450, 400);
        detailsPanel.setBackground(new Color(255, 255, 255, 20));
        detailsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.add(detailsPanel);

        // Patient ID Section
        JLabel id = new JLabel("Patient ID");
        id.setBounds(20, 10, 100, 25);
        id.setForeground(Color.WHITE);
        id.setFont(new Font("Tahoma", Font.BOLD, 14));
        detailsPanel.add(id);

        Choice choice = new Choice();
        choice.setBounds(20, 40, 200, 30);
        choice.setFont(new Font("Tahoma", Font.PLAIN, 14));
        choice.setBackground(new Color(255, 255, 255, 40));
        choice.setForeground(Color.WHITE);
        detailsPanel.add(choice);

        try {
            conn c = new conn();
            ResultSet resultSet = c.statement.executeQuery("select * from patient_info");
            while(resultSet.next()) {
                choice.add(resultSet.getString("Number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Name Section:
        JLabel name = new JLabel("Patient Name");
        name.setBounds(20, 80, 100, 25);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Tahoma", Font.BOLD, 14));
        detailsPanel.add(name);

        JLabel nameLabel = new JLabel();
        nameLabel.setBounds(20, 110, 200, 30);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        nameLabel.setOpaque(true);
        nameLabel.setBackground(new Color(72, 123, 191));
        nameLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        detailsPanel.add(nameLabel);

        // Room Number Section
        JLabel roomNumber = new JLabel("Room Number");
        roomNumber.setBounds(20, 150, 150, 25);
        roomNumber.setForeground(Color.WHITE);
        roomNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
        detailsPanel.add(roomNumber);

        // Modified room number label without background image
        JLabel rNo = new JLabel();
        rNo.setBounds(20, 180, 200, 30);
        rNo.setForeground(Color.WHITE);
        rNo.setFont(new Font("Tahoma", Font.BOLD, 14));
        rNo.setOpaque(true);
        rNo.setBackground(new Color(72, 123, 191));  // Solid color background
        rNo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        detailsPanel.add(rNo);

        // In Time Section
        JLabel inTime = new JLabel("In Time");
        inTime.setBounds(20, 230, 100, 25);
        inTime.setForeground(Color.WHITE);
        inTime.setFont(new Font("Tahoma", Font.BOLD, 14));
        detailsPanel.add(inTime);

        // Modified time label without background image
        JLabel time = new JLabel();
        time.setBounds(20, 260, 350, 30);
        time.setForeground(Color.WHITE);
        time.setFont(new Font("Tahoma", Font.BOLD, 14));
        time.setOpaque(true);
        time.setBackground(new Color(72, 123, 191));  // Solid color background
        time.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        detailsPanel.add(time);

        // Out Time Section
        JLabel outTime = new JLabel("Out Time");
        outTime.setBounds(20, 310, 100, 25);
        outTime.setForeground(Color.WHITE);
        outTime.setFont(new Font("Tahoma", Font.BOLD, 14));
        detailsPanel.add(outTime);

        Date date = new Date();
        JLabel Time = new JLabel("" + date);
        Time.setBounds(20, 340, 350, 30);
        Time.setForeground(Color.WHITE);
        Time.setFont(new Font("Tahoma", Font.BOLD, 14));
        Time.setOpaque(true);
        Time.setBackground(new Color(72, 123, 191));  // Solid color background
        Time.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        detailsPanel.add(Time);

        // Back Button
        JButton back = new JButton("Back");
        back.setBounds(20, 550, 120, 40);
        back.setFont(new Font("Segoe UI", Font.BOLD, 14));
        back.setFocusPainted(false);
        back.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        back.setBackground(new Color(72, 123, 191));
        back.setForeground(Color.WHITE);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButtonAnimation(back);
        back.addActionListener(e -> setVisible(false));
        panel.add(back);

        // Check Button
        JButton check = new JButton("Check");
        check.setBounds(200, 550, 120, 40);
        check.setFont(new Font("Segoe UI", Font.BOLD, 14));
        check.setFocusPainted(false);
        check.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        check.setBackground(new Color(72, 123, 191));
        check.setForeground(Color.WHITE);
        check.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButtonAnimation(check);
        panel.add(check);

        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conn c = new conn();
                try {
                    ResultSet resultSet = c.statement.executeQuery("select * from patient_info where Number = '"+choice.getSelectedItem()+"' ");
                    while(resultSet.next()) {
                        rNo.setText(resultSet.getString("Room_Number"));
                        time.setText(resultSet.getString("Time"));
                        nameLabel.setText(resultSet.getString("Name"));
                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });

        // Discharge Button
        JButton discharge1 = new JButton("Discharge");
        discharge1.setBounds(335, 550, 120, 40);
        discharge1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        discharge1.setFocusPainted(false);
        discharge1.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        discharge1.setBackground(new Color(72, 123, 191));
        discharge1.setForeground(Color.WHITE);
        discharge1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButtonAnimation(discharge1);
        panel.add(discharge1);

        discharge1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conn c = new conn();
                try {
                    c.statement.executeUpdate("delete from patient_info where Number = '"+choice.getSelectedItem()+"' ");
                    c.statement.executeUpdate("update Room set Availability = 'Available' where Room_no = '"+rNo.getText()+"' ");
                    JOptionPane.showMessageDialog(null, "The Patient is Discharged");
                    Reception.refreshAllDashboards();
                    setVisible(false);
                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });

        // Frame setup
        setUndecorated(true);
        setSize(950, 650);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
        setLocation(530, 130);
    }

    // Helper method for button animations
    private void addButtonAnimation(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
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
                    button.setBackground(interpolateColor(new Color(72, 123, 191), Color.WHITE, alpha));
                    button.setForeground(interpolateColor(Color.WHITE, new Color(72, 123, 191), alpha));
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
                    button.setBackground(interpolateColor(new Color(72, 123, 191), Color.WHITE, alpha));
                    button.setForeground(interpolateColor(Color.WHITE, new Color(72, 123, 191), alpha));
                });
                timer.start();
            }
        });
    }

    private Color interpolateColor(Color c1, Color c2, float ratio) {
        int red = (int)(c1.getRed() * (1 - ratio) + c2.getRed() * ratio);
        int green = (int)(c1.getGreen() * (1 - ratio) + c2.getGreen() * ratio);
        int blue = (int)(c1.getBlue() * (1 - ratio) + c2.getBlue() * ratio);
        return new Color(red, green, blue);
    }

    public static void main(String[] args) {
        new PatientDischarge();
    }
}