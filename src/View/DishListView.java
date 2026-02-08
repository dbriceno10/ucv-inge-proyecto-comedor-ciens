package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.io.File;

public class DishListView extends JFrame {

    // --- Componentes Públicos ---
    public RoundedButton btnCrearPlato;
    public RoundedTextField txtBuscar;
    public JPanel gridPanel; // Aquí se agregan las tarjetas

    // --- Colores Figma ---
    private final Color COLOR_FONDO = new Color(242, 244, 247);
    private final Color COLOR_BLANCO = Color.WHITE;
    private final Color COLOR_AZUL_UCV = new Color(30, 80, 150);
    private final Color COLOR_NARANJA = new Color(255, 180, 120);

    public DishListView() {
        setTitle("SGCU - Listado de Platos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 1. PANEL PRINCIPAL (Fondo Gris)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COLOR_FONDO);
        mainPanel.setBorder(new EmptyBorder(20, 60, 20, 60)); // Márgenes laterales

        // 2. HEADER SUPERIOR (Perfil)
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(COLOR_FONDO);
        headerPanel.add(createProfileBadge("Daniel Briceño (Administrador)"));
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // 3. TARJETA BLANCA (Contenedor Principal)
        JPanel whiteCard = new JPanel(new BorderLayout());
        whiteCard.setBackground(COLOR_BLANCO);
        whiteCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            new EmptyBorder(30, 40, 30, 40)
        ));

        // --- A. BARRA DE HERRAMIENTAS ---
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(COLOR_BLANCO);
        toolbar.setBorder(new EmptyBorder(0, 0, 25, 0));

        // Izquierda: Título y Botón Crear
        JPanel leftTool = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        leftTool.setBackground(COLOR_BLANCO);
        
        JLabel lblTitle = new JLabel("Lista de Platos");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitle.setForeground(COLOR_AZUL_UCV);
        
        btnCrearPlato = new RoundedButton("Crear Plato");
        btnCrearPlato.setBackground(COLOR_AZUL_UCV);
        btnCrearPlato.setForeground(Color.WHITE);
        btnCrearPlato.setPreferredSize(new Dimension(130, 38));
        btnCrearPlato.setFont(new Font("SansSerif", Font.BOLD, 12));

        leftTool.add(lblTitle);
        leftTool.add(btnCrearPlato);

        // Derecha: Buscador con Lupa
        JPanel searchBox = new JPanel(new BorderLayout());
        searchBox.setBackground(COLOR_BLANCO);
        
        txtBuscar = new RoundedTextField();
        txtBuscar.setText(" Buscar Plato..."); // Placeholder simple
        txtBuscar.setForeground(Color.GRAY);
        txtBuscar.setPreferredSize(new Dimension(220, 38));
        
        // Icono Lupa a la derecha del campo
        JLabel searchIcon = new JLabel(new SearchIcon());
        searchIcon.setBorder(new EmptyBorder(0, 8, 0, 0));
        
        JPanel searchWrapper = new JPanel(new BorderLayout());
        searchWrapper.setOpaque(false);
        searchWrapper.add(txtBuscar, BorderLayout.CENTER);
        searchWrapper.add(searchIcon, BorderLayout.EAST);

        toolbar.add(leftTool, BorderLayout.WEST);
        toolbar.add(searchWrapper, BorderLayout.EAST);

        whiteCard.add(toolbar, BorderLayout.NORTH);

        // --- B. GRILLA DE PLATOS (Tarjetas Naranjas) ---
        gridPanel = new JPanel(new GridLayout(0, 4, 25, 25)); // 4 Columnas, espacio de 25px
        gridPanel.setBackground(COLOR_BLANCO);

        // Añadimos tarjetas de ejemplo
        addDishCard("Pasta con Vegetales");
        addDishCard("Pasta con Vegetales");
        addDishCard("Pasta con Vegetales");
        addDishCard("Pasta con Vegetales");
        addDishCard("Pasta con Vegetales");

