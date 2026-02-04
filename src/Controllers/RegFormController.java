package Controllers;
import View.RegFormView;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegFormController implements ActionListener{
    private RegFormView view;

    public RegFormController(RegFormView view) {
        this.view = view;

        this.view.addSaveListener(this);
        this.view.addReportListener(this);
        this.view.addExitListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand();
        switch (button) {
            case "Registrarse":
                //get_RegFormData();
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
        }
    }

    public void get_RegFormData() {
            String firstName = view.getFirstName();
            String lastName = view.getLastName();
            String ID = view.getID();
            String email = view.getEmail();
            String faculty = view.getFaculty();
            String type = view.getType_();
            String pwd = view.getPwd();
            String pwd_repeat = view.getPwd_repeat();
            String name_resp = view.getPwd_repeat();

            //validar registro, guardar datos...
    }
}

