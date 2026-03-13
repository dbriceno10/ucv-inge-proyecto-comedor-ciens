package Controllers.UserControllers;

import View.User.StudentTypeModal;
import Model.User.UserService;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentTypeController implements ActionListener {
    private StudentTypeModal view;
    private UserService userService;
    private StudentListController parentController;

    public StudentTypeController(StudentTypeModal view, StudentListController parentController) {
        this.view = view;
        this.parentController = parentController;
        this.userService = new UserService();

        this.view.saveListener(this);
        this.view.cancelListener(this);
        
        this.view.setVisible(true); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (command.equals("Cancelar")) {
            view.dispose();
        } 
        else if (command.equals("Guardar")) {
            try {
                String newType = view.getSelectedType();
                Integer studentId = view.getStudentId();
                
                // Llama a la base de datos para guardar el cambio
                userService.changeTypeStudent(studentId, newType);
                
                JOptionPane.showMessageDialog(view, "¡Beneficio actualizado!");
                view.dispose(); 
                
                // Refresca la lista de atrás para que la tarjeta cambie de color
                parentController.loadStudents();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}