        // ScrollPane limpio
        JScrollPane scroll = new JScrollPane(gridPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(COLOR_BLANCO);
        scroll.getVerticalScrollBar().setUnitIncrement(16); // Scroll más suave
        
        whiteCard.add(scroll, BorderLayout.CENTER);

        mainPanel.add(whiteCard, BorderLayout.CENTER);
        
        // Configuración Final Frame
        this.setContentPane(mainPanel);
        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    // --- MÉTODO PARA CREAR TARJETA NARANJA ---
    private void addDishCard(String name) {
        // Panel con fondo pintado manualmente (Naranja redondeado)
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_NARANJA);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            }
        };
        card.setLayout(new BorderLayout());
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(200, 150));
        card.setBorder(new EmptyBorder(10, 15, 10, 15)); // Padding interno de la tarjeta

        // 1. Iconos de Acción (Arriba Derecha)
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actionsPanel.setOpaque(false);
        
        JButton btnEdit = createIconButton(new EditIcon());
        JButton btnDel = createIconButton(new TrashIcon());
        
        actionsPanel.add(btnEdit);
        actionsPanel.add(btnDel);

        // 2. Imagen del Plato (Centro)
        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // Carga la imagen 'pasta.jpg'
        ImageIcon pastaImg = loadScaledImage("assets/images/pasta.jpg", 90, 65);
        if (pastaImg != null) {
            imgLabel.setIcon(pastaImg);
        } else {
            // Fallback si no hay imagen
            imgLabel.setText("Sin Foto");
            imgLabel.setForeground(Color.WHITE);
        }

        // 3. Nombre del Plato (Abajo)
        JLabel nameLabel = new JLabel(name);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        nameLabel.setBorder(new EmptyBorder(5, 0, 5, 0));

        card.add(actionsPanel, BorderLayout.NORTH);
        card.add(imgLabel, BorderLayout.CENTER);
        card.add(nameLabel, BorderLayout.SOUTH);

        gridPanel.add(card);
    }

    // --- UTILS ---

    private JButton createIconButton(Icon icon) {
        JButton btn = new JButton(icon);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(28, 28)); // Área clickeable
        return btn;
    }

    private JPanel createProfileBadge(String text) {
        JPanel badge = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        badge.setBackground(COLOR_BLANCO);
        badge.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        
        JLabel iconLbl = new JLabel();
        ImageIcon icon = loadScaledImage("assets/images/admin_profile.png", 35, 35);
        if (icon != null) iconLbl.setIcon(icon);
        else iconLbl.setText("●");
        
        JLabel nameLbl = new JLabel(text);
        nameLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        nameLbl.setForeground(COLOR_AZUL_UCV);

        badge.add(iconLbl);
        badge.add(nameLbl);
        return badge;
    }

    private ImageIcon loadScaledImage(String path, int w, int h) {
        File f = new File(path);
        if (f.exists()) {
            return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
        }
        return null;
    }

    // --- CLASES DE ICONOS DIBUJADOS (Sin descargar nada) ---

    // 1. Icono Lápiz Mejorado (Azul, Rotado, Geométrico)
    private static class EditIcon implements Icon {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Color Azul UCV o Blanco si quieres contraste
            g2.setColor(new Color(30, 80, 150)); 

            // Trasladar al centro y rotar 45 grados
            int cx = x + 8;
            int cy = y + 8;
            AffineTransform old = g2.getTransform();
            g2.rotate(Math.toRadians(45), cx, cy);

            // Dibujar cuerpo (Rectángulo)
            g2.fillRect(cx - 3, cy - 6, 6, 10);
            
            // Dibujar punta (Triángulo)
            int[] px = {cx - 3, cx + 3, cx};
            int[] py = {cy + 4, cy + 4, cy + 8};
            g2.fillPolygon(px, py, 3);
            
            // Dibujar goma (Rectángulo superior)
            g2.setColor(new Color(100, 180, 255)); // Azul más claro para la goma
            g2.fillRect(cx - 3, cy - 9, 6, 2);

            g2.setTransform(old);
            g2.dispose();
        }
        @Override
        public int getIconWidth() { return 16; }
        @Override
        public int getIconHeight() { return 16; }
    }

    // 2. Icono Papelera (Rojo)
    private static class TrashIcon implements Icon {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.RED);
            
            // Tapa
            g2.fillRoundRect(x, y, 14, 3, 2, 2);
            g2.fillRect(x + 4, y - 2, 6, 2); // Asa
            
            // Cuerpo
            g2.fillRoundRect(x + 2, y + 4, 10, 12, 3, 3);
            
            // Líneas
            g2.setColor(Color.WHITE);
            g2.fillRect(x + 4, y + 6, 1, 8);
            g2.fillRect(x + 9, y + 6, 1, 8); // Ajustado para centrar mejor
            
            g2.dispose();
        }
        @Override
        public int getIconWidth() { return 16; }
        @Override
        public int getIconHeight() { return 18; }
    }

    // 3. Icono Lupa (Gris)
    private static class SearchIcon implements Icon {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(2f));
            
            int size = 10;
            g2.drawOval(x, y, size, size); // Círculo
            g2.drawLine(x + size - 2, y + size - 2, x + size + 3, y + size + 3); // Mango
            
            g2.dispose();
        }
        @Override
        public int getIconWidth() { return 16; }
        @Override
        public int getIconHeight() { return 16; }
    }
}