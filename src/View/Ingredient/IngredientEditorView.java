package View.Ingredient;

import View.CustomComponents.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class IngredientEditorView extends JFrame {

    // Componentes Públicos
    public RoundedButton btnGuardar, btnCancelar, btnUploadImage;
    public RoundedTextField txtNombre, txtStock;
    public RoundedComboBox<String> cmbUnidad;
    public JLabel lblImagePreview; // Para mostrar la foto si la suben

    // Colores Figma
    private final Color COLOR_FONDO = new Color(242, 244, 247);
    private final Color COLOR_BLANCO = Color.WHITE;
    private final Color COLOR_AZUL_UCV = new Color(30, 80, 150);
    private final Color COLOR_AZUL_CLARO = new Color(37, 132, 255);
    private final Color COLOR_AMARILLO = new Color(255, 210, 100);

    public IngredientEditorView() {
        setTitle("SGCU - Nuevo Ingrediente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // 1. PANEL PRINCIPAL
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COLOR_FONDO);
        mainPanel.setBorder(new EmptyBorder(20, 60, 40, 60)); 

        // 2. HEADER (Perfil)
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(COLOR_FONDO);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        headerPanel.add(createProfileBadge("Daniel Briceño (Administrador)"));
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // 3. TARJETA BLANCA DIVIDIDA (Igual que Crear Plato)
        JPanel whiteCard = new JPanel(new GridLayout(1, 2, 40, 0)); // 2 Columnas
        whiteCard.setBackground(COLOR_BLANCO);
        whiteCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            new EmptyBorder(40, 50, 40, 50)
        ));

        // --- IZQUIERDA: FOTO DEL INGREDIENTE ---
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(COLOR_BLANCO);
        
        // Contenedor del botón "+"
        JPanel imageContainer = new JPanel(new GridBagLayout());
        imageContainer.setBackground(new Color(250, 250, 252));
        imageContainer.setBorder(BorderFactory.createDashedBorder(Color.LIGHT_GRAY, 2, 5));
        
        btnUploadImage = new RoundedButton("+");
        btnUploadImage.setBackground(COLOR_AZUL_CLARO);
        btnUploadImage.setForeground(Color.WHITE);
        btnUploadImage.setFont(new Font("SansSerif", Font.BOLD, 50));
        btnUploadImage.setPreferredSize(new Dimension(80, 80));
        btnUploadImage.setMargin(new Insets(0,0,0,0));
        
        imageContainer.add(btnUploadImage);
        leftPanel.add(imageContainer, BorderLayout.CENTER);
        
        // Etiqueta (opcional) debajo de la foto
        JLabel lblFoto = new JLabel("Foto del Ingrediente");
        lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
        lblFoto.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblFoto.setForeground(Color.GRAY);
        lblFoto.setBorder(new EmptyBorder(10, 0, 0, 0));
        leftPanel.add(lblFoto, BorderLayout.SOUTH);

        // --- DERECHA: FORMULARIO ---
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(COLOR_BLANCO);

        // Título del Formulario
        JLabel lblFormTitle = new JLabel("Datos del Ingrediente");
        lblFormTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblFormTitle.setForeground(COLOR_AZUL_UCV);
        lblFormTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(lblFormTitle);
        rightPanel.add(Box.createVerticalStrut(30));

        // Campo: Nombre
        rightPanel.add(createLabel("NOMBRE"));
        txtNombre = new RoundedTextField();
        txtNombre.setPreferredSize(new Dimension(300, 40));
        txtNombre.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        rightPanel.add(txtNombre);
        rightPanel.add(Box.createVerticalStrut(20));

        // Campo: Unidad
        rightPanel.add(createLabel("UNIDAD DE MEDIDA"));
        String[] unidades = {"Kilogramos (kg)", "Gramos (g)", "Litros (L)", "Mililitros (ml)", "Unidades (und)", "Paquetes"};
        cmbUnidad = new RoundedComboBox<>(unidades);
        cmbUnidad.setPreferredSize(new Dimension(300, 40));
        cmbUnidad.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        rightPanel.add(cmbUnidad);
        rightPanel.add(Box.createVerticalStrut(20));

        // Campo: Stock
        rightPanel.add(createLabel("CANTIDAD DISPONIBLE (STOCK)"));
        txtStock = new RoundedTextField();
        txtStock.setText("0");
        txtStock.setPreferredSize(new Dimension(300, 40));
        txtStock.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        rightPanel.add(txtStock);
        rightPanel.add(Box.createVerticalStrut(40));

        // Botones
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        btnPanel.setBackground(COLOR_BLANCO);
        btnPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnGuardar = new RoundedButton("Guardar");
        btnGuardar.setBackground(COLOR_AZUL_UCV);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setPreferredSize(new Dimension(120, 40));

        btnCancelar = new RoundedButton("Cancelar");
        btnCancelar.setBackground(COLOR_AMARILLO);
        btnCancelar.setForeground(Color.DARK_GRAY);
        btnCancelar.setPreferredSize(new Dimension(120, 40));

        btnPanel.add(btnGuardar);
        btnPanel.add(btnCancelar);
        rightPanel.add(btnPanel);

        // UNIÓN
        whiteCard.add(leftPanel);
        whiteCard.add(rightPanel);

        mainPanel.add(whiteCard, BorderLayout.CENTER);

        this.setContentPane(mainPanel);
        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    // --- UTILS ---
    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 10));
        lbl.setForeground(Color.GRAY);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBorder(new EmptyBorder(0, 0, 5, 0));
        return lbl;
    }

    private JPanel createProfileBadge(String text) {
        JPanel badge = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        badge.setBackground(COLOR_BLANCO);
        badge.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        
        JLabel iconLbl = new JLabel();
        ImageIcon icon = loadScaledImage("assets/images/admin_profile.png", 30, 30);
        if(icon != null) iconLbl.setIcon(icon);
        else iconLbl.setText("●");
        
        JLabel nameLbl = new JLabel(text);
        nameLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        nameLbl.setForeground(COLOR_AZUL_UCV);
        badge.add(iconLbl); badge.add(nameLbl);
        return badge;
    }

    private ImageIcon loadScaledImage(String path, int w, int h) {
        File f = new File(path);
        if (f.exists()) {
            return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
        }
        return null;
    }
}