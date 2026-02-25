/* import View.Auth.LoginView;
import View.Goalkeeper.GoalkeeperView;
import Controllers.AuthControllers.LoginController;
import Controllers.GoalkeeperControllers.GoalkeeperController;

import javax.swing.SwingUtilities; //to ensure that the graphical interface is handled on its own dedicated thread.

public class Main {
    public static void main(String[] args) {
        /*SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginView view = new LoginView();      
                new LoginController(view);
            }
        });
        GoalkeeperView view = new GoalkeeperView();
        new GoalkeeperController(view);
    }
}

*/

import View.Auth.LoginView;
import Controllers.AuthControllers.LoginController;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // SwingUtilities.invokeLater asegura que la interfaz gráfica 
        // se maneje en su propio hilo dedicado, evitando cuelgues.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 1. Instanciamos la vista del Login
                LoginView view = new LoginView();      
                
                // 2. Le pasamos la vista al controlador para que tome el mando
                new LoginController(view);
            }
        });
    }
}