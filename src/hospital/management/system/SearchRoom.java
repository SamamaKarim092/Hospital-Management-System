package hospital.management.system;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class SearchRoom extends JFrame {
    private Choice choice;

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

    SearchRoom() {
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

        // Apply renderer to all columns
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

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

        // Room Availability Heading
        JLabel heading = new JLabel("Search For Room");
        heading.setBounds(12, 15, 250, 45);
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Tahoma", Font.BOLD, 25));
        panel.add(heading);

        // Status Heading
        JLabel status = new JLabel("Status :");
        status.setBounds(350, 35, 80, 20);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(status);

        // Choice
        choice = new Choice();
        choice.setBounds(450, 37, 150, 20);
        choice.add("Available");
        choice.add("Occupied");
        panel.add(choice);

        // Column Headers
        JLabel number = new JLabel("Room Number");
        number.setBounds(40, 95, 250, 45);
        number.setForeground(Color.WHITE);
        number.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(number);

        JLabel availability = new JLabel("Availability");
        availability.setBounds(275, 95, 250, 45);
        availability.setForeground(Color.WHITE);
        availability.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(availability);

        JLabel realname = new JLabel("Price");
        realname.setBounds(490, 95, 250, 45);
        realname.setForeground(Color.WHITE);
        realname.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(realname);

        JLabel bed = new JLabel("Bed Type");
        bed.setBounds(700, 95, 250, 45);
        bed.setForeground(Color.WHITE);
        bed.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(bed);

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

        // Search Button
        JButton search = new JButton("Search");
        search.setBounds(200, 550, 120, 40);
        search.setFont(new Font("Segoe UI", Font.BOLD, 14));
        search.setFocusPainted(false);
        search.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        search.setBackground(new Color(72, 123, 191));
        search.setForeground(Color.WHITE);
        search.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButtonAnimation(search);
        panel.add(search);
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String q = "select * from room where Availability = '"+choice.getSelectedItem()+"' ";
                try{

                    conn c = new conn();
                    ResultSet resultSet = c.statement.executeQuery(q);
                    table.setModel(DbUtils.resultSetToTableModel(resultSet));

                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });


        // Getting data from the Database
        try {
            conn c = new conn();
            String q = "select * from room";
            ResultSet resultSet = c.statement.executeQuery(q);
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Frame setup
        setUndecorated(true);
        setSize(950, 650);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
        setLocation(530, 130);
    }

    public static void main(String[] args) {
        new SearchRoom();
    }
}