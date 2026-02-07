package View.Auth;

import javax.swing.JOptionPane;
import java.awt.Component;

public class showMessageView {
    // methods for displaying native but stylized alerts
    public static void showMsg(Component parent, String msg, int type) {
        String title = "Notificación";

        if (type == JOptionPane.ERROR_MESSAGE) {
            title = "Error de Registro";
        } else if (type == JOptionPane.INFORMATION_MESSAGE) {
            title = "Operación Exitosa";
        }
        // The type attribute allows to differentiate whether the screen will display
        // a red 'X' (ERROR_MESSAGE) or a blue 'i' (INFORMATION_MESSAGE).
        JOptionPane.showMessageDialog(parent, msg, title, type);
    }
}
