import Controllers.RegFormController;
import View.RegFormView;
import javax.swing.SwingUtilities; //to ensure that the graphical interface is handled on its own dedicated thread.

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegFormView view = new RegFormView();
            
            new RegFormController(view);

        }); 
    }
}