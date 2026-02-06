package View.Auth;
import Enums.UserRoles;
import View.CustomComponents.RoundedButton;
import View.CustomComponents.RoundedComboBox;
import View.CustomComponents.RoundedTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class RegisterView extends JFrame {
    private JTextField txtFirstName, txtLastName, txt_documentId, txtEmail, txtPwd, txtPwd_repeat;
    private JComboBox<String> cmbFaculty, cmbType; // for the user type and faculty drop-down list.
    private JButton btnRegister, btnLogin, btnUpload;

    public RegisterView() {
        setTitle("UCV - SGCU");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // objects are initialized to avoid null references.
        txtFirstName = new RoundedTextField();
        txtLastName = new RoundedTextField();
        txt_documentId = new RoundedTextField();
        txtEmail = new RoundedTextField();
        txtPwd = new RoundedTextField();
        txtPwd_repeat = new RoundedTextField();

        String[] facultys = {"Facultad de Agronomía", "Facultad de Arquitectura", "Facultad de Ciencias", 
        "Facultad de Ciencias Económicas y Sociales", "Facultad de Farmacia", "Facultad de Humanidades y Educación", 
        "Facultad de Ingeniería", "Facultad de Ciencias Jurídicas y Políticas", "Facultad de Medicina", 
        "Facultad de Odontología", "Facultad de Veterinaria"};
        cmbFaculty = new RoundedComboBox<>(facultys);

        String[] role_type = {UserRoles.COMMENSAL, UserRoles.ADMIN, };
        cmbType = new RoundedComboBox<>(role_type);
        
        btnRegister = new RoundedButton("Registrarse");
        btnLogin = new RoundedButton("Iniciar sesión");
        btnUpload = new RoundedButton("Cargar documento");

        // 1. main panel (the background).
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        mainPanel.setBackground(new Color(225, 225, 225));

        // 2. header panel: contains the logo, the main title, and the title of the form.
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(225, 225, 225));

        ImageIcon logo = new ImageIcon("assets/images/UCVlogo.png");
        Image scaledLogo = logo.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(scaledLogo));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = new JLabel("COMEDOR");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24)); 
        lblTitle.setForeground(new Color(0, 33, 71));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitle = new JLabel("Registrarse");
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblSubtitle.setForeground(Color.GRAY);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(lblLogo);
        headerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        headerPanel.add(lblTitle);
        headerPanel.add(lblSubtitle);
        headerPanel.add(Box.createRigidArea(new Dimension(0,20)));

        // 3. form panel: it has two columns to organize the fields.
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 20, 15)); // rows - columns - horizontal space - vertical space.
        formPanel.setBackground(new Color(225, 225, 225));

        formPanel.add(newField("NOMBRE", txtFirstName));
        formPanel.add(newField("APELLIDO", txtLastName));
        formPanel.add(newField("CÉDULA", txt_documentId));
        formPanel.add(newField("CORREO ELECTRÓNICO", txtEmail));
        formPanel.add(newField("FACULTAD", cmbFaculty));
        formPanel.add(newField("TIPO DE USUARIO", cmbType));
        formPanel.add(newField("CONTRASEÑA", txtPwd));
        formPanel.add(newField("REPETIR CONTRASEÑA", txtPwd_repeat));

        // 4. section for the upload document button.
        JPanel uploadDocPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        uploadDocPanel.setBackground(new Color(225, 225, 225));
        uploadDocPanel.add(btnUpload); 
        // configuration details for btnUpload
        btnUpload.setBackground(new Color(225, 225, 225));
        btnUpload.setForeground(new Color(30, 80, 150));
        btnUpload.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnUpload.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnUpload.setHorizontalTextPosition(SwingConstants.CENTER);

        ImageIcon iconUpload = new ImageIcon("assets/images/upload_doc.png");
        Image imgScaled = iconUpload.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        btnUpload.setIcon(new ImageIcon(imgScaled));

        // 5. section for the register button.
        JPanel registerBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerBtnPanel.setBackground(new Color(225, 225, 225));
        btnRegister.setPreferredSize(new Dimension(200, 40));
        btnRegister.setBackground(new Color(0, 33, 71)); //color: dark blue
        btnRegister.setForeground(new Color(225, 225, 225));
        registerBtnPanel.add(btnRegister);

        // 6. section to link to the login screen.
        JPanel loginBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginBtnPanel.setBackground(new Color(225, 225, 225));
        loginBtnPanel.add(btnLogin);

        // Final assembly.
        mainPanel.add(headerPanel);
        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,20))); //space
        mainPanel.add(uploadDocPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,20))); //space
        mainPanel.add(registerBtnPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10))); //space
        mainPanel.add(loginBtnPanel);

        // SPLIT SCREEN
        // A. left container -> header, form, buttons.
        JPanel leftContainer = new JPanel(new GridBagLayout());
        leftContainer.setBackground(new Color(225, 225, 225));
        leftContainer.add(mainPanel);

        // B. right container -> representative image.
        JPanel rightContainer = new JPanel(new BorderLayout());
        rightContainer.setBackground(new Color(225, 225, 225));
        ImageIcon UCVdining = new ImageIcon("assets/images/UCVdining.png");
        Image repImg = UCVdining.getImage().getScaledInstance(-1, 650, Image.SCALE_SMOOTH);
        JLabel lbl_repImg = new JLabel(new ImageIcon(repImg));
        lbl_repImg.setHorizontalAlignment(JLabel.CENTER);
        rightContainer.add(lbl_repImg, BorderLayout.CENTER);

        // C. Slipt main panel (1 row, 2 columns).
        JPanel splitPanel = new JPanel(new GridLayout(1, 2));
        splitPanel.add(leftContainer);
        splitPanel.add(rightContainer);

        this.add(splitPanel);
        // important! The window is instructed to “pack” the components. This forces the Layout to calculate 
        // the size and position of each text and button in order to display it correctly.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.setVisible(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); //to display the interface in full screen mode.
    }

    private JPanel newField(String title, JComponent component) {
        //a small "container" is created, which will be returned as a result.
        //'BorderLayout' is used to easily configure the container's position.
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(new Color(225, 225, 225));
        
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 10));
        lbl.setForeground(Color.GRAY);

        panel.add(lbl, BorderLayout.NORTH); //the title (or label) is placed above the field.
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    public void clearFields() {
        txtFirstName.setText("");
        txtLastName.setText("");
        txt_documentId.setText("");
        txtEmail.setText("");
        cmbFaculty.setSelectedIndex(0);
        cmbType.setSelectedIndex(0);
        txtPwd.setText("");
        txtPwd_repeat.setText("");
    }

    //---getters/ event listener
    public String getFirstName() { return txtFirstName.getText(); }
    public String getLastName() { return txtLastName.getText(); }
    public String get_documentId() { return txt_documentId.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public String getFaculty() { return (String) cmbFaculty.getSelectedItem(); }
    public String getType_() { return (String) cmbType.getSelectedItem(); }
    public String getPwd() { return txtPwd.getText(); }
    public String getPwd_repeat() { return txtPwd_repeat.getText(); }

    // component getters, not text
    public JTextField getComponentFirstName() { return txtFirstName; }
    public JTextField getComponentLastName() { return txtLastName; }
    public JTextField getComponentdocumentId() { return txt_documentId; }
    public JTextField getComponentEmail() { return txtEmail; }
    public JTextField getComponentPwd() { return txtPwd; }
    public JTextField getComponentPwd_repeat() { return txtPwd_repeat; }
 
    public void registerListener(ActionListener listener) { btnRegister.addActionListener(listener);}
    public void loginListener(ActionListener listener) { btnLogin.addActionListener(listener);}
    public void uploadListener(ActionListener listener) { btnUpload.addActionListener(listener);}   
}