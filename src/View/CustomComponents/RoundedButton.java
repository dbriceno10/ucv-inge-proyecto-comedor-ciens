package View.CustomComponents;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {
    private int arcW = 20; // Curvature width
    private int arcH = 20; // Curvature height

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false); // Painting the square by default is avoided
        setFocusPainted(false); // Remove the dotted box by clicking
        setBorderPainted(false); // Removes the default linear border
        
        //hand cursor for the button
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public RoundedButton() {
        setContentAreaFilled(false); 
        setFocusPainted(false);
        setBorderPainted(false); 
        
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // COLOR LOGIC:
        // If the button is pressed, the background color darkens
        if (getModel().isArmed()) {
            g2.setColor(getBackground().darker());
        } else {
            g2.setColor(getBackground()); // Use the color assigned in the view
        }

        // The rounded background is painted
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcW, arcH);
        // The text and icon are painted
        super.paintComponent(g2);
        g2.dispose();
    }
    
    // 'paintBorder' is overridden to prevent it from painting square borders
    @Override
    protected void paintBorder(Graphics g) {
        // intentionally empty
    }
}