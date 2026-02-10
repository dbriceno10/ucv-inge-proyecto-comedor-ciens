package View.Menu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

import View.CustomComponents.RoundedButton;
import View.CustomComponents.RoundedTextField;
import View.Food.DishListView;           
import View.Ingredient.IngredientListView; 

import Context.User.UserSession;
import DTO.User.AuthUserDto;

public class MenuListView extends JFrame {

    public RoundedButton btnCrearMenu, btnInventario, btnPlatos; 
    public RoundedTextField txtFechaDesde, txtFechaHasta;
    public JPanel gridPanel;

    // --- Colores ---
    private final Color COLOR_FONDO = new Color(242, 244, 247);
    private final Color COLOR_BLANCO = Color.WHITE;
    private final Color COLOR_AZUL_OSCURO = new Color(30, 80, 150); // Para Crear Menú
    private final Color COLOR_AZUL_CLARO = new Color(85, 170, 255); // Para Navegación (Nuevo)
    private final Color COLOR_NARANJA = new Color(255, 180, 120);

    public MenuListView() {
        setTitle("SGCU - Gestión de Menús");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 1. PANEL PRINCIPAL
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COLOR_FONDO);
        mainPanel.setBorder(new EmptyBorder(20, 60, 20, 60));

        // --- CONTENEDOR SUPERIOR (Perfil + Botones Navegación) ---
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setBackground(COLOR_FONDO);

        // A. Header (Perfil)
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        headerPanel.setBackground(COLOR_FONDO);
        headerPanel.add(createProfileBadge("Daniel Briceño (Administrador)"));
        
        // B. Panel de Navegación (Botones Azul Claro fuera de la tarjeta)
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        navPanel.setBackground(COLOR_FONDO);
        navPanel.setBorder(new EmptyBorder(10, 0, 10, 0)); // Un poco de aire arriba y abajo

        // Botón Inventario
        btnInventario = new RoundedButton("Inventario");
        estilarBoton(btnInventario, COLOR_AZUL_CLARO);
        btnInventario.addActionListener(e -> new IngredientListView().setVisible(true));

        // Botón Platos
        btnPlatos = new RoundedButton("Platos");
        estilarBoton(btnPlatos, COLOR_AZUL_CLARO);
        btnPlatos.addActionListener(e -> new DishListView().setVisible(true));

        navPanel.add(btnInventario);
        navPanel.add(btnPlatos);

        // Agregamos al contenedor superior
        topContainer.add(headerPanel);
        topContainer.add(navPanel); // Esto los pone encima de la tarjeta, a la derecha

        mainPanel.add(topContainer, BorderLayout.NORTH);

