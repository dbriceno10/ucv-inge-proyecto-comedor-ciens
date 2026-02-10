import View.Auth.LoginView;
import Controllers.AuthControllers.LoginController;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Aseguramos que la interfaz inicie limpia
            try {
                LoginView view = new LoginView();      
                new LoginController(view);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error al iniciar la aplicación: " + e.getMessage());
            }
        });
    }
}