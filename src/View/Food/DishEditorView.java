package View.Food;

import View.CustomComponents.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.File;
import java.awt.*;
import java.awt.event.*;

public class DishEditorView extends JFrame {
    private RoundedButton btnSave, btnCancel, btnUploadImage;
    private RoundedTextField txtName;
    private JTextArea txtDesc;
    private RoundedComboBox<String> cmbStatus;
    private JLabel lblImagePreview; // Aquí mostraremos la foto cargada
    private Colors color = new Colors();

    public DishEditorView() {
        setTitle("SGCU - Crear / Editar Plato");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // 1. PANEL PRINCIPAL (Fondo Gris)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(color.BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(20, 60, 40, 60)); // Márgenes externos

        // 2. HEADER SUPERIOR (Perfil fuera de la tarjeta)
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(color.BACKGROUND);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        headerPanel.add(createProfileBadge("Daniel Briceño (Administrador)"));
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // 3. TARJETA BLANCA CENTRAL
        JPanel whiteCard = new JPanel(new GridLayout(1, 2, 40, 0)); // Dividido en 2 columnas (Foto | Formulario)
        whiteCard.setBackground(color.WHITE);
        whiteCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.LIGHT_GRAY, 1),
            new EmptyBorder(40, 50, 40, 50)
        ));

        // --- COLUMNA IZQUIERDA: ÁREA DE IMAGEN ---
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(color.WHITE);
        
        // Contenedor para el botón "+" (Simulando el área de carga)
        JPanel imageContainer = new JPanel(new GridBagLayout());
        imageContainer.setBackground(color.LIGHT_GRAY); // Gris muy clarito
        
        // Botón "+" Gigante
        btnUploadImage = new RoundedButton("+");
        btnUploadImage.setBackground(color.LIGHT_BLUE);
        btnUploadImage.setForeground(color.WHITE);
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
        rightPanel.setBackground(color.WHITE);

        // -- Campo Nombre --
        rightPanel.add(createLabel("NOMBRE"));
        txtName = new RoundedTextField();
        txtName.setPreferredSize(new Dimension(300, 40));
        txtName.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        rightPanel.add(txtName);
        rightPanel.add(Box.createVerticalStrut(20));

        // -- Campo Estado --
        rightPanel.add(createLabel("ESTADO"));
        String[] estados = {"Disponible", "Agotado", "Fuera de Temporada"};
        cmbStatus = new RoundedComboBox<>(estados);
        cmbStatus.setPreferredSize(new Dimension(300, 40));
        cmbStatus.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        rightPanel.add(cmbStatus);
        rightPanel.add(Box.createVerticalStrut(20));

        // -- Campo Descripción --
        rightPanel.add(createLabel("DESCRIPCIÓN"));
        txtDesc = new JTextArea();
        txtDesc.setFont(new Font("SansSerif", Font.PLAIN, 12));
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        txtDesc.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding interno
        
        JScrollPane scrollDesc = new JScrollPane(txtDesc);
        scrollDesc.setBorder(BorderFactory.createLineBorder(color.DARK_GRAY)); // Borde del área de texto
        scrollDesc.setPreferredSize(new Dimension(300, 100));
        rightPanel.add(scrollDesc);
        rightPanel.add(Box.createVerticalStrut(30));

        // -- Botones de Acción (Guardar / Cancelar) --
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        btnPanel.setBackground(color.WHITE);

        btnSave = new RoundedButton("Guardar");
        btnSave.setBackground(color.OXFORD_BLUE);
        btnSave.setForeground(color.WHITE);
        btnSave.setPreferredSize(new Dimension(120, 40));

        btnCancel = new RoundedButton("Cancelar");
        btnCancel.setBackground(color.YELLOW);
        btnCancel.setForeground(color.BLACK);
        btnCancel.setPreferredSize(new Dimension(120, 40));

        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
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
        badge.setBackground(color.WHITE);
        badge.setBorder(BorderFactory.createLineBorder(color.LIGHT_GRAY, 1));
        
        // Cargar foto de perfil
        JLabel iconLbl = new JLabel();
        ImageIcon icon = loadScaledImage("assets/images/admin_profile.png", 40, 40);
        
        if (icon != null) {
            iconLbl.setIcon(icon);
        } else {
            iconLbl.setText("●");
            iconLbl.setForeground(color.DARK_GRAY);
        }
        
        JLabel nameLbl = new JLabel(text);
        nameLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        nameLbl.setForeground(color.OXFORD_BLUE);

        badge.add(iconLbl);
        badge.add(nameLbl);
        return badge;
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 10));
        lbl.setForeground(color.DARK_GRAY);
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

    public void saveBtnListener(ActionListener listener) { btnSave.addActionListener(listener);} 
    public void cancelBtnListener(ActionListener listener) { btnCancel.addActionListener(listener);} 
    public void uploadBtnListener(ActionListener listener) { btnUploadImage.addActionListener(listener);} 
    public void cmbStatusListener(ActionListener listener) { cmbStatus.addActionListener(listener);} 
}