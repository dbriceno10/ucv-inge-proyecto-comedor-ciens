package View.Wallet;

import javax.swing.*;
import java.awt.*;

public class WalletView extends JDialog {

    // --- COMPONENTES PÚBLICOS (Para que el Controlador los use) ---
    public JLabel lblBalance;
    public JButton btnPay, btnTopUp, btnHistory;
    public JButton btnSave, btnCancel;
    public JPanel transactionListPanel; 

    public WalletView(JFrame parent) {
        // Configuración de la ventana MODAL
        super(parent, "Mi Billetera", true);
        setTitle("Billetera Digital");
        setSize(500, 650);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(parent);
        getContentPane().setBackground(new Color(245, 245, 245)); 
        
        // --- AQUÍ ESTABA EL ERROR ---
        // this.setVisible(true);  <-- ¡NO LO PONGAS AQUÍ! Bloquea la construcción.
        
        setLayout(new BorderLayout(15, 15));

        // --- HEADER: Balance ---
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        headerPanel.setBackground(new Color(245, 245, 245));
        
        JPanel balanceContainer = new JPanel(new GridLayout(2, 1));
        balanceContainer.setBackground(new Color(210, 230, 235)); 
        balanceContainer.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel balanceTitleLabel = new JLabel("Balance Total", SwingConstants.RIGHT);
        balanceTitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        lblBalance = new JLabel("$ 0.00"); 
        lblBalance.setFont(new Font("Arial", Font.BOLD, 18));
        lblBalance.setHorizontalAlignment(SwingConstants.RIGHT);
        
        balanceContainer.add(balanceTitleLabel);
        balanceContainer.add(lblBalance);
        headerPanel.add(balanceContainer);
        add(headerPanel, BorderLayout.NORTH);

        // --- CENTER: Navegación e Historial ---
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(Color.WHITE);
        mainContentPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        JPanel navigationMenu = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        navigationMenu.setBackground(Color.WHITE);
        
        btnPay = new JButton("Pagos");
        btnTopUp = new JButton("Recarga");
        btnHistory = new JButton("Historial");
        
        Dimension navBtnSize = new Dimension(110, 30);
        btnPay.setPreferredSize(navBtnSize);
        btnTopUp.setPreferredSize(navBtnSize);
        btnHistory.setPreferredSize(navBtnSize);

        navigationMenu.add(btnPay);
        navigationMenu.add(btnTopUp);
        navigationMenu.add(btnHistory);
        
        mainContentPanel.add(navigationMenu, BorderLayout.NORTH);

        transactionListPanel = new JPanel();
        transactionListPanel.setLayout(new BoxLayout(transactionListPanel, BoxLayout.Y_AXIS));
        transactionListPanel.setBackground(Color.WHITE);

        transactionListPanel.add(createTransactionRow("07/02/2026", "$100.00"));
        transactionListPanel.add(createTransactionRow("06/02/2026", "$250.00"));
        transactionListPanel.add(createTransactionRow("05/02/2026", "$50.00"));

        JScrollPane scrollPane = new JScrollPane(transactionListPanel);
        scrollPane.setBorder(null);
        mainContentPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainContentPanel, BorderLayout.CENTER);

        // --- FOOTER: Acciones ---
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        footerPanel.setBackground(new Color(245, 245, 245));

        btnSave = new JButton("Guardar");
        btnSave.setPreferredSize(new Dimension(120, 40));
        btnSave.setBackground(new Color(0, 102, 204));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);

        btnCancel = new JButton("Cancelar");
        btnCancel.setPreferredSize(new Dimension(120, 40));
        btnCancel.setBackground(new Color(255, 204, 153)); 
        btnCancel.setFocusPainted(false);

        footerPanel.add(btnSave);
        footerPanel.add(btnCancel);
        add(footerPanel, BorderLayout.SOUTH);

        // --- CORRECCIÓN FINAL ---
        // Si vas a probar SOLO la vista, descomenta la línea de abajo.
        // PERO: Si usas el WalletController, ¡COMENTA O BORRA ESTA LÍNEA!
        // El controlador es quien debe decidir cuándo mostrar la ventana.
        
        this.setVisible(true); 
    }

    public JPanel createTransactionRow(String dateText, String amountText) {
        JPanel rowPanel = new JPanel(new BorderLayout());
        rowPanel.setBackground(Color.WHITE);
        rowPanel.setMaximumSize(new Dimension(600, 50));
        rowPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 240)));

        JLabel dateLabel = new JLabel(dateText);
        dateLabel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        dateLabel.setForeground(Color.DARK_GRAY);
        
        JLabel amountLabel = new JLabel(amountText);
        amountLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 25));
        amountLabel.setFont(new Font("Arial", Font.BOLD, 14));

        rowPanel.add(dateLabel, BorderLayout.WEST);
        rowPanel.add(amountLabel, BorderLayout.EAST);
        return rowPanel;
    }
}