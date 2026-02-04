package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

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

        // 1. main panel (the white background)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        mainPanel.setBackground(Color.WHITE);

        // 2. form panel: it has two columns to organize the fields.
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 20, 15)); // rows - columns - horizontal space - vertical space 
        formPanel.setBackground(Color.WHITE);

        formPanel.add(newField("NOMBRE", txtFirstName));
        formPanel.add(newField("APELLIDO", txtLastName));
        formPanel.add(newField("CÉDULA", txtID));
        formPanel.add(newField("CORREO ELECTRÓNICO", txtEmail));
        formPanel.add(newField("FACULTAD", txtFaculty));
        formPanel.add(newField("TIPO", txtType));
        formPanel.add(newField("CONTRASEÑA", txtPwd));
        formPanel.add(newField("REPETIR CONTRASEÑA", txtPwd_repeat));

        // 3. section for the upload document button
        JPanel uploadDocPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        uploadDocPanel.setBackground(Color.WHITE);
        uploadDocPanel.add(btnUpload); //personalizar btn icon***

        // 4. section for the register button
        JPanel registerBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerBtnPanel.setBackground(Color.WHITE);
        btnRegister.setPreferredSize(new Dimension(200, 40));
        btnRegister.setBackground(new Color(30, 80, 150)); //color: dark blue
        btnRegister.setForeground(Color.WHITE);
        registerBtnPanel.add(btnRegister);

        // 5. section to link to the login screen
        JPanel loginBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginBtnPanel.setBackground(Color.WHITE);
        loginBtnPanel.add(btnLogin);

        // Final assembly
        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,20))); //space
        mainPanel.add(uploadDocPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,20))); //space
        mainPanel.add(registerBtnPanel);
        mainPanel.add(loginBtnPanel);
        this.add(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null); //center the window.
        this.setVisible(true);
    }

    private JPanel newField(String title, JTextField txt) {
        //a small "container" is created, which will be returned as a result.
        //'BorderLayout' is used to easily configure the container's position.
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(Color.WHITE);
        
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 10));
        lbl.setForeground(Color.GRAY);

        panel.add(lbl, BorderLayout.NORTH); //the title (or label) is placed above the field
        panel.add(txt, BorderLayout.CENTER);
        return panel;
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

    //---getters/ event listener
    public String getFirstName() { return txtFirstName.getText(); }
    public String getLastName() { return txtLastName.getText(); }
    public String getID() { return txtID.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public String getFaculty() { return txtFaculty.getText(); }
    public String getType_() { return txtType.getText(); }
    public String getPwd() { return txtPwd.getText(); }
    public String getPwd_repeat() { return txtPwd_repeat.getText(); }

    //---event listener
    public void registerListener(ActionListener listener) { btnRegister.addActionListener(listener);}
    public void loginListener(ActionListener listener) { btnLogin.addActionListener(listener);}
    public void uploadListener(ActionListener listener) { btnUpload.addActionListener(listener);}
}