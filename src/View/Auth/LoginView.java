package View.Auth;
import View.CustomComponents.Colors;
import View.CustomComponents.RoundedButton;
import View.CustomComponents.RoundedTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends JFrame {
    private JTextField txtEmail, txtPwd;
    private JButton btnLogin, btnRegister, btn_forgotPwd;
    private Colors color = new Colors();

    public LoginView() {
        setTitle("UCV - SGCU");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // objects are initialized to avoid null references.
        txtEmail = new RoundedTextField();
        txtPwd = new RoundedTextField();

        //txtEmail.setText("maria.gomez@example.com");
        //txtPwd.setText("securepass");
        txtEmail.setText("juan.perez@example.com");
        txtPwd.setText("password123");
        
        btnLogin = new RoundedButton("Iniciar sesión");
        btnRegister = new RoundedButton("Registrarse");
        btn_forgotPwd = new RoundedButton("¿Olvidó su contraseña?");

        // 1. main panel (the white background).
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        mainPanel.setBackground(color.BACKGROUND);

        // 2. header panel: contains the logo, the main title, and the title of the form.
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(color.BACKGROUND);

        ImageIcon logo = new ImageIcon("assets/images/Icons/UCVlogo.png");
        Image scaledLogo = logo.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(scaledLogo));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = new JLabel("COMEDOR");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24)); 
        lblTitle.setForeground(color.OXFORD_BLUE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitle = new JLabel("Iniciar sesión");
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblSubtitle.setForeground(color.DARK_GRAY);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(lblLogo);
        headerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        headerPanel.add(lblTitle);
        headerPanel.add(lblSubtitle);
        headerPanel.add(Box.createRigidArea(new Dimension(0,20)));

        // 3. form panel:
        JPanel formPanel = new JPanel(new GridLayout(0, 1, 20, 15));
        formPanel.setBackground(color.BACKGROUND);

        formPanel.add(newField("CORREO ELECTRÓNICO", txtEmail));
        formPanel.add(newField("CONTRASEÑA", txtPwd));

        // 4. forgot pwd button
        JPanel forgotPwdPanel = new JPanel();
        forgotPwdPanel.setBackground(color.BACKGROUND);
        forgotPwdPanel.add(btn_forgotPwd); 
        btn_forgotPwd.setBackground(color.BACKGROUND);
        btn_forgotPwd.setForeground(color.BLACK);
        btn_forgotPwd.setFont(new Font("SansSerif", Font.BOLD, 12));

        // 5. section for the login button.
        JPanel loginBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginBtnPanel.setBackground(color.BACKGROUND);
        btnLogin.setPreferredSize(new Dimension(200, 40));
        btnLogin.setBackground(color.OXFORD_BLUE);
        btnLogin.setForeground(color.BACKGROUND);
        loginBtnPanel.add(btnLogin);

        // 6. section to link to the register screen.
        JPanel registerBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerBtnPanel.setBackground(color.BACKGROUND);
        btnRegister.setBackground(color.LIGHT_GRAY);
        btnRegister.setForeground(color.BLACK);
        
        registerBtnPanel.add(btnRegister);

        // Final assembly.
        mainPanel.add(headerPanel);
        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,20))); //space
        mainPanel.add(loginBtnPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10))); //space
        mainPanel.add(registerBtnPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10))); //space
        mainPanel.add(forgotPwdPanel);
        
        // SPLIT SCREEN
        // A. left container -> header, form, buttons.
        JPanel leftContainer = new JPanel(new GridBagLayout());
        leftContainer.setBackground(color.BACKGROUND);
        leftContainer.add(mainPanel);

        // B. right container -> representative image.
        JPanel rightContainer = new JPanel(new BorderLayout());
        rightContainer.setBackground(color.BACKGROUND);
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
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); //to display the interface in full screen mode.
    }

    private JPanel newField(String title, JComponent component) {
        //a small "container" is created, which will be returned as a result.
        //'BorderLayout' is used to easily configure the container's position.
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(color.BACKGROUND);
        
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 10));
        lbl.setForeground(color.DARK_GRAY);

        panel.add(lbl, BorderLayout.NORTH); //the title (or label) is placed above the field.
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    //---getters/ event listener
    public String getEmail_login() { return txtEmail.getText(); }
    public String getPwd_login() { return txtPwd.getText(); }

    // component getters, not text
    public JTextField getComponentEmail() { return txtEmail; }
    public JTextField getComponentPwd() { return txtPwd; }
 
    public void loginListener(ActionListener listener) { btnLogin.addActionListener(listener);}
    public void registerListener(ActionListener listener) { btnRegister.addActionListener(listener);}
    public void forgotPwdListener(ActionListener listener) { btn_forgotPwd.addActionListener(listener);}   
}

