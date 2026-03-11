package Controllers.UserControllers;

import View.User.StudentListView;
import View.User.StudentTypeModal; 
import Model.User.UserService;
import Model.User.UserModel; 

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

public class StudentListController implements ActionListener {
    private StudentListView view;
    private UserService userService;
    private String currentSearch = ""; // Guarda lo que estamos buscando

    public StudentListController(StudentListView view) {
        this.view = view;
        this.userService = new UserService();

        this.view.volverListener(this);
        this.view.setCardListener(this);

        // --- MAGIA DEL BUSCADOR ---
        // 1. Quitar el texto por defecto al hacer clic
        this.view.getSearchField().addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (view.getSearchField().getText().trim().equals("Buscar por cédula...")) {
                    view.getSearchField().setText("");
                }
            }
            public void focusLost(FocusEvent e) {
                if (view.getSearchField().getText().trim().isEmpty()) {
                    view.getSearchField().setText(" Buscar por cédula...");
                }
            }
        });

        // 2. Filtrar cada vez que se presiona una tecla
        this.view.getSearchField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                currentSearch = view.getSearchField().getText().trim();
                if (currentSearch.equals("Buscar por cédula...")) currentSearch = "";
                loadStudents(); // Recarga la lista aplicando el filtro
            }
        });
        // --------------------------

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
            String idString = command.replace("OPEN_MODAL_", "");
            Integer studentId = Integer.parseInt(idString);
            openStudentModal(studentId); 
        }
    }

    public void loadStudents() {
        JPanel panel = view.getComponent_gridPanel();
        panel.removeAll(); 
        
        try {
            ArrayList<UserModel> students = userService.getAllStudents();
            if (students != null) {
                for (UserModel student : students) {
                    String fullName = student.getFirstName() + " " + student.getLastName();
                    String docId = String.valueOf(student.getDocumentId());
                    String type = student.getType();
                    
                    // FILTRO DE BÚSQUEDA: Si escribimos algo y no coincide ni la cédula ni el nombre, lo saltamos
                    if (!currentSearch.isEmpty() && 
                        !docId.contains(currentSearch) && 
                        !fullName.toLowerCase().contains(currentSearch.toLowerCase())) {
                        continue; 
                    }
                    
                    view.addStudentCard(student.getId(), fullName, docId, type);
                }
            }
        } catch (Exception ex) {
            System.out.println("Error cargando estudiantes: " + ex.getMessage());
        }
        
        panel.revalidate();
        panel.repaint();
    }

    private void openStudentModal(Integer studentId) {
        try {
            StudentTypeModal modal = new StudentTypeModal(this.view, studentId);
            new StudentTypeController(modal, this); 
        } catch (Exception ex) {
            System.out.println("💥 Error al abrir el modal 💥");
            ex.printStackTrace();
        }
    }
}