package View.Menu;

import View.CustomComponents.*;
import Enums.Days;   
import Enums.MenuTypes;      

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.awt.event.*;

public class MenuEditorView extends JFrame {

    private Colors color = new Colors();
    private RoundedButton btnSave, btnCancel, btnAddDish;
    private RoundedTextField txtDate, txtQty;
    private RoundedComboBox<String> cmbDay;
    private RoundedComboBox<String> cmbType;
    private JPanel listPanel;

    public MenuEditorView() {
        setTitle("SGCU - Crear/Editar Menú");
        
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

        // --- A. CABECERA DE LA TARJETA ---
        JPanel cardHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        cardHeader.setBackground(color.WHITE);
        
        // 1. Selector de Día
        String[] days = {Days.MONDAY, Days.TUESDAY, Days.WEDNESDAY, Days.THURSDAY, Days.FRIDAY, Days.SATURDAY, Days.SUNDAY};
        cmbDay = new RoundedComboBox<>(days);
        cmbDay.setPreferredSize(new Dimension(130, 35));

        // 2. Selector de Turno
        String[] types = {MenuTypes.BREACKFAST, MenuTypes.LUNCH, MenuTypes.DINNER};
        cmbType = new RoundedComboBox<>(types);
        cmbType.setPreferredSize(new Dimension(130, 35));
        
        // 3. Campo de Fecha
        txtDate = new RoundedTextField();
        txtDate.setText("08/12/2025");
        txtDate.setPreferredSize(new Dimension(110, 35));

        // 4. Campo de Cantidad de bandejas (Se mantiene por el diseño, aunque la cantidad real ahora va por plato)
        txtQty = new RoundedTextField();
        txtQty.setText("10"); 
        txtQty.setPreferredSize(new Dimension(70, 35));
        
        // Añadimos todo al panel usando un método auxiliar para ponerles títulos
        cardHeader.add(createLabeledField("DÍA", cmbDay));
        cardHeader.add(createLabeledField("TURNO", cmbType));
        cardHeader.add(createLabeledField("FECHA", txtDate));
        cardHeader.add(createLabeledField("BANDEJAS", txtQty));
        
        whiteCard.add(cardHeader, BorderLayout.NORTH);

        // --- B. LISTA DE PLATOS (Centro) ---
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(color.WHITE);

        // Espacio inicial y platos de ejemplo
        listPanel.add(Box.createVerticalStrut(20));

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
        this.setContentPane(mainPanel); 
        
        // Finalizar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1024, 768));
        setLocationRelativeTo(null);
    }

    // --- MÉTODOS AUXILIARES ---

    private JPanel createProfileBadge(String text) {
        JPanel badge = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        badge.setBackground(color.WHITE);
        badge.setBorder(BorderFactory.createLineBorder(color.LIGHT_GRAY, 1));
        
        // Cargar foto de perfil
        JLabel iconLbl = new JLabel();
        ImageIcon icon = loadScaledImage("assets/images/admin_profile.png", 40, 40);
        
        if (icon != null) {
            iconLbl.setIcon(icon);
        } else {
            iconLbl.setText("●"); 
            iconLbl.setForeground(color.DARK_GRAY);
        }
        
        JLabel nameLbl = new JLabel(text);
        nameLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        nameLbl.setForeground(color.OXFORD_BLUE);

        badge.add(iconLbl);
        badge.add(nameLbl);
        return badge;
    }

