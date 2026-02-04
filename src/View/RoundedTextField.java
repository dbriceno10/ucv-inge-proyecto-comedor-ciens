package View;

import javax.swing.*;
import java.awt.*;

public class RoundedTextField extends JTextField {
    private int arcW = 20; // Curvatura ancho
    private int arcH = 20; // Curvatura alto

    public RoundedTextField() {
        setOpaque(false); // Hacemos transparente el fondo cuadrado original
        // Margen interno para que el texto no toque la curva
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        // Activar suavizado de bordes (Antialiasing)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 1. Pintar el fondo blanco redondeado
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcW, arcH);
        
        super.paintComponent(g2); // Pintar el texto encima
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 2. Pintar el borde gris redondeado
        g2.setColor(Color.LIGHT_GRAY); 
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcW, arcH);
        
        g2.dispose();
    }
}
