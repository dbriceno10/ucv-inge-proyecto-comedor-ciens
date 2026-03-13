package View.CustomComponents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FlatMenuItem extends JMenuItem {

    public FlatMenuItem(String text) {
        super(text); // Llama al constructor original de JMenuItem para poner el texto

        // Configuración visual nativa
        setFont(new Font("SansSerif", Font.PLAIN, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(true);
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding interno (Arriba, Izq, Abajo, Der)

        // Efecto hover encapsulado en el propio componente
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(245, 245, 245)); // Gris muy claro al pasar el cursor
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.WHITE); // Vuelve a blanco al quitar el cursor
            }
        });
    }
}