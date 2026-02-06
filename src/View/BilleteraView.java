package View;

import javax.swing.*;
import java.awt.*;

public class BilleteraView extends JPanel {
    private JLabel lblSaldoMonto;
    private JButton btnRecargar;

    public BilleteraView() {
        // Configuración básica del panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Título estilizado
        JLabel lblTitulo = new JLabel("Monedero Digital");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Espacio
        add(Box.createRigidArea(new Dimension(0, 20)));

        // Etiqueta de Saldo
        JLabel lblInfo = new JLabel("Saldo disponible:");
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblInfo.setForeground(Color.GRAY);
        lblInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(lblInfo);

        // Monto del Saldo (Grande y azul como la UCV)
        lblSaldoMonto = new JLabel("Bs. 0.00");
        lblSaldoMonto.setFont(new Font("Arial", Font.BOLD, 36));
        lblSaldoMonto.setForeground(new Color(0, 102, 204));
        lblSaldoMonto.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(lblSaldoMonto);

        // Espacio grande antes del botón
        add(Box.createRigidArea(new Dimension(0, 40)));

        
        btnRecargar = new JButton("Recargar Saldo");
        btnRecargar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRecargar.setBackground(new Color(0, 153, 76)); // Verde
        btnRecargar.setForeground(Color.WHITE);
        btnRecargar.setFocusPainted(false);
        btnRecargar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnRecargar);

        add(lblTitulo);
    }

    
    public void setSaldo(double monto) {
        lblSaldoMonto.setText(String.format("Bs. %.2f", monto));
    }
}