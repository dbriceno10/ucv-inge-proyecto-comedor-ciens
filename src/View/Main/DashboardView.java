package View.Main;

import Enums.MenuOptions;
import View.CustomComponents.RoundedButton;
import View.CustomComponents.RoundedComboBox;
import Utils.ImageUtils;

// Importaciones para conectar Billetera y Usuario
import Controllers.WalletControllers.*;
import View.Wallet.WalletView;
import Context.User.UserSession;
import DTO.User.AuthUserDto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class DashboardView extends JFrame {
    private JButton btnProfile, btnWallet;
    private JComboBox<String> selectMenu_type; 
    private JLabel lblSectionTitle;
    private JPanel cardsPanel;
    
    public DashboardView() {
        setTitle("UCV - SGCU");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // --- 1. OBTENER DATOS DE SESIÓN (Dinámico) ---
        AuthUserDto user = UserSession.getInstance().getUser();
        String userName = "Invitado";
        String userRole = "Visitante";

        if (user != null) {
            userName = user.getFirstName() + " " + user.getLastName();
            // Capitalizar rol (ADMIN -> Admin)
            String rawRole = user.getRole() != null ? user.getRole() : "User";
            userRole = rawRole.substring(0, 1).toUpperCase() + rawRole.substring(1).toLowerCase();
        }

        // --- 2. MAIN PANEL ---
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        
        // --- 3. HEADER ---
        JPanel headerPanel = new JPanel(new BorderLayout()); 
        headerPanel.setBackground(new Color(245, 245, 245));
        
        // A. Profile Button (Left) - CON DATOS REALES
        String profileText = "<html><b>" + userName + "</b><br><i style='font-size:10px'>" + userRole + "</i></html>";
        btnProfile = new RoundedButton(profileText);
        btnProfile.setBackground(new Color(213, 213, 213));
        btnProfile.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        // Carga de imagen segura (Fallback si no existe defaultUser)
        String userImgPath = "assets/images/defaultUser.png";
        if (!new File(userImgPath).exists()) userImgPath = "assets/images/admin_profile.png"; // Fallback
        
        if (new File(userImgPath).exists()) {
             ImageIcon icon = new ImageIcon(new ImageIcon(userImgPath).getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
             btnProfile.setIcon(icon);
        }
        
        btnProfile.setHorizontalAlignment(SwingConstants.LEFT);
        btnProfile.setPreferredSize(new Dimension(200, 70)); // Un poco más ancho para nombres largos
        
        // B. Drop-down list (Right)
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

        // --- 4. BODY SECTION ---
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
        
        // --- 5. FOOTER (WALLET) ---
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(new Color(245, 245, 245));
        footerPanel.setBorder(new EmptyBorder(0, 0, 15, 15));

        btnWallet = new RoundedButton();
        btnWallet.setPreferredSize(new Dimension(80, 80));
        btnWallet.setBackground(new Color(0, 51, 102));
        btnWallet.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Cargar icono Wallet
        String walletPath = "assets/images/wallet.png";
        if (new File(walletPath).exists()) {
             btnWallet.setIcon(Utils.ImageUtils.getRoundedIcon(walletPath, 45, 45, 0));
        } else {
             btnWallet.setText("Wallet");
             btnWallet.setForeground(Color.WHITE);
        }

        // --- CONEXIÓN: ABRIR BILLETERA ---
        btnWallet.addActionListener(e -> {
            // 1. Instanciamos la Vista (Modal) pasando el Dashboard como padre
            WalletView walletView = new WalletView(this);
            
            // 2. Instanciamos el Controlador (Carga el saldo y maneja lógica)
            new WalletController(walletView);
            
            // Nota: Como es modal, el código se pausa aquí hasta que se cierra la billetera
        });

        footerPanel.add(btnWallet);
        
        // --- FINAL ASSEMBLY ---
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        mainPanel.setBorder(new EmptyBorder(20, 40, 10, 40));
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        this.add(mainPanel);
        
        updateContent(MenuOptions.DAILY);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true); // Se muestra
    }
      
    private void updateContent(String viewType) {
        cardsPanel.removeAll(); 
        
        if (viewType.equals(MenuOptions.DAILY)) {
            lblSectionTitle.setText("Menú del día");
            cardsPanel.add(new MenuCard("Pabellón Criollo", "assets/images/Menu/pabellon.jpg"));
            cardsPanel.add(new MenuCard("Opción Veggie", "assets/images/Menu/veggie.jpg"));
            cardsPanel.add(new MenuCard("Sopa del Día", "assets/images/Menu/sopa.jpg"));
            cardsPanel.add(new MenuCard("Pasta Boloñesa", "assets/images/Menu/pasta.jpg"));
            cardsPanel.add(new MenuCard("Cachapa", "assets/images/Menu/cachapa.jpg"));
            cardsPanel.add(new MenuCard("Arepa", "assets/images/Menu/arepa.jpg"));
        } else {
            lblSectionTitle.setText("Menú de la Semana");
            cardsPanel.add(new MenuCard("Lunes: Pasta", "assets/images/Menu/lunes.jpg"));
            cardsPanel.add(new MenuCard("Martes: Pollo", "assets/images/Menu/martes.jpg"));
            cardsPanel.add(new MenuCard("Miércoles: Lentejas", "assets/images/Menu/miercoles.jpg"));
            cardsPanel.add(new MenuCard("Jueves: Pescado", "assets/images/Menu/jueves.jpg"));
        }
        
        cardsPanel.revalidate();
        cardsPanel.repaint();
    }
    
    // --- INNER CLASS: MENU CARD ---
    private class MenuCard extends JPanel {
        private boolean isSelected = false;
        private Color defaultColor = new Color(255, 180, 120); // Naranja UCV
        private Color selectedColor = new Color(30, 80, 150);  // Azul UCV
        
        public MenuCard(String title, String imgPath) {
            setLayout(new BorderLayout());
            setBackground(defaultColor); 
            setOpaque(false); // Importante para que se pinten las esquinas redondeadas bien
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBorder(new EmptyBorder(15, 15, 15, 15)); 

            JLabel lblImg = new JLabel();
            lblImg.setHorizontalAlignment(SwingConstants.CENTER);
            
            if (imgPath != null && new File(imgPath).exists()) {
                 lblImg.setIcon(Utils.ImageUtils.getRoundedIcon(imgPath, 180, 120, 30));
            } else {
                 lblImg.setText("<html><center>🥘<br>" + title + "</center></html>");
                 lblImg.setForeground(Color.WHITE);
            }
            
            JLabel lblTitle = new JLabel(title);
            lblTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
            lblTitle.setForeground(Color.WHITE);
            lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
            lblTitle.setBorder(new EmptyBorder(10, 0, 0, 0)); 
            
            add(lblImg, BorderLayout.CENTER);
            add(lblTitle, BorderLayout.SOUTH);
            
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    toggleSelection(); 
                }
            });
        }
          
        private void toggleSelection() {
            isSelected = !isSelected;
            // Solo cambiamos el color de fondo y repintamos
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Usamos el color según selección
            g2.setColor(isSelected ? selectedColor : defaultColor);
            
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); 
            g2.dispose();
            
            super.paintComponent(g); // Llama al pintado estándar
        }
    }
}