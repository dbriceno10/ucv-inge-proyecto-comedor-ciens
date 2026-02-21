package View.Main;

import Enums.MenuOptions;
import View.CustomComponents.Colors;
import View.CustomComponents.RoundedButton;
import View.CustomComponents.RoundedComboBox;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import DTO.Food.FoodDto;
import DTO.Menu.MenuDto;

import java.awt.*;
import java.awt.event.*;

public class DashboardView extends JFrame {
    private JButton btnProfile, btnWallet;
    private JComboBox<String> selectMenu_type;
    private JLabel lblSectionTitle;
    private JPanel cardsPanel;
    private Colors color = new Colors();

    public DashboardView() {
        setTitle("UCV - SGCU");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 1. Main panel (the background)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(color.BACKGROUND);

        // 2. header: user profile button and menu type selector
        // BorderLayout is used to easily push components to the edges
        JPanel headerPanel = new JPanel(new BorderLayout()); 
        headerPanel.setBackground(color.BACKGROUND);

        // A. profile button (left)
        // HTML is used to give the desired formatting to the text within a Swing component
        String profileText = "<html><b>User</b><br><i style='font-size:10px'>User_type</i></html>";
        btnProfile = new RoundedButton(profileText);
        btnProfile.setBackground(color.LIGHT_GRAY);
        btnProfile.setFont(new Font("SansSerif", Font.PLAIN, 14));

        ImageIcon defaultUser = new ImageIcon("assets/images/defaultUser.png");
        Image imgUser = defaultUser.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        btnProfile.setIcon(new ImageIcon(imgUser));
        btnProfile.setHorizontalAlignment(SwingConstants.LEFT);
        btnProfile.setPreferredSize(new Dimension(180, 70));
    
        // B. drop-down list. (right)
        String[] menu_options = {MenuOptions.DAILY, MenuOptions.WEEKLY};
        selectMenu_type = new RoundedComboBox<>(menu_options);
        selectMenu_type.setPreferredSize(new Dimension(150, 40));

        // Container for the selector (JComboBox) and the text
        JPanel rightHeader = new JPanel();
        rightHeader.setLayout(new BoxLayout(rightHeader, BoxLayout.Y_AXIS));
        rightHeader.setBackground(color.BACKGROUND);

        JLabel lblSelect = new JLabel("Seleccionar menú");
        lblSelect.setAlignmentX(Component.RIGHT_ALIGNMENT);
        lblSelect.setFont(new Font("SansSerif", Font.PLAIN, 12));
        selectMenu_type.setAlignmentX(Component.RIGHT_ALIGNMENT);

        rightHeader.add(lblSelect);
        rightHeader.add(Box.createRigidArea(new Dimension(0, 5))); 
        rightHeader.add(selectMenu_type);

        // The components are organized within the header container
        headerPanel.add(btnProfile, BorderLayout.WEST);
        headerPanel.add(rightHeader, BorderLayout.EAST);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Body section: title and cards with the menus
        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setBackground(color.BACKGROUND);
        bodyPanel.setBorder(new EmptyBorder(40, 50, 50, 50)); 

        // Section title
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(color.BACKGROUND);

        lblSectionTitle = new JLabel("Menú del día");
        lblSectionTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblSectionTitle.setForeground(color.OXFORD_BLUE);
        lblSectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        titlePanel.add(lblSectionTitle);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Card panel: where the available menus are inserted.
        cardsPanel = new JPanel(new GridLayout(0, 4, 50, 30));
        cardsPanel.setBackground(color.BACKGROUND);

        // ScrollPane in case there are many menus, or the screen is small
        JPanel cardsWrapper = new JPanel(new BorderLayout());
        cardsWrapper.setBackground(color.BACKGROUND);
        cardsWrapper.add(cardsPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(cardsWrapper);
        scrollPane.setBorder(null); 
        scrollPane.getViewport().setBackground(color.BACKGROUND);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Scroll speed

        bodyPanel.add(titlePanel, BorderLayout.NORTH);
        bodyPanel.add(Box.createRigidArea(new Dimension(20, 0))); 
        bodyPanel.add(scrollPane, BorderLayout.CENTER);

        // FooterPanel for the wallet button
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(color.BACKGROUND);
        footerPanel.setBorder(new EmptyBorder(0, 0, 15, 15));

        btnWallet = new RoundedButton();
        btnWallet.setActionCommand("OPEN_WALLET"); // tag used in the controller.
        btnWallet.setPreferredSize(new Dimension(80, 80));
        btnWallet.setBackground(color.OXFORD_BLUE);
        btnWallet.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnWallet.setIcon(Utils.ImageUtils.getRoundedIcon("assets/images/wallet.png", 45, 45, 0));
        footerPanel.add(btnWallet);

        // Final assembly
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        mainPanel.setBorder(new EmptyBorder(20, 40, 10, 40));
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        this.add(mainPanel);
        
        // INITIAL VIEW.
        updateTitles(MenuOptions.DAILY);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // To display the interface in full screen mode.
    }

    public void updateTitles(String viewType) { // It updates the titles based on the daily or weekly menu selection
        if (viewType.equals(MenuOptions.DAILY)) {
            lblSectionTitle.setText("Menú del día");
        } else {
            lblSectionTitle.setText("Menú de la Semana");
        }
    }

    public void showDailyMenu(MenuDto menuDay) { // considerar tipo de menú: desayuno, almuerzo, cena...
        cardsPanel.removeAll();

        if (menuDay == null || menuDay.getFoods() == null || menuDay.getFoods().isEmpty()) {
            lblSectionTitle.setText("Menú del día");

            JLabel emptyLabel = new JLabel("No hay menú registrado para hoy.");
            emptyLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
            emptyLabel.setForeground(color.DARK_GRAY);
            cardsPanel.add(emptyLabel);

        } else {
            lblSectionTitle.setText("Menú del día: " + menuDay.getDay());

            for (FoodDto food : menuDay.getFoods()) {
                String imgPath = "assets/images/Menu/" + food.getId() + ".png";
                MenuCard card = new MenuCard(food.getName(), imgPath);
                
                cardsPanel.add(card);
            }

            cardsPanel.revalidate();
            cardsPanel.repaint();
        }
    }

    private class MenuCard extends JPanel {
        private boolean isSelected = false;
        private Color defaultColor = color.ORANGE; 
        private Color selectedColor = new Color(100, 100, 100); 
        
        public MenuCard(String title, String imgPath) {
            setLayout(new BorderLayout());
            setBackground(defaultColor); 
            setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor
            setBorder(new EmptyBorder(15, 15, 15, 15)); // Border to improve image visibility

            JLabel lblImg = new JLabel();
            lblImg.setHorizontalAlignment(SwingConstants.CENTER);
            ImageIcon checkIcon = new ImageIcon(imgPath);

            // Default image in case of incorrect path or no image for the menu
            if (checkIcon.getIconWidth() == -1) { checkIcon = new ImageIcon("assets/images/Menu/default.png"); } 
            
            lblImg.setIcon(Utils.ImageUtils.getRoundedIcon(imgPath, 180, 120, 30));
            
            // Title
            JLabel lblTitle = new JLabel(title);
            lblTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
            lblTitle.setForeground(color.WHITE);
            lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
            lblTitle.setBorder(new EmptyBorder(10, 0, 0, 0)); // Space between image and text

            add(lblImg, BorderLayout.CENTER);
            add(lblTitle, BorderLayout.SOUTH);

            // The listener: what enables button-like behavior
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) { 
                    // to be implemented: intermediate screen to display shifts ***
                    toggleSelection(); 
                }
            });
        }
    
        private void toggleSelection() { // Change color when selecting
            isSelected = !isSelected;
            setBackground(isSelected ? selectedColor : defaultColor);
            repaint();
        }

        // Ignore, rounded-edge style
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

    // event listener
    public void menuTypeListener(ActionListener listener) { selectMenu_type.addActionListener(listener);} 
    public void walletBtnListener(ActionListener listener) { btnWallet.addActionListener(listener);} 

}