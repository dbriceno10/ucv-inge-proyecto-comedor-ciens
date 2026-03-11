package View.User;

import View.CustomComponents.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class StudentListView extends JFrame {
    private RoundedButton btnVolver;
    private RoundedTextField txtSearch;
    private JPanel gridPanel;
    private Colors color = new Colors();
    private ActionListener cardListener;

    public StudentListView() {
        setTitle("SGCU - Listado de Estudiantes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 1. PANEL PRINCIPAL (Fondo Gris)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(color.BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(20, 60, 20, 60)); // Márgenes laterales

        // 2. CONTENEDOR SUPERIOR (Perfil + Botón Volver)
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setBackground(color.BACKGROUND);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        headerPanel.setBackground(color.BACKGROUND);

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        navPanel.setBackground(color.BACKGROUND);
        navPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        btnVolver = new RoundedButton("Volver");
        getStyleBtn(btnVolver, color.OXFORD_BLUE);
        btnVolver.setActionCommand("Volver");
        navPanel.add(btnVolver);

        topContainer.add(headerPanel);
        topContainer.add(navPanel);
        mainPanel.add(topContainer, BorderLayout.NORTH);

        // 3. TARJETA BLANCA (Contenedor Principal)
        JPanel whiteCard = new JPanel(new BorderLayout());
        whiteCard.setBackground(color.WHITE);
        whiteCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.LIGHT_GRAY, 1),
            new EmptyBorder(30, 40, 30, 40)));

        // --- A. BARRA DE HERRAMIENTAS ---
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(color.WHITE);
        toolbar.setBorder(new EmptyBorder(0, 0, 25, 0));

        JPanel leftTool = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        leftTool.setBackground(color.WHITE);

        JLabel lblTitle = new JLabel("Lista de Estudiantes");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitle.setForeground(color.OXFORD_BLUE);
        leftTool.add(lblTitle);

        // Buscador con Lupa nativa
        JPanel searchBox = new JPanel(new BorderLayout());
        searchBox.setBackground(color.WHITE);

        txtSearch = new RoundedTextField();
        txtSearch.setText(" Buscar por cédula...");
        txtSearch.setForeground(color.DARK_GRAY);
        txtSearch.setPreferredSize(new Dimension(220, 38));

        JLabel searchIcon = new JLabel(new SearchIcon());
        searchIcon.setBorder(new EmptyBorder(0, 8, 0, 0));

        JPanel searchWrapper = new JPanel(new BorderLayout());
        searchWrapper.setOpaque(false);
        searchWrapper.add(txtSearch, BorderLayout.CENTER);
        searchWrapper.add(searchIcon, BorderLayout.EAST);

        toolbar.add(leftTool, BorderLayout.WEST);
        toolbar.add(searchWrapper, BorderLayout.EAST);
        whiteCard.add(toolbar, BorderLayout.NORTH);

        // --- B. GRILLA DE ESTUDIANTES ---
        gridPanel = new JPanel(new GridLayout(0, 4, 25, 25)); // 4 Columnas
        gridPanel.setBackground(color.WHITE);

        JScrollPane scroll = new JScrollPane(gridPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(color.WHITE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        whiteCard.add(scroll, BorderLayout.CENTER);
        mainPanel.add(whiteCard, BorderLayout.CENTER);

        // Configuración Final Frame
        this.setContentPane(mainPanel);
        setMinimumSize(new Dimension(1024, 768));
        setLocationRelativeTo(null);
    }

    // --- MÉTODO PARA DIBUJAR A LOS ESTUDIANTES ---
    public void addStudentCard(Integer studentId, String name, String documentId, String type) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Color según el tipo
                if ("BECARIO".equalsIgnoreCase(type)) {
                    g2.setColor(new Color(40, 167, 69)); // Verde
                } else if ("EXONERADO".equalsIgnoreCase(type)) {
                    g2.setColor(color.LIGHT_BLUE); // Azul
                } else {
                    g2.setColor(color.DARK_GRAY); // Gris
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            }
        };

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(200, 130));
        card.setBorder(new EmptyBorder(15, 15, 15, 15));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblName = new JLabel(name);
        lblName.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblName.setForeground(color.WHITE);
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblDoc = new JLabel("C.I: " + documentId);
        lblDoc.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblDoc.setForeground(color.WHITE);
        lblDoc.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblType = new JLabel(type != null ? type : "ESTUDIANTE");
        lblType.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblType.setForeground(color.WHITE);
        lblType.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblType.setBorder(new EmptyBorder(10, 0, 0, 0));

        card.add(Box.createVerticalGlue());
        card.add(lblName);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(lblDoc);
        card.add(lblType);
        card.add(Box.createVerticalGlue());

        // Escuchador de clic en la tarjeta
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (cardListener != null) {
                    cardListener.actionPerformed(new ActionEvent(card, ActionEvent.ACTION_PERFORMED, "OPEN_MODAL_" + studentId));
                }
            }
        });

        gridPanel.add(card);
    }

    private void getStyleBtn(RoundedButton btn, Color btnColor) {
        btn.setBackground(btnColor);
        btn.setForeground(color.WHITE);
        btn.setPreferredSize(new Dimension(130, 38));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
    }

    private static class SearchIcon implements Icon {
        private Colors color = new Colors();
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color.BLACK);
            g2.setStroke(new BasicStroke(2f));
            int size = 10;
            g2.drawOval(x, y, size, size);
            g2.drawLine(x + size - 2, y + size - 2, x + size + 3, y + size + 3);
            g2.dispose();
        }
        @Override public int getIconWidth() { return 16; }
        @Override public int getIconHeight() { return 16; }
    }

    // Listeners y Getters
    public void volverListener(ActionListener listener) { btnVolver.addActionListener(listener); }
    public void setCardListener(ActionListener listener) { this.cardListener = listener; }
    public JPanel getComponent_gridPanel() { return gridPanel; }
    public RoundedTextField getSearchField() { return txtSearch; }
}