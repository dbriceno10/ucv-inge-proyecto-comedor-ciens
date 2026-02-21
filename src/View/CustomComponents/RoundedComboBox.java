package View.CustomComponents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

import java.awt.*;

// <E> is used to make it generic (supports Strings, objects, etc.)
public class RoundedComboBox<E> extends JComboBox<E> {
    private int arcW = 20; // Curvature width
    private int arcH = 20; // Curvature height

    public RoundedComboBox(E[] items) {
        super(items);
        setOpaque(false); // The main container becomes transparent
        setFont(new Font("SansSerif", Font.PLAIN, 12));
        setBackground(Color.WHITE);
        setForeground(Color.DARK_GRAY);
        // Empty border to give space to the internal text
        setBorder(new EmptyBorder(5, 10, 5, 10));

        // Important: The default user interface (the square one) is replaced
        setUI(new BasicComboBoxUI() {
            
            @Override
            protected JButton createArrowButton() {
                // An "invisible" button is created for the arrow
                JButton btn = new JButton();
                btn.setBorder(BorderFactory.createEmptyBorder());
                btn.setContentAreaFilled(false);
                btn.setFocusPainted(false);
                
                // A simple arrow is drawn
                btn.setIcon(new Icon() {
                    @Override
                    public void paintIcon(Component c, Graphics g, int x, int y) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(Color.GRAY);
                        // Coordinates for a downward-pointing triangle
                        int size = 8;
                        int[] xPoints = {x, x + size, x + size / 2};
                        int[] yPoints = {y, y, y + size - 2};
                        g2.fillPolygon(xPoints, yPoints, 3);
                        g2.dispose();
                    }
                    @Override
                    public int getIconWidth() { return 10; }
                    @Override
                    public int getIconHeight() { return 10; }
                });
                return btn;
            }

            // It prevents the painting of the standard square background.
            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                // intentionally empty
            }
        });
    }

    // The rounded background is painted
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(getBackground()); // White background
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcW, arcH);
        
        super.paintComponent(g2); 
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