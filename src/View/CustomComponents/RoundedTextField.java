package View.CustomComponents;

import javax.swing.*;
import java.awt.*;

public class RoundedTextField extends JTextField {
    private int arcW = 20; // Curvature width
    private int arcH = 20; // Curvature height

    public RoundedTextField() {
        setOpaque(false); // The original square background becomes transparent
        // Internal margin so that the text does not touch the curve
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        // Edge smoothing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // The white background of the rounded figure is painted
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcW, arcH);
        
        super.paintComponent(g2); // The text is painted on top
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(Color.LIGHT_GRAY); // Gray border
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcW, arcH);
        g2.dispose();
    }
}
