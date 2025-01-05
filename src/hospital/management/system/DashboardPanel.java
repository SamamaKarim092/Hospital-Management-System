package hospital.management.system;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class DashboardPanel extends JPanel {
    private JPanel doctorPanel;
    private JPanel patientPanel;
    private JPanel activePatientPanel;
    private JPanel appointmentPanel;

    private JLabel doctorCountLabel;
    private JLabel patientCountLabel;
    private JLabel activePatientCountLabel;
    private JLabel appointmentCountLabel;

    public DashboardPanel() {
        setLayout(new GridLayout(1, 4, 10, 0));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setOpaque(false);

        // Initialize all panels
        initializePanels();

        // Add panels to main dashboard
        add(doctorPanel);
        add(patientPanel);
        add(activePatientPanel);
        add(appointmentPanel);
    }

    private void initializePanels() {
        // Doctors Panel
        ImageIcon doctorIcon = loadIcon("icon/doctor-icon.png", "\u2695"); // Medical symbol as fallback
        doctorPanel = createStatsPanel("Total Doctors", doctorIcon);
        doctorCountLabel = createCountLabel("0");
        doctorPanel.add(doctorCountLabel);

        // Patients Panel
        ImageIcon patientIcon = loadIcon("icon/patients-icon.png", "\u263A"); // Smiley face as fallback
        patientPanel = createStatsPanel("Total Patients", patientIcon);
        patientCountLabel = createCountLabel("0");
        patientPanel.add(patientCountLabel);

        // Active Patients Panel
        ImageIcon activePatientIcon = loadIcon("icon/Search room button.png", "\u2764"); // Heart as fallback
        activePatientPanel = createStatsPanel("Occupied Room", activePatientIcon);
        activePatientCountLabel = createCountLabel("0");
        activePatientPanel.add(activePatientCountLabel);

        // Appointments Panel
        ImageIcon appointmentIcon = loadIcon("icon/Ambulance detail button.png", "\u23F3"); // Clock as fallback
        appointmentPanel = createStatsPanel("Available Ambulance", appointmentIcon);
        appointmentCountLabel = createCountLabel("0");
        appointmentPanel.add(appointmentCountLabel);
    }

    private ImageIcon loadIcon(String path, String fallbackSymbol) {
        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(path));
            if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            }
        } catch (Exception e) {
            // If icon loading fails, return null and we'll use the fallback symbol
        }
        return null;
    }

    private JPanel createStatsPanel(String title, ImageIcon icon) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(72, 123, 191)); // Matching your blue color
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create icon label
        JLabel iconLabel;
        if (icon != null) {
            iconLabel = new JLabel(icon);
        } else {
            // Use Unicode symbol if icon is null
            iconLabel = new JLabel(title.contains("Doctor") ? "\u2695" :
                    title.contains("Patient") ? "\u263A" :
                            title.contains("Active") ? "\u2764" : "\u23F3");
            iconLabel.setFont(new Font("Arial", Font.PLAIN, 32));
            iconLabel.setForeground(Color.WHITE);
        }
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create title label
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components
        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(titleLabel);

        return panel;
    }

    private JLabel createCountLabel(String initialValue) {
        JLabel label = new JLabel(initialValue);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Tahoma", Font.BOLD, 24));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    // Update methods for each statistic
    public void updateDoctorCount(int count) {
        doctorCountLabel.setText(String.valueOf(count));
    }


    public void updatePatientCount(int count) {
        patientCountLabel.setText(String.valueOf(count));
    }

    public void updateActivePatientCount(int count) {
        activePatientCountLabel.setText(String.valueOf(count));
    }

    public void updateAppointmentCount(int count) {
        appointmentCountLabel.setText(String.valueOf(count));
    }

    // Method to update all statistics at once
    public void updateAllStats(int doctors, int patients, int activePatients, int appointments) {
        updateDoctorCount(doctors);
        updatePatientCount(patients);
        updateActivePatientCount(activePatients);
        updateAppointmentCount(appointments);
    }
}