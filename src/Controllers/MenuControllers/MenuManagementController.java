package Controllers.MenuControllers;

import View.Menu.*;
import View.Food.*;
import View.Ingredient.IngredientListView;
import DTO.Menu.*;
import DTO.Food.*;
import Model.Menu.*;
import Model.Food.*;

import javax.swing.*;

import Controllers.FoodControllers.DishListController;
import Controllers.IngredientControllers.IngredientListController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MenuManagementController implements ActionListener {
    private MenuManagementView view;
    private MenuEditorView editorView;
    private MenuService menuService;
    private FoodService foodService;
    private List<Integer> tempFoodIds; 

    public MenuManagementController(MenuManagementView view) {
        this.view = view;
        this.menuService = new MenuService();
        this.foodService = new FoodService();
        
        this.view.CreateMenuListener(this);
        this.view.InventoryListener(this);
        this.view.DishesListener(this);

        loadMenus();
        this.view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand();
        switch (button) {
            case "Crear Menú":
                openEditor();
                break;
            case "Inventario":
                IngredientListView ingredientView = new IngredientListView();
                new IngredientListController(ingredientView);
                break;
            case "Platos":
                DishListView dishView = new DishListView();
                new DishListController(dishView);
                break;
            default: break;
        }
    }

    private void loadMenus() {
        JPanel menu_containter = view.getComponentMenuContainer();
        menu_containter.removeAll();
        List<MenuDto> menus = menuService.getAllMenus();
        for (MenuDto menu : menus) {
            try {
                java.lang.reflect.Method method = view.getClass().getDeclaredMethod("addMenuCard", String.class);
                method.setAccessible(true);
                method.invoke(view, menu.getDate() + " (" + menu.getDay() + ")");
            } catch (Exception ex) {}
        }
        menu_containter.revalidate();
        menu_containter.repaint();
    }

    private void openEditor() {
        this.editorView = new MenuEditorView();
        this.tempFoodIds = new ArrayList<>();
        this.editorView.btnAddPlato.addActionListener(e -> showFoodSelector());
        this.editorView.btnGuardar.addActionListener(e -> saveMenu());
        
        // Si cancela, vuelve a abrir el Menú Principal
        this.editorView.btnCancelar.addActionListener(e -> {
            editorView.dispose();
            new MenuManagementController(new MenuManagementView());
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
            new MenuManagementController(new MenuManagementView()); // Vuelve al menú
        } catch (Exception ex) { JOptionPane.showMessageDialog(editorView, "Error: " + ex.getMessage()); }
    }
}