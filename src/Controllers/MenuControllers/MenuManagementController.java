package Controllers.MenuControllers;

import View.Menu.*;
import View.Config.ConfigView;
import View.Food.*;
import View.Ingredient.IngredientListView;
import Model.DTO.Menu.*;
import Model.Menu.*;

import javax.swing.*;

import Controllers.ConfigControllers.ConfigController;
import Controllers.FoodControllers.DishListController;
import Controllers.IngredientControllers.IngredientListController;

import View.User.StudentListView; // Linea añadida
import Controllers.UserControllers.StudentListController; // Linea añadida

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuManagementController implements ActionListener {
    private MenuManagementView view;
    private MenuService menuService;

    public MenuManagementController(MenuManagementView view) {
        this.view = view;
        menuService = new MenuService();

        this.view.CreateMenuListener(this);
        this.view.InventoryListener(this);
        this.view.DishesListener(this);
        this.view.configListener(this);
        this.view.StudentsListener(this); // Linea añadida

        loadMenus();
        this.view.setVisible(true);
        this.view.setExtendedState(JFrame.MAXIMIZED_BOTH); // to display the interface in full screen mode.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand();
        System.out.println("➡️ Clic detectado en el botón: [" + button + "]");
        
        try {
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
                case "Configurar CCB":
                    ConfigView configView = new ConfigView(this.view); 
                    new ConfigController(configView);
                    break;
                case "Estudiantes": 
                    StudentListView studentView = new StudentListView(); 
                    new StudentListController(studentView); 
                    break; 
                default: 
                    System.out.println("Comando no reconocido.");
                    break;
            }
        } catch (Throwable ex) {
            System.out.println("💥 ¡ERROR CRÍTICO AL ABRIR LA VISTA! 💥");
            ex.printStackTrace(); // Esto nos mostrará el culpable en rojo
        }
    }

    private void loadMenus() {
        JPanel menu_containter = view.getComponentMenuContainer();
        menu_containter.removeAll();
        ArrayList<MenuDto> menus = menuService.getAllMenus();
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