        // --- 2. TARJETA BLANCA (Contenido Principal) ---
        JPanel whiteCard = new JPanel(new BorderLayout());
        whiteCard.setBackground(COLOR_BLANCO);
        whiteCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            new EmptyBorder(30, 40, 30, 40)
        ));

        // --- TOOLBAR INTERNA (Título, Crear Menú y Fechas) ---
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(COLOR_BLANCO);
        toolbar.setBorder(new EmptyBorder(0, 0, 25, 0));

        // Izquierda: Título y Crear Menú
        JPanel leftTool = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftTool.setBackground(COLOR_BLANCO);
        
        JLabel lblTitle = new JLabel("Lista de Menús"); // Título corregido
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setForeground(COLOR_AZUL_OSCURO);
        
        btnCrearMenu = new RoundedButton("Crear Menú");
        estilarBoton(btnCrearMenu, COLOR_AZUL_OSCURO); // Este se queda oscuro
        btnCrearMenu.addActionListener(e -> new MenuEditorView().setVisible(true));

        leftTool.add(lblTitle);
        leftTool.add(btnCrearMenu);

        // Derecha: Filtros de Fecha
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        filterPanel.setBackground(COLOR_BLANCO);

        txtFechaDesde = createDateInput("01/12/2025");
        txtFechaHasta = createDateInput("12/12/2025");
        
        filterPanel.add(txtFechaDesde.getParent());
        filterPanel.add(txtFechaHasta.getParent());

        toolbar.add(leftTool, BorderLayout.WEST);
        toolbar.add(filterPanel, BorderLayout.EAST);

        whiteCard.add(toolbar, BorderLayout.NORTH);

        // --- GRILLA DE MENÚS ---
        gridPanel = new JPanel(new GridLayout(0, 4, 25, 25));
        gridPanel.setBackground(COLOR_BLANCO);

        addMenuCard("08/12/2025");
        addMenuCard("09/12/2025");
        addMenuCard("10/12/2025");
        addMenuCard("11/12/2025");
        addMenuCard("12/12/2025");

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

    // --- MÉTODOS AUXILIARES ---

    private void estilarBoton(RoundedButton btn, Color colorFondo) {
        btn.setBackground(colorFondo);
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(130, 38));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
    }

    private void addMenuCard(String fecha) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_NARANJA);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        card.setLayout(new BorderLayout());
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(200, 120));
        card.setBorder(new EmptyBorder(10, 20, 10, 10));

        JLabel dateLabel = new JLabel(fecha);
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        dateLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        JPanel actionsPanel = new JPanel(new GridLayout(3, 1, 0, 5));
        actionsPanel.setOpaque(false);
        actionsPanel.add(createIconButton(new EyeIcon()));
        actionsPanel.add(createIconButton(new EditIcon()));
        actionsPanel.add(createIconButton(new TrashIcon()));

        JPanel rightWrapper = new JPanel(new BorderLayout());
        rightWrapper.setOpaque(false);
        rightWrapper.add(actionsPanel, BorderLayout.EAST);

        card.add(dateLabel, BorderLayout.CENTER);
        card.add(rightWrapper, BorderLayout.EAST);
        gridPanel.add(card);
    }

    private RoundedTextField createDateInput(String placeholder) {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(COLOR_BLANCO);
        
        RoundedTextField txt = new RoundedTextField();
        txt.setText(placeholder);
        txt.setPreferredSize(new Dimension(110, 30));
        
        JLabel icon = new JLabel(new CalendarIcon());
        icon.setBorder(new EmptyBorder(0, 5, 0, 0));
        
        JPanel inputWrap = new JPanel(new BorderLayout());
        inputWrap.setOpaque(false);
        inputWrap.add(txt, BorderLayout.CENTER);
        inputWrap.add(icon, BorderLayout.EAST);
        
        container.add(inputWrap, BorderLayout.CENTER);
        return txt;
    }

    private JButton createIconButton(Icon icon) {
        JButton btn = new JButton(icon);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JPanel createProfileBadge(String textoDefault) {
        JPanel badge = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        badge.setBackground(COLOR_BLANCO);
        badge.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        
        AuthUserDto user = UserSession.getInstance().getUser();
        String nombreMostrar = (user != null) ? user.getFirstName() + " " + user.getLastName() : textoDefault;
        
        JLabel iconLbl = new JLabel();
        String path = "assets/images/admin_profile.png"; 
        if(new File(path).exists()) iconLbl.setIcon(new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH)));
        else iconLbl.setText("●");
        
        JLabel nameLbl = new JLabel(nombreMostrar);
        nameLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        nameLbl.setForeground(COLOR_AZUL_OSCURO);

        badge.add(iconLbl);
        badge.add(nameLbl);
        return badge;
    }

    // Iconos
    private static class EyeIcon implements Icon {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g; g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(30, 80, 150));
            g2.drawArc(x, y+2, 16, 12, 0, 360); g2.fillOval(x+5, y+5, 6, 6);
        }
        public int getIconWidth() { return 16; } public int getIconHeight() { return 16; }
    }
    private static class CalendarIcon implements Icon {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g; g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.GRAY);
            g2.drawRect(x, y+2, 14, 12); g2.fillRect(x, y+2, 14, 3);
            g2.drawLine(x+3, y, x+3, y+2); g2.drawLine(x+10, y, x+10, y+2);
        }
        public int getIconWidth() { return 16; } public int getIconHeight() { return 16; }
    }
    private static class TrashIcon implements Icon {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(Color.RED); g.fillRect(x+3, y+3, 10, 12); g.fillRect(x+1, y, 14, 2);
        }
        public int getIconWidth() { return 16; } public int getIconHeight() { return 16; }
    }
    private static class EditIcon implements Icon {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(Color.BLUE);
            int[] xs = {x+2, x+10, x+12, x+4}; int[] ys = {x+10, x+2, x+4, x+12};
            g.fillPolygon(xs, ys, 4);
        }
        public int getIconWidth() { return 16; } public int getIconHeight() { return 16; }
    }
}