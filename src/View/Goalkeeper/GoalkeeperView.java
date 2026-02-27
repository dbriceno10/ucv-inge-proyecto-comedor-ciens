package View.Goalkeeper;

import View.CustomComponents.*;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class GoalkeeperView extends JFrame {
    private JTextField txtUserID; // id del usuario a buscar
    private JButton btnSearch, btnClose, btnProcess;
    private JComboBox<String> cmbShift;
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

        JLabel lblInput = new JLabel("CÉDULA DEL COMENSAL");
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
            "DESAYUNO",
            "ALMUERZO",
            "CENA"
        };

        cmbShift = new RoundedComboBox<>(menuOptions);
        cmbShift.setFont(new Font("SansSerif", Font.PLAIN, 16));
        cmbShift.setPreferredSize(new Dimension(180, 40));
        cmbShift.setBackground(Color.WHITE);
        cmbShift.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmbShift.setActionCommand("FILTER_USER_TYPE");

        comboPanel.add(lblCombo, BorderLayout.NORTH);
        comboPanel.add(cmbShift, BorderLayout.CENTER);

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

        // este botón se dibuja (muestra) cuando se genera una tarjeta de reservación
        btnProcess = new RoundedButton("PROCESAR RESERVA");
        btnProcess.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnProcess.setBackground(color.OXFORD_BLUE);
        btnProcess.setForeground(Color.WHITE);
        btnProcess.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnProcess.setActionCommand("PROCESS_BOOKING");
        
        Dimension btnSize = new Dimension(250, 45);
        btnProcess.setPreferredSize(btnSize);
        btnProcess.setMaximumSize(btnSize);
        btnProcess.setAlignmentX(Component.CENTER_ALIGNMENT);
        //---------------------------------------------------------------------------

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
        // usamos GridBagLayout para que la única tarjeta quede perfectamente centrada
        cardsContainer.setLayout(new GridBagLayout());
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

    private JPanel createReservationCard(Integer userDocumentId, String date, 
        Double price, String status) {

        // panel principal de la tarjeta
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(color.DARK_GRAY);
        card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(color.LIGHT_GRAY, 1, true),
        BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        // limitamos la altura máxima, con posibilidad de expandirse a lo ancho
        Dimension cardSize = new Dimension(600, 120);
        card.setPreferredSize(cardSize);
        card.setMaximumSize(cardSize);
        card.setMinimumSize(cardSize);

        // superior: cédula y fecha
        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);
        
        JLabel lblId = new JLabel("C.I: " + userDocumentId);
        lblId.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblId.setForeground(color.LIGHT_GRAY);
        
        JLabel lblDate = new JLabel(date);
        lblDate.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblDate.setForeground(color.LIGHT_GRAY);
        
        topRow.add(lblId, BorderLayout.WEST);
        topRow.add(lblDate, BorderLayout.EAST);

        // inferior: monto y estado
        JPanel bottomRow = new JPanel(new BorderLayout());
        bottomRow.setOpaque(false);
        
        JLabel lblPrice = new JLabel(String.format("$%.2f", price));
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
    
    public void displayCard(Integer userDocumentId, String date, 
        Double price, String status) {
            cardsContainer.removeAll();

            JPanel wrapper = new JPanel();
            wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
            wrapper.setOpaque(false);

            JPanel newCard = createReservationCard(userDocumentId, date, price, status);
            newCard.setAlignmentX(Component.CENTER_ALIGNMENT);

            wrapper.add(newCard);
            wrapper.add(Box.createRigidArea(new Dimension(0, 20)));
            wrapper.add(btnProcess); // agregamos el botón de procesar debajo

            cardsContainer.add(wrapper); // añadimos la tarjeta al contenedor
            cardsContainer.revalidate(); // refrescamos UI
            cardsContainer.repaint();
        }

    public void showMessage(String message) {
        cardsContainer.removeAll();
        JLabel lblMsg = new JLabel(message);
        lblMsg.setFont(new Font("SansSerif", Font.ITALIC, 16));
        lblMsg.setForeground(Color.DARK_GRAY);
        cardsContainer.add(lblMsg);
        cardsContainer.revalidate();
        cardsContainer.repaint();
    }
    
    public Integer getID() { 
        String text = txtUserID.getText().trim();
        if (text.isEmpty()) return null;
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String getShift() { return (String) cmbShift.getSelectedItem(); }

    // component getter, not text
    public JTextField getComponent_txtUserID() { return txtUserID; }
    public JPanel getComponent_cardsContainer() { return cardsContainer; }

    public void searchListener(ActionListener listener) { btnSearch.addActionListener(listener);}
    public void closeListener(ActionListener listener) { btnClose.addActionListener(listener);}
    public void processListener(ActionListener listener) { btnProcess.addActionListener(listener); }
}
