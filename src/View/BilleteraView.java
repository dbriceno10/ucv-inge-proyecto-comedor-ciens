package View;

import javax.swing.*;
import java.awt.*;

public class BilleteraView extends JFrame {

    public BilleteraView() {
        // 1. Configuración de la ventana (Tamaño y Título)
        setTitle("Mi Billetera - UCV");
        setSize(400, 400); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Esto la centra en tu pantalla
        
        // 2. Fondo y Layout
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); 

        // 3. Título Superior (Azul)
        JLabel lblTitulo = new JLabel("Mi Monedero Digital", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(0, 51, 153)); 
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // 4. Saldo Central (Verde y Grande)
        // Usamos un JPanel para centrar el texto vertical y horizontalmente
        JPanel pnlCentral = new JPanel(new GridBagLayout());
        pnlCentral.setBackground(Color.WHITE);

        JLabel lblSaldoMonto = new JLabel("Bs. 500.00");
        lblSaldoMonto.setFont(new Font("SansSerif", Font.BOLD, 48));
        lblSaldoMonto.setForeground(new Color(0, 153, 76)); 
        
        pnlCentral.add(lblSaldoMonto);
        add(pnlCentral, BorderLayout.CENTER);

        // 5. Mensaje inferior (Gris)
        JLabel lblFooter = new JLabel("Saldo disponible", SwingConstants.CENTER);
        lblFooter.setFont(new Font("SansSerif", Font.ITALIC, 14));
        lblFooter.setForeground(Color.GRAY);
        lblFooter.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        add(lblFooter, BorderLayout.SOUTH);
    }

    // EL MÉTODO MAIN: Esto es lo que hace que se abra tu ventana y no el login
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BilleteraView vista = new BilleteraView();
            vista.setVisible(true); // Hace que la ventana aparezca
        });
    }
}