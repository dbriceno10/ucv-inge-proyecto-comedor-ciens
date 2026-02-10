import View.Auth.*;
import View.Admin.*;
import View.Main.*;
import Controllers.AuthControllers.*;
import Controllers.MainControllers.*;

import javax.swing.SwingUtilities; //to ensure that the graphical interface is handled on its own dedicated thread.

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView view = new LoginView();      
            
            new LoginController(view);

        });
    }
}