package hospital.management.system;

import javax.swing.*;
import java.awt.*;

public class Reception extends JFrame {

    Reception() {

        // Left Panel
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 400, 1525);
        panel.setBackground(new Color(255, 255, 255));
        add(panel);

        // Right Panel
        JPanel panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBounds(400, 0, 1525, 1525);
        panel2.setBackground(new Color(109, 164, 170));
        add(panel2);

        // Heading of Hospital Management System
        JLabel title = new JLabel("   Hospital Management System");
        title.setBounds(20, 20, 350, 40); // Position and size of the text box
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        title.setForeground(Color.white);
        title.setBackground(new Color(72, 123, 191)); // Set the background color
        title.setOpaque(true); // Make the label opaque so the background is visible
        panel.add(title);

        // Setting the logo of Ambulance on the main panel
        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/Ambulance + sign.jpg"));
        Image i1 = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        ImageIcon imageIcon1 = new ImageIcon(i1);
        JLabel label = new JLabel(imageIcon1);
        label.setBounds(10, 40, 300, 200);
        panel.add(label);

        // Heading of Feature on left panel
        JLabel feature = new JLabel("   Features");
        feature.setBounds(20, 250, 300, 40);
        feature.setFont(new Font("Tahoma", Font.BOLD, 20));
        feature.setForeground(Color.white);
        feature.setBackground(new Color(72, 123, 191));
        feature.setOpaque(true);
        panel.add(feature);

        // Heading of Patient on left panel
        JLabel patient = new JLabel("   Patient");
        patient.setBounds(8, 300, 75, 40);
        patient.setFont(new Font("Tahoma", Font.PLAIN, 15));
        patient.setForeground(Color.GRAY);
        patient.setBackground(Color.WHITE);
        patient.setOpaque(true);
        panel.add(patient);

        // "Add new Patient" Button
        ImageIcon addIcon = new ImageIcon(ClassLoader.getSystemResource("icon/Add patient button.png")); // Replace with your icon path
        Image addImg = addIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT); // Scale the icon to fit the button
        ImageIcon scaledAddIcon = new ImageIcon(addImg);

        JButton btn1 = new JButton("Add new Patient");
        btn1.setBounds(15, 340, 180, 30);
        btn1.setBackground(Color.YELLOW);
        btn1.setIcon(scaledAddIcon);
        btn1.setHorizontalTextPosition(SwingConstants.RIGHT);
        btn1.setIconTextGap(10);

        panel.add(btn1);

        // "Patient Info" Button
        ImageIcon infoIcon = new ImageIcon(ClassLoader.getSystemResource("icon/Patient info button.png"));
        Image infoImg = infoIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon scaledInfoIcon = new ImageIcon(infoImg);

        JButton btn2 = new JButton("Patient Info");
        btn2.setBounds(205, 340, 180, 30);
        btn2.setBackground(Color.YELLOW);
        btn2.setIcon(scaledInfoIcon); // Set the icon on the button
        btn2.setHorizontalTextPosition(SwingConstants.RIGHT);
        btn2.setIconTextGap(10);

        panel.add(btn2);

        // "Update Patient Info" Button
        ImageIcon infoIcon1 = new ImageIcon(ClassLoader.getSystemResource("icon/Update patient button.png"));
        Image infoImg1 = infoIcon1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon scaledInfoIcon1 = new ImageIcon(infoImg1);

        JButton btn3 = new JButton("Update Patient Info");
        btn3.setBounds(15, 380, 180, 30);
        btn3.setBackground(Color.YELLOW);
        btn3.setIcon(scaledInfoIcon1); // Set the icon on the button
        btn3.setHorizontalTextPosition(SwingConstants.RIGHT);
        btn3.setIconTextGap(10);

        panel.add(btn3);

        // "Patient Discharge" Button
        ImageIcon infoIcon2 = new ImageIcon(ClassLoader.getSystemResource("icon/Patient discharge button.png"));
        Image infoImg2 = infoIcon2.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon scaledInfoIcon2 = new ImageIcon(infoImg2);

        JButton btn4 = new JButton("Patient Discharge");
        btn4.setBounds(205, 380, 180, 30);
        btn4.setBackground(Color.YELLOW);
        btn4.setIcon(scaledInfoIcon2); // Set the icon on the button
        btn4.setHorizontalTextPosition(SwingConstants.RIGHT);
        btn4.setIconTextGap(10);

        panel.add(btn4);





        // Whole panel of management system
        setSize(1950, 1090);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Reception();
    }
}
