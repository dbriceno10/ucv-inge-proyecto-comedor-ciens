package Controllers.AuthControllers;

import View.Auth.*;
import Model.User.AuthUserService;
import Utils.InputValidator;

import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

public class RegisterController implements ActionListener {
    private RegisterView view;

    public RegisterController(RegisterView view) {
        this.view = view;

        this.view.registerListener(this);
        this.view.loginListener(this);
        this.view.uploadListener(this);

        InputValidator.addInputRestriction(this.view.getComponentFirstName(), "ONLY_LETTERS", 30);
        InputValidator.addInputRestriction(this.view.getComponentLastName(), "ONLY_LETTERS", 30);
        InputValidator.addInputRestriction(this.view.getComponentdocumentId(), "ONLY_NUMBERS", 8);
        InputValidator.addInputRestriction(this.view.getComponentEmail(), "DEFAULT", 40);
        InputValidator.addInputRestriction(this.view.getComponentPwd(), "DEFAULT", 12);
        InputValidator.addInputRestriction(this.view.getComponentPwd_repeat(), "DEFAULT", 12);

        this.view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand();
        switch (button) {
            case "Registrarse":
                processRegistration();
                break;
            case "Iniciar sesión":
                view.dispose();
                LoginView loginView = new LoginView();
                new LoginController(loginView);
                break;
            case "Cargar documento":
                //
                break;
            default:
                break;
        }
    }

    private void processRegistration() {
        String firstName = view.getFirstName();
        String lastName = view.getLastName();
        Integer documentId = view.get_documentId();
        String email = view.getEmail();
        String faculty = view.getFaculty();
        String type = view.getType_();
        String pwd = view.getPwd();
        String pwd_repeat = view.getPwd_repeat();

        try {
            AuthUserService user = new AuthUserService();
            user.register(email, type, firstName, lastName, pwd, pwd_repeat, documentId);
            showMessageView.showMsg(view, "¡Usuario registrado correctamente!", JOptionPane.INFORMATION_MESSAGE);
            //redirigir pantalla login
        } catch (IllegalArgumentException e) {
            showMessageView.showMsg(view, e.getMessage(), JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            showMessageView.showMsg(view, "Error crítico: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
}

