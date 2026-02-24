package View.Goalkeeper;

import View.CustomComponents.*;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class GoalkeeperView extends JFrame {
    private JTextField txtUserID; // id del usuario a buscar
    private JButton btnSearch, btnClose;
    private JComboBox<String> cmbType;
    private JPanel cardsContainer;
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
        
        // Encerramos las dimensiones para que el BoxLayout no pueda deformarlo
        Dimension squareSize = new Dimension(1200, 450);
        squarePanel.setPreferredSize(squareSize);
        squarePanel.setMinimumSize(new Dimension(400, 300));
        squarePanel.setMaximumSize(squareSize);
        squarePanel.setBackground(color.LIGHT_GRAY);
        squarePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        squarePanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        squarePanel.setLayout(new BorderLayout());

        // panel del campo de ID
        JPanel fieldPanel = new JPanel(new BorderLayout(0, 5));
        fieldPanel.setOpaque(false);

        JLabel lblInput = new JLabel("ID DEL COMENSAL");
        lblInput.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblInput.setForeground(color.DARK_GRAY);

        txtUserID = new RoundedTextField(); 
        txtUserID.setFont(new Font("SansSerif", Font.PLAIN, 18));
        txtUserID.setPreferredSize(new Dimension(300, 40)); // Ancho ajustado

        fieldPanel.add(lblInput, BorderLayout.NORTH);
        fieldPanel.add(txtUserID, BorderLayout.CENTER);

        // panel de cmbBox
        JPanel comboPanel = new JPanel(new BorderLayout(0, 5));
        comboPanel.setOpaque(false);

        JLabel lblCombo = new JLabel("TIPO DE MENÚ");
        lblCombo.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblCombo.setForeground(color.DARK_GRAY);

        String[] menuOptions = {
            Enums.MenuTypes.BREACKFAST.toString(), 
            Enums.MenuTypes.LUNCH.toString(), 
            Enums.MenuTypes.DINNER.toString()
        };

        cmbType = new RoundedComboBox<>(menuOptions);
        cmbType.setFont(new Font("SansSerif", Font.PLAIN, 16));
        cmbType.setPreferredSize(new Dimension(180, 40));
        cmbType.setBackground(Color.WHITE);
        cmbType.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmbType.setActionCommand("FILTER_USER_TYPE");

        comboPanel.add(lblCombo, BorderLayout.NORTH);
        comboPanel.add(cmbType, BorderLayout.CENTER);

        // panel de botón de búsqueda
        JPanel buttonPanel = new JPanel(new BorderLayout(0, 5));
        buttonPanel.setOpaque(false);
        
        JLabel lblEmpty = new JLabel(" "); 
        lblEmpty.setFont(new Font("SansSerif", Font.BOLD, 12));
        
        btnSearch = new RoundedButton();
        btnSearch.setPreferredSize(new Dimension(40, 40));
        btnSearch.setBackground(color.OXFORD_BLUE);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.setIcon(Utils.ImageUtils.getRoundedIcon("assets/images/Icons/magnifying_glass.png", 20, 20, 0));
        btnSearch.setActionCommand("SEARCH_USER");

        buttonPanel.add(lblEmpty, BorderLayout.NORTH);
        buttonPanel.add(btnSearch, BorderLayout.CENTER);

        // contenedor horizontal centrado
        JPanel searchContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        searchContainer.setOpaque(false); 
        searchContainer.add(fieldPanel);
        searchContainer.add(comboPanel);
        searchContainer.add(buttonPanel);

        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.setOpaque(false);
        topWrapper.add(searchContainer, BorderLayout.CENTER);
        
        // Creamos la línea recta
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.GRAY); // Color de la línea
        
        // Panel para darle márgenes (respiración) a la línea por arriba y por abajo
        JPanel sepPanel = new JPanel(new BorderLayout());
        sepPanel.setOpaque(false);
        sepPanel.setBorder(new EmptyBorder(15, 0, 10, 0)); 
        sepPanel.add(separator, BorderLayout.CENTER);
        
        topWrapper.add(sepPanel, BorderLayout.SOUTH);

        // contenedor dinámico central 
        cardsContainer = new JPanel();
        cardsContainer.setLayout(new BoxLayout(cardsContainer, BoxLayout.Y_AXIS));
        cardsContainer.setOpaque(false); // hereda el fondo de squarePanel

        squarePanel.add(topWrapper, BorderLayout.NORTH);
        squarePanel.add(cardsContainer, BorderLayout.CENTER);

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

    private JPanel createReservationCard(String userDocumentId, String date, 
        String price, String status) {

        // panel principal de la tarjeta
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(color.LIGHT_GRAY, 1, true),
        BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        // limitamos la altura máxima, con posibilidad de expandirse a lo ancho
        card.setMaximumSize(new Dimension(800, 100));

        // superior: cédula y fecha
        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);
        
        JLabel lblId = new JLabel("C.I: " + userDocumentId);
        lblId.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblId.setForeground(Color.GRAY);
        
        JLabel lblDate = new JLabel(date);
        lblDate.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblDate.setForeground(Color.GRAY);
        
        topRow.add(lblId, BorderLayout.WEST);
        topRow.add(lblDate, BorderLayout.EAST);

        // inferior: monto y estado
        JPanel bottomRow = new JPanel(new BorderLayout());
        bottomRow.setOpaque(false);
        
        JLabel lblPrice = new JLabel("$" + price);
        lblPrice.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblPrice.setForeground(color.OXFORD_BLUE);
        
        JLabel lblStatus = new JLabel(status.toUpperCase());
        lblStatus.setFont(new Font("SansSerif", Font.BOLD, 18));

        bottomRow.add(lblPrice, BorderLayout.WEST);
        bottomRow.add(lblStatus, BorderLayout.EAST);

        card.add(topRow);
        card.add(Box.createRigidArea(new Dimension(0, 10))); // Separación entre filas
        card.add(bottomRow);

        return card;
    }
    
    public void displayCard(String userDocumentId, String date, 
        String price, String status) {
            createReservationCard(userDocumentId, date, price, status);
        }
    
    public Integer getID() { return Integer.parseInt(txtUserID.getText()); }
    public String getType_() { return (String) cmbType.getSelectedItem(); }

    // component getter, not text
    public JTextField getComponent_txtUserID() { return txtUserID; }

    public void searchListener(ActionListener listener) { btnSearch.addActionListener(listener);}
    public void closeListener(ActionListener listener) { btnClose.addActionListener(listener);}
}
