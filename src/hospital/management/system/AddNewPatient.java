package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.net.URL;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import javafx.embed.swing.JFXPanel;


public class AddNewPatient extends JFrame implements ActionListener {

    JComboBox<String> comboBox;
    JTextField textFieldNumber, textFieldName, textFieldDisease, textFieldDeposit;
    JRadioButton r1, r2;
    ButtonGroup genderGroup;
    Choice c1;
    JLabel date;
    JButton btn1, btn2;
    private MediaPlayer mediaPlayer;
    private String radioBTN;

    AddNewPatient() {
        // Initialize JavaFX toolkit
        JFXPanel fxPanel = new JFXPanel();

        // Add new Patient Panel
        JPanel panel = new JPanel();
        panel.setBounds(5, 8, 840, 585);
        panel.setBackground(new Color(72, 123, 191));
        panel.setLayout(null);
        add(panel);

        // Setting up video player
        try {
            // Create JavaFX panel for video
            JFXPanel videoPanel = new JFXPanel();
            videoPanel.setBounds(550, 150, 200, 200);
            panel.add(videoPanel);

            // Initialize and setup media player in JavaFX thread
            javafx.application.Platform.runLater(() -> {
                try {
                    URL videoFileUrl = getClass().getResource("/icon/Patient.mp4");
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
                        mediaView.setFitWidth(300);
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
                        System.err.println("Video file not found in resources folder: /icon/Patient.mp4");
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

        // Heading of "New Patient Form"
        JLabel labelName = new JLabel("New Patient Form");
        labelName.setBounds(118, 11, 260, 53);
        labelName.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel.add(labelName);

        // ID
        JLabel labelId = new JLabel("ID :");
        labelId.setBounds(35, 76, 200, 14);
        labelId.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelId.setForeground(Color.WHITE);
        panel.add(labelId);

        // Dropdown
        comboBox = new JComboBox<>(new String[]{"CNIC", "Voter ID", "Driving License"});
        comboBox.setBounds(271, 73, 150, 20);
        comboBox.setBackground(new Color(3, 45, 48));
        comboBox.setForeground(Color.WHITE);
        comboBox.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(comboBox);

        // Number
        JLabel labelNumber = new JLabel("Number :");
        labelNumber.setBounds(35, 111, 200, 14);
        labelNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelNumber.setForeground(Color.WHITE);
        panel.add(labelNumber);

        // Number Textbox
        textFieldNumber = new JTextField();
        textFieldNumber.setBounds(271, 111, 150, 20);
        panel.add(textFieldNumber);

        // Name
        JLabel labelName1 = new JLabel("Name :");
        labelName1.setBounds(35, 151, 200, 14);
        labelName1.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelName1.setForeground(Color.WHITE);
        panel.add(labelName1);
        // Name Textbox
        textFieldName = new JTextField();
        textFieldName.setBounds(271, 151, 150, 20);
        panel.add(textFieldName);

        // Gender
        JLabel labelGender = new JLabel("Gender :");
        labelGender.setBounds(35, 191, 200, 14);
        labelGender.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelGender.setForeground(Color.WHITE);
        panel.add(labelGender);

        // Adding Radio Buttons with ButtonGroup
        genderGroup = new ButtonGroup();
        r1 = new JRadioButton("Male");
        r1.setFont(new Font("Tahoma", Font.BOLD, 14));
        r1.setForeground(Color.white);
        r1.setBackground(Color.black);
        r1.setBounds(271, 191, 80, 15);
        r2 = new JRadioButton("Female");
        r2.setFont(new Font("Tahoma", Font.BOLD, 14));
        r2.setForeground(Color.white);
        r2.setBackground(Color.black);
        r2.setBounds(350, 191, 80, 15);
        genderGroup.add(r1);
        genderGroup.add(r2);
        panel.add(r1);
        panel.add(r2);

        // Disease
        JLabel labelDisease = new JLabel("Disease :");
        labelDisease.setBounds(35, 231, 200, 14);
        labelDisease.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelDisease.setForeground(Color.WHITE);
        panel.add(labelDisease);
        // Disease Textbox
        textFieldDisease = new JTextField();
        textFieldDisease.setBounds(271, 231, 150, 20);
        panel.add(textFieldDisease);

        // Room
        JLabel labelRoom = new JLabel("Room :");
        labelRoom.setBounds(35, 274, 200, 14);
        labelRoom.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelRoom.setForeground(Color.WHITE);
        panel.add(labelRoom);

        // Room Dropdown
        c1 = new Choice();
        try {
            conn c = new conn();
            ResultSet resultset = c.statement.executeQuery("select * from Room");
            while(resultset.next()) {
                c1.add(resultset.getString("Room_no"));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        c1.setBounds(271, 274, 150, 20);
        c1.setFont(new Font("Tahoma", Font.BOLD, 14));
        c1.setForeground(Color.WHITE);
        c1.setBackground(new Color(3, 45, 48));
        panel.add(c1);

        // Date
        JLabel labelDate = new JLabel("Time :");
        labelDate.setBounds(35, 316, 200, 14);
        labelDate.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelDate.setForeground(Color.WHITE);
        panel.add(labelDate);

        Date date1 = new Date();
        date = new JLabel("" + date1);
        date.setBounds(271, 316, 250, 14);
        date.setForeground(Color.WHITE);
        date.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(date);

        // Deposit
        JLabel labelDeposit = new JLabel("Deposit :");
        labelDeposit.setBounds(35, 359, 200, 17);
        labelDeposit.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelDeposit.setForeground(Color.WHITE);
        panel.add(labelDeposit);
        // Deposit Textbox
        textFieldDeposit = new JTextField();
        textFieldDeposit.setBounds(271, 359, 150, 20);
        panel.add(textFieldDeposit);

        // Buttons
        // ADD
        btn1 = new JButton("Add");
        btn1.setBounds(100, 470, 120, 40);
        btn1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn1.setFocusPainted(false);
        btn1.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        btn1.setBackground(new Color(72, 123, 191));
        btn1.setForeground(Color.WHITE);
        btn1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btn1);

        // Hover effect on Add Button
        btn1.addMouseListener(new java.awt.event.MouseAdapter() {
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
                    Color background = interpolateColor(new Color(72, 123, 191), Color.WHITE, alpha);
                    Color foreground = interpolateColor(Color.WHITE, new Color(72, 123, 191), alpha);
                    btn1.setBackground(background);
                    btn1.setForeground(foreground);
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
                    Color background = interpolateColor(new Color(72, 123, 191), Color.WHITE, alpha);
                    Color foreground = interpolateColor(Color.WHITE, new Color(72, 123, 191), alpha);
                    btn1.setBackground(background);
                    btn1.setForeground(foreground);
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

        btn1.addActionListener(this);

        // Back
        btn2 = new JButton("Back");
        btn2.setBounds(300, 470, 120, 40);
        btn2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn2.setFocusPainted(false);
        btn2.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        btn2.setBackground(new Color(72, 123, 191));
        btn2.setForeground(Color.WHITE);
        btn2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btn2);

        // Hover effect on Back Button
        btn2.addMouseListener(new java.awt.event.MouseAdapter() {
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
                    Color background = interpolateColor(new Color(72, 123, 191), Color.WHITE, alpha);
                    Color foreground = interpolateColor(Color.WHITE, new Color(72, 123, 191), alpha);
                    btn2.setBackground(background);
                    btn2.setForeground(foreground);
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
                    Color background = interpolateColor(new Color(72, 123, 191), Color.WHITE, alpha);
                    Color foreground = interpolateColor(Color.WHITE, new Color(72, 123, 191), alpha);
                    btn2.setBackground(background);
                    btn2.setForeground(foreground);
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

        btn2.addActionListener(this);

        setUndecorated(true);
        setSize(850, 600);
        setLayout(null);
        setLocation(530, 130);
        setVisible(true);
        setTitle("Hospital Management System - Add Patient");
    }



    public static void main(String[] args) {
        new AddNewPatient();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn1) {
            if (r1.isSelected()) {
                radioBTN = "Male";
            } else if (r2.isSelected()) {
                radioBTN = "Female";
            }

            String s1 = (String) comboBox.getSelectedItem();
            String s2 = textFieldNumber.getText();
            String s3 = textFieldName.getText();
            String s4 = radioBTN;
            String s5 = textFieldDisease.getText();
            String s6 = c1.getSelectedItem();
            String s7 = date.getText();
            String s8 = textFieldDeposit.getText();

            try {
                conn c = new conn();
                String q = "insert into Patient_info values ('" + s1 + "', '" + s2 + "', '" + s3 + "', '" + s4 + "', '" + s5 + "', '" + s6 + "', '" + s7 + "', '" + s8 + "')";
                String q1 = "update room set availability = 'Occupied' where Room_no = " + s6;
                c.statement.executeUpdate(q);
                c.statement.executeUpdate(q1);
                JOptionPane.showMessageDialog(null, "Added Successfully");
                Reception.refreshAllDashboards();
                setVisible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == btn2) {
            setVisible(false);
        }
    }
}