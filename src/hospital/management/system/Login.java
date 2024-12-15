package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Stack;

public class Login extends JFrame {

    // Declaring the Fields
    JTextField textField;
    JPasswordField jPasswordField;
    JButton b1, b2;

    // HashMap to store username and hashed password
    HashMap<String, String> users;



    Login() {
        // Initialize user database
        users = new HashMap<>();
        addUser("admin", hashPassword("admin123"));
        addUser("user", hashPassword("password"));

        // Text Username
        JLabel newLabel = new JLabel("Username");
        newLabel.setBounds(40, 20, 100, 30);
        newLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        newLabel.setForeground(Color.black);
        add(newLabel);

        // Text Password
        JLabel password = new JLabel("Password");
        password.setBounds(40, 70, 100, 30);
        password.setFont(new Font("Tahoma", Font.BOLD, 16));
        password.setForeground(Color.black);
        add(password);

        // Textbox Username
        textField = new JTextField();
        textField.setBounds(150, 20, 150, 30);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textField.setBackground(new Color(255, 179, 0));
        add(textField);

        // Textbox Password
        jPasswordField = new JPasswordField();
        jPasswordField.setBounds(150, 70, 150, 30);
        jPasswordField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        jPasswordField.setBackground(new Color(255, 179, 0));
        add(jPasswordField);

        // Setting the logo image on the login panel
        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/login.png"));
        Image i1 = imageIcon.getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT);
        ImageIcon imageIcon1 = new ImageIcon(i1);
        JLabel label = new JLabel(imageIcon1);
        label.setBounds(320, -30, 400, 300);
        add(label);

        // Creating a Login Button
        b1 = new JButton("Login");
        b1.setBounds(40, 140, 120, 30);
        b1.setFont(new Font("serif", Font.BOLD, 15));
        b1.setBackground(Color.black);
        b1.setForeground(Color.white);
        addHoverEffect(b1, new Color(0, 128, 0), Color.black); // Green hover effect
        add(b1);

        // Add login functionality
        b1.addActionListener(e -> validateLogin());

        // Creating a Cancel Button
        b2 = new JButton("Cancel");
        b2.setBounds(180, 140, 120, 30);
        b2.setFont(new Font("serif", Font.BOLD, 15));
        b2.setBackground(Color.black);
        b2.setForeground(Color.white);
        addHoverEffect(b2, new Color(255, 0, 0), Color.black); // Red hover effect
        add(b2);

        // Makes a Login Panel
        getContentPane().setBackground(new Color(109, 164, 170));
        setSize(750, 300);
        setLocation(400, 270);
        setLayout(null);
        setVisible(true);
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

    // Add user to HashMap
    private void addUser(String username, String hashedPassword) {
        users.put(username, hashedPassword);
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

    // Validate login credentials
    private void validateLogin() {
        String username = textField.getText();
        String password = new String(jPasswordField.getPassword());

        // Validate input format using stack
        if (!isInputValid(username) || !isInputValid(password)) {
            JOptionPane.showMessageDialog(this, "Invalid username or password format.");
            return;
        }

        // Validate username and password
        String hashedPassword = users.get(username);
        if (hashedPassword != null && hashedPassword.equals(hashPassword(password))) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Credentials.");
        }
    }

    // Validate input using Stack
    private boolean isInputValid(String input) {
        Stack<Character> stack = new Stack<>();
        for (char c : input.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if (c == ')' || c == '}' || c == ']') {
                if (stack.isEmpty() || !isMatchingPair(stack.pop(), c)) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private boolean isMatchingPair(char open, char close) {
        return (open == '(' && close == ')') ||
                (open == '{' && close == '}') ||
                (open == '[' && close == ']');
    }

    public static void main(String[] args) {
        new Login();
    }
}
