package View.Ingredient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import View.CustomComponents.*;
import Context.User.UserSession;

public class IngredientListView extends JFrame {

    public RoundedButton btnNuevoIngrediente;
    public RoundedButton btnMenu, btnPlatos;
    public RoundedTextField txtBuscar;
    public JPanel gridPanel;

    private final Color COLOR_FONDO = new Color(242, 244, 247);
    private final Color COLOR_BLANCO = Color.WHITE;
    private final Color COLOR_AZUL_UCV = new Color(30, 80, 150);
    private final Color COLOR_NARANJA = new Color(255, 180, 120); 
    private final Color COLOR_AZUL_CLARO = new Color(85, 170, 255);

    public IngredientListView() {
        setTitle("SGCU - Inventario de Ingredientes");
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
        headerPanel.add(createProfileBadge(textoBadge));
        // ---------------------------------
        
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        navPanel.setBackground(COLOR_FONDO);
        navPanel.setBorder(new EmptyBorder(10, 0, 10, 0)); 

        btnMenu = new RoundedButton("Menús");
        estilarBoton(btnMenu, COLOR_AZUL_CLARO);

        btnPlatos = new RoundedButton("Platos");
        estilarBoton(btnPlatos, COLOR_AZUL_CLARO);

        navPanel.add(btnMenu);
        navPanel.add(btnPlatos);

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
        
        JLabel lblTitle = new JLabel("Inventario");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitle.setForeground(COLOR_AZUL_UCV);
        
        btnNuevoIngrediente = new RoundedButton("Nuevo Ingrediente");
        btnNuevoIngrediente.setBackground(COLOR_AZUL_UCV);
        btnNuevoIngrediente.setForeground(Color.WHITE);
        btnNuevoIngrediente.setPreferredSize(new Dimension(200, 38));
        btnNuevoIngrediente.setCursor(new Cursor(Cursor.HAND_CURSOR));

        leftTool.add(lblTitle);
        leftTool.add(btnNuevoIngrediente);

        JPanel searchBox = new JPanel(new BorderLayout());
        searchBox.setBackground(COLOR_BLANCO);
        txtBuscar = new RoundedTextField();
        txtBuscar.setText(" Buscar ingrediente...");
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

        gridPanel = new JPanel(new GridLayout(0, 5, 20, 20)); 
        gridPanel.setBackground(COLOR_BLANCO);
        JScrollPane scroll = new JScrollPane(gridPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(COLOR_BLANCO);
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

    public void addIngredientCard(String name, String stock, String imageName) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_NARANJA); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        card.setLayout(new BorderLayout());
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(160, 130));
        card.setBorder(new EmptyBorder(8, 10, 8, 10));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 0));
        actions.setOpaque(false);
        actions.add(createIconButton(new EditIcon()));
        actions.add(createIconButton(new TrashIcon()));

        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        ImageIcon icon = loadScaledImage("assets/images/" + imageName + ".jpg", 50, 40);
        if (icon == null) icon = loadScaledImage("assets/images/" + imageName + ".png", 50, 40);
        if (icon != null) imgLabel.setIcon(icon);
        else imgLabel.setIcon(new BoxIcon()); 

        JPanel info = new JPanel(new GridLayout(2, 1));
        info.setOpaque(false);
        info.setBorder(new EmptyBorder(5, 0, 0, 0));
        
        JLabel lblName = new JLabel(name);
        lblName.setForeground(Color.WHITE);
        lblName.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblName.setHorizontalAlignment(SwingConstants.LEFT);
        
        JLabel lblStock = new JLabel(stock); 
        lblStock.setForeground(new Color(255, 245, 230));
        lblStock.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblStock.setHorizontalAlignment(SwingConstants.LEFT);

        info.add(lblName);
        info.add(lblStock);
        card.add(actions, BorderLayout.NORTH);
        card.add(imgLabel, BorderLayout.CENTER);
        card.add(info, BorderLayout.SOUTH);
        gridPanel.add(card);
    }
    
    private ImageIcon loadScaledImage(String path, int w, int h) {
        File f = new File(path);
        if (f.exists()) return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
        return null;
    }

    private JButton createIconButton(Icon icon) {
        JButton btn = new JButton(icon);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(20, 20));
        return btn;
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

    private static class BoxIcon implements Icon { 
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(255, 255, 255, 100));
            g2.drawRect(x+15, y+5, 20, 20); 
            g2.fillRect(x+15, y+5, 20, 20);
        }
        public int getIconWidth() { return 50; }
        public int getIconHeight() { return 40; }
    }
    private static class TrashIcon implements Icon {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(Color.WHITE); 
            g.fillRect(x+2, y, 8, 2); g.fillRect(x+3, y+3, 6, 7);
        }
        public int getIconWidth() { return 12; }
        public int getIconHeight() { return 12; }
    }
    private static class EditIcon implements Icon {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(Color.WHITE);
            int[] xs = {x, x+8, x+10, x+2}; int[] ys = {x+10, x+2, x+4, x+12};
            g.fillPolygon(xs, ys, 4);
        }
        public int getIconWidth() { return 12; }
        public int getIconHeight() { return 12; }
    }
    private static class SearchIcon implements Icon {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D)g; g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.GRAY); g2.setStroke(new BasicStroke(2));
            g2.drawOval(x, y, 8, 8); g2.drawLine(x+7, y+7, x+11, y+11);
        }
        public int getIconWidth() { return 12; }
        public int getIconHeight() { return 12; }
    }
}