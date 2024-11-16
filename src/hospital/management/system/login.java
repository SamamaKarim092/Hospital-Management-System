package hospital.management.system;

import javax.swing.*;
import java.awt.*;

public class login extends JFrame {

    JTextField textField;

    public login() {

        JLabel namelabel = new JLabel("Username");
        namelabel.setBounds(40,20,100,30);
        namelabel.setFont(new Font("Tahoma", Font.BOLD , 16));
        add(namelabel);


        setSize(750,300);
        setLocation(400,270);
        setLayout(null);
        setVisible(true);

    }
    public static void main(String[] args) {
        new login();
    }
}