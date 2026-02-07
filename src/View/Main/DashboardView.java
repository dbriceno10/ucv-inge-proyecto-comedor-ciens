package View.Main;

import Enums.MenuOptions;
import View.CustomComponents.RoundedButton;
import View.CustomComponents.RoundedComboBox;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTML;

import java.awt.*;
import java.awt.event.*;

public class DashboardView extends JFrame {
    private JButton btnProfile;
    private JComboBox<String> selectMenu_type; // for the user type drop-down list.
    private JLabel lblSectionTitle;
    private JPanel cardsPanel;

    public DashboardView() {
        setTitle("UCV - SGCU");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 1. main panel (the background).
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // 2. header: user profile button and menu type selector.
        // BorderLayout is used to easily push components to the edges.
        JPanel headerPanel = new JPanel(new BorderLayout()); 
        headerPanel.setBackground(new Color(245, 245, 245));
        headerPanel.setBorder(new EmptyBorder(20, 40, 10, 40));

        // A. profile button (left)
        // HTML is used to give the desired formatting to the text within a Swing component.
        String profileText = "<html><b>User</b><br><i style='font-size:10px'>User_type</i></html>";
        btnProfile = new RoundedButton(profileText);
        btnProfile.setBackground(new Color(245, 245, 245));

        btnProfile.setFont(new Font("SansSerif", Font.PLAIN, 14));

        ImageIcon defaultUser = new ImageIcon("assets/images/defaultUser.png");
        Image imgUser = defaultUser.getImage().getScaledInstance(50, 45, Image.SCALE_SMOOTH);
        btnProfile.setIcon(new ImageIcon(imgUser));
        btnProfile.setHorizontalAlignment(SwingConstants.LEFT);
        btnProfile.setPreferredSize(new Dimension(200, 60));
        
        // B. drop-down list. (right)
        String[] menu_options = {MenuOptions.DAILY, MenuOptions.WEEKLY};
        selectMenu_type = new RoundedComboBox<>(menu_options);
        selectMenu_type.setPreferredSize(new Dimension(150, 40));
        
        // listener to change the view when selecting
        selectMenu_type.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateContent((String) e.getItem());
            }
        });

        // container for the selector (JComboBox) and the text.
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

        // the components are organized within the header container.
        headerPanel.add(btnProfile, BorderLayout.WEST);
        headerPanel.add(rightHeader, BorderLayout.EAST);

        // body section: title and cards with the menus.
        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setBackground(new Color(245,245,245));
        bodyPanel.setBorder(new EmptyBorder(10, 50, 50, 50)); 

        // section title.
        lblSectionTitle = new JLabel("Menú del día");
        lblSectionTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblSectionTitle.setForeground(new Color(0, 33, 71));
        lblSectionTitle.setBorder(new EmptyBorder(0, 0, 20, 0));

        // card panel: where the available menus are inserted.
        cardsPanel = new JPanel(new GridLayout(0, 4, 20, 20));
        cardsPanel.setBackground(new Color(245, 245, 245));

        // ScrollPane in case there are many menus.
        JPanel cardsWrapper = new JPanel(new BorderLayout());
        cardsWrapper.setBackground(new Color(245, 245, 245));
        cardsWrapper.add(cardsPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(cardsWrapper);
        scrollPane.setBorder(null); 
        scrollPane.getViewport().setBackground(new Color(245, 245, 245));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // scroll speed.

        bodyPanel.add(lblSectionTitle, BorderLayout.NORTH);
        bodyPanel.add(scrollPane, BorderLayout.CENTER);

        // final assembly
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);

        this.add(mainPanel);
        
        // INITIAL VIEW.
        updateContent(MenuOptions.DAILY);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.setVisible(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); //to display the interface in full screen mode.
    }

    private void updateContent(String viewType) {
        cardsPanel.removeAll(); 

        if (viewType.equals(MenuOptions.DAILY)) {
            lblSectionTitle.setText("Menú del día");
            cardsPanel.add(new MenuCard("Pabellón Criollo", "img/plato1.jpg"));
            cardsPanel.add(new MenuCard("Opción Veggie", "img/plato2.jpg"));
            cardsPanel.add(new MenuCard("Sopa del Día", "img/sopa.jpg"));
            cardsPanel.add(new MenuCard("Pasta Boloñesa", "img/lunes.jpg"));
        
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
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // cursor hovering over.
        setBorder(new EmptyBorder(15, 15, 15, 15)); // border to improve image visibility.

        JLabel lblImg = new JLabel();
        lblImg.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon icon = new ImageIcon(imgPath);
  
        //default image in case of incorrect path or no image for the menu.
        if (icon.getIconWidth() == -1) { icon = new ImageIcon("assets/images/Menu/default.png"); } 

        Image img = icon.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);
        lblImg.setIcon(new ImageIcon(img));
        
        // title
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBorder(new EmptyBorder(10, 0, 0, 0)); // space between image and text

        add(lblImg, BorderLayout.CENTER);
        add(lblTitle, BorderLayout.SOUTH);

        // the listener: what enables button-like behavior
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // to be implemented: intermediate screen to display shifts ***
                toggleSelection(); 
            }
        });
    }

    //change color when selecting
    private void toggleSelection() {
        isSelected = !isSelected;
        setBackground(isSelected ? selectedColor : defaultColor);
        repaint();
    }

    // ignore, rounded-edge style
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
