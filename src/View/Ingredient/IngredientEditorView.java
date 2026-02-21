package View.Ingredient;

import View.CustomComponents.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.awt.event.ActionListener;

public class IngredientEditorView extends JFrame {
    private RoundedButton btnSave, btnCancel, btnUploadImage;
    private RoundedTextField txtName, txtStock;
    private RoundedComboBox<String> cmbUnitMeasurement;
    //private JLabel lblImagePreview; 
    private Colors color = new Colors();

    public IngredientEditorView() {
        setTitle("SGCU - Nuevo Ingrediente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // 1. PANEL PRINCIPAL
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(color.BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(20, 60, 40, 60)); 

        // 2. HEADER (Perfil)
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(color.BACKGROUND);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        headerPanel.add(createProfileBadge("Daniel Briceño (Administrador)"));
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // 3. TARJETA BLANCA DIVIDIDA (Igual que Crear Plato)
        JPanel whiteCard = new JPanel(new GridLayout(1, 2, 40, 0)); // 2 Columnas
        whiteCard.setBackground(color.WHITE);
        whiteCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.LIGHT_GRAY, 1),
            new EmptyBorder(40, 50, 40, 50)
        ));

        // --- IZQUIERDA: FOTO DEL INGREDIENTE ---
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(color.WHITE);
        
        // Contenedor del botón "+"
        JPanel imageContainer = new JPanel(new GridBagLayout());
        imageContainer.setBackground(color.WHITE);
        imageContainer.setBorder(BorderFactory.createDashedBorder(color.LIGHT_GRAY, 2, 5));
        
        btnUploadImage = new RoundedButton("+");
        btnUploadImage.setBackground(color.LIGHT_BLUE);
        btnUploadImage.setForeground(color.WHITE);
        btnUploadImage.setFont(new Font("SansSerif", Font.BOLD, 50));
        btnUploadImage.setPreferredSize(new Dimension(80, 80));
        btnUploadImage.setMargin(new Insets(0,0,0,0));
        
        imageContainer.add(btnUploadImage);
        leftPanel.add(imageContainer, BorderLayout.CENTER);
        
        // Etiqueta (opcional) debajo de la foto
        JLabel lblFoto = new JLabel("Foto del Ingrediente");
        lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
        lblFoto.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblFoto.setForeground(color.DARK_GRAY);
        lblFoto.setBorder(new EmptyBorder(10, 0, 0, 0));
        leftPanel.add(lblFoto, BorderLayout.SOUTH);

        // --- DERECHA: FORMULARIO ---
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(color.WHITE);

        // Título del Formulario
        JLabel lblFormTitle = new JLabel("Datos del Ingrediente");
        lblFormTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblFormTitle.setForeground(color.OXFORD_BLUE);
        lblFormTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(lblFormTitle);
        rightPanel.add(Box.createVerticalStrut(30));

        // Campo: Nombre
        rightPanel.add(createLabel("NOMBRE"));
        txtName = new RoundedTextField();
        txtName.setPreferredSize(new Dimension(300, 40));
        txtName.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        rightPanel.add(txtName);
        rightPanel.add(Box.createVerticalStrut(20));

        // Campo: Unidad
        rightPanel.add(createLabel("UNIDAD DE MEDIDA"));
        String[] unidades = {"Kilogramos (kg)", "Gramos (g)", "Litros (L)", "Mililitros (ml)", "Unidades (und)", "Paquetes"};
        cmbUnitMeasurement = new RoundedComboBox<>(unidades);
        cmbUnitMeasurement.setPreferredSize(new Dimension(300, 40));
        cmbUnitMeasurement.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        rightPanel.add(cmbUnitMeasurement);
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
        btnPanel.setBackground(color.WHITE);
        btnPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

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

        // UNIÓN
        whiteCard.add(leftPanel);
        whiteCard.add(rightPanel);

        mainPanel.add(whiteCard, BorderLayout.CENTER);

        this.setContentPane(mainPanel);
        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    // --- UTILS ---
    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 10));
        lbl.setForeground(color.DARK_GRAY);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBorder(new EmptyBorder(0, 0, 5, 0));
        return lbl;
    }

    private JPanel createProfileBadge(String text) {
        JPanel badge = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        badge.setBackground(color.WHITE);
        badge.setBorder(BorderFactory.createLineBorder(color.LIGHT_GRAY, 1));
        
        JLabel iconLbl = new JLabel();
        ImageIcon icon = loadScaledImage("assets/images/admin_profile.png", 30, 30);
        if(icon != null) iconLbl.setIcon(icon);
        else iconLbl.setText("●");
        
        JLabel nameLbl = new JLabel(text);
        nameLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        nameLbl.setForeground(color.OXFORD_BLUE);
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

    public void saveListener(ActionListener listener) { btnSave.addActionListener(listener);}
    public void cancelListener(ActionListener listener) { btnCancel.addActionListener(listener);}
    public void uploadImgListener(ActionListener listener) { btnUploadImage.addActionListener(listener);}

    public String getTxtName() { return txtName.getText(); }
    public String getTextStock() { return txtStock.getText(); }
    public String getUnitMeasurement() { return (String) cmbUnitMeasurement.getSelectedItem(); }
}