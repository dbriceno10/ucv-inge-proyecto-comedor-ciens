package View.Menu;

import View.CustomComponents.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Enums.MenuTypes;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReportModalView extends JDialog {
    private RoundedButton btnClose;
    private RoundedComboBox<String> cmbShift;
    private JPanel dataPanel;
    private JLabel lblTotal;
    private Colors color = new Colors();

    public ReportModalView(JFrame parent) {
        super(parent, "SGCU - Estadísticas de Comensales", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // 1. Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(color.BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // 2. Cabecera (Título y Selector de Turno)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(color.BACKGROUND);

        JLabel lblTitle = new JLabel("Estadísticas por Turno");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setForeground(color.OXFORD_BLUE);
        headerPanel.add(lblTitle, BorderLayout.WEST);

        // Selector de turno (Desayuno, Almuerzo, Cena)
        String[] shifts = {MenuTypes.BREACKFAST, MenuTypes.LUNCH, MenuTypes.DINNER};
        cmbShift = new RoundedComboBox<>(shifts);
        cmbShift.setPreferredSize(new Dimension(150, 35));
        cmbShift.setActionCommand("SHIFT_CHANGED");
        headerPanel.add(cmbShift, BorderLayout.EAST);

        // 3. Tarjeta blanca central (donde irán los datos)
        JPanel whiteCard = new JPanel(new BorderLayout());
        whiteCard.setBackground(color.WHITE);
        whiteCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.LIGHT_GRAY, 1),
            new EmptyBorder(20, 30, 20, 30)
        ));

        // Panel donde se inyectarán las filas de datos
        dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.setBackground(color.WHITE);
        whiteCard.add(dataPanel, BorderLayout.CENTER);

        // 4. Pie de la tarjeta (Total)
        JPanel footerDataPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerDataPanel.setBackground(color.WHITE);
        footerDataPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, color.LIGHT_GRAY));

        lblTotal = new JLabel("Total General: 0");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTotal.setForeground(color.OXFORD_BLUE);
        footerDataPanel.add(lblTotal);
        whiteCard.add(footerDataPanel, BorderLayout.SOUTH);

        // 5. Botón inferior
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(color.BACKGROUND);

        btnClose = new RoundedButton("Cerrar Reporte");
        btnClose.setBackground(color.OXFORD_BLUE);
        btnClose.setForeground(color.WHITE);
        btnClose.setPreferredSize(new Dimension(200, 40));
        btnClose.setActionCommand("CLOSE_REPORT");
        footerPanel.add(btnClose);

        // Ensamblaje final
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(whiteCard, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Configuración de la ventana
        setUndecorated(true);
        setContentPane(mainPanel);
        setSize(550, 450);
        setLocationRelativeTo(parent);
    }

    // recibe el ArrayList del servicio y lo pinta en la pantalla.
    public void updateStatistics(ArrayList<String> statsList) {
        dataPanel.removeAll();
        int totalSum = 0;

        for (String statLine : statsList) {

            JPanel row = new JPanel(new BorderLayout());
            row.setBackground(color.WHITE);
            row.setBorder(new EmptyBorder(10, 0, 10, 0));

            // Extraemos el nombre y el número asumiendo un formato "Nombre: Numero"
            String[] parts = statLine.split(":"); 
            String labelName = parts.length > 0 ? parts[0].trim() : statLine;
            String valueNumber = parts.length > 1 ? parts[1].trim() : "0";

            // Etiqueta del tipo de perfil
            JLabel lblName = new JLabel(labelName);
            lblName.setFont(new Font("SansSerif", Font.PLAIN, 16));
            lblName.setForeground(color.DARK_GRAY);

            // Etiqueta del número
            JLabel lblValue = new JLabel(valueNumber);
            lblValue.setFont(new Font("SansSerif", Font.BOLD, 16));
            lblValue.setForeground(color.BLACK);

            row.add(lblName, BorderLayout.WEST);
            row.add(lblValue, BorderLayout.EAST);
            
            dataPanel.add(row);

            try {
                totalSum += Integer.parseInt(valueNumber);
            } catch (NumberFormatException e) {
                // Ignoramos si no se pudo convertir a número
            }
        }

        lblTotal.setText("Total General: " + totalSum);

        dataPanel.revalidate();
        dataPanel.repaint();
    }

    public String getSelectedShift() {
        return (String) cmbShift.getSelectedItem();
    }

    public void shiftChangeListener(ActionListener listener) { cmbShift.addActionListener(listener); }

    public void closeListener(ActionListener listener) { btnClose.addActionListener(listener); }
}