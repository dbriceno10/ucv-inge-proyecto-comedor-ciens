package View.Main;

import View.CustomComponents.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Model.DTO.Food.FoodDto;

import java.awt.*;
import java.awt.event.ActionListener;

public class FoodDetailsView extends JDialog {
    private Colors color = new Colors();
    private JButton btnClose, btnReserve;

    // Recibe el JFrame padre (Dashboard) y los datos del plato (FoodDto)
    public FoodDetailsView(JFrame parent, FoodDto food) {
        super(parent, true); 
        
        setUndecorated(true); 
        setSize(new Dimension(450, 600)); 
        setLocationRelativeTo(parent); 
        
        // --- Panel Principal ---
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(color.BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(25, 35, 30, 35));

        // --- 1. Barra Superior (Botón de Cerrar) ---
        JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
        topBar.setOpaque(false);
        
        topBar.add(Box.createHorizontalGlue()); // Empuja el botón a la derecha
        
        btnClose = new RoundedButton("✕");
        btnClose.setFont(new Font("SansSerif", Font.BOLD, 22));
        btnClose.setForeground(color.DARK_GRAY);
        btnClose.setContentAreaFilled(false);
        btnClose.setBorderPainted(false);
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.setActionCommand("CLOSE_MODAL");
        topBar.add(btnClose);

        // --- 2. Imagen del Plato ---
        String imgPath = "assets/images/Menu/" + food.getId() + ".png";
        ImageIcon checkIcon = new ImageIcon(imgPath);
        if (checkIcon.getIconWidth() == -1) { 
            imgPath = "assets/images/Menu/default.png";
        }
        
        JLabel lblImage = new JLabel(Utils.ImageUtils.getRoundedIcon(imgPath, 300, 200, 30));
        lblImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- 3. Título ---
        JLabel lblTitle = new JLabel(food.getName().toUpperCase());
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTitle.setForeground(color.OXFORD_BLUE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- 4. Descripción ---
        // Usamos JTextArea en lugar de JLabel para permitir saltos de línea automáticos
        JTextArea txtDesc = new JTextArea();
        txtDesc.setText("desc.");
        txtDesc.setFont(new Font("SansSerif", Font.PLAIN, 15));
        txtDesc.setForeground(color.BLACK);
        txtDesc.setLineWrap(true);        // Activa el salto de línea
        txtDesc.setWrapStyleWord(true);   // Salta palabras completas, no corta a medias
        txtDesc.setOpaque(false);         // Fondo transparente
        txtDesc.setEditable(false);       // Modo solo lectura
        txtDesc.setFocusable(false);
        txtDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtDesc.setMaximumSize(new Dimension(350, 80));

        // --- 5. Costo ---
        // ajustar lógica de costo
        JLabel lblCost = new JLabel("Costo: $0.00 (Pendiente)"); 
        lblCost.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblCost.setForeground(new Color(40, 167, 69)); 
        lblCost.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- 6. Botón de Acción ---
        btnReserve = new RoundedButton("CREAR RESERVACIÓN");
        btnReserve.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnReserve.setBackground(color.OXFORD_BLUE);
        btnReserve.setForeground(color.WHITE);
        btnReserve.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReserve.setActionCommand("CREATE_RESERVATION");
        
        Dimension btnSize = new Dimension(280, 50);
        btnReserve.setPreferredSize(btnSize);
        btnReserve.setMaximumSize(btnSize);
        btnReserve.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- Ensamblaje ---
        mainPanel.add(topBar);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(lblImage);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(txtDesc);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        mainPanel.add(lblCost);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 35)));
        mainPanel.add(btnReserve);

        this.add(mainPanel);
    }

    public void closeListener(ActionListener listener) { btnClose.addActionListener(listener); }
    public void reserveListener(ActionListener listener) { btnReserve.addActionListener(listener); }
}