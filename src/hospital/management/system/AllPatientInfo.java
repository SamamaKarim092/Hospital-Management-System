package hospital.management.system;

import net.proteanit.sql.DbUtils;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.ResultSet;

public class AllPatientInfo extends JFrame {

    // Static method to update and get the patient count
    public static int updatePatientCount() {
        int patientCount = 0;
        try {
            // Establishing the connection
            conn c = new conn();

            // Execute query to get the patient count
            ResultSet resultSet = c.statement.executeQuery("SELECT COUNT(*) AS count FROM patient_info");
            if (resultSet.next()) {
                patientCount = resultSet.getInt("count");
            }

            // Optionally, log or update a class variable
            System.out.println("Updated patient count: " + patientCount);

        } catch (Exception e) {
            // Handle and log any exception
            e.printStackTrace();
        }

        // Return the updated count
        return patientCount;
    }



    AllPatientInfo() {

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

        // Table Setup with modernized look
        JTable table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(Color.WHITE);
        table.setBackground(new Color(72, 123, 191, 80));
        table.setRowHeight(35);
        table.setIntercellSpacing(new Dimension(4, 4));
        table.setShowGrid(true);
        table.setGridColor(new Color(255, 255, 255, 30));
        table.setSelectionBackground(new Color(255, 255, 255, 40));
        table.setSelectionForeground(Color.WHITE);
        table.setFocusable(false);

        // Modern header with White Color
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
        header.setBackground(Color.white);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 35));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(255, 255, 255, 50)));

        // Custom cell renderer with dynamic padding
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
                    setBackground(new Color(72, 123, 191, 80));
                    setForeground(Color.WHITE);
                }

                // Dynamic padding based on column content
                String text = value != null ? value.toString() : "";
                int padding = Math.min(text.length() * 2, 15);
                setBorder(BorderFactory.createEmptyBorder(2, padding, 2, padding));

                return c;
            }
        };
        centerRenderer.setHorizontalAlignment(JLabel.LEFT);

        // Enhanced scrollpane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 140, 910, 390);
        scrollPane.setBackground(new Color(72, 123, 191));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 30), 1));
        scrollPane.getViewport().setBackground(new Color(72, 123, 191));

        // Custom scrollbar styling
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(255, 255, 255, 60);
                this.trackColor = new Color(72, 123, 191);
            }
        });
        panel.add(scrollPane);

        // Fetch and display data with dynamic column sizing
        try {
            conn c = new conn();
            ResultSet resultSet = c.statement.executeQuery("select * from patient_info");
            table.setModel(DbUtils.resultSetToTableModel(resultSet));

            // Dynamic column width adjustment
            TableColumnModel columnModel = table.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                // Get maximum width needed for header and data
                int headerWidth = table.getColumnName(i).length() * 10;
                int maxDataWidth = 0;

                for (int row = 0; row < table.getRowCount(); row++) {
                    Object value = table.getValueAt(row, i);
                    if (value != null) {
                        int cellWidth = value.toString().length() * 10;
                        maxDataWidth = Math.max(maxDataWidth, cellWidth);
                    }
                }

                // Set column width based on content (with minimum and maximum constraints)
                int finalWidth = Math.max(headerWidth, maxDataWidth);
                finalWidth = Math.max(80, Math.min(finalWidth, 220)); // Min 60px, Max 200px
                columnModel.getColumn(i).setPreferredWidth(finalWidth);
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }




        // Modern back button with animation
        JButton back = new JButton("Back");
        back.setBounds(20, 550, 120, 40);
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


        // Patient Information Heading
        JLabel info = new JLabel("Patients Information");
        info.setBounds(25, 25, 300, 45);
        info.setForeground(Color.WHITE);
        info.setFont(new Font("Tahoma" , Font.BOLD , 24));
        panel.add(info);


        // ID Heading
        JLabel id = new JLabel("ID");
        id.setBounds(40, 95, 250, 45);
        id.setForeground(Color.WHITE);
        id.setFont(new Font("Tahoma" , Font.BOLD , 14));
        panel.add(id);

        // Number Heading
        JLabel age = new JLabel("Number");
        age.setBounds(130, 95, 250, 45);
        age.setForeground(Color.WHITE);
        age.setFont(new Font("Tahoma" , Font.BOLD , 14));
        panel.add(age);

        // Name Heading
        JLabel realname = new JLabel("Name");
        realname.setBounds(260, 95, 250, 45);
        realname.setForeground(Color.WHITE);
        realname.setFont(new Font("Tahoma" , Font.BOLD , 14));
        panel.add(realname);

        // Gender Heading
        JLabel gender = new JLabel("Gender");
        gender.setBounds(335, 95, 250, 45);
        gender.setForeground(Color.WHITE);
        gender.setFont(new Font("Tahoma" , Font.BOLD , 14));
        panel.add(gender);

        // Disease Heading
        JLabel disease = new JLabel("Disease");
        disease.setBounds(420, 95, 250, 45);
        disease.setForeground(Color.WHITE);
        disease.setFont(new Font("Tahoma" , Font.BOLD , 14));
        panel.add(disease);

        // Room Heading
        JLabel room = new JLabel("Room");
        room.setBounds(560, 95, 250, 45);
        room.setForeground(Color.WHITE);
        room.setFont(new Font("Tahoma" , Font.BOLD , 14));
        panel.add(room);

        // Time Heading
        JLabel time = new JLabel("Time");
        time.setBounds(730, 95, 250, 45);
        time.setForeground(Color.WHITE);
        time.setFont(new Font("Tahoma" , Font.BOLD , 14));
        panel.add(time);

        // Deposit Heading
        JLabel deposit = new JLabel("Deposit");
        deposit.setBounds(860, 95, 250, 45);
        deposit.setForeground(Color.WHITE);
        deposit.setFont(new Font("Tahoma" , Font.BOLD , 14));
        panel.add(deposit);




        // Frame setup
        setUndecorated(true);
        setSize(950, 650);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
        setLocation(530, 130);
    }

    public static void main(String[] args) {
        new AllPatientInfo();
    }
}