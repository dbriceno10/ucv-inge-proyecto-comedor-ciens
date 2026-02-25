package View.Wallet;

import View.CustomComponents.Colors;
import View.CustomComponents.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WalletView extends JDialog { // a modal window is used as an intermediary to display the wallet.
    private JLabel lblBalance;
    private JButton btnTopUp, btnClose;
    private JPanel transactionListPanel;
    private Colors color = new Colors();

    public WalletView(JFrame parent) {
        super(parent, "Mi Billetera", true);
        setSize(400, 550);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); 
        setUndecorated(true); // quita la barra superior de Windows para que se vea más moderno.
       
        // lógica para la posición del modal
        int paddingRight = 50;  
        int paddingBottom = 35;
        int x = parent.getX() + parent.getWidth() - this.getWidth() - paddingRight;
        int y = parent.getY() + parent.getHeight() - this.getHeight() - paddingBottom;
        setLocation(x, y);

        getContentPane().setBackground(color.BACKGROUND); 
        setLayout(new BorderLayout(15, 15));

        // header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(color.BACKGROUND);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel balanceTextPanel = new JPanel();
        balanceTextPanel.setLayout(new BoxLayout(balanceTextPanel, BoxLayout.Y_AXIS));
        balanceTextPanel.setBackground(color.BACKGROUND);
        
        JLabel balanceTitleLabel = new JLabel("Balance Total");
        balanceTitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        balanceTitleLabel.setForeground(Color.GRAY);
        
        lblBalance = new JLabel("$ 0.00"); 
        lblBalance.setFont(new Font("SansSerif", Font.PLAIN, 28));

        balanceTextPanel.add(balanceTitleLabel);
        balanceTextPanel.add(lblBalance);
   
        // close button (top right)
        btnClose = new JButton("✕");
        btnClose.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnClose.setForeground(color.DARK_GRAY);
        btnClose.setFocusPainted(false);
        btnClose.setBorderPainted(false);
        btnClose.setContentAreaFilled(false); 
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.setActionCommand("CLOSE_WALLET");

        headerPanel.add(balanceTextPanel, BorderLayout.WEST);
        headerPanel.add(btnClose, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // center
        transactionListPanel = new JPanel();
        transactionListPanel.setLayout(new BoxLayout(transactionListPanel, BoxLayout.Y_AXIS));
        transactionListPanel.setBackground(color.WHITE);

        JScrollPane scrollPane = new JScrollPane(transactionListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scrollPane, BorderLayout.CENTER);

        // footer: actions
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        footerPanel.setBackground(color.BACKGROUND);
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, color.LIGHT_GRAY));

        btnTopUp = new RoundedButton("Recarga");
        btnTopUp.setPreferredSize(new Dimension(150, 40));
        btnTopUp.setBackground(color.OXFORD_BLUE);
        btnTopUp.setForeground(color.WHITE);
        btnTopUp.setActionCommand("Recarga");

        footerPanel.add(btnTopUp);
        add(footerPanel, BorderLayout.SOUTH);
    }

    public JPanel createTransactionRow(String dateText, String amountText) {
        JPanel rowPanel = new JPanel(new BorderLayout());
        rowPanel.setBackground(color.WHITE);
        rowPanel.setMaximumSize(new Dimension(600, 50));
        rowPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, color.LIGHT_GRAY));

        JLabel dateLabel = new JLabel(dateText);
        dateLabel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        dateLabel.setForeground(color.BLACK);
        
        JLabel amountLabel = new JLabel(amountText);
        amountLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 25));
        amountLabel.setFont(new Font("Arial", Font.BOLD, 14));

        rowPanel.add(dateLabel, BorderLayout.WEST);
        rowPanel.add(amountLabel, BorderLayout.EAST);
        return rowPanel;
    }

    public JLabel getComponentBlc() { return lblBalance; }
    public JPanel getTransactionListPanel() { return transactionListPanel; }

    public void topUpListener(ActionListener listener) { btnTopUp.addActionListener(listener);}
    public void cancelListener(ActionListener listener) { btnClose.addActionListener(listener);}

}