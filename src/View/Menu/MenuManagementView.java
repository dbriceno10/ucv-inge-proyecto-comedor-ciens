package View.Menu;

import View.CustomComponents.*;
import View.Food.DishListView;           
import View.Ingredient.IngredientListView; 
import Context.User.UserSession;
import DTO.User.AuthUserDto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class MenuManagementView extends JFrame {
    private RoundedButton btnCreateMenu, btnInventory, btnDishes; 
    private RoundedTextField txtDateFrom, txtDateUntil;
    private JPanel menuContainer;
    private Colors color = new Colors();

    public MenuManagementView() {
        setTitle("SGCU - Gestión de Menús");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 1. main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(color.BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(20, 60, 20, 60));

        // 2. top container: profile button and navigation buttons
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setBackground(color.BACKGROUND);

        // A. header 
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        headerPanel.setBackground(color.BACKGROUND);
        
        // B. navigation panel
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        navPanel.setBackground(color.BACKGROUND);
        navPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        btnInventory = new RoundedButton("Inventario");
        stylize_button(btnInventory, color.LIGHT_BLUE);

        btnDishes = new RoundedButton("Platos");
        stylize_button(btnDishes, color.LIGHT_BLUE);

        navPanel.add(btnInventory);
        navPanel.add(btnDishes);

        // Is added to the top container
        topContainer.add(headerPanel);
        topContainer.add(navPanel); 

        // 3. body
        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(color.WHITE);
        body.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.LIGHT_GRAY, 1),
            new EmptyBorder(30, 40, 30, 40)
        ));

        // Internal toolbar: title, create menu button, and date filters
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(color.WHITE);
        toolbar.setBorder(new EmptyBorder(0, 0, 25, 0));

        // A. Left: Title and Create Menu
        JPanel leftTool = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftTool.setBackground(color.WHITE);
        
        JLabel lblTitle = new JLabel("Lista de Menús"); 
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setForeground(color.OXFORD_BLUE);
        
        btnCreateMenu = new RoundedButton("Crear Menú");
        stylize_button(btnCreateMenu, color.OXFORD_BLUE); 

        leftTool.add(lblTitle);
        leftTool.add(btnCreateMenu);

        // B. Right: Date Filters
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        filterPanel.setBackground(color.WHITE);

        txtDateFrom = createDateInput("01/12/2025"); //***
        txtDateUntil = createDateInput("12/12/2025");
        
        filterPanel.add(txtDateFrom.getParent());
        filterPanel.add(txtDateUntil.getParent());

        toolbar.add(leftTool, BorderLayout.WEST);
        toolbar.add(filterPanel, BorderLayout.EAST);
        body.add(toolbar, BorderLayout.NORTH);

        // C. menu container
        menuContainer = new JPanel(new GridLayout(0, 4, 25, 25));
        menuContainer.setBackground(color.WHITE);

        JScrollPane scroll = new JScrollPane(menuContainer);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(color.WHITE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        body.add(scroll, BorderLayout.CENTER);

        // Final assembly
        mainPanel.add(topContainer, BorderLayout.NORTH);
        mainPanel.add(body, BorderLayout.CENTER);

        this.add(mainPanel);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); //to display the interface in full screen mode.
    }

    // AUXILIARY METHODS

    private void stylize_button(RoundedButton btn, Color background_color) {
        btn.setBackground(background_color);
        btn.setForeground(color.WHITE);
        btn.setPreferredSize(new Dimension(130, 38));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
    }

    private void addMenuCard(String fecha) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color.ORANGE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };

        card.setLayout(new BorderLayout());
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(200, 120));
        card.setBorder(new EmptyBorder(10, 20, 10, 10));

        JLabel dateLabel = new JLabel(fecha);
        dateLabel.setForeground(color.WHITE);
        dateLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        dateLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        JPanel actionsPanel = new JPanel(new GridLayout(3, 1, 0, 5));
        actionsPanel.setOpaque(false);
        /*actionsPanel.add(createIconButton(new EyeIcon()));
        actionsPanel.add(createIconButton(new EditIcon()));
        actionsPanel.add(createIconButton(new TrashIcon()));*/

        JPanel rightWrapper = new JPanel(new BorderLayout());
        rightWrapper.setOpaque(false);
        rightWrapper.add(actionsPanel, BorderLayout.EAST);

        card.add(dateLabel, BorderLayout.CENTER);
        card.add(rightWrapper, BorderLayout.EAST);
        menuContainer.add(card);
    }

    //for the date filters fields
    private RoundedTextField createDateInput(String placeholder) { 
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(color.WHITE);
        
        RoundedTextField txt = new RoundedTextField();
        txt.setText(placeholder);
        txt.setPreferredSize(new Dimension(110, 30));
        
        JPanel inputWrap = new JPanel(new BorderLayout());
        inputWrap.setOpaque(false);
        inputWrap.add(txt, BorderLayout.CENTER);
        
        container.add(inputWrap, BorderLayout.CENTER);
        return txt;
    }

    /*private JButton createIconButton(Icon icon) {
        JButton btn = new JButton(icon);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }*/
    
    // event listener
    public void CreateMenuListener(ActionListener listener) { btnCreateMenu.addActionListener(listener);}
    public void InventoryListener(ActionListener listener) { btnInventory.addActionListener(listener);}
    public void DishesListener(ActionListener listener) { btnDishes.addActionListener(listener);} 

    // component getters, not text
    public JPanel getComponentMenuContainer() { return menuContainer; }
}