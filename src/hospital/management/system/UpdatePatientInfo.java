package hospital.management.system;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;

public class UpdatePatientInfo extends JFrame {
    private MediaPlayer mediaPlayer;
    UpdatePatientInfo() {

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
            videoPanel.setBounds(450, 70, 400, 400);
            panel.add(videoPanel);

            javafx.application.Platform.runLater(() -> {
                try {
                    URL videoFileUrl = getClass().getResource("/icon/Update Patient Info.mp4");
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
                        mediaView.setFitWidth(400);
                        mediaView.setFitHeight(400);
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

        // Update Patient Info Heading
        JLabel update = new JLabel("Update Patient Info");
        update.setBounds(30, 10, 300, 45);
        update.setForeground(Color.WHITE);
        update.setFont(new Font("Tahoma", Font.BOLD, 26));
        panel.add(update);

        // Name
        JLabel name = new JLabel("Name :");
        name.setBounds(20, 100, 150, 14);
        name.setFont(new Font("Tahoma", Font.BOLD, 16));
        name.setForeground(Color.WHITE);
        panel.add(name);

        Choice choice = new Choice();
        choice.setBounds(250, 100, 125, 14);
        panel.add(choice);

        try{
            conn c = new conn();
            ResultSet resultSet = c.statement.executeQuery("select * from patient_info");
            while (resultSet.next()) {
                choice.add(resultSet.getString("Name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Room Number
        JLabel room = new JLabel("Room Number :");
        room.setBounds(20, 180, 150, 14);
        room.setFont(new Font("Tahoma", Font.BOLD, 16));
        room.setForeground(Color.WHITE);
        panel.add(room);

        JTextField textFieldR = new JTextField();
        textFieldR.setBounds(250 , 180 , 125 , 20);
        panel.add(textFieldR);

        // In Time
        JLabel time = new JLabel("In Time :");
        time.setBounds(20, 260, 150, 14);
        time.setFont(new Font("Tahoma", Font.BOLD, 16));
        time.setForeground(Color.WHITE);
        panel.add(time);

        JTextField textFieldInTime = new JTextField();
        textFieldInTime.setBounds(250 , 260 , 125 , 20);
        panel.add(textFieldInTime);


        // Amount Paid (RS)
        JLabel paid = new JLabel("Amount Paid (RS) :");
        paid.setBounds(20, 340, 190, 14);
        paid.setFont(new Font("Tahoma", Font.BOLD, 16));
        paid.setForeground(Color.WHITE);
        panel.add(paid);

        JTextField textFieldAmount = new JTextField();
        textFieldAmount.setBounds(250 , 340 , 125 , 20);
        panel.add(textFieldAmount);

        // Pending Amount (RS)
        JLabel pending = new JLabel("Pending Amount (RS) :");
        pending.setBounds(20, 420, 200, 20);
        pending.setFont(new Font("Tahoma", Font.BOLD, 16));
        pending.setForeground(Color.WHITE);
        panel.add(pending);

        JTextField textFieldpending = new JTextField();
        textFieldpending.setBounds(250 , 420 , 125 , 20);
        panel.add(textFieldpending);


        // Check Button
        JButton check = new JButton("Check");
        check.setBounds(400, 550, 120, 40);
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
                String id = choice.getSelectedItem();
                try {
                    conn c = new conn();
                    // First query to get patient info
                    ResultSet resultSet = c.statement.executeQuery("select * from patient_info where Name = '" + id + "'");
                    while(resultSet.next()) {
                        String roomNumber = resultSet.getString("Room_Number");
                        textFieldR.setText(roomNumber);
                        textFieldInTime.setText(resultSet.getString("Time"));
                        textFieldAmount.setText(resultSet.getString("Deposit"));

                        // Second query to get room price using the correct room number
                        ResultSet resultSet1 = c.statement.executeQuery("select * from Room where Room_no = '" + roomNumber + "'");
                        while (resultSet1.next()) {
                            String price = resultSet1.getString("Price");
                            int amountPaid = Integer.parseInt(textFieldAmount.getText());
                            int roomPrice = Integer.parseInt(price);
                            int pendingAmount = roomPrice - amountPaid;
                            textFieldpending.setText(String.valueOf(pendingAmount));
                        }
                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });

        // Update Button
        JButton updateb = new JButton("Update");
        updateb.setBounds(270, 550, 120, 40);
        updateb.setFont(new Font("Segoe UI", Font.BOLD, 14));
        updateb.setFocusPainted(false);
        updateb.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        updateb.setBackground(new Color(72, 123, 191));
        updateb.setForeground(Color.WHITE);
        updateb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButtonAnimation(updateb);
        panel.add(updateb);
        updateb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    conn c = new conn();
                    String q = choice.getSelectedItem();
                    String room = textFieldR.getText();
                    String time = textFieldInTime.getText();
                    String amount = textFieldAmount.getText();
                    c.statement.executeUpdate("update patient_info set Room_Number = '"+room+"' , Time = '"+time+"' , Deposit = '"+amount+"' where name = '"+q+"'");
                    JOptionPane.showMessageDialog(null , "Update Sucessfully");
                    setVisible(false);

                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });

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



        new UpdatePatientInfo();
    }
}
