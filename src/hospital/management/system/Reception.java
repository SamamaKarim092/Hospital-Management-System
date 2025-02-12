package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Reception extends JFrame {
    private JPanel leftPanel;
    private JPanel rightPanel;
    private DashboardPanel dashboardPanel;
    private static List<Reception> instances = new ArrayList<>();
    private String userName;

    // Queue to manage dashboard updates
    private Queue<DashboardUpdate> updateQueue;
    private Thread updateProcessor;

    // Class to encapsulate dashboard update data
    private static class DashboardUpdate {
        final int totalDoctors;
        final int totalPatients;
        final int activePatients;
        final int totalAppointments;

        DashboardUpdate(int doctors, int patients, int active, int appointments) {
            this.totalDoctors = doctors;
            this.totalPatients = patients;
            this.activePatients = active;
            this.totalAppointments = appointments;
        }
    }

    // Static method to refresh all Reception instances
    public static void refreshAllDashboards() {
        // Safely iterate through all instances and trigger updates
        for (Reception reception : instances) {
            if (reception != null && reception.isDisplayable()) {
                reception.refreshDashboard();
            }
        }
    }

    // Add this method to handle individual dashboard refresh
    public void refreshDashboard() {
        // Get latest counts from database
        int totalDoctors = DoctorInfo.updateDoctorCount();
        int totalPatients = AllPatientInfo.updatePatientCount();
        int activePatients = RoomAvailability.getOccupiedRoomCount();
        int totalAppointments = Ambulance.updateAmbulanceCount();

        // Add update to queue
        updateQueue.offer(new DashboardUpdate(
                totalDoctors,
                totalPatients,
                activePatients,
                totalAppointments
        ));
    }

    Reception(String userName) {
        this.userName = userName;
        System.out.println("Welcome, " + userName + "!");
        instances.add(this);

        // Initialize the update queue
        this.updateQueue = new LinkedBlockingQueue<>();

        setupPanels();
        setupHeaderAndLogo();
        setupFeatureSection();
        setupButtons();
        setupMainFrame();
        startUpdateProcessor();
        updateDashboardStats();
    }

    private void startUpdateProcessor() {
        updateProcessor = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (!updateQueue.isEmpty()) {
                    DashboardUpdate update = updateQueue.poll();
                    if (update != null) {
                        SwingUtilities.invokeLater(() -> {
                            dashboardPanel.updateAllStats(
                                    update.totalDoctors,
                                    update.totalPatients,
                                    update.activePatients,
                                    update.totalAppointments
                            );
                        });
                    }
                }
                try {
                    Thread.sleep(100); // Small delay to prevent CPU overuse
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        updateProcessor.setDaemon(true);
        updateProcessor.start();
    }

    private void updateDashboardStats() {
        // Get latest counts from database
        int totalDoctors = DoctorInfo.updateDoctorCount();
        int totalPatients = AllPatientInfo.updatePatientCount();
        int activePatients = RoomAvailability.getOccupiedRoomCount();
        int totalAppointments = Ambulance.updateAmbulanceCount();

        // Add update to queue instead of updating directly
        updateQueue.offer(new DashboardUpdate(
                totalDoctors,
                totalPatients,
                activePatients,
                totalAppointments
        ));
    }

    @Override
    public void dispose() {
        if (updateProcessor != null) {
            updateProcessor.interrupt();
        }
        instances.remove(this);
        super.dispose();
    }


    private void setupPanels() {
        leftPanel = createPanel(0, 0, 400, 1525, Color.WHITE);
        rightPanel = createPanel(0, 0, 1525, 1525, new Color(109, 164, 170));

        // Add the HeaderPanel with user name
        HeaderPanel headerPanel = new HeaderPanel(userName);
        rightPanel.add(headerPanel);

        dashboardPanel = new DashboardPanel();
        dashboardPanel.setBounds(420, 80, 1000, 125);
        rightPanel.add(dashboardPanel);

        // Add credit text at the bottom
        JLabel creditLabel = new JLabel("Done By :  Samama Karim || Tabin Alam || Taimoor Abrar");
        creditLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        creditLabel.setForeground(new Color(72, 123, 191));
        creditLabel.setBackground(new Color(255, 255, 255, 255));  // 128 is the alpha value (50% opacity)
        creditLabel.setOpaque(true); // This makes the background visible
        creditLabel.setBounds(600, 750, 700, 30); // Reduced height to match text
        creditLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text
        rightPanel.add(creditLabel);

        add(leftPanel);
        add(rightPanel);
    }


    private JPanel createPanel(int x, int y, int width, int height, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(x, y, width, height);
        panel.setBackground(color);
        return panel;
    }

    private void setupHeaderAndLogo() {
        addStyledLabel("   Hospital Management System", 20, 20, 350, 40,
                new Font("Tahoma", Font.BOLD, 20), Color.white, new Color(72, 123, 191));
        addImage("icon/Ambulance + sign.jpg", 50, 40, 250, 200);
    }

    private void setupFeatureSection() {
        addStyledLabel("   Features", 20, 250, 300, 40,
                new Font("Tahoma", Font.BOLD, 20), Color.white, new Color(72, 123, 191));
        addStyledLabel("   Patient", 8, 300, 75, 40,
                new Font("Tahoma", Font.PLAIN, 15), Color.GRAY, Color.WHITE);
        addStyledLabel("   Department", 8, 425, 100, 40,
                new Font("Tahoma", Font.PLAIN, 15), Color.GRAY, Color.WHITE);
    }

    private void addStyledLabel(String text, int x, int y, int width, int height,
                                Font font, Color foreground, Color background) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setFont(font);
        label.setForeground(foreground);
        label.setBackground(background);
        label.setOpaque(true);
        leftPanel.add(label);
    }

    private void addImage(String path, int x, int y, int width, int height) {
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(path));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        JLabel label = new JLabel(new ImageIcon(img));
        label.setBounds(x, y, width, height);
        leftPanel.add(label);
    }

    private void setupButtons() {
        createStyledButton("Add new Patient", "icon/Add patient button.png", 15, 340, e -> new AddNewPatient());
        createStyledButton("Patient Info", "icon/Patient info button.png", 205, 340, e -> new AllPatientInfo());
        createStyledButton("Update Patient Info", "icon/Update patient button.png", 15, 380, e -> new UpdatePatientInfo());
        createStyledButton("Patient Discharge", "icon/Patient discharge button.png", 205, 380, e -> new PatientDischarge());
        createStyledButton("Search Room", "icon/Search room button.png", 15, 468, e -> new SearchRoom());
        createStyledButton("Available Room", "icon/Available room button.png", 205, 468, e -> new RoomAvailability());
        createStyledButton("Department Info", "icon/Department Info button.png", 15, 508, e -> new Department());
        createStyledButton("Doctor Info", "icon/Doctor info button.png", 205, 508, e -> new DoctorInfo());
        createStyledButton("Ambulance Detail", "icon/Ambulance detail button.png", 15, 550, e -> new Ambulance());

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(15, 725, 180, 30);
        logoutBtn.setBackground(Color.RED);
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 12));
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Login();
            }
        });
        logoutBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logoutBtn.setBackground(new Color(178, 34, 34));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logoutBtn.setBackground(Color.RED);
            }
        });
        leftPanel.add(logoutBtn);
    }

    private void createStyledButton(String text, String iconPath, int x, int y,
                                    java.awt.event.ActionListener action) {
        AnimatedButton button = new AnimatedButton(text, iconPath);
        button.setBounds(x, y, 180, 30);
        button.addActionListener(action);
        leftPanel.add(button);
    }

    private void setupMainFrame() {
        setSize(1950, 1090);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setVisible(true);
        setTitle("Hospital Management System - Reception");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String loggedInUserName = "Admin";
            new Reception(loggedInUserName);
        });
    }
}