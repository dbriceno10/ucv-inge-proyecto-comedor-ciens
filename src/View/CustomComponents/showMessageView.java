package View.CustomComponents;

import javax.swing.JOptionPane;
import java.awt.Component;
import java.awt.Window;
import javax.swing.SwingUtilities;

public class showMessageView {
    // methods for displaying native but stylized alerts
    public static void showMsg(Component parent, String msg, int type) {
        String title = "Notificación";

        // The type attribute allows to differentiate whether the screen will display
        // a red 'X' (ERROR_MESSAGE) or a blue 'i' (INFORMATION_MESSAGE).
        if (type == JOptionPane.ERROR_MESSAGE) {
            title = "Error";
        } else if (type == JOptionPane.INFORMATION_MESSAGE) {
            title = "Operación Exitosa";
        }
        
        Window mainWindow = SwingUtilities.getWindowAncestor(parent);

        if (mainWindow == null) {
            JOptionPane.showMessageDialog(parent, msg, title, type);
        } else {
            JOptionPane.showMessageDialog(mainWindow, msg, title, type);
        }
    }
}
