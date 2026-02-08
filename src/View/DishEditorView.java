package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class DishEditorView extends JFrame {

    // --- Componentes Públicos (Para que el Controlador los use) ---
    public RoundedButton btnGuardar, btnCancelar, btnUploadImage;
    public RoundedTextField txtNombre;
    public JTextArea txtDescripcion;
    public RoundedComboBox<String> cmbEstado;
    public JLabel lblImagePreview; // Aquí mostraremos la foto cargada

    // --- Colores del Tema ---
    private final Color COLOR_FONDO = new Color(242, 244, 247);
    private final Color COLOR_BLANCO = Color.WHITE;
    private final Color COLOR_AZUL_UCV = new Color(30, 80, 150);
    private final Color COLOR_AZUL_CLARO = new Color(37, 132, 255);
    private final Color COLOR_NARANJA = new Color(255, 180, 120);
    private final Color COLOR_AMARILLO = new Color(255, 210, 100);

    public DishEditorView() {
        setTitle("SGCU - Crear / Editar Plato");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // 1. PANEL PRINCIPAL (Fondo Gris)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COLOR_FONDO);
        mainPanel.setBorder(new EmptyBorder(20, 60, 40, 60)); // Márgenes externos

        // 2. HEADER SUPERIOR (Perfil fuera de la tarjeta)
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(COLOR_FONDO);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        headerPanel.add(createProfileBadge("Daniel Briceño (Administrador)"));
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // 3. TARJETA BLANCA CENTRAL
        JPanel whiteCard = new JPanel(new GridLayout(1, 2, 40, 0)); // Dividido en 2 columnas (Foto | Formulario)
        whiteCard.setBackground(COLOR_BLANCO);
        whiteCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            new EmptyBorder(40, 50, 40, 50)
        ));

        // --- COLUMNA IZQUIERDA: ÁREA DE IMAGEN ---
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(COLOR_BLANCO);
        
        // Contenedor para el botón "+" (Simulando el área de carga)
        JPanel imageContainer = new JPanel(new GridBagLayout());
        imageContainer.setBackground(new Color(250, 250, 252)); // Gris muy clarito
        imageContainer.setBorder(BorderFactory.createDashedBorder(Color.LIGHT_GRAY, 2, 5)); // Borde punteado opcional
        
        // Botón "+" Gigante
        btnUploadImage = new RoundedButton("+");
        btnUploadImage.setBackground(COLOR_AZUL_CLARO);
        btnUploadImage.setForeground(Color.WHITE);
        btnUploadImage.setFont(new Font("SansSerif", Font.BOLD, 50));
        btnUploadImage.setPreferredSize(new Dimension(80, 80));
        btnUploadImage.setMargin(new Insets(0,0,0,0)); // Quitar márgenes internos para que se vea redondo
        
        // Etiqueta para previsualizar imagen (oculta al principio)
        lblImagePreview = new JLabel();
        lblImagePreview.setHorizontalAlignment(SwingConstants.CENTER);
        
        imageContainer.add(btnUploadImage); // Añadimos el botón al centro
        leftPanel.add(imageContainer, BorderLayout.CENTER);

        // --- COLUMNA DERECHA: FORMULARIO ---
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(COLOR_BLANCO);

        // -- Campo Nombre --
        rightPanel.add(createLabel("NOMBRE"));
        txtNombre = new RoundedTextField();
        txtNombre.setPreferredSize(new Dimension(300, 40));
        txtNombre.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        rightPanel.add(txtNombre);
        rightPanel.add(Box.createVerticalStrut(20));

        // -- Campo Estado --
        rightPanel.add(createLabel("ESTADO"));
        String[] estados = {"Disponible", "Agotado", "Fuera de Temporada"};
        cmbEstado = new RoundedComboBox<>(estados);
        cmbEstado.setPreferredSize(new Dimension(300, 40));
        cmbEstado.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        rightPanel.add(cmbEstado);
        rightPanel.add(Box.createVerticalStrut(20));

        // -- Campo Descripción --
        rightPanel.add(createLabel("DESCRIPCIÓN"));
        txtDescripcion = new JTextArea();
        txtDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 12));
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding interno
        
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        scrollDesc.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Borde del área de texto
        scrollDesc.setPreferredSize(new Dimension(300, 100));
        rightPanel.add(scrollDesc);
        rightPanel.add(Box.createVerticalStrut(30));

        // -- Botones de Acción (Guardar / Cancelar) --
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        btnPanel.setBackground(COLOR_BLANCO);

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

        // UNIÓN DE PANELES
        whiteCard.add(leftPanel);
        whiteCard.add(rightPanel);

        // Añadir tarjeta al centro
        mainPanel.add(whiteCard, BorderLayout.CENTER);

        // CONFIGURACIÓN FINAL
        this.setContentPane(mainPanel);
        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    // --- MÉTODOS AUXILIARES ---

    private JPanel createProfileBadge(String text) {
        JPanel badge = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        badge.setBackground(COLOR_BLANCO);
        badge.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        
        // Cargar foto de perfil
        JLabel iconLbl = new JLabel();
        ImageIcon icon = loadScaledImage("assets/images/admin_profile.png", 40, 40);
        
        if (icon != null) {
            iconLbl.setIcon(icon);
        } else {
            iconLbl.setText("●");
            iconLbl.setForeground(Color.GRAY);
        }
        
        JLabel nameLbl = new JLabel(text);
        nameLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        nameLbl.setForeground(COLOR_AZUL_UCV);

        badge.add(iconLbl);
        badge.add(nameLbl);
        return badge;
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 10));
        lbl.setForeground(Color.GRAY);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBorder(new EmptyBorder(0, 0, 5, 0));
        return lbl;
    }

    private ImageIcon loadScaledImage(String path, int w, int h) {
        File f = new File(path);
        if (f.exists()) {
            ImageIcon icon = new ImageIcon(path);
            Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
        return null;
    }
}