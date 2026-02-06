import View.Auth.RegisterView;
import View.Main.DashboardView;
import View.Auth.LoginView;

import javax.swing.SwingUtilities; //to ensure that the graphical interface is handled on its own dedicated thread.

import Controllers.AuthControllers.RegisterController;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegisterView view = new RegisterView();
            
            new RegisterController(view);

        });
    }
}