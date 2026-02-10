package Controllers.AuthControllers;

import View.Auth.RegisterView;
import View.Auth.LoginView;
import Model.User.UserService;
import DTO.User.CreateUserDto;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterController implements ActionListener {
    private RegisterView view;
    private UserService userService;

    public RegisterController(RegisterView view) {
        this.view = view;
        this.userService = new UserService();
        
        this.view.btnRegister.addActionListener(this);
        this.view.btnLogin.addActionListener(this); // Botón "Volver al login"
        
        this.view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == view.btnRegister) {
            registerUser();
        } else if (source == view.btnLogin) {
            view.dispose();
            new LoginController(new LoginView());
        }
    }

    private void registerUser() {
        try {
            // Validaciones básicas
            String pass = view.txtPassword.getText();
            String confirm = view.txtConfirmPassword.getText();
            
            if (!pass.equals(confirm)) {
                JOptionPane.showMessageDialog(view, "Las contraseñas no coinciden.");
                return;
            }

            CreateUserDto newUser = new CreateUserDto(
                view.txtNombre.getText(),
                view.txtApellido.getText(),
                view.txtEmail.getText(),
                pass,
                "COMENSAL", // Rol por defecto
                "ESTUDIANTE" // Tipo por defecto
            );
            
            userService.create(newUser);
            
            JOptionPane.showMessageDialog(view, "¡Registro exitoso! Inicia sesión.");
            view.dispose();
            new LoginController(new LoginView());
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
        }
    }
}