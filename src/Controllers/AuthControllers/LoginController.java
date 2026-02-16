package Controllers.AuthControllers;

import View.Auth.*;
import View.Main.DashboardView;
import View.Menu.MenuManagementView; 
import DTO.User.AuthUserDto;
import Model.User.AuthUserService;
import Controllers.MainControllers.DashboardController;
import Controllers.MenuControllers.MenuManagementController;
import Utils.InputValidator;
import Enums.UserRoles;
import Context.User.UserSession;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class LoginController implements ActionListener {
    private LoginView view;

    public LoginController(LoginView view) {
        this.view = view;

        this.view.loginListener(this);
        this.view.registerListener(this);
        this.view.forgotPwdListener(this);

        InputValidator.addInputRestriction(this.view.getComponentEmail(), "DEFAULT", 40);
        InputValidator.addInputRestriction(this.view.getComponentPwd(), "DEFAULT", 30);

        this.view.setVisible(true);
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
                //
                break;
            default: break;
        }
    }

    private void processLogin() {
        String txtEmail = view.getEmail_login();
        String txtPwd = view.getPwd_login();

        try {
            AuthUserService user = new AuthUserService();
            AuthUserDto auth_user = user.login(txtEmail, txtPwd);

            UserSession.getInstance().setUser(auth_user);
            showMessageView.showMsg(view, "¡Inicio de sesión exitoso!", JOptionPane.INFORMATION_MESSAGE);
            view.dispose();

            if (UserRoles.ADMIN.equals(auth_user.getRole())) {
                MenuManagementView adminView = new MenuManagementView();
                new MenuManagementController(adminView);
            } else {
                DashboardView dashboardView = new DashboardView();
                new DashboardController(dashboardView);
            }
            
        } catch (IllegalArgumentException e) {
            showMessageView.showMsg(view, e.getMessage(), JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            showMessageView.showMsg(view, "Error crítico: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
}