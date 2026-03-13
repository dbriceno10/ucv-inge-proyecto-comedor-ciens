package View.CustomComponents;

import javax.swing.*;
import java.awt.*;

public class FlatPopupMenu extends JPopupMenu {

    public FlatPopupMenu() {
        // Quita el borde relieve por defecto y pone uno plano gris claro
        setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210), 1));
        setBackground(Color.WHITE);
    }
}