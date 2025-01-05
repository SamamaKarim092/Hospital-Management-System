package hospital.management.system;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.ResultSet;

public class DoctorInfo extends JFrame {


    // Static method to update and get the doctor count
    public static int updateDoctorCount() {
        int doctorCount = 0;
        try {
            // Establishing the connection
            conn c = new conn();

            // Execute query to get the doctor count
            ResultSet resultSet = c.statement.executeQuery("SELECT COUNT(*) AS count FROM Employee_Info");
            if (resultSet.next()) {
                doctorCount = resultSet.getInt("count");
            }

            // Optionally, log or update a class variable
            System.out.println("Updated doctor count: " + doctorCount);

        } catch (Exception e) {
            // Handle and log any exception
            e.printStackTrace();
        }

        // Return the updated count
        return doctorCount;
    }


    DoctorInfo() {
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
        panel.setBounds(5, 8, 840, 585);
        panel.setLayout(null);
        add(panel);

        // Enhanced Table Setup
        JTable table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setForeground(Color.WHITE);
        table.setBackground(new Color(72, 123, 191, 100));
        table.setRowHeight(35);
        table.setIntercellSpacing(new Dimension(10, 10));
        table.setShowGrid(false);

        // Custom table header renderer
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(Color.WHITE);  // Now solid white
        header.setForeground(Color.WHITE);

        // Custom cell renderer for better alignment and appearance
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);

                if (isSelected) {
                    setBackground(new Color(255, 255, 255, 40));
                    setForeground(Color.WHITE);
                } else {
                    setBackground(new Color(72, 123, 191, 100));
                    setForeground(Color.WHITE);
                }
                setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
                return c;
            }
        };
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Apply renderer to all columns
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Scrollpane with custom styling
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 140, 810, 340);
        scrollPane.setBackground(new Color(72, 123, 191));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 50), 1));
        scrollPane.getViewport().setBackground(new Color(72, 123, 191));
        panel.add(scrollPane);

        // Fetch and display data
        try {
            conn c = new conn();
            ResultSet resultSet = c.statement.executeQuery("select * from Employee_Info");
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Enhanced heading with modern styling
        JLabel heading = new JLabel("Doctor Information");
        heading.setBounds(20, 20, 400, 45);
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panel.add(heading);

        // Stylish column headers
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(20, 95, 810, 40);
        headerPanel.setBackground(new Color(255, 255, 255, 20));
        headerPanel.setLayout(null);
        panel.add(headerPanel);

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

        // Name Heading
        JLabel name = new JLabel("Name");
        name.setBounds(40, 95, 250, 45);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Tahoma" , Font.BOLD , 16));
        panel.add(name);

        // Age Heading
        JLabel age = new JLabel("Age");
        age.setBounds(180, 95, 250, 45);
        age.setForeground(Color.WHITE);
        age.setFont(new Font("Tahoma" , Font.BOLD , 16));
        panel.add(age);

        // Phone Number Heading
        JLabel number = new JLabel("Phone No");
        number.setBounds(300, 95, 250, 45);
        number.setForeground(Color.WHITE);
        number.setFont(new Font("Tahoma" , Font.BOLD , 16));
        panel.add(number);

        // Salary Heading
        JLabel salary = new JLabel("Salary");
        salary.setBounds(440, 95, 250, 45);
        salary.setForeground(Color.WHITE);
        salary.setFont(new Font("Tahoma" , Font.BOLD , 16));
        panel.add(salary);

        // E-mail Heading
        JLabel mail = new JLabel("E-mail");
        mail.setBounds(580, 95, 250, 45);
        mail.setForeground(Color.WHITE);
        mail.setFont(new Font("Tahoma" , Font.BOLD , 16));
        panel.add(mail);

        // CNIC Heading
        JLabel cnic = new JLabel("CNIC");
        cnic.setBounds(720, 95, 250, 45);
        cnic.setForeground(Color.WHITE);
        cnic.setFont(new Font("Tahoma" , Font.BOLD , 16));
        panel.add(cnic);

        // Frame setup
        setUndecorated(true);
        setSize(850, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
        setLocation(530, 130);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new DoctorInfo());
    }
}