package hospital.management.system;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.ResultSet;

// AmbulanceNode class for BST
class AmbulanceNode {
    String ambulanceId;
    String driverName;
    String carName;
    boolean isAvailable;
    String phone;
    String location;
    AmbulanceNode left, right;

    public AmbulanceNode(String ambulanceId, String driverName, String carName,
                         boolean isAvailable, String phone, String location) {
        this.ambulanceId = ambulanceId;
        this.driverName = driverName;
        this.carName = carName;
        this.isAvailable = isAvailable;
        this.phone = phone;
        this.location = location;
        this.left = this.right = null;
    }
}

// BST implementation for Ambulance management
class AmbulanceBST {
    private AmbulanceNode root;

    public AmbulanceBST() {
        root = null;
    }

    public void insert(String ambulanceId, String driverName, String carName,
                       boolean isAvailable, String phone, String location) {
        root = insertRec(root, ambulanceId, driverName, carName, isAvailable, phone, location);
    }

    private AmbulanceNode insertRec(AmbulanceNode root, String ambulanceId, String driverName,
                                    String carName, boolean isAvailable, String phone, String location) {
        if (root == null) {
            return new AmbulanceNode(ambulanceId, driverName, carName, isAvailable, phone, location);
        }

        if (ambulanceId.compareTo(root.ambulanceId) < 0) {
            root.left = insertRec(root.left, ambulanceId, driverName, carName, isAvailable, phone, location);
        } else if (ambulanceId.compareTo(root.ambulanceId) > 0) {
            root.right = insertRec(root.right, ambulanceId, driverName, carName, isAvailable, phone, location);
        }

        return root;
    }

    public AmbulanceNode search(String ambulanceId) {
        return searchRec(root, ambulanceId);
    }

    private AmbulanceNode searchRec(AmbulanceNode root, String ambulanceId) {
        if (root == null || root.ambulanceId.equals(ambulanceId)) {
            return root;
        }

        if (ambulanceId.compareTo(root.ambulanceId) < 0) {
            return searchRec(root.left, ambulanceId);
        }

        return searchRec(root.right, ambulanceId);
    }

    public AmbulanceNode findNearestAvailable(String location) {
        AmbulanceNode[] nearest = {null};
        int[] minDistance = {Integer.MAX_VALUE};
        findNearestAvailableRec(root, location, nearest, minDistance);
        return nearest[0];
    }

    private void findNearestAvailableRec(AmbulanceNode node, String location,
                                         AmbulanceNode[] nearest, int[] minDistance) {
        if (node == null) return;

        if (node.isAvailable) {
            int distance = calculateDistance(location, node.location);
            if (distance < minDistance[0]) {
                minDistance[0] = distance;
                nearest[0] = node;
            }
        }

        findNearestAvailableRec(node.left, location, nearest, minDistance);
        findNearestAvailableRec(node.right, location, nearest, minDistance);
    }

    private int calculateDistance(String location1, String location2) {
        return Math.abs(location1.hashCode() - location2.hashCode()) % 100;
    }
}

public class Ambulance extends JFrame {
    private AmbulanceBST ambulanceBST;

    // Static method to update and get the ambulance count
    public static int updateAmbulanceCount() {
        int ambulanceCount = 0;
        try {
            conn c = new conn();
            ResultSet resultSet = c.statement.executeQuery("SELECT COUNT(*) AS count FROM Ambulance");
            if (resultSet.next()) {
                ambulanceCount = resultSet.getInt("count");
            }
            System.out.println("Updated ambulance count: " + ambulanceCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ambulanceCount;
    }

    // Method to find nearest available ambulance
    public AmbulanceNode findNearestAmbulance(String location) {
        return ambulanceBST.findNearestAvailable(location);
    }

    Ambulance() {
        // Initialize BST
        ambulanceBST = new AmbulanceBST();

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

        // Table Setup
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

        // Table Header Setup
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
        header.setBackground(Color.white);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 35));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(255, 255, 255, 50)));

        // Custom cell renderer
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

                String text = value != null ? value.toString() : "";
                int padding = Math.min(text.length() * 2, 15);
                setBorder(BorderFactory.createEmptyBorder(2, padding, 2, padding));

                return c;
            }
        };
        centerRenderer.setHorizontalAlignment(JLabel.LEFT);

        // Scrollpane Setup
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 140, 910, 390);
        scrollPane.setBackground(new Color(72, 123, 191));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 30), 1));
        scrollPane.getViewport().setBackground(new Color(72, 123, 191));

        // Custom scrollbar
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(255, 255, 255, 60);
                this.trackColor = new Color(72, 123, 191);
            }
        });
        panel.add(scrollPane);

        // Fetch and display data
        try {
            conn c = new conn();
            ResultSet resultSet = c.statement.executeQuery("select * from Ambulance");
            table.setModel(DbUtils.resultSetToTableModel(resultSet));

            // Populate BST while fetching data
            while(resultSet.next()) {
                ambulanceBST.insert(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("car_name"),
                        resultSet.getString("available").equalsIgnoreCase("yes"),
                        resultSet.getString("phone"),
                        resultSet.getString("location")
                );
            }

            // Dynamic column width adjustment
            TableColumnModel columnModel = table.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                int headerWidth = table.getColumnName(i).length() * 10;
                int maxDataWidth = 0;

                for (int row = 0; row < table.getRowCount(); row++) {
                    Object value = table.getValueAt(row, i);
                    if (value != null) {
                        int cellWidth = value.toString().length() * 10;
                        maxDataWidth = Math.max(maxDataWidth, cellWidth);
                    }
                }

                int finalWidth = Math.max(headerWidth, maxDataWidth);
                finalWidth = Math.max(80, Math.min(finalWidth, 220));
                columnModel.getColumn(i).setPreferredWidth(finalWidth);
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Back button with animation
        JButton back = new JButton("Back");
        back.setBounds(20, 550, 120, 40);
        back.setFont(new Font("Segoe UI", Font.BOLD, 14));
        back.setFocusPainted(false);
        back.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        back.setBackground(new Color(72, 123, 191));
        back.setForeground(Color.WHITE);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Button hover effect
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

        // Ambulance Information Heading
        JLabel info = new JLabel("Ambulance Information");
        info.setBounds(25, 25, 300, 45);
        info.setForeground(Color.WHITE);
        info.setFont(new Font("Tahoma", Font.BOLD, 24));
        panel.add(info);

        // Name Heading
        JLabel name = new JLabel("Name");
        name.setBounds(40, 95, 250, 45);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(name);

        // Car Heading
        JLabel car = new JLabel("Car Name");
        car.setBounds(180, 95, 250, 45);
        car.setForeground(Color.WHITE);
        car.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(car);

        // Available Heading
        JLabel available = new JLabel("Available");
        available.setBounds(350, 95, 250, 45);
        available.setForeground(Color.WHITE);
        available.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(available);

        // Phone Heading
        JLabel phone = new JLabel("Phone");
        phone.setBounds(500, 95, 250, 45);
        phone.setForeground(Color.WHITE);
        phone.setFont(new Font("Tahoma", Font.BOLD, 14));

        // Location Heading
        JLabel location = new JLabel("Location");
        location.setBounds(720, 95, 250, 45);
        location.setForeground(Color.WHITE);
        location.setFont(new Font("Tahoma" , Font.BOLD , 14));
        panel.add(location);

        // Frame setup
        setUndecorated(true);
        setSize(950, 650);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
        setLocation(530, 130);

    }

    public static void main(String[] args) {
        new Ambulance();
    }
}
