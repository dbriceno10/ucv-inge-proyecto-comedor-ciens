package View.Config;

import View.CustomComponents.Colors;
import View.CustomComponents.RoundedButton;
import View.CustomComponents.RoundedTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ConfigView extends JDialog { // Usamos JDialog como en WalletView
    private RoundedTextField txtValueCF, txtStudentPct, txtTeacherPct, txtWorkerPct;
    private JButton btnClose;
    private RoundedButton btnSave;
    private Colors color = new Colors();

    public ConfigView(JFrame parent) {
        super(parent, "Configuración CCB", true);
        setSize(400, 550); // Mismo tamaño que la billetera
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); 
        setUndecorated(true); // Quita la barra superior de Windows

        // Centramos el modal relativo a la ventana principal del admin
        setLocationRelativeTo(parent);

        getContentPane().setBackground(color.BACKGROUND); 
        setLayout(new BorderLayout(15, 15));

        // header (Exactamente igual que en WalletView)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(color.BACKGROUND);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel titleTextPanel = new JPanel();
        titleTextPanel.setLayout(new BoxLayout(titleTextPanel, BoxLayout.Y_AXIS));
        titleTextPanel.setBackground(color.BACKGROUND);
        
        JLabel titleLabel = new JLabel("Configurar CCB");
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        titleTextPanel.add(titleLabel);
   
        // close button (top right)
        btnClose = new JButton("✕");
        btnClose.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnClose.setForeground(color.DARK_GRAY);
        btnClose.setFocusPainted(false);
        btnClose.setBorderPainted(false);
        btnClose.setContentAreaFilled(false); 
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.setActionCommand("CLOSE_CONFIG");

        headerPanel.add(titleTextPanel, BorderLayout.WEST);
        headerPanel.add(btnClose, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // center (El formulario)
        JPanel formPanel = new JPanel(new GridLayout(8, 1, 5, 5));
        formPanel.setBackground(color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        txtValueCF = new RoundedTextField();
        txtStudentPct = new RoundedTextField();
        txtTeacherPct = new RoundedTextField();
        txtWorkerPct = new RoundedTextField();

        formPanel.add(createLabel("Costo Fijo Total (CF):"));
        formPanel.add(txtValueCF);
        formPanel.add(createLabel("Tarifa Estudiantes (%):"));
        formPanel.add(txtStudentPct);
        formPanel.add(createLabel("Tarifa Profesores (%):"));
        formPanel.add(txtTeacherPct);
        formPanel.add(createLabel("Tarifa Empleados (%):"));
        formPanel.add(txtWorkerPct);
        
        add(formPanel, BorderLayout.CENTER);

        // footer: actions
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        footerPanel.setBackground(color.BACKGROUND);
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, color.LIGHT_GRAY));

        btnSave = new RoundedButton("Guardar");
        btnSave.setPreferredSize(new Dimension(150, 40));
        btnSave.setBackground(color.OXFORD_BLUE);
        btnSave.setForeground(color.WHITE);
        btnSave.setActionCommand("SAVE_CONFIG"); // Command explícito

        footerPanel.add(btnSave);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        lbl.setForeground(color.DARK_GRAY);
        return lbl;
    }

    // Getters
    public String getTxtValueCF() { return txtValueCF.getText(); }
    public String getTxtStudentPct() { return txtStudentPct.getText(); }
    public String getTxtTeacherPct() { return txtTeacherPct.getText(); }
    public String getTxtWorkerPct() { return txtWorkerPct.getText(); }

    public void setConfigData(String cf, String student, String teacher, String worker) {
        txtValueCF.setText(cf);
        txtStudentPct.setText(student);
        txtTeacherPct.setText(teacher);
        txtWorkerPct.setText(worker);
    }

    // Listeners
    public void saveListener(ActionListener listener) { btnSave.addActionListener(listener);}
    public void cancelListener(ActionListener listener) { btnClose.addActionListener(listener);}
}