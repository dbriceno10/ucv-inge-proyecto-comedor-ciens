package Controllers.MenuControllers;

import View.Menu.*;
import DTO.Food.FoodDto;
import DTO.Menu.CreateMenuDto;
import Model.Food.FoodService;
import Model.Menu.MenuService;
import Enums.Days;
import Enums.MenuTypes;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuEditorController implements ActionListener {
    private MenuEditorView view;
    private ArrayList<Integer> tempFoodIds;
    private MenuService menuService;
    private FoodService foodService;
        
    public MenuEditorController(MenuEditorView view) { 
        this.view = view;

        tempFoodIds = new ArrayList<>();
        menuService = new MenuService();
        foodService = new FoodService();

        this.view.saveListener(this);
        this.view.cancelListener(this);
        this.view.addDishListener(this);

        this.view.setVisible(true);
        this.view.setExtendedState(JFrame.MAXIMIZED_BOTH); // to display the interface in full screen mode.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Guardar":
                processMenuSave();
                break;
            case "Cancelar":
                view.dispose();
                break;
            case "Añadir Plato":
                showFoodSelector();
                break;
            default: break;
        }
    }

    void processMenuSave() {
        try {
            if (tempFoodIds.isEmpty()) { 
                JOptionPane.showMessageDialog(view, "Añada un plato."); 
                return; 
            }

            CreateMenuDto newMenu = new CreateMenuDto(Days.MONDAY, MenuTypes.BREACKFAST,tempFoodIds.toArray(new Integer[0]),10, view.getTxtDate());
            menuService.create(newMenu);
            JOptionPane.showMessageDialog(view, "Guardado");
        } catch (Exception ex) { JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage()); }
    }

    void showFoodSelector() {
        ArrayList<FoodDto> foods = foodService.getAllFoods();
        if (foods.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No hay platos creados.");
            return;
        }

        String[] foodNames = new String[foods.size()];
        for (int i = 0; i < foods.size(); i++) foodNames[i] = foods.get(i).getName();

        String selectedName = (String) JOptionPane.showInputDialog(
            view, "Seleccione plato:", "Añadir",
            JOptionPane.QUESTION_MESSAGE, null, foodNames, foodNames[0]);

        if (selectedName != null) {
            for (FoodDto food : foods) {
                if (food.getName().equals(selectedName)) {
                    tempFoodIds.add(food.getId());
                    try {
                        java.lang.reflect.Method m = view.getClass().getDeclaredMethod("addPlatoRow", String.class);
                        m.setAccessible(true);
                        m.invoke(view, food.getName());
                    } catch (Exception ex) {}
                    break;
                }
            }
        }
    }
}
  