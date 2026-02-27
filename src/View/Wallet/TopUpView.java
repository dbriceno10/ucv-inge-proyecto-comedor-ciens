package View.Wallet;

import View.CustomComponents.*;
import Enums.Banks; 

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class TopUpView extends JDialog { 
    private RoundedButton btnSubmit, btnCancel;
    private RoundedTextField txtDate, txtAmount, txtReference;
    private RoundedComboBox<String> cmbBank;
    
    private Colors color = new Colors();

    public TopUpView(WalletView parent) {
        super(parent, "SGCU - Recargar Billetera", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(color.BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(20, 40, 20, 40)); 

        // HEADER
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(color.BACKGROUND);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        JLabel lblMainTitle = new JLabel("Recargar Billetera (Pago Móvil)");
        lblMainTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblMainTitle.setForeground(color.OXFORD_BLUE);
        headerPanel.add(lblMainTitle);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // TARJETA BLANCA
        JPanel whiteCard = new JPanel(new GridLayout(1, 2, 40, 0));
        whiteCard.setBackground(color.WHITE);
        whiteCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.LIGHT_GRAY, 1),
            new EmptyBorder(20, 40, 20, 40)
        ));

        // --- IZQUIERDA: Datos Receptores ---
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(color.WHITE);
        
        JLabel lblInfoTitle = new JLabel("Datos Receptores");
        lblInfoTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblInfoTitle.setForeground(color.OXFORD_BLUE);
        lblInfoTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(lblInfoTitle);
        leftPanel.add(Box.createVerticalStrut(20));

        leftPanel.add(createDataRow("BANCO RECEPTOR:", "Banco de Venezuela (0102)"));
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(createDataRow("TELÉFONO:", "0414-1234567"));
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(createDataRow("DOCUMENTO (RIF):", "J-12345678-9"));
        
        JTextArea txtHelp = new JTextArea("Realice la transferencia a los datos indicados y registre el pago en el formulario de la derecha.");
        txtHelp.setWrapStyleWord(true);
        txtHelp.setLineWrap(true);
        txtHelp.setEditable(false);
        txtHelp.setFocusable(false);
        txtHelp.setBackground(color.WHITE);
        txtHelp.setForeground(color.DARK_GRAY);
        txtHelp.setFont(new Font("SansSerif", Font.ITALIC, 12));
        txtHelp.setBorder(new EmptyBorder(30, 0, 0, 0));
        txtHelp.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(txtHelp);

        // --- DERECHA: Formulario ---
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(color.WHITE);

        JLabel lblFormTitle = new JLabel("Registrar Pago");
        lblFormTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblFormTitle.setForeground(color.OXFORD_BLUE);
        lblFormTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(lblFormTitle);
        rightPanel.add(Box.createVerticalStrut(20));

        rightPanel.add(createLabel("FECHA DE PAGO (DD/MM/YYYY)"));
        txtDate = new RoundedTextField();
        txtDate.setPreferredSize(new Dimension(300, 35));
        txtDate.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        rightPanel.add(txtDate);
        rightPanel.add(Box.createVerticalStrut(10));

        rightPanel.add(createLabel("MONTO (Bs.)"));
        txtAmount = new RoundedTextField();
        txtAmount.setPreferredSize(new Dimension(300, 35));
        txtAmount.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        rightPanel.add(txtAmount);
        rightPanel.add(Box.createVerticalStrut(10));

        rightPanel.add(createLabel("REFERENCIA"));
        txtReference = new RoundedTextField();
        txtReference.setPreferredSize(new Dimension(300, 35));
        txtReference.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        rightPanel.add(txtReference);
        rightPanel.add(Box.createVerticalStrut(10));

        rightPanel.add(createLabel("BANCO EMISOR"));
        cmbBank = new RoundedComboBox<>(Banks.VENEZUELAN_BANKS);
        cmbBank.setPreferredSize(new Dimension(300, 35));
        cmbBank.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        rightPanel.add(cmbBank);

        // UNIÓN de la tarjeta blanca
        whiteCard.add(leftPanel);
        whiteCard.add(rightPanel);
        mainPanel.add(whiteCard, BorderLayout.CENTER);

        // --- NUEVO: FOOTER CON BOTONES (Anclados abajo del todo) ---
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        footerPanel.setBackground(color.BACKGROUND);
        footerPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        btnSubmit = new RoundedButton("Mandar");
        btnSubmit.setBackground(color.OXFORD_BLUE);
        btnSubmit.setForeground(color.WHITE);
        btnSubmit.setPreferredSize(new Dimension(120, 40));

        btnCancel = new RoundedButton("Cancelar");
        btnCancel.setBackground(color.YELLOW);
        btnCancel.setForeground(color.BLACK);
        btnCancel.setPreferredSize(new Dimension(120, 40));

        footerPanel.add(btnSubmit);
        footerPanel.add(btnCancel);

        // Añadimos los botones a la parte baja (SOUTH) del panel principal
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        setUndecorated(true); // quita la barra superior de Windows para que se vea más moderno.
        this.setContentPane(mainPanel);
        this.setSize(900, 600); 
        this.setLocationRelativeTo(parent);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 10));
        lbl.setForeground(color.DARK_GRAY);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBorder(new EmptyBorder(0, 0, 5, 0));
        return lbl;
    }

    private JPanel createDataRow(String title, String value) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
        row.setBackground(color.WHITE);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 10));
        lblTitle.setForeground(color.DARK_GRAY);
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblValue.setForeground(color.BLACK);
        
        row.add(lblTitle);
        row.add(lblValue);
        return row;
    }

    public String getTxtDate() { return txtDate.getText(); }
    public String getTxtAmount() { return txtAmount.getText(); }
    public String getTxtReference() { return txtReference.getText(); }
    public String getCmbBank() { return (String) cmbBank.getSelectedItem(); }

    public JTextField getComponentDate() { return txtDate; }
    public JTextField getComponentAmount() { return txtAmount; }
    public JTextField getComponentReference() { return txtReference; }

    public void submitListener(ActionListener listener) { btnSubmit.addActionListener(listener); }
    public void cancelListener(ActionListener listener) { btnCancel.addActionListener(listener); }
}