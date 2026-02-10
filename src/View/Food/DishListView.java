package View.Food;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import View.CustomComponents.*;
import Context.User.UserSession;

public class DishListView extends JFrame {

    public RoundedButton btnCrearPlato;
    public RoundedButton btnMenu, btnInventario; 
    public RoundedTextField txtBuscar;
    public JPanel gridPanel; 

    private final Color COLOR_FONDO = new Color(242, 244, 247);
    private final Color COLOR_BLANCO = Color.WHITE;
    private final Color COLOR_AZUL_UCV = new Color(30, 80, 150);
    private final Color COLOR_NARANJA = new Color(255, 180, 120);
    private final Color COLOR_AZUL_CLARO = new Color(85, 170, 255); 

    public DishListView() {
        setTitle("SGCU - Listado de Platos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COLOR_FONDO);
        mainPanel.setBorder(new EmptyBorder(20, 60, 20, 60)); 

        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setBackground(COLOR_FONDO);

        // --- CORRECCIÓN: NOMBRE LIMPIO ---
        String textoBadge = "Usuario";
        if (UserSession.getInstance().getUser() != null) {
            textoBadge = UserSession.getInstance().getUser().getFirstName() + " " + UserSession.getInstance().getUser().getLastName();
        }

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        headerPanel.setBackground(COLOR_FONDO);
        headerPanel.add(createProfileBadge(textoBadge)); // Solo el nombre, sin rol ni paréntesis
        // ---------------------------------
        
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        navPanel.setBackground(COLOR_FONDO);
        navPanel.setBorder(new EmptyBorder(10, 0, 10, 0)); 

        btnMenu = new RoundedButton("Menús");
        estilarBoton(btnMenu, COLOR_AZUL_CLARO);

        btnInventario = new RoundedButton("Inventario");
        estilarBoton(btnInventario, COLOR_AZUL_CLARO);

        navPanel.add(btnMenu);
        navPanel.add(btnInventario);

        topContainer.add(headerPanel);
        topContainer.add(navPanel);
        mainPanel.add(topContainer, BorderLayout.NORTH);

        JPanel whiteCard = new JPanel(new BorderLayout());
        whiteCard.setBackground(COLOR_BLANCO);
        whiteCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            new EmptyBorder(30, 40, 30, 40)
        ));

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(COLOR_BLANCO);
        toolbar.setBorder(new EmptyBorder(0, 0, 25, 0));

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
        btnCrearPlato.setCursor(new Cursor(Cursor.HAND_CURSOR));

        leftTool.add(lblTitle);
        leftTool.add(btnCrearPlato);

        JPanel searchBox = new JPanel(new BorderLayout());
        searchBox.setBackground(COLOR_BLANCO);
        txtBuscar = new RoundedTextField();
        txtBuscar.setText(" Buscar Plato...");
        txtBuscar.setForeground(Color.GRAY);
        txtBuscar.setPreferredSize(new Dimension(220, 38));
        
        JLabel searchIcon = new JLabel(new SearchIcon());
        searchIcon.setBorder(new EmptyBorder(0, 8, 0, 0));
        JPanel searchWrapper = new JPanel(new BorderLayout());
        searchWrapper.setOpaque(false);
        searchWrapper.add(txtBuscar, BorderLayout.CENTER);
        searchWrapper.add(searchIcon, BorderLayout.EAST);

        toolbar.add(leftTool, BorderLayout.WEST);
        toolbar.add(searchWrapper, BorderLayout.EAST);
        whiteCard.add(toolbar, BorderLayout.NORTH);

        gridPanel = new JPanel(new GridLayout(0, 4, 25, 25));
        gridPanel.setBackground(COLOR_BLANCO);
        JScrollPane scroll = new JScrollPane(gridPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(COLOR_BLANCO);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        
        whiteCard.add(scroll, BorderLayout.CENTER);
        mainPanel.add(whiteCard, BorderLayout.CENTER);
        
        this.setContentPane(mainPanel);
        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    private void estilarBoton(RoundedButton btn, Color colorFondo) {
        btn.setBackground(colorFondo);
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(130, 38));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
    }

    public void addDishCard(String name) {
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
        card.setBorder(new EmptyBorder(10, 15, 10, 15));

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actionsPanel.setOpaque(false);
        actionsPanel.add(createIconButton(new EditIcon()));
        actionsPanel.add(createIconButton(new TrashIcon()));

        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon pastaImg = loadScaledImage("assets/images/pasta.jpg", 90, 65);
        if (pastaImg != null) imgLabel.setIcon(pastaImg);
        else { imgLabel.setText("Sin Foto"); imgLabel.setForeground(Color.WHITE); }

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

    private JButton createIconButton(Icon icon) {
        JButton btn = new JButton(icon);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(28, 28));
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
        badge.add(iconLbl); badge.add(nameLbl);
        return badge;
    }

    private ImageIcon loadScaledImage(String path, int w, int h) {
        File f = new File(path);
        if (f.exists()) return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
        return null;
    }

    // Iconos
    private static class EditIcon implements Icon {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(30, 80, 150)); 
            int cx = x + 8; int cy = y + 8;
            AffineTransform old = g2.getTransform();
            g2.rotate(Math.toRadians(45), cx, cy);
            g2.fillRect(cx - 3, cy - 6, 6, 10);
            int[] px = {cx - 3, cx + 3, cx}; int[] py = {cy + 4, cy + 4, cy + 8};
            g2.fillPolygon(px, py, 3);
            g2.setColor(new Color(100, 180, 255));
            g2.fillRect(cx - 3, cy - 9, 6, 2);
            g2.setTransform(old);
            g2.dispose();
        }
        public int getIconWidth() { return 16; }
        public int getIconHeight() { return 16; }
    }
    private static class TrashIcon implements Icon {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.RED);
            g2.fillRoundRect(x, y, 14, 3, 2, 2);
            g2.fillRect(x + 4, y - 2, 6, 2);
            g2.fillRoundRect(x + 2, y + 4, 10, 12, 3, 3);
            g2.setColor(Color.WHITE);
            g2.fillRect(x + 4, y + 6, 1, 8);
            g2.fillRect(x + 9, y + 6, 1, 8); 
            g2.dispose();
        }
        public int getIconWidth() { return 16; }
        public int getIconHeight() { return 18; }
    }
    private static class SearchIcon implements Icon {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(2f));
            g2.drawOval(x, y, 10, 10);
            g2.drawLine(x + 8, y + 8, x + 13, y + 13);
            g2.dispose();
        }
        public int getIconWidth() { return 16; }
        public int getIconHeight() { return 16; }
    }
}