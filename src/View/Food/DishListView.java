package View.Food;

import View.CustomComponents.*;
import View.Ingredient.IngredientListView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.File;

public class DishListView extends JFrame {
  private RoundedButton btnCreateDish, btnMenu, btnIngredients;
  private RoundedTextField txtSearch;
  private JPanel gridPanel; // Aquí se agregan las tarjetas
  private Colors color = new Colors();

  public DishListView() {
    setTitle("SGCU - Listado de Platos");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // panel de navegacion
    JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
    navPanel.setBackground(color.BACKGROUND);
    navPanel.setBorder(new EmptyBorder(10, 0, 10, 0)); // Un poco de aire arriba y abajo

    // 1. PANEL PRINCIPAL (Fondo Gris)
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(color.BACKGROUND);
    mainPanel.setBorder(new EmptyBorder(20, 60, 20, 60)); // Márgenes laterales

    // 2. HEADER SUPERIOR (Perfil)
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    headerPanel.setBackground(color.BACKGROUND);
    headerPanel.add(createProfileBadge("Daniel Briceño (Administrador)"));
    mainPanel.add(headerPanel, BorderLayout.NORTH);

    // navegacion a otros modulos

    btnMenu = new RoundedButton("Volver");
    getStyleBtn(btnMenu, color.OXFORD_BLUE);
    navPanel.add(btnMenu);
    

    btnIngredients = new RoundedButton("Ingredientes");
    getStyleBtn(btnIngredients, color.OXFORD_BLUE);
    navPanel.add(btnIngredients);
    btnIngredients.addActionListener(e -> new IngredientListView().setVisible(true));

    mainPanel.add(navPanel, BorderLayout.NORTH);

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

    // Izquierda: Título y Botón Crear
    JPanel leftTool = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
    leftTool.setBackground(color.WHITE);

    JLabel lblTitle = new JLabel("Lista de Platos");
    lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
    lblTitle.setForeground(color.OXFORD_BLUE);

    btnCreateDish = new RoundedButton("Crear Plato");
    btnCreateDish.setBackground(color.OXFORD_BLUE);
    btnCreateDish.setForeground(color.WHITE);
    btnCreateDish.setPreferredSize(new Dimension(130, 38));
    btnCreateDish.setFont(new Font("SansSerif", Font.BOLD, 12));

    leftTool.add(lblTitle);
    leftTool.add(btnCreateDish);

    // Derecha: Buscador con Lupa
    JPanel searchBox = new JPanel(new BorderLayout());
    searchBox.setBackground(color.WHITE);

    txtSearch = new RoundedTextField();
    txtSearch.setText(" Buscar Plato..."); // Placeholder simple
    txtSearch.setForeground(color.DARK_GRAY);
    txtSearch.setPreferredSize(new Dimension(220, 38));

    // Icono Lupa a la derecha del campo
    JLabel searchIcon = new JLabel(new SearchIcon());
    searchIcon.setBorder(new EmptyBorder(0, 8, 0, 0));

    JPanel searchWrapper = new JPanel(new BorderLayout());
    searchWrapper.setOpaque(false);
    searchWrapper.add(txtSearch, BorderLayout.CENTER);
    searchWrapper.add(searchIcon, BorderLayout.EAST);

    toolbar.add(leftTool, BorderLayout.WEST);
    toolbar.add(searchWrapper, BorderLayout.EAST);

    whiteCard.add(toolbar, BorderLayout.NORTH);

    // --- B. GRILLA DE PLATOS (Tarjetas Naranjas) ---
    gridPanel = new JPanel(new GridLayout(0, 4, 25, 25)); // 4 Columnas, espacio de 25px
    gridPanel.setBackground(color.WHITE);

    // Añadimos tarjetas de ejemplo
    addDishCard("Pasta con Vegetales");
    addDishCard("Pasta con Vegetales");
    addDishCard("Pasta con Vegetales");
    addDishCard("Pasta con Vegetales");
    addDishCard("Pasta con Vegetales");

    // ScrollPane limpio
    JScrollPane scroll = new JScrollPane(gridPanel);
    scroll.setBorder(null);
    scroll.getViewport().setBackground(color.WHITE);
    scroll.getVerticalScrollBar().setUnitIncrement(16); // Scroll más suave

    whiteCard.add(scroll, BorderLayout.CENTER);

    mainPanel.add(whiteCard, BorderLayout.CENTER);

    // Configuración Final Frame
    this.setContentPane(mainPanel);
    this.pack();
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        g2.setColor(color.ORANGE);
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
    ImageIcon pastaImg = loadScaledImage("assets/images/Ingredients/pasta.jpg", 90, 65);
    if (pastaImg != null) {
      imgLabel.setIcon(pastaImg);
    } else {
      // Fallback si no hay imagen
      imgLabel.setText("Sin Foto");
      imgLabel.setForeground(color.WHITE);
    }

    // 3. Nombre del Plato (Abajo)
    JLabel nameLabel = new JLabel(name);
    nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    nameLabel.setForeground(color.WHITE);
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
    badge.setBackground(color.WHITE);
    badge.setBorder(BorderFactory.createLineBorder(color.LIGHT_GRAY, 1));

    JLabel iconLbl = new JLabel();
    ImageIcon icon = loadScaledImage("assets/images/admin_profile.png", 35, 35);
    if (icon != null)
      iconLbl.setIcon(icon);
    else
      iconLbl.setText("●");

    JLabel nameLbl = new JLabel(text);
    nameLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
    nameLbl.setForeground(color.OXFORD_BLUE);

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
    private Colors color = new Colors();
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
      Graphics2D g2 = (Graphics2D) g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      // Color Azul UCV o Blanco si quieres contraste
      g2.setColor(color.OXFORD_BLUE);

      // Trasladar al centro y rotar 45 grados
      int cx = x + 8;
      int cy = y + 8;
      AffineTransform old = g2.getTransform();
      g2.rotate(Math.toRadians(45), cx, cy);

      // Dibujar cuerpo (Rectángulo)
      g2.fillRect(cx - 3, cy - 6, 6, 10);

      // Dibujar punta (Triángulo)
      int[] px = { cx - 3, cx + 3, cx };
      int[] py = { cy + 4, cy + 4, cy + 8 };
      g2.fillPolygon(px, py, 3);

      // Dibujar goma (Rectángulo superior)
      g2.setColor(color.LIGHT_BLUE); // Azul más claro para la goma
      g2.fillRect(cx - 3, cy - 9, 6, 2);

      g2.setTransform(old);
      g2.dispose();
    }