    public void addPlatoRow(String nombrePlato, Integer foodId) {
        JPanel row = new JPanel(new GridBagLayout());
        row.setBackground(color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        row.setName("row_" + foodId); // <--- Etiquetamos la fila con el ID
        
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
        
        ImageIcon dishImg = loadScaledImage("assets/images/Ingredients/pasta.jpg", 80, 60);
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
        
        RoundedTextField txtQtyLocal = new RoundedTextField(); // <-- Renombrado para no confundir
        txtQtyLocal.setText("1"); // Empezamos con 1 bandeja mínimo
        txtQtyLocal.setPreferredSize(new Dimension(160, 35));
        txtQtyLocal.setName("qty_" + foodId); // <--- Etiquetamos el texto con el ID
        
        inputStack.add(lblQty, BorderLayout.NORTH);
        inputStack.add(txtQtyLocal, BorderLayout.CENTER);

        RoundedButton btnPlus = createCircularButton("+");

        // --- ACCIONES DE LOS BOTONES + Y - ---
        btnPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int currentQty = Integer.parseInt(txtQtyLocal.getText());
                    txtQtyLocal.setText(String.valueOf(currentQty + 1));
                } catch (NumberFormatException ex) {
                    txtQtyLocal.setText("1");
                }
            }
        });

        btnMinus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int currentQty = Integer.parseInt(txtQtyLocal.getText());
                    if (currentQty > 1) { // No permitimos que baje a 0
                        txtQtyLocal.setText(String.valueOf(currentQty - 1));
                    }
                } catch (NumberFormatException ex) {
                    txtQtyLocal.setText("1");
                }
            }
        });
        // -------------------------------------

        ctrlPanel.add(btnMinus);
        ctrlPanel.add(inputStack);
        ctrlPanel.add(btnPlus);
        
        row.add(ctrlPanel, gbc);
        
        // 3. PAPELERA ROJA 
        gbc.gridx = 2; gbc.weightx = 0.2; gbc.anchor = GridBagConstraints.EAST;
        
        JButton btnDel = new JButton();
        ImageIcon trashImg = loadScaledImage("assets/images/Icons/boton-eliminar.png", 24, 28);
        
        if(trashImg != null) {
            btnDel.setIcon(trashImg);
        } else {
            btnDel.setText("X");
            btnDel.setForeground(color.RED);
        }
        
        btnDel.setBorderPainted(false);
        btnDel.setContentAreaFilled(false);
        btnDel.setCursor(new Cursor(Cursor.HAND_CURSOR));
   
        btnDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listPanel.remove(row);
                listPanel.revalidate();
                listPanel.repaint();
            }
        });
        
        row.add(btnDel, gbc);
        listPanel.add(row);
        listPanel.add(Box.createVerticalStrut(10));
        listPanel.revalidate(); 
        listPanel.repaint();    
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

    // Getters para los campos
    public String getTxtDate() { return txtDate.getText(); }
    public String getCmbDay() { return String.valueOf(cmbDay.getSelectedItem()); }
    public String getCmbType() { return String.valueOf(cmbType.getSelectedItem()); }
    public String getTxtQty() { return txtQty.getText(); }

    // Método auxiliar para crear campos con título
    private JPanel createLabeledField(String title, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(color.WHITE);
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 10));
        lbl.setForeground(color.DARK_GRAY);
        panel.add(lbl, BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    // --- MÉTODOS PARA QUE EL CONTROLADOR LEA LOS PLATOS (Imprescindibles) ---
    public java.util.ArrayList<Model.DTO.Menu.MenuQtyDto> getPlatosSeleccionados() {
        java.util.ArrayList<Model.DTO.Menu.MenuQtyDto> lista = new java.util.ArrayList<>();
        Component[] filas = listPanel.getComponents();
        for (Component fila : filas) {
            if (fila instanceof JPanel && fila.getName() != null && fila.getName().startsWith("row_")) {
                int foodId = Integer.parseInt(fila.getName().replace("row_", ""));
                JTextField txt = findTextField((JPanel) fila, "qty_" + foodId);
                if (txt != null) {
                    int qty = Integer.parseInt(txt.getText());
                    lista.add(new Model.DTO.Menu.MenuQtyDto(foodId, qty, qty));
                }
            }
        }
        return lista;
    }

    private JTextField findTextField(Container container, String name) {
        for (Component c : container.getComponents()) {
            if (c instanceof JTextField && name.equals(c.getName())) return (JTextField) c;
            if (c instanceof Container) {
                JTextField f = findTextField((Container) c, name);
                if (f != null) return f;
            }
        }
        return null;
    }

    // Listeners
    public void saveListener(ActionListener listener) { btnSave.addActionListener(listener);}
    public void cancelListener(ActionListener listener) { btnCancel.addActionListener(listener);}
    public void addDishListener(ActionListener listener) { btnAddDish.addActionListener(listener);} 
}