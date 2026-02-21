package Controllers.MenuControllers;

import View.Menu.*;
import View.Food.*;
import View.Ingredient.IngredientListView;
import DTO.Menu.*;
import Model.Menu.*;

import javax.swing.*;

import Controllers.FoodControllers.DishListController;
import Controllers.IngredientControllers.IngredientListController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuManagementController implements ActionListener {
    private MenuManagementView view;
    private MenuService menuService;

    public MenuManagementController(MenuManagementView view) {
        this.view = view;
        menuService = new MenuService();

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
                MenuEditorView menuEdit = new MenuEditorView();
                new MenuEditorController(menuEdit);
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
}