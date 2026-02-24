import View.Auth.LoginView;
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
        });*/
        GoalkeeperView view = new GoalkeeperView();
        new GoalkeeperController(view);
    }
}