package Controllers.IngredientControllers;

import View.Ingredient.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import DTO.Ingredient.CreateIngredientDto;
import Model.Ingredient.IngredientSevice;

public class IngredientEditorController implements ActionListener {
    IngredientEditorView view;
    private IngredientSevice service;
    private IngredientListController ingtController;
    
    public IngredientEditorController(IngredientEditorView view) {
        this.view = view;
        service = new IngredientSevice();

        this.view.saveListener(this);
        this.view.cancelListener(this);
        this.view.uploadImgListener(this);

        this.view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Guardar":
                processIngtSave();
                break;
            case "Cancelar":
                view.dispose();
                break;
            case "+": 
               
                break;
            default: break;
        }
    }

    void processIngtSave() {
        ingtController = new IngredientListController();
        try {
            service.create(new CreateIngredientDto(
                view.getTxtName(), 
                view.getUnitMeasurement(), 
                Double.valueOf(view.getTextStock()), 
                0.0));

                JOptionPane.showMessageDialog(view, "Guardado");
                ingtController.loadIngredients();
        } catch(Exception ex) { JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage()); }
    }
}


