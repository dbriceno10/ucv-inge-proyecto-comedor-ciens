package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import View.CustomComponents.*;

public class MenuListView extends JFrame {

    // --- Componentes Públicos ---
    public RoundedButton btnCrearMenu;
    public RoundedTextField txtFechaDesde, txtFechaHasta;
    public JPanel gridPanel;

    // --- Colores ---
    private final Color COLOR_FONDO = new Color(242, 244, 247);
    private final Color COLOR_BLANCO = Color.WHITE;
    private final Color COLOR_AZUL_UCV = new Color(30, 80, 150);
    private final Color COLOR_NARANJA = new Color(255, 180, 120);

    public MenuListView() {
        setTitle("SGCU - Gestión de Menús");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 1. PANEL PRINCIPAL
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COLOR_FONDO);
        mainPanel.setBorder(new EmptyBorder(20, 60, 20, 60));

        // 2. HEADER (Perfil)
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(COLOR_FONDO);
        headerPanel.add(createProfileBadge("Daniel Briceño (Administrador)"));
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // 3. TARJETA BLANCA
        JPanel whiteCard = new JPanel(new BorderLayout());
        whiteCard.setBackground(COLOR_BLANCO);
        whiteCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            new EmptyBorder(30, 40, 30, 40)
        ));

        // --- A. TOOLBAR (Crear + Filtros de Fecha) ---
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(COLOR_BLANCO);
        toolbar.setBorder(new EmptyBorder(0, 0, 25, 0));

        // Izquierda: Título y Botón Crear
        JPanel leftTool = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        leftTool.setBackground(COLOR_BLANCO);
        
        JLabel lblTitle = new JLabel("Lista de Menús");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitle.setForeground(COLOR_AZUL_UCV);
        
        btnCrearMenu = new RoundedButton("Crear Menú");
        btnCrearMenu.setBackground(COLOR_AZUL_UCV);
        btnCrearMenu.setForeground(Color.WHITE);
        btnCrearMenu.setPreferredSize(new Dimension(130, 38));

        leftTool.add(lblTitle);
        leftTool.add(btnCrearMenu);

        // Derecha: Filtros de Fecha (Desde / Hasta)
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        filterPanel.setBackground(COLOR_BLANCO);

        txtFechaDesde = createDateInput("DESDE", "01/12/2025");
        txtFechaHasta = createDateInput("HASTA", "12/12/2025");
        
        filterPanel.add(txtFechaDesde.getParent()); // Añadimos el contenedor padre
        filterPanel.add(txtFechaHasta.getParent());

        toolbar.add(leftTool, BorderLayout.WEST);
        toolbar.add(filterPanel, BorderLayout.EAST);

        whiteCard.add(toolbar, BorderLayout.NORTH);

        // --- B. GRILLA DE MENÚS ---
        gridPanel = new JPanel(new GridLayout(0, 4, 25, 25));
        gridPanel.setBackground(COLOR_BLANCO);

        // Añadimos tarjetas de ejemplo (Fechas)
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

    // --- TARJETA DE MENÚ (Naranja con Iconos Verticales) ---
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

        // 1. Fecha (Izquierda/Centro)
        JLabel dateLabel = new JLabel(fecha);
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        dateLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        // 2. Iconos de Acción (Columna Derecha)
        JPanel actionsPanel = new JPanel(new GridLayout(3, 1, 0, 5)); // 3 filas, 1 col
        actionsPanel.setOpaque(false);
        
        // Botones pequeños
        JButton btnView = createIconButton(new EyeIcon(), Color.WHITE);
        JButton btnEdit = createIconButton(new EditIcon(), Color.BLUE);
        JButton btnDel = createIconButton(new TrashIcon(), Color.RED);
        
        actionsPanel.add(btnView);
        actionsPanel.add(btnEdit);
        actionsPanel.add(btnDel);

        // Contenedor para alinear iconos a la derecha
        JPanel rightWrapper = new JPanel(new BorderLayout());
        rightWrapper.setOpaque(false);
        rightWrapper.add(actionsPanel, BorderLayout.EAST);

        card.add(dateLabel, BorderLayout.CENTER);
        card.add(rightWrapper, BorderLayout.EAST);

        gridPanel.add(card);
    }

    // --- INPUT DE FECHA CON ETIQUETA ---
    private RoundedTextField createDateInput(String labelText, String placeholder) {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(COLOR_BLANCO);
        
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 9));
        lbl.setForeground(Color.GRAY);
        
        RoundedTextField txt = new RoundedTextField();
        txt.setText(placeholder);
        txt.setPreferredSize(new Dimension(110, 30));
        
        // Icono Calendario a la derecha
        JLabel icon = new JLabel(new CalendarIcon());
        icon.setBorder(new EmptyBorder(0, 5, 0, 0));
        
        JPanel inputWrap = new JPanel(new BorderLayout());
        inputWrap.setOpaque(false);
        inputWrap.add(txt, BorderLayout.CENTER);
        inputWrap.add(icon, BorderLayout.EAST);
        
        container.add(lbl, BorderLayout.NORTH);
        container.add(inputWrap, BorderLayout.CENTER);
        
        return txt;
    }

    // --- UTILS ---
    private JButton createIconButton(Icon icon, Color c) {
        JButton btn = new JButton(icon);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JPanel createProfileBadge(String text) {
        JPanel badge = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        badge.setBackground(COLOR_BLANCO);
        badge.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        
        JLabel iconLbl = new JLabel();
        File f = new File("assets/images/admin_profile.png");
        if(f.exists()) iconLbl.setIcon(new ImageIcon(new ImageIcon(f.getPath()).getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH)));
        else iconLbl.setText("●");
        
        JLabel nameLbl = new JLabel(text);
        nameLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        nameLbl.setForeground(COLOR_AZUL_UCV);

        badge.add(iconLbl);
        badge.add(nameLbl);
        return badge;
    }

    // --- ICONOS DIBUJADOS ---

    // 1. Icono OJO (Ver)
    private static class EyeIcon implements Icon {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(30, 80, 150)); // Azul
            // Forma de almendra
            g2.drawArc(x, y+2, 16, 12, 0, 360); 
            // Pupila
            g2.fillOval(x+5, y+5, 6, 6);
        }
        public int getIconWidth() { return 16; }
        public int getIconHeight() { return 16; }
    }

    // 2. Icono CALENDARIO
    private static class CalendarIcon implements Icon {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.GRAY);
            g2.drawRect(x, y+2, 14, 12); // Marco
            g2.fillRect(x, y+2, 14, 3); // Header
            g2.drawLine(x+3, y, x+3, y+2); // Anillas
            g2.drawLine(x+10, y, x+10, y+2);
        }
        public int getIconWidth() { return 16; }
        public int getIconHeight() { return 16; }
    }
    
    // 3. Reutilizamos Trash y Edit del anterior (versiones simplificadas aquí)
    private static class TrashIcon implements Icon {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(Color.RED); g.fillRect(x+3, y+3, 10, 12);
            g.fillRect(x+1, y, 14, 2);
        }
        public int getIconWidth() { return 16; }
        public int getIconHeight() { return 16; }
    }
    private static class EditIcon implements Icon {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(Color.BLUE);
            int[] xs = {x+2, x+10, x+12, x+4}; int[] ys = {x+10, x+2, x+4, x+12};
            g.fillPolygon(xs, ys, 4);
        }
        public int getIconWidth() { return 16; }
        public int getIconHeight() { return 16; }
    }
}