    @Override
    public int getIconWidth() {
      return 16;
    }

    @Override
    public int getIconHeight() {
      return 16;
    }
  }

  // 2. Icono Papelera (Rojo)
  private static class TrashIcon implements Icon {
    private Colors color = new Colors();
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
      Graphics2D g2 = (Graphics2D) g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setColor(color.RED);

      // Tapa
      g2.fillRoundRect(x, y, 14, 3, 2, 2);
      g2.fillRect(x + 4, y - 2, 6, 2); // Asa

      // Cuerpo
      g2.fillRoundRect(x + 2, y + 4, 10, 12, 3, 3);

      // Líneas
      g2.setColor(color.WHITE);
      g2.fillRect(x + 4, y + 6, 1, 8);
      g2.fillRect(x + 9, y + 6, 1, 8); // Ajustado para centrar mejor

      g2.dispose();
    }

    @Override
    public int getIconWidth() {
      return 16;
    }

    @Override
    public int getIconHeight() {
      return 18;
    }
  }

  // 3. Icono Lupa (Gris)
  private static class SearchIcon implements Icon {
    private Colors color = new Colors();
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
      Graphics2D g2 = (Graphics2D) g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setColor(color.BLACK);
      g2.setStroke(new BasicStroke(2f));

      int size = 10;
      g2.drawOval(x, y, size, size); // Círculo
      g2.drawLine(x + size - 2, y + size - 2, x + size + 3, y + size + 3); // Mango

      g2.dispose();
    }

    @Override
    public int getIconWidth() {
      return 16;
    }

    @Override
    public int getIconHeight() {
      return 16;
    }

  }

  // métodos privados
  private void getStyleBtn(RoundedButton btn, Color btnColor) {
    Colors color = new Colors();
    btn.setBackground(btnColor);
    btn.setForeground(color.WHITE);
    btn.setPreferredSize(new Dimension(130, 38));
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btn.setFont(new Font("SansSerif", Font.BOLD, 12));
  }

    public void createDishListener(ActionListener listener) { btnCreateDish.addActionListener(listener);}
    public void menuListener(ActionListener listener) { btnMenu.addActionListener(listener);}
    public void inventoryListener(ActionListener listener) { btnIngredients.addActionListener(listener);}

    public JPanel getComponent_gridPanel() { return gridPanel; }

}