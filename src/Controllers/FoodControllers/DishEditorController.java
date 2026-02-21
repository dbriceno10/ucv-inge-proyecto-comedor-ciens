package Controllers.FoodControllers;

import View.Food.DishEditorView;
import DTO.Food.CreateFoodDto;
import Model.Food.FoodService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class DishEditorController implements ActionListener {
    private DishEditorView view;
    private FoodService service;
    private DishListController dishController;

    public DishEditorController (DishEditorView view) {
        this.view = view;
        service = new FoodService();

        this.view.saveBtnListener(this);
        this.view.cancelBtnListener(this);
        this.view.uploadBtnListener(this);

        this.view.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Guardar":
            processDishSave();
                break;
            case "Cancelar":
                view.dispose();
                break;
            case "+": // btnUploadImage 
                //lógica para añadir y guardar una imagen
                break;
            default: break;
        }
    }

    void processDishSave() {
        dishController = new DishListController();
        try {
            service.create(new CreateFoodDto(
                view.getTxtName(), 
                view.getTxtDesc(), 
                0.0, 0.0, new Integer[]{1}));
                JOptionPane.showMessageDialog(view, "Guardado");

                dishController.loadDishes();
        } catch (Exception e) {JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());}
    }
}
