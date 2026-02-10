package Controllers;

import View.Menu.MenuListView;
import View.Menu.MenuEditorView;
import View.Food.DishListView;
import View.Ingredient.IngredientListView;
import Model.Menu.MenuService;
import Model.Food.FoodService;
import DTO.Menu.MenuDto;
import DTO.Menu.CreateMenuDto;
import DTO.Food.FoodDto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MenuController implements ActionListener {
    private MenuListView view;
    private MenuEditorView editorView;
    private MenuService menuService;
    private FoodService foodService;
    private List<Integer> tempFoodIds; 

    public MenuController(MenuListView view) {
        this.view = view;
        this.menuService = new MenuService();
        this.foodService = new FoodService();
        
        loadMenus();
        
        // Asignar los botones
        this.view.btnCrearMenu.addActionListener(this);
        this.view.btnInventario.addActionListener(this);
        this.view.btnPlatos.addActionListener(this);
        
        this.view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        // --- AQUÍ ESTÁ LA LÓGICA TIPO LOGIN ---
        
        if (source == view.btnInventario) {
            // 1. Cierra la ventana actual (IGUAL QUE EN EL LOGIN)
            view.dispose(); 
            
            // 2. Abre la ventana de Inventario
            IngredientListView ingView = new IngredientListView();
            new IngredientController(ingView); 
        } 
        else if (source == view.btnPlatos) {
            // 1. Cierra la ventana actual (IGUAL QUE EN EL LOGIN)
            view.dispose(); 
            
            // 2. Abre la ventana de Platos
            DishListView dishView = new DishListView();
            new DishController(dishView);
        }
        else if (source == view.btnCrearMenu) {
            // 1. Cierra la ventana actual
            view.dispose();
            openEditor();
        } 
    }

    // ... (El resto de métodos de carga y guardado siguen igual abajo)
    
    private void loadMenus() {
        view.gridPanel.removeAll();
        List<MenuDto> menus = menuService.getAllMenus();
        for (MenuDto menu : menus) {
            try {
                java.lang.reflect.Method method = view.getClass().getDeclaredMethod("addMenuCard", String.class);
                method.setAccessible(true);
                method.invoke(view, menu.getDate() + " (" + menu.getDay() + ")");
            } catch (Exception ex) {}
        }
        view.gridPanel.revalidate();
        view.gridPanel.repaint();
    }

    private void openEditor() {
        this.editorView = new MenuEditorView();
        this.tempFoodIds = new ArrayList<>();
        this.editorView.btnAddPlato.addActionListener(e -> showFoodSelector());
        this.editorView.btnGuardar.addActionListener(e -> saveMenu());
        
        // Si cancela, vuelve a abrir el Menú Principal
        this.editorView.btnCancelar.addActionListener(e -> {
            editorView.dispose();
            new MenuController(new MenuListView());
        });
        
        this.editorView.setVisible(true);
    }

    private void showFoodSelector() {
        List<FoodDto> foods = foodService.getAllFoods();
        if (foods.isEmpty()) {
            JOptionPane.showMessageDialog(editorView, "No hay platos creados.");
            return;
        }
        String[] foodNames = new String[foods.size()];
        for (int i = 0; i < foods.size(); i++) foodNames[i] = foods.get(i).getName();

        String selectedName = (String) JOptionPane.showInputDialog(
                editorView, "Seleccione plato:", "Añadir",
                JOptionPane.QUESTION_MESSAGE, null, foodNames, foodNames[0]);

        if (selectedName != null) {
            for (FoodDto food : foods) {
                if (food.getName().equals(selectedName)) {
                    tempFoodIds.add(food.getId());
                    try {
                        java.lang.reflect.Method m = editorView.getClass().getDeclaredMethod("addPlatoRow", String.class);
                        m.setAccessible(true);
                        m.invoke(editorView, food.getName());
                    } catch (Exception ex) {}
                    break;
                }
            }
        }
    }

    private void saveMenu() {
        try {
            if (tempFoodIds.isEmpty()) { JOptionPane.showMessageDialog(editorView, "Añada un plato."); return; }
            CreateMenuDto newMenu = new CreateMenuDto("LUNES", tempFoodIds.toArray(new Integer[0]), editorView.txtFecha.getText());
            menuService.create(newMenu);
            JOptionPane.showMessageDialog(editorView, "Guardado");
            editorView.dispose();
            new MenuController(new MenuListView()); // Vuelve al menú
        } catch (Exception ex) { JOptionPane.showMessageDialog(editorView, "Error: " + ex.getMessage()); }
    }
}