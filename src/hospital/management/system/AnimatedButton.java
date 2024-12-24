package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

public class AnimatedButton extends JButton {
    private ImageIcon colorIcon;
    private ImageIcon grayIcon;
    private boolean isHovered = false;
    private Timer colorTimer;
    private float transition = 0.0f;
    private final int ANIMATION_STEPS = 10;
    private final int ANIMATION_SPEED = 10; // milliseconds

    public AnimatedButton(String text, String iconPath) {
        super(text);
        setupButton(iconPath);
        setupAnimationTimer();
    }

    private void setupButton(String iconPath) {
        // Load and setup the original color icon
        ImageIcon originalIcon = new ImageIcon(ClassLoader.getSystemResource(iconPath));
        Image img = originalIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        colorIcon = new ImageIcon(img);

        // Create grayscale version
        grayIcon = createGrayscaleIcon(colorIcon);

        // Initial setup
        setIcon(grayIcon);
        setBackground(Color.LIGHT_GRAY);
        setHorizontalTextPosition(SwingConstants.RIGHT);
        setIconTextGap(10);
        setBorderPainted(true);
        setFocusPainted(false);

        // Set initial border and margin
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)
        ));

        // Create hover effect
        setupHoverEffect();
    }

    private void setupAnimationTimer() {
        colorTimer = new Timer(ANIMATION_SPEED, e -> {
            if (isHovered && transition < 1.0f) {
                transition += 1.0f / ANIMATION_STEPS;
                if (transition > 1.0f) transition = 1.0f;
            } else if (!isHovered && transition > 0.0f) {
                transition -= 1.0f / ANIMATION_STEPS;
                if (transition < 0.0f) transition = 0.0f;
            }

            // Update colors
            Color background = interpolateColor(Color.LIGHT_GRAY, Color.YELLOW, transition);
            setBackground(background);

            // Update border for pop effect
            Color borderColor = interpolateColor(Color.LIGHT_GRAY, new Color(255, 200, 0), transition);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(borderColor, 1),
                    BorderFactory.createEmptyBorder(
                            (int)(4 + transition * 2),
                            (int)(4 + transition * 2),
                            (int)(4 + transition * 2),
                            (int)(4 + transition * 2)
                    )
            ));

            // Update icon
            if (transition > 0.5f && getIcon() != colorIcon) {
                setIcon(colorIcon);
            } else if (transition <= 0.5f && getIcon() != grayIcon) {
                setIcon(grayIcon);
            }

            // Stop timer if animation is complete
            if ((isHovered && transition >= 1.0f) || (!isHovered && transition <= 0.0f)) {
                ((Timer)e.getSource()).stop();
            }

            repaint();
        });
    }

    private void setupHoverEffect() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                colorTimer.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                colorTimer.start();
            }
        });
    }

    private Color interpolateColor(Color c1, Color c2, float ratio) {
        int red = (int)(c1.getRed() * (1 - ratio) + c2.getRed() * ratio);
        int green = (int)(c1.getGreen() * (1 - ratio) + c2.getGreen() * ratio);
        int blue = (int)(c1.getBlue() * (1 - ratio) + c2.getBlue() * ratio);
        return new Color(red, green, blue);
    }

    private ImageIcon createGrayscaleIcon(ImageIcon original) {
        Image img = original.getImage();
        BufferedImage buffered = new BufferedImage(
                img.getWidth(null),
                img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g2d = buffered.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        // Convert to grayscale
        for (int x = 0; x < buffered.getWidth(); x++) {
            for (int y = 0; y < buffered.getHeight(); y++) {
                int rgb = buffered.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xff;
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;
                int grayValue = (r + g + b) / 3;
                int gray = (alpha << 24) | (grayValue << 16) | (grayValue << 8) | grayValue;
                buffered.setRGB(x, y, gray);
            }
        }

        return new ImageIcon(buffered);
    }
}