package View.Menu;

import View.CustomComponents.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.awt.event.*;

public class MenuEditorView extends JFrame {
    private RoundedButton btnSave, btnCancel, btnAddDish;
    private RoundedTextField txtDate;
    private JPanel listPanel;
    private Colors color = new Colors();

    public MenuEditorView() {
        setTitle("SGCU - Crear/Editar Menú");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // 1. CONFIGURACIÓN DEL PANEL PRINCIPAL (Fondo Gris)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(color.BACKGROUND);
        // Margen grande para centrar la tarjeta blanca
        mainPanel.setBorder(new EmptyBorder(20, 80, 40, 80)); 

        // 2. HEADER SUPERIOR (Perfil fuera de la tarjeta)
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(color.BACKGROUND);
        headerPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        headerPanel.add(createProfileBadge("Daniel Briceño (Administrador)"));
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // 3. TARJETA BLANCA (Contenido)
        JPanel whiteCard = new JPanel(new BorderLayout());
        whiteCard.setBackground(color.WHITE);
        // Sombra simulada con borde compuesto
        whiteCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.LIGHT_GRAY, 1),
            new EmptyBorder(30, 40, 30, 40)
        ));

        // --- A. CABECERA DE LA TARJETA (Fecha) ---
        JPanel cardHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cardHeader.setBackground(color.WHITE);
        
        JLabel lblFecha = new JLabel("FECHA");
        lblFecha.setFont(new Font("SansSerif", Font.BOLD, 10));
        lblFecha.setForeground(color.DARK_GRAY);
        
        txtDate = new RoundedTextField();
        txtDate.setText("08/12/2025");
        txtDate.setPreferredSize(new Dimension(150, 35));
        
        JPanel dateBox = new JPanel(new BorderLayout());
        dateBox.setBackground(color.WHITE);
        dateBox.add(lblFecha, BorderLayout.NORTH);
        dateBox.add(txtDate, BorderLayout.CENTER);
        
        cardHeader.add(dateBox);
        whiteCard.add(cardHeader, BorderLayout.NORTH);

        // --- B. LISTA DE PLATOS (Centro) ---
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(color.WHITE);

        // Espacio inicial y platos de ejemplo
        listPanel.add(Box.createVerticalStrut(20));
        addPlatoRow("Pasta con Vegetales");
        addPlatoRow("Pasta con Vegetales");
        addPlatoRow("Pasta con Vegetales");

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null); // Sin borde feo
        scroll.getViewport().setBackground(color.WHITE);
        whiteCard.add(scroll, BorderLayout.CENTER);

        // --- C. FOOTER (Botones) ---
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(color.WHITE);
        footerPanel.setBorder(new EmptyBorder(30, 0, 0, 0));

        // Botón Izquierdo: Añadir
        btnAddDish = new RoundedButton("Añadir Plato");
        btnAddDish.setBackground(color.LIGHT_BLUE);
        btnAddDish.setForeground(Color.WHITE);
        btnAddDish.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnAddDish.setPreferredSize(new Dimension(140, 40));
        
        JPanel leftBtnBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftBtnBox.setBackground(color.WHITE);
        leftBtnBox.add(btnAddDish);

        // Botones Derechos: Guardar/Cancelar
        JPanel rightBtnBox = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightBtnBox.setBackground(color.WHITE);

        btnSave = new RoundedButton("Guardar");
        btnSave.setBackground(color.OXFORD_BLUE);
        btnSave.setForeground(color.WHITE);
        btnSave.setPreferredSize(new Dimension(120, 40));

        btnCancel = new RoundedButton("Cancelar");
        btnCancel.setBackground(color.YELLOW);
        btnCancel.setForeground(color.BLACK);
        btnCancel.setPreferredSize(new Dimension(120, 40));

        rightBtnBox.add(btnSave);
        rightBtnBox.add(btnCancel);

        footerPanel.add(leftBtnBox, BorderLayout.WEST);
        footerPanel.add(rightBtnBox, BorderLayout.EAST);

        whiteCard.add(footerPanel, BorderLayout.SOUTH);

        // AÑADIR AL FRAME
        mainPanel.add(whiteCard, BorderLayout.CENTER);
        this.setContentPane(mainPanel); // Asegura que el fondo gris se vea
        
        // Finalizar
        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    // --- MÉTODOS AUXILIARES ---

    private JPanel createProfileBadge(String text) {
        JPanel badge = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        badge.setBackground(color.WHITE);
        badge.setBorder(BorderFactory.createLineBorder(color.LIGHT_GRAY, 1));
        
        // Cargar foto de perfil (admin_profile.png)
        JLabel iconLbl = new JLabel();
        ImageIcon icon = loadScaledImage("assets/images/admin_profile.png", 40, 40);
        
        if (icon != null) {
            iconLbl.setIcon(icon);
        } else {
            iconLbl.setText("●"); // Fallback si no hay imagen
            iconLbl.setForeground(color.DARK_GRAY);
        }
        
        JLabel nameLbl = new JLabel(text);
        nameLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        nameLbl.setForeground(color.OXFORD_BLUE);

        badge.add(iconLbl);
        badge.add(nameLbl);
        return badge;
    }

    private void addPlatoRow(String nombrePlato) {
        JPanel row = new JPanel(new GridBagLayout());
        row.setBackground(color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.fill = GridBagConstraints.NONE;

        // 1. CARD DEL PLATO (Izquierda)
        gbc.gridx = 0; gbc.weightx = 0.4; gbc.anchor = GridBagConstraints.WEST;
        
        RoundedButton dishCard = new RoundedButton(nombrePlato);
        dishCard.setBackground(color.ORANGE);
        dishCard.setForeground(color.WHITE);
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
        ctrlPanel.setBackground(color.WHITE);

        RoundedButton btnMinus = createCircularButton("-");
        
        JPanel inputStack = new JPanel(new BorderLayout());
        inputStack.setBackground(color.WHITE);
        JLabel lblQty = new JLabel("CANTIDAD DISPONIBLE");
        lblQty.setFont(new Font("SansSerif", Font.BOLD, 9));
        lblQty.setForeground(color.DARK_GRAY);
        
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
        btn.setBackground(color.ORANGE);
        btn.setForeground(color.WHITE);
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
        private Colors color = new Colors();
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color.RED);
            
            // Tapa
            g2.fillRoundRect(x, y, 20, 4, 2, 2);
            g2.fillRect(x + 5, y - 3, 10, 3);
            // Cuerpo
            g2.fillRoundRect(x + 2, y + 6, 16, 20, 5, 5);
            // Rayas blancas
            g2.setColor(color.WHITE);
            g2.fillRect(x + 6, y + 10, 2, 12);
            g2.fillRect(x + 12, y + 10, 2, 12);
            
            g2.dispose();
        }

        @Override
        public int getIconWidth() { return 24; }
        @Override
        public int getIconHeight() { return 28; }
    }

    public String getTxtDate() { return txtDate.getText(); }

    public void saveListener(ActionListener listener) { btnSave.addActionListener(listener);}
    public void cancelListener(ActionListener listener) { btnCancel.addActionListener(listener);}
    public void addDishListener(ActionListener listener) { btnAddDish.addActionListener(listener);} 
}
