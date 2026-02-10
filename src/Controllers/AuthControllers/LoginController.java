package Controllers.AuthControllers;

import View.Auth.*;
import View.Main.DashboardView;
import View.Menu.MenuListView; // Importamos tu vista de Menús
import DTO.User.AuthUserDto;
import Model.User.AuthUserService;
import Controllers.MainControllers.DashboardController;
import Utils.InputValidator;
import Enums.UserRoles; // Importamos los roles para el IF
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
                // pantalla para reiniciar contraseña, no implementado
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
            AuthUserDto auth_user = user.login(txtEmail, txtPwd);
            
            System.out.println("Usuario autenticado: " + auth_user.getFirstName() + " (" + auth_user.getRole() + ")");
            
            UserSession.getInstance().setUser(auth_user);
            
            showMessageView.showMsg(view, "¡Inicio de sesión exitoso!", JOptionPane.INFORMATION_MESSAGE);
            view.dispose();

            // --- AQUÍ ESTÁ EL IF SIMPLE QUE PEDISTE ---
            if (UserRoles.ADMIN.equals(auth_user.getRole())) {
                // Si es ADMIN, solo abrimos la vista y ya
                MenuListView adminView = new MenuListView();
                adminView.setVisible(true); 
            } else {
                // Si NO es admin, abrimos el Dashboard normal
                DashboardView dashboardView = new DashboardView();
                new DashboardController(dashboardView);
            }
            // ------------------------------------------

        } catch (IllegalArgumentException e) {
            showMessageView.showMsg(view, e.getMessage(), JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            showMessageView.showMsg(view, "Error crítico: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
}