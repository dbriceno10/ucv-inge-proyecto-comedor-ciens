package Controllers.MenuControllers;

import View.Menu.*;
import Model.DTO.Food.FoodDto;
import Model.DTO.Menu.CreateMenuDto;
import Model.DTO.Menu.MenuQtyDto;
import Model.Food.FoodService;
import Model.Menu.MenuService;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuEditorController implements ActionListener {
    private MenuEditorView view;
    private MenuService menuService;
    private FoodService foodService;
        
    public MenuEditorController(MenuEditorView view) { 
        this.view = view;
        this.menuService = new MenuService();
        this.foodService = new FoodService();

        this.view.saveListener(this);
        this.view.cancelListener(this);
        this.view.addDishListener(this);

        this.view.setVisible(true);
        this.view.setExtendedState(JFrame.MAXIMIZED_BOTH); 
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
            // 1. Le pedimos a la Vista la lista de todo lo que el usuario configuró con los botones + y -
            ArrayList<MenuQtyDto> menuQuantities = view.getPlatosSeleccionados();

            if (menuQuantities.isEmpty()) { 
                JOptionPane.showMessageDialog(view, "Debe añadir al menos un plato a la lista.", "Alerta", JOptionPane.WARNING_MESSAGE);
                return; 
            }

            // (Extraemos los IDs para mantener compatibilidad con la base de datos vieja)
            ArrayList<Integer> tempFoodIds = new ArrayList<>();
            for(MenuQtyDto item : menuQuantities) tempFoodIds.add(item.getFoodId());
            
            String day = view.getCmbDay();
            String type = view.getCmbType();
            String date = view.getTxtDate();
            
            // 2. Armamos el paquete
            CreateMenuDto newMenu = new CreateMenuDto(day, type, tempFoodIds, menuQuantities, date);
            
            // 3. ¡Guardamos!
            menuService.create(newMenu);
            
            JOptionPane.showMessageDialog(view, "¡Menú guardado exitosamente!");
            view.dispose(); 

        } catch (Exception ex) { 
            JOptionPane.showMessageDialog(view, "Error crítico al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    void showFoodSelector() {
        ArrayList<FoodDto> foods = foodService.getAllFoods();
        if (foods.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No hay platos creados en el inventario.");
            return;
        }

        String[] foodNames = new String[foods.size()];
        for (int i = 0; i < foods.size(); i++) foodNames[i] = foods.get(i).getName();

        // Preguntamos qué plato quiere añadir
        String selectedName = (String) JOptionPane.showInputDialog(
            view, "Seleccione plato:", "Añadir",
            JOptionPane.QUESTION_MESSAGE, null, foodNames, foodNames[0]);

        if (selectedName != null) {
            for (FoodDto food : foods) {
                if (food.getName().equals(selectedName)) {
                    // ¡Dibujamos la fila y le pasamos el ID para poder rastrearlo!
                    view.addPlatoRow(food.getName(), food.getId());
                    break;
                }
            }
        }
    }
}