package View.User;

import View.CustomComponents.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class StudentTypeModal extends JDialog {
    private RoundedComboBox<String> cmbType;
    private RoundedButton btnSave, btnCancel;
    private Colors color = new Colors();
    private Integer studentId;

    public StudentTypeModal(JFrame parent, Integer studentId) {
        super(parent, "Cambiar Beneficio", true); // true = bloquea la ventana de atrás
        this.studentId = studentId;

        setSize(350, 250);
        setLocationRelativeTo(parent); 
        setUndecorated(true); 

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(color.WHITE);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.LIGHT_GRAY, 2),
            new EmptyBorder(25, 20, 20, 20)
        ));

        // Título
        JLabel lblTitle = new JLabel("Asignar Beneficio", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitle.setForeground(color.OXFORD_BLUE);
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Selector (Combo Box)
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(color.WHITE);
        
        String[] types = {"ESTUDIANTE", "BECARIO", "EXONERADO"};
        cmbType = new RoundedComboBox<>(types);
        cmbType.setPreferredSize(new Dimension(200, 40));
        centerPanel.add(cmbType);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Botones (Guardar y Cancelar)
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setBackground(color.WHITE);

        btnSave = new RoundedButton("Guardar");
        btnSave.setBackground(color.OXFORD_BLUE);
        btnSave.setForeground(color.WHITE);
        btnSave.setPreferredSize(new Dimension(110, 38));
        btnSave.setActionCommand("Guardar");

        btnCancel = new RoundedButton("Cancelar");
        btnCancel.setBackground(color.YELLOW);
        btnCancel.setForeground(Color.BLACK);
        btnCancel.setPreferredSize(new Dimension(110, 38));
        btnCancel.setActionCommand("Cancelar");

        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);

        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    public String getSelectedType() { return (String) cmbType.getSelectedItem(); }
    public Integer getStudentId() { return studentId; }

    public void saveListener(ActionListener listener) { btnSave.addActionListener(listener); }
    public void cancelListener(ActionListener listener) { btnCancel.addActionListener(listener); }
}