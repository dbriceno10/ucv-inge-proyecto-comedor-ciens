import javax.swing.SwingUtilities;
import View.MenuEditorView;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Solo una línea para evitar conflictos
            new MenuEditorView(); 
        });
    }
}