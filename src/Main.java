import View.Auth.LoginView;
import Controllers.AuthControllers.LoginController;

import javax.swing.SwingUtilities; //to ensure that the graphical interface is handled on its own dedicated thread.

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginView view = new LoginView();      
                new LoginController(view);
            }
        });
    }
}