package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.Font;

public class RegFormView extends JFrame {
    private JTextField txtFirstName, txtLastName, txtID, txtEmail, txtFaculty, txtType, 
    txtPwd, txtPwd_repeat;
    private JButton btnRegister, btnLogin, btnUpload;

    public RegFormView() {
        setTitle("UCV - SGCU");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //---objects are initialized to avoid null references.
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtID = new JTextField();
        txtEmail = new JTextField();
        txtFaculty = new JTextField();
        txtType = new JTextField();
        txtPwd = new JTextField();
        txtPwd_repeat = new JTextField();
        
        btnRegister = new JButton("Registrarse");
        btnLogin = new JButton("Iniciar sesión");
        btnUpload = new JButton("Cargar documento");

        //---configure JPanel
        JPanel formPanel = new JPanel();
        JLabel titleFormPanel = new JLabel("Registrarse");
        formPanel.setLayout(new GridLayout(0, 2, 20, 15)); // rows - columns - horizontal space - vertical space 
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(titleFormPanel);
        formPanel.add(new JLabel("Nombre")); formPanel.add(txtFirstName);
        formPanel.add(new JLabel("Apellido")); formPanel.add(txtLastName);
        formPanel.add(new JLabel("Cédula")); formPanel.add(txtID);
        formPanel.add(new JLabel("Correo electrónico")); formPanel.add(txtEmail);
        formPanel.add(new JLabel("Facultad")); formPanel.add(txtFaculty);
        formPanel.add(new JLabel("Tipo")); formPanel.add(txtType);
        formPanel.add(new JLabel("Contraseña")); formPanel.add(txtPwd);
        formPanel.add(new JLabel("Repetir contraseña")); formPanel.add(txtPwd_repeat);

        //---BorderLayout is used to center the form and place the buttons at the bottom.
        this.setLayout(new BorderLayout()); //defines the JFrame organization.
        this.add(formPanel, BorderLayout.CENTER); //the panel is added to the JFrame.

        //---a separate panel is used for the buttons.
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnUpload);
        this.add(buttonPanel, BorderLayout.SOUTH); //the panel is added to the JFrame at the bottom.

        this.pack(); //adjust size automatically.
        this.setLocationRelativeTo(null); //center the window.
        this.setVisible(true); //window perceptible to the user.
    }

    public void clearFields() {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtID.setText("");
        txtEmail.setText("");
        txtFaculty.setText("");
        txtType.setText("");
        txtPwd.setText("");
        txtPwd_repeat.setText("");
    }

    //---getters
    public String getFirstName() { return txtFirstName.getText(); }
    public String getLastName() { return txtLastName.getText(); }
    public String getID() { return txtID.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public String getFaculty() { return txtFaculty.getText(); }
    public String getType_() { return txtType.getText(); }
    public String getPwd() { return txtPwd.getText(); }
    public String getPwd_repeat() { return txtPwd_repeat.getText(); }

    //---event listener
    public void addSaveListener(ActionListener listener) { btnRegister.addActionListener(listener);}
    public void addReportListener(ActionListener listener) { btnLogin.addActionListener(listener);}
    public void addExitListener(ActionListener listener) { btnUpload.addActionListener(listener);}

    public void displayReport(String reportData) {
        JTextArea textArea = new JTextArea(reportData);
        textArea.setEditable(false);
        textArea.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(350, 400));
        JOptionPane.showMessageDialog(this, scrollPane, "Inventory Statistics", JOptionPane.INFORMATION_MESSAGE);
    }

}