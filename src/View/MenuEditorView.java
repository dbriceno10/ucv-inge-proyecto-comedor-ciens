package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import View.CustomComponents.*;

public class MenuEditorView extends JFrame {

    // Componentes públicos para que el controlador los use después
    public RoundedButton btnGuardar, btnCancelar, btnAddPlato;
    public RoundedTextField txtFecha;
    
    private JPanel listPanel;

    // Colores exactos del diseño
    private final Color COLOR_FONDO = new Color(242, 244, 247);
    private final Color COLOR_BLANCO = Color.WHITE;
    private final Color COLOR_AZUL_UCV = new Color(30, 80, 150);
    private final Color COLOR_AZUL_CLARO = new Color(37, 132, 255);
    private final Color COLOR_NARANJA = new Color(255, 180, 120);
    private final Color COLOR_AMARILLO = new Color(255, 210, 100);

    public MenuEditorView() {
        setTitle("SGCU - Crear/Editar Menú");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // 1. CONFIGURACIÓN DEL PANEL PRINCIPAL (Fondo Gris)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COLOR_FONDO);
        // Margen grande para centrar la tarjeta blanca
        mainPanel.setBorder(new EmptyBorder(20, 80, 40, 80)); 

        // 2. HEADER SUPERIOR (Perfil fuera de la tarjeta)
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(COLOR_FONDO);
        headerPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        headerPanel.add(createProfileBadge("Daniel Briceño (Administrador)"));
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // 3. TARJETA BLANCA (Contenido)
        JPanel whiteCard = new JPanel(new BorderLayout());
        whiteCard.setBackground(COLOR_BLANCO);
        // Sombra simulada con borde compuesto
        whiteCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            new EmptyBorder(30, 40, 30, 40)
        ));

        // --- A. CABECERA DE LA TARJETA (Fecha) ---
        JPanel cardHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cardHeader.setBackground(COLOR_BLANCO);
        
        JLabel lblFecha = new JLabel("FECHA");
        lblFecha.setFont(new Font("SansSerif", Font.BOLD, 10));
        lblFecha.setForeground(Color.GRAY);
        
        txtFecha = new RoundedTextField();
        txtFecha.setText("08/12/2025");
        txtFecha.setPreferredSize(new Dimension(150, 35));
        
        JPanel dateBox = new JPanel(new BorderLayout());
        dateBox.setBackground(COLOR_BLANCO);
        dateBox.add(lblFecha, BorderLayout.NORTH);
        dateBox.add(txtFecha, BorderLayout.CENTER);
        
        cardHeader.add(dateBox);
        whiteCard.add(cardHeader, BorderLayout.NORTH);

        // --- B. LISTA DE PLATOS (Centro) ---
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(COLOR_BLANCO);

        // Espacio inicial y platos de ejemplo
        listPanel.add(Box.createVerticalStrut(20));
        addPlatoRow("Pasta con Vegetales");
        addPlatoRow("Pasta con Vegetales");
        addPlatoRow("Pasta con Vegetales");

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null); // Sin borde feo
        scroll.getViewport().setBackground(COLOR_BLANCO);
        whiteCard.add(scroll, BorderLayout.CENTER);

        // --- C. FOOTER (Botones) ---
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(COLOR_BLANCO);
        footerPanel.setBorder(new EmptyBorder(30, 0, 0, 0));

        // Botón Izquierdo: Añadir
        btnAddPlato = new RoundedButton("Añadir Plato");
        btnAddPlato.setBackground(COLOR_AZUL_CLARO);
        btnAddPlato.setForeground(Color.WHITE);
        btnAddPlato.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnAddPlato.setPreferredSize(new Dimension(140, 40));
        
        JPanel leftBtnBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftBtnBox.setBackground(COLOR_BLANCO);
        leftBtnBox.add(btnAddPlato);

        // Botones Derechos: Guardar/Cancelar
        JPanel rightBtnBox = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightBtnBox.setBackground(COLOR_BLANCO);

        btnGuardar = new RoundedButton("Guardar");
        btnGuardar.setBackground(COLOR_AZUL_UCV);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setPreferredSize(new Dimension(120, 40));

        btnCancelar = new RoundedButton("Cancelar");
        btnCancelar.setBackground(COLOR_AMARILLO);
        btnCancelar.setForeground(Color.DARK_GRAY);
        btnCancelar.setPreferredSize(new Dimension(120, 40));

        rightBtnBox.add(btnGuardar);
        rightBtnBox.add(btnCancelar);

        footerPanel.add(leftBtnBox, BorderLayout.WEST);
        footerPanel.add(rightBtnBox, BorderLayout.EAST);

        whiteCard.add(footerPanel, BorderLayout.SOUTH);

        // AÑADIR TODO AL FRAME
        mainPanel.add(whiteCard, BorderLayout.CENTER);
        this.setContentPane(mainPanel); // Asegura que el fondo gris se vea
        
        // Finalizar
        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    // --- MÉTODOS AUXILIARES ---

    private JPanel createProfileBadge(String text) {
        JPanel badge = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        badge.setBackground(COLOR_BLANCO);
        badge.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        
        // Cargar foto de perfil (admin_profile.png)
        JLabel iconLbl = new JLabel();
        ImageIcon icon = loadScaledImage("assets/images/admin_profile.png", 40, 40);
        
        if (icon != null) {
            iconLbl.setIcon(icon);
        } else {
            iconLbl.setText("●"); // Fallback si no hay imagen
            iconLbl.setForeground(Color.GRAY);
        }
        
        JLabel nameLbl = new JLabel(text);
        nameLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        nameLbl.setForeground(COLOR_AZUL_UCV);

        badge.add(iconLbl);
        badge.add(nameLbl);
        return badge;
    }

    private void addPlatoRow(String nombrePlato) {
        JPanel row = new JPanel(new GridBagLayout());
        row.setBackground(COLOR_BLANCO);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.fill = GridBagConstraints.NONE;

        // 1. CARD DEL PLATO (Izquierda)
        gbc.gridx = 0; gbc.weightx = 0.4; gbc.anchor = GridBagConstraints.WEST;
        
        RoundedButton dishCard = new RoundedButton(nombrePlato);
        dishCard.setBackground(COLOR_NARANJA);
        dishCard.setForeground(Color.WHITE);
        dishCard.setFont(new Font("SansSerif", Font.BOLD, 14));
        dishCard.setPreferredSize(new Dimension(240, 75));
        dishCard.setHorizontalAlignment(SwingConstants.LEFT);
        dishCard.setMargin(new Insets(0, 10, 0, 0));
        
        // Cargar foto de la pasta (pasta.jpg) -- ¡AQUÍ ESTÁ EL CAMBIO!
        ImageIcon dishImg = loadScaledImage("assets/images/pasta.jpg", 80, 60);
        if(dishImg != null) {
             dishCard.setIcon(dishImg);
             dishCard.setIconTextGap(15);
        }
        
        row.add(dishCard, gbc);

        // 2. CONTROLES (Centro)
        gbc.gridx = 1; gbc.weightx = 0.4; gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel ctrlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        ctrlPanel.setBackground(COLOR_BLANCO);

        RoundedButton btnMinus = createCircularButton("-");
        
        JPanel inputStack = new JPanel(new BorderLayout());
        inputStack.setBackground(COLOR_BLANCO);
        JLabel lblQty = new JLabel("CANTIDAD DISPONIBLE");
        lblQty.setFont(new Font("SansSerif", Font.BOLD, 9));
        lblQty.setForeground(Color.GRAY);
        
        RoundedTextField txtQty = new RoundedTextField();
        txtQty.setText("0");
        txtQty.setPreferredSize(new Dimension(160, 35));
        
        inputStack.add(lblQty, BorderLayout.NORTH);
        inputStack.add(txtQty, BorderLayout.CENTER);

        RoundedButton btnPlus = createCircularButton("+");

        ctrlPanel.add(btnMinus);
        ctrlPanel.add(inputStack);
        ctrlPanel.add(btnPlus);
        
        row.add(ctrlPanel, gbc);

        // 3. PAPELERA ROJA (Derecha) - Dibujada a mano
        gbc.gridx = 2; gbc.weightx = 0.2; gbc.anchor = GridBagConstraints.EAST;
        
        JButton btnDel = new JButton();
        btnDel.setIcon(new TrashIcon()); // Usamos la clase TrashIcon de abajo
        btnDel.setBorderPainted(false);
        btnDel.setContentAreaFilled(false);
        btnDel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        row.add(btnDel, gbc);

        listPanel.add(row);
        listPanel.add(Box.createVerticalStrut(10));
    }

    private RoundedButton createCircularButton(String symbol) {
        RoundedButton btn = new RoundedButton(symbol);
        btn.setBackground(COLOR_NARANJA);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 20));
        btn.setPreferredSize(new Dimension(45, 45));
        btn.setMargin(new Insets(0,0,0,0)); // Quita márgenes internos
        return btn;
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

    // CLASE INTERNA PARA DIBUJAR LA PAPELERA ROJA
    private static class TrashIcon implements Icon {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.RED);
            
            // Tapa
            g2.fillRoundRect(x, y, 20, 4, 2, 2);
            g2.fillRect(x + 5, y - 3, 10, 3);
            // Cuerpo
            g2.fillRoundRect(x + 2, y + 6, 16, 20, 5, 5);
            // Rayas blancas
            g2.setColor(Color.WHITE);
            g2.fillRect(x + 6, y + 10, 2, 12);
            g2.fillRect(x + 12, y + 10, 2, 12);
            
            g2.dispose();
        }

        @Override
        public int getIconWidth() { return 24; }
        @Override
        public int getIconHeight() { return 28; }
    }
}