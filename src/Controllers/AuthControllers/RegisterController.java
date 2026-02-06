package Controllers.AuthControllers;
import View.Auth.RegisterView;
import Model.User.AuthUserService;

import java.awt.event.ActionListener;

import javax.swing.JTextField;

import java.awt.event.ActionEvent;

public class RegisterController implements ActionListener{
    private RegisterView view;

    public RegisterController(RegisterView view) {
        this.view = view;

        this.view.registerListener(this);
        this.view.loginListener(this);
        this.view.uploadListener(this);

        addInputRestriction(this.view.getComponentFirstName(), "ONLY_LETTERS", 40);
        addInputRestriction(this.view.getComponentLastName(), "ONLY_LETTERS", 40);
        addInputRestriction(this.view.getComponentdocumentId(), "ONLY_NUMBERS", 8);
        addInputRestriction(this.view.getComponentEmail(), "DEFAULT", 50);
        addInputRestriction(this.view.getComponentPwd(), "DEFAULT", 12);
        addInputRestriction(this.view.getComponentPwd_repeat(), "DEFAULT", 12);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand();
        switch (button) {
            case "Registrarse":
                processRegistration();
                System.out.println("case Registarse");
                break;
            case "Iniciar sesión":
                //redirigir a la vista.
                System.out.println("case Iniciar sesión");
                break;
            case "Cargar documento":
                //
                System.out.println("case Cargar documento");
                break;
            default:
        }
    }

    private void processRegistration() {
            String firstName = view.getFirstName();
            String lastName = view.getLastName();
            String documentId = view.get_documentId();
            String email = view.getEmail();
            String faculty = view.getFaculty();
            String type = view.getType_();
            String pwd = view.getPwd();
            String pwd_repeat = view.getPwd_repeat();

            AuthUserService user = new AuthUserService();
            user.register(email, type, firstName, lastName, pwd, pwd_repeat);
    }

    private void addInputRestriction(JTextField field, String type, int limit) {
    field.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override
        public void keyTyped(java.awt.event.KeyEvent e) {
            char c = e.getKeyChar();
            if (Character.isISOControl(c)) { return; }

            int selectionLen = (field.getSelectedText() != null) ? field.getSelectedText().length() : 0;
            if (field.getText().length() - selectionLen >= limit) {
                e.consume(); 
                return;
            }
            switch (type) {
                case "ONLY_LETTERS":
                    if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                        e.consume();
                    }
                    break;
                case "ONLY_NUMBERS":
                    if (!Character.isDigit(c)) {
                        e.consume();
                    }
                    break;
            }
        }
    });
    }
}

