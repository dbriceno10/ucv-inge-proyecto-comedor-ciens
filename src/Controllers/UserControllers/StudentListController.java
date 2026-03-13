package Controllers.UserControllers;

import View.User.StudentListView;
import View.User.StudentTypeModal; 
import Model.User.UserService;
import Model.User.UserModel; 

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class StudentListController implements ActionListener {
    private StudentListView view;
    private UserService userService;
    private String currentSearch = ""; 

    public StudentListController(StudentListView view) {
        this.view = view;
        this.userService = new UserService();

        this.view.volverListener(this);
        this.view.setCardListener(this);

        // El buscador solo escucha el teclado
        this.view.getSearchField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                currentSearch = view.getSearchField().getText().trim();
                if (currentSearch.equals("Buscar por cédula...")) currentSearch = "";
                loadStudents(); 
            }
        });

        loadStudents(); 
        this.view.setVisible(true);
        this.view.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Volver")) {
            view.dispose(); 
        } 
        else if (command.startsWith("OPEN_MODAL_")) {
            Integer studentId = Integer.parseInt(command.replace("OPEN_MODAL_", ""));
            openStudentModal(studentId); 
        }
    }

    public void loadStudents() {
        // Le decimos a la vista que se limpie sola
        view.limpiarCuadricula();
        
        try {
            ArrayList<UserModel> students = userService.getAllStudents();
            if (students != null) {
                for (UserModel student : students) {
                    String fullName = student.getFirstName() + " " + student.getLastName();
                    String docId = String.valueOf(student.getDocumentId());
                    String type = student.getType();
                    
                    if (!currentSearch.isEmpty() && 
                        !docId.contains(currentSearch) && 
                        !fullName.toLowerCase().contains(currentSearch.toLowerCase())) {
                        continue; 
                    }
                    
                    view.addStudentCard(student.getId(), fullName, docId, type);
                }
            }
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(view, "Error al cargar los estudiantes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // Le decimos a la vista que se refresque sola
        view.refrescarPantalla();
    }

    private void openStudentModal(Integer studentId) {
        try {
            StudentTypeModal modal = new StudentTypeModal(this.view, studentId);
            new StudentTypeController(modal, this); 
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(view, "Error al abrir opciones: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}