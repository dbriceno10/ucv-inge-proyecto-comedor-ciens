package Controllers.FoodControllers;

import View.Food.DishListView;
import View.Ingredient.IngredientListView;
import View.Food.DishEditorView;
import Model.Food.FoodService;
import DTO.Food.FoodDto;
import Controllers.IngredientControllers.IngredientListController;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class DishListController implements ActionListener {
    private DishListView view;
    private FoodService service;

    public DishListController(DishListView view) {
        this.view = view;
        service = new FoodService();

        this.view.createDishListener(this);
        this.view.menuListener(this);
        this.view.inventoryListener(this);
        
        loadDishes();
        this.view.setVisible(true);
        this.view.setExtendedState(JFrame.MAXIMIZED_BOTH); // to display the interface in full screen mode.
    }

    public DishListController() {}

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Crear Plato":
                DishEditorView dishEditor = new DishEditorView();
                new DishEditorController(dishEditor);
                break;
            case "Volver":
                view.dispose();
                break;
            case "Ingredientes": //inventory
                IngredientListView ingtView = new IngredientListView ();
                new IngredientListController(ingtView);
                break;
            default: break;
        }
    }

    public void loadDishes() { 
        JPanel panel = view.getComponent_gridPanel();
        panel.removeAll();
        ArrayList<FoodDto> list = service.getAllFoods();
        for(FoodDto f : list) {
            try {
                java.lang.reflect.Method m = view.getClass().getDeclaredMethod("addDishCard", String.class);
                m.setAccessible(true);
                m.invoke(view, f.getName());
            } catch (Exception e) {}
        }
        panel.revalidate();
        panel.repaint();
    }
}