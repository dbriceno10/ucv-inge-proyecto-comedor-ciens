package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class MenuEditorView extends JFrame {
    private JPanel listPanel;
    public RoundedButton btnGuardar, btnCancelar, btnAddPlato;
    public RoundedTextField txtFecha;

    public MenuEditorView() {
        setTitle("SGCU - Crear/Editar Menú");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Colores extraídos de tus archivos
        Color ucvBlue = new Color(30, 80, 150);
        Color ucvOrange = new Color(255, 180, 120);

        // 1. Panel de fondo (Gris claro)
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(new Color(242, 244, 247));

        // 2. Tarjeta Blanca (Copiando el estilo de RegFormView)
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(30, 50, 30, 50));
        card.setPreferredSize(new Dimension(800, 600));

        // --- SECCIÓN FECHA ---
        txtFecha = new RoundedTextField();
        txtFecha.setText("08/12/2025");
        txtFecha.setPreferredSize(new Dimension(150, 30));
        
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        datePanel.setBackground(Color.WHITE);
        datePanel.add(new JLabel("FECHA:"));
        datePanel.add(txtFecha);

        // --- LISTA DE PLATOS (Scrollable) ---
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);
        
        // Simulación inicial (Como en el Figma)
        addPlatoRow("Pasta con Vegetales", ucvOrange);
        addPlatoRow("Pasta con Vegetales", ucvOrange);

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);

        // --- BOTONES DE ACCIÓN ---
        btnAddPlato = new RoundedButton("Añadir Plato");
        btnGuardar = new RoundedButton("Guardar");
        btnCancelar = new RoundedButton("Cancelar");

        // Estilos
        btnGuardar.setBackground(ucvBlue);
        btnGuardar.setForeground(Color.WHITE);
        btnCancelar.setBackground(new Color(255, 210, 100)); // Amarillo Figma

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(Color.WHITE);
        footer.setBorder(new EmptyBorder(20, 0, 0, 0));
        footer.add(btnAddPlato, BorderLayout.WEST);
        
        JPanel rightButtons = new JPanel(new FlowLayout());
        rightButtons.setBackground(Color.WHITE);
        rightButtons.add(btnGuardar);
        rightButtons.add(btnCancelar);
        footer.add(rightButtons, BorderLayout.EAST);

        // Ensamblaje
        card.add(datePanel);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(scroll);
        card.add(footer);

        background.add(card);
        this.add(background);

        // MODIFICACIONES FINALES PARA EVITAR PANTALLA EN BLANCO:
        this.pack(); // Fuerza a Swing a calcular tamaños de componentes
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla completa como en RegFormView
        this.setLocationRelativeTo(null); // Centra la ventana
        this.setVisible(true); // Se hace visible al final de todo el proceso de dibujado
    }

    private void addPlatoRow(String nombre, Color bg) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        row.setBackground(Color.WHITE);

        // La mini-card del plato
        RoundedButton plate = new RoundedButton(nombre);
        plate.setBackground(bg);
        plate.setPreferredSize(new Dimension(160, 60));

        // Cantidad (RoundedTextField pequeño)
        RoundedTextField qty = new RoundedTextField();
        qty.setText("0");
        qty.setPreferredSize(new Dimension(50, 30));

        row.add(plate);
        row.add(new RoundedButton("-"));
        row.add(qty);
        row.add(new RoundedButton("+"));
        
        listPanel.add(row);
        listPanel.revalidate(); // Asegura que la lista se refresque al añadir elementos
    }
}