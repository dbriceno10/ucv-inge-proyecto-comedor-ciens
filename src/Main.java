import javax.swing.SwingUtilities;
import View.IngredientEditorView; 

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new IngredientEditorView(); 
        });
    }
}