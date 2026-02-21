package Controllers.IngredientControllers;

import View.Ingredient.IngredientListView;
import View.Menu.MenuManagementView;
import View.Food.DishListView;
import View.Ingredient.IngredientEditorView;
import Model.Ingredient.IngredientSevice; 
import DTO.Ingredient.IngredientDto;
import Controllers.FoodControllers.DishListController;
import Controllers.MenuControllers.MenuManagementController;

import javax.swing.JPanel;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IngredientListController implements ActionListener {
    private IngredientListView view;
    private IngredientSevice service;

    public IngredientListController(IngredientListView view) {
        this.view = view;
        service = new IngredientSevice();

        this.view.newIngredientListener(this);
        this.view.menuListener(this);
        this.view.dishesListener(this);

        loadIngredients();
        this.view.setVisible(true);
    }

    public IngredientListController() {}

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Nuevo Ingrediente":
                IngredientEditorView ingtEditor = new IngredientEditorView();
                new IngredientEditorController(ingtEditor);
                break;
            case "Menú":
                view.dispose();
                MenuManagementView menuMagView = new MenuManagementView();
                new MenuManagementController(menuMagView);
                break;
            case "Platos": 
                view.dispose();
                DishListView dishView = new DishListView();
                new DishListController(dishView);
                break;
            default: break;
        }
    }

    public void loadIngredients() {
        JPanel panel = view.getComponent_gridPanel();
        panel.removeAll();
        List<IngredientDto> list = service.getAllIngredients();
        for(IngredientDto i : list) {
            try {
                java.lang.reflect.Method m = view.getClass().getDeclaredMethod("addIngredientCard", String.class, String.class, String.class);
                m.setAccessible(true);
                m.invoke(view, i.getName(), i.getQuantity() + " " + i.getUnit(), i.getName().toLowerCase());
            } catch (Exception e) {}
        }
        panel.revalidate();
        panel.repaint();
    }
}