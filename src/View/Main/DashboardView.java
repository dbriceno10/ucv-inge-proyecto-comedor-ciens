package View.Main;

import Enums.MenuOptions;
import View.CustomComponents.RoundedButton;
import View.CustomComponents.RoundedComboBox;
import View.Wallet.WalletView; // Ya no se usa aquí, pero no estorba
import Utils.ImageUtils;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class DashboardView extends JFrame {
    private JButton btnProfile;
    public JButton btnWallet; // <--- AHORA ES PUBLIC
    private JComboBox<String> selectMenu_type;
    private JLabel lblSectionTitle;
    private JPanel cardsPanel;

    public DashboardView() {
        setTitle("UCV - SGCU");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // HEADER
        JPanel headerPanel = new JPanel(new BorderLayout()); 
        headerPanel.setBackground(new Color(245, 245, 245));

        String profileText = "<html><b>User</b><br><i style='font-size:10px'>User_type</i></html>";
        btnProfile = new RoundedButton(profileText);
        btnProfile.setBackground(new Color(213, 213, 213));
        btnProfile.setFont(new Font("SansSerif", Font.PLAIN, 14));

        ImageIcon defaultUser = new ImageIcon("assets/images/defaultUser.png");
        Image imgUser = defaultUser.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        btnProfile.setIcon(new ImageIcon(imgUser));
        btnProfile.setHorizontalAlignment(SwingConstants.LEFT);
        btnProfile.setPreferredSize(new Dimension(180, 70));
        
        String[] menu_options = {MenuOptions.DAILY, MenuOptions.WEEKLY};
        selectMenu_type = new RoundedComboBox<>(menu_options);
        selectMenu_type.setPreferredSize(new Dimension(150, 40));
        
        selectMenu_type.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateContent((String) e.getItem());
            }
        });

        JPanel rightHeader = new JPanel();
        rightHeader.setLayout(new BoxLayout(rightHeader, BoxLayout.Y_AXIS));
        rightHeader.setBackground(new Color(245,245,245));

        JLabel lblSelect = new JLabel("Seleccionar menú");
        lblSelect.setAlignmentX(Component.RIGHT_ALIGNMENT);
        lblSelect.setFont(new Font("SansSerif", Font.PLAIN, 12));
        selectMenu_type.setAlignmentX(Component.RIGHT_ALIGNMENT);

        rightHeader.add(lblSelect);
        rightHeader.add(Box.createRigidArea(new Dimension(0, 5))); 
        rightHeader.add(selectMenu_type);

        headerPanel.add(btnProfile, BorderLayout.WEST);
        headerPanel.add(rightHeader, BorderLayout.EAST);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // BODY
        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setBackground(new Color(245,245,245));
        bodyPanel.setBorder(new EmptyBorder(40, 50, 50, 50)); 

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(new Color(245, 245, 245));

        lblSectionTitle = new JLabel("Menú del día");
        lblSectionTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblSectionTitle.setForeground(new Color(0, 33, 71));
        lblSectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        titlePanel.add(lblSectionTitle);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 30)));

        cardsPanel = new JPanel(new GridLayout(0, 4, 50, 30));
        cardsPanel.setBackground(new Color(245, 245, 245));

        JPanel cardsWrapper = new JPanel(new BorderLayout());
        cardsWrapper.setBackground(new Color(245, 245, 245));
        cardsWrapper.add(cardsPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(cardsWrapper);
        scrollPane.setBorder(null); 
        scrollPane.getViewport().setBackground(new Color(245, 245, 245));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); 

        bodyPanel.add(titlePanel, BorderLayout.NORTH);
        bodyPanel.add(Box.createRigidArea(new Dimension(20, 0))); 
        bodyPanel.add(scrollPane, BorderLayout.CENTER);

        // FOOTER
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(new Color(245, 245, 245));
        footerPanel.setBorder(new EmptyBorder(0, 0, 15, 15));

        btnWallet = new RoundedButton();
        btnWallet.setPreferredSize(new Dimension(80, 80));
        btnWallet.setBackground(new Color(0, 51, 102));
        btnWallet.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnWallet.setIcon(Utils.ImageUtils.getRoundedIcon("assets/images/wallet.png", 45, 45, 0));
        
        // NOTA: Se eliminó el listener aquí para ponerlo en el Controller
        
        footerPanel.add(btnWallet);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        mainPanel.setBorder(new EmptyBorder(20, 40, 10, 40));
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        this.add(mainPanel);
        
        updateContent(MenuOptions.DAILY);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.setVisible(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void updateContent(String viewType) {
        cardsPanel.removeAll(); 
        if (viewType.equals(MenuOptions.DAILY)) {
            lblSectionTitle.setText("Menú del día");
            cardsPanel.add(new MenuCard("Pabellón Criollo", ""));
            cardsPanel.add(new MenuCard("Opción Veggie", ""));
            cardsPanel.add(new MenuCard("Sopa del Día", ""));
            cardsPanel.add(new MenuCard("Pasta Boloñesa", ""));
            cardsPanel.add(new MenuCard("Cachapa", ""));
            cardsPanel.add(new MenuCard("Arepa", ""));
        } else {
            lblSectionTitle.setText("Menú de la Semana");
            cardsPanel.add(new MenuCard("Lunes: Pasta", "img/lunes.jpg"));
            cardsPanel.add(new MenuCard("Martes: Pollo", "img/martes.jpg"));
            cardsPanel.add(new MenuCard("Miércoles: Lentejas", "img/miercoles.jpg"));
            cardsPanel.add(new MenuCard("Jueves: Pescado", "img/jueves.jpg"));
        }
        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    private class MenuCard extends JPanel {
        private boolean isSelected = false;
        private Color defaultColor = new Color(255, 160, 80); 
        private Color selectedColor = new Color(100, 100, 100); 
        
        public MenuCard(String title, String imgPath) {
            setLayout(new BorderLayout());
            setBackground(defaultColor); 
            setCursor(new Cursor(Cursor.HAND_CURSOR)); 
            setBorder(new EmptyBorder(15, 15, 15, 15)); 

            JLabel lblImg = new JLabel();
            lblImg.setHorizontalAlignment(SwingConstants.CENTER);
            ImageIcon checkIcon = new ImageIcon(imgPath);
            if (checkIcon.getIconWidth() == -1) { checkIcon = new ImageIcon("assets/images/Menu/default.png"); } 
            lblImg.setIcon(Utils.ImageUtils.getRoundedIcon(imgPath, 180, 120, 30));
            
            JLabel lblTitle = new JLabel(title);
            lblTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
            lblTitle.setForeground(Color.WHITE);
            lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
            lblTitle.setBorder(new EmptyBorder(10, 0, 0, 0)); 

            add(lblImg, BorderLayout.CENTER);
            add(lblTitle, BorderLayout.SOUTH);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) { toggleSelection(); }
            });
        }
    
        private void toggleSelection() {
            isSelected = !isSelected;
            setBackground(isSelected ? selectedColor : defaultColor);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); 
            g2.dispose();
            super.paintChildren(g); 
        }
    }
}