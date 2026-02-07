package Controllers.AuthControllers;

import View.Auth.*;
import View.Main.DashboardView;
import Model.User.AuthUserModel;
import Model.User.AuthUserService;
import Controllers.MainControllers.DashboardController;
import Utils.InputValidator;

import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

public class LoginController implements ActionListener {
    private LoginView view;

    LoginController(LoginView view) {
        this.view = view;

        this.view.loginListener(this);
        this.view.registerListener(this);
        this.view.forgotPwdListener(this);

        InputValidator.addInputRestriction(this.view.getComponentEmail(), "ONLY_LETTERS", 30);
        InputValidator.addInputRestriction(this.view.getComponentPwd(), "ONLY_LETTERS", 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand();
        switch (button) {
            case "Iniciar sesión":
                processLogin();
                break;
            case "Registrarse":
                view.dispose();
                RegisterView regView = new RegisterView();
                new RegisterController(regView);
                break;
            case "¿Olvidó su contraseña?":
                //pantalla para reiniciar contraseña, no implementado
                break;
            default:
                break;
        }
    }

    private void processLogin() {
        String txtEmail = view.getEmail_login();
        String txtPwd = view.getPwd_login();

        try {
            AuthUserService user = new AuthUserService();
            AuthUserModel auth_user = user.login(txtEmail, txtPwd);
            showMessageView.showMsg(view, "¡Inicio de sesión exitoso!", JOptionPane.INFORMATION_MESSAGE);

            //redirigir al dashboard, pasar data del user
            view.dispose();
            DashboardView dashboardView = new DashboardView();
            new DashboardController(dashboardView);
        
        } catch (IllegalArgumentException e) {
            showMessageView.showMsg(view, e.getMessage(), JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            showMessageView.showMsg(view, "Error crítico: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
}
