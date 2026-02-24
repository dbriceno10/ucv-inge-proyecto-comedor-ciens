package View.Goalkeeper;

import View.CustomComponents.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class GoalkeeperView extends JFrame {
    private JTextField txtUserID; // id del usuario a buscar
    private JButton btnSearch, btnClose;
    private Colors color = new Colors();

    public GoalkeeperView() {

        // main panel (the white background).
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        mainPanel.setBackground(color.BACKGROUND);

        // header panel: contains the logo and the main title
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(color.BACKGROUND);

        // Barra superior para el botón de cerrar 
        JPanel topBar = new JPanel();
        // Usamos BoxLayout horizontal para empujar el botón a la derecha
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
        topBar.setBackground(color.BACKGROUND);
        topBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Altura máxima

        topBar.add(Box.createHorizontalGlue()); // Empuja lo que siga a la derecha

        btnClose = new RoundedButton("✕");
        btnClose.setFont(new Font("SansSerif", Font.BOLD, 22));
        btnClose.setForeground(Color.GRAY);
        btnClose.setFocusPainted(false);
        btnClose.setBorderPainted(false);
        btnClose.setContentAreaFilled(false);
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.setActionCommand("CLOSE_VIEW");

        topBar.add(btnClose);

        ImageIcon logo = new ImageIcon("assets/images/Icons/UCVlogo.png");
        Image scaledLogo = logo.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(scaledLogo));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = new JLabel("COMEDOR UNIVERSITARIO");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24)); 
        lblTitle.setForeground(color.OXFORD_BLUE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitle = new JLabel("SISTEMA DE IDENTIFICACIÓN DE COMENSAL");
        lblSubtitle.setFont(new Font("SansSerif", Font.BOLD, 24)); 
        lblSubtitle.setForeground(color.DARK_GRAY);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(lblLogo);
        headerPanel.add(Box.createRigidArea(new Dimension(0,20)));
        headerPanel.add(lblTitle);
        headerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        headerPanel.add(lblSubtitle);

        //--------------------------------------

        JPanel squarePanel = new JPanel();
        
        // Encerramos las dimensiones para que el BoxLayout NO pueda deformarlo
        Dimension squareSize = new Dimension(1200, 450);
        squarePanel.setPreferredSize(squareSize);
        squarePanel.setMinimumSize(new Dimension(400, 300));
        squarePanel.setMaximumSize(squareSize);
        squarePanel.setBackground(color.LIGHT_GRAY);
        squarePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        squarePanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        squarePanel.setLayout(new BorderLayout());
        // iniciamos el campo txtUserID
        txtUserID = new RoundedTextField(); 
        txtUserID.setFont(new Font("SansSerif", Font.PLAIN, 18));
        txtUserID.setPreferredSize(new Dimension(350, 40));

        //aprovechamos el subpanel para insertar el botón de búsqued (btnSearch)
        btnSearch = new RoundedButton();
        btnSearch.setPreferredSize(new Dimension(40,40));
        btnSearch.setBackground(color.OXFORD_BLUE);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.setIcon(Utils.ImageUtils.getRoundedIcon("assets/images/Icons/magnifying_glass.png", 20, 20, 0));
        btnSearch.setActionCommand("SEARCH_USER"); 

        // Agrupamos el campo de texto y el botón en una fila horizontal
        JPanel inputRow = new JPanel(new BorderLayout(10, 0)); // 10px de separación entre input y botón
        inputRow.setOpaque(false);
        inputRow.setPreferredSize(new Dimension(400, 40));

        inputRow.add(txtUserID, BorderLayout.CENTER);
        inputRow.add(btnSearch, BorderLayout.EAST);
       
        // Construimos el contenedor del título + campo
        JPanel fieldPanel = new JPanel(new BorderLayout(0, 5)); 
        fieldPanel.setOpaque(false);

        JLabel lblInput = new JLabel("ID DEL COMENSAL");
        lblInput.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblInput.setForeground(color.DARK_GRAY);

        fieldPanel.add(lblInput, BorderLayout.NORTH);
        fieldPanel.add(inputRow, BorderLayout.CENTER);

        // subpanel genérico para que no se estire por todo el ancho del squarePanel
        JPanel searchContainer = new JPanel(); 
        searchContainer.setOpaque(false); 
        searchContainer.add(fieldPanel);

        squarePanel.add(searchContainer, BorderLayout.NORTH);

        mainPanel.add(topBar); //close button
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        mainPanel.add(squarePanel);

        this.add(mainPanel);

        setUndecorated(true); // quita la barra superior de Windows para que se vea más moderno.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1024, 768));
        setLocationRelativeTo(null);
    }



    public void searchListener(ActionListener listener) { btnSearch.addActionListener(listener);}
    public void closeListener(ActionListener listener) { btnClose.addActionListener(listener);}
}
