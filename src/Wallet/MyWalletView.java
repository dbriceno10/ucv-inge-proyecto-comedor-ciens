package Wallet;

import javax.swing.*;
import java.awt.*;

public class MyWalletView extends JFrame {

    public MyWalletView() {
        // Configuración de la ventana
        setTitle("Billetera Digital");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 245, 245)); 
        setLayout(new BorderLayout(15, 15));

        // --- HEADER: Balance ---
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        headerPanel.setBackground(new Color(245, 245, 245));
        
        JPanel balanceContainer = new JPanel(new GridLayout(2, 1));
        balanceContainer.setBackground(new Color(210, 230, 235)); 
        balanceContainer.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel balanceTitleLabel = new JLabel("Balance Total", SwingConstants.RIGHT);
        balanceTitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel balanceValueLabel = new JLabel("$7,783.00");
        balanceValueLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        balanceContainer.add(balanceTitleLabel);
        balanceContainer.add(balanceValueLabel);
        headerPanel.add(balanceContainer);
        add(headerPanel, BorderLayout.NORTH);

        // --- CENTER: Navegación e Historial ---
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(Color.WHITE);
        mainContentPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        JPanel navigationMenu = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        navigationMenu.setBackground(Color.WHITE);
        
        JButton payButton = new JButton("Pagos");
        JButton topUpButton = new JButton("Recarga");
        JButton historyButton = new JButton("Historial");
        
        Dimension navBtnSize = new Dimension(110, 30);
        payButton.setPreferredSize(navBtnSize);
        topUpButton.setPreferredSize(navBtnSize);
        historyButton.setPreferredSize(navBtnSize);

        navigationMenu.add(payButton);
        navigationMenu.add(topUpButton);
        navigationMenu.add(historyButton);
        
        mainContentPanel.add(navigationMenu, BorderLayout.NORTH);

        JPanel transactionListPanel = new JPanel();
        transactionListPanel.setLayout(new BoxLayout(transactionListPanel, BoxLayout.Y_AXIS));
        transactionListPanel.setBackground(Color.WHITE);

        transactionListPanel.add(createTransactionRow("07/02/2026", "$100.00"));
        transactionListPanel.add(createTransactionRow("06/02/2026", "$250.00"));
        transactionListPanel.add(createTransactionRow("05/02/2026", "$50.00"));
        transactionListPanel.add(createTransactionRow("04/02/2026", "$1,200.00"));

        JScrollPane scrollPane = new JScrollPane(transactionListPanel);
        scrollPane.setBorder(null);
        mainContentPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainContentPanel, BorderLayout.CENTER);

        // --- FOOTER: Acciones ---
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        footerPanel.setBackground(new Color(245, 245, 245));

        JButton saveButton = new JButton("Guardar");
        saveButton.setPreferredSize(new Dimension(120, 40));
        saveButton.setBackground(new Color(0, 102, 204));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setPreferredSize(new Dimension(120, 40));
        cancelButton.setBackground(new Color(255, 204, 153)); 
        cancelButton.setFocusPainted(false);

        footerPanel.add(saveButton);
        footerPanel.add(cancelButton);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createTransactionRow(String dateText, String amountText) {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Se inicia con el nombre de clase correcto
            new MyWalletView().setVisible(true); 
        });
    }
}