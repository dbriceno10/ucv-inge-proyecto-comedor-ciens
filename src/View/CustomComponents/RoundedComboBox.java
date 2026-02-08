package View.CustomComponents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

// Usamos <E> para que sea genérico (soporte Strings, objetos, etc.)
public class RoundedComboBox<E> extends JComboBox<E> {
    private int arcW = 20; // Curvatura ancho
    private int arcH = 20; // Curvatura alto

    public RoundedComboBox(E[] items) {
        super(items);
        setOpaque(false); // Hacemos transparente el contenedor principal
        setFont(new Font("SansSerif", Font.PLAIN, 12));
        setBackground(Color.WHITE);
        setForeground(Color.DARK_GRAY);
        
        // Borde vacío para dar aire al texto interno
        setBorder(new EmptyBorder(5, 10, 5, 10));

        // AQUÍ ESTÁ EL TRUCO: Reemplazamos la UI por defecto (la cuadrada)
        setUI(new BasicComboBoxUI() {
            
            @Override
            protected JButton createArrowButton() {
                // Creamos un botón "invisible" para la flecha
                JButton btn = new JButton();
                btn.setBorder(BorderFactory.createEmptyBorder());
                btn.setContentAreaFilled(false);
                btn.setFocusPainted(false);
                
                // Dibujamos una flechita sencilla nosotros mismos
                btn.setIcon(new Icon() {
                    @Override
                    public void paintIcon(Component c, Graphics g, int x, int y) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(Color.GRAY);
                        // Coordenadas para un triángulo hacia abajo
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

            // Evitamos que pinte el fondo cuadrado estándar
            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                // No hacer nada aquí para dejar ver nuestro fondo redondeado
            }
        });
    }

    // --- PINTADO DEL FONDO REDONDEADO (IGUAL QUE EN EL TEXTFIELD) ---
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 1. Fondo blanco
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcW, arcH);
        
        // (Opcional) Si quieres pintar el texto seleccionado manualmente, 
        // super.paintComponent(g) lo hace, pero a veces trae fondo. 
        // Al haber anulado paintCurrentValueBackground, esto debería ser seguro:
        super.paintComponent(g2); 
        
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 2. Borde gris
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcW, arcH);
        
        g2.dispose();
    }
}