package View;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {
    private int arcW = 20; // Curvatura ancho
    private int arcH = 20; // Curvatura alto

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false); // Evita que se pinte el cuadrado por defecto
        setFocusPainted(false); // Quita el recuadro punteado al hacer click
        setBorderPainted(false); // Quitamos el borde lineal por defecto
        
        // Un cursor de mano siempre queda bien en botones redondeados
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // LÓGICA DE COLOR:
        // Si se está presionando, oscurecemos un poco el color de fondo
        if (getModel().isArmed()) {
            g2.setColor(getBackground().darker());
        } else {
            g2.setColor(getBackground()); // Usa el color que le asignes en la Vista
        }

        // 1. Pintamos el fondo redondeado
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcW, arcH);

        // 2. Pintamos el texto e icono (llamando al padre después del fondo)
        super.paintComponent(g2);
        
        g2.dispose();
    }
    
    // Sobrescribimos paintBorder para que no pinte bordes cuadrados 
    // (o podrías implementar uno redondeado si quisieras)
    @Override
    protected void paintBorder(Graphics g) {
        // Lo dejamos vacío para tener un botón "flat" (plano) y limpio
    }
}