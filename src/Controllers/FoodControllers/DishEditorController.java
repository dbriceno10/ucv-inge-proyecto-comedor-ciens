package Controllers.FoodControllers;

import View.Food.DishListView;
import View.Wallet.WalletView;
import View.Food.DishEditorView;
import Model.Food.FoodService;
import DTO.Food.CreateFoodDto;
import DTO.Food.FoodDto;
import Enums.MenuOptions;

import javax.swing.*;

import Controllers.WalletControllers.WalletController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class DishEditorController implements ActionListener {
    private DishListView view;
    private FoodService service;

    public DishEditorController(DishListView view) {
        this.view = view;
        this.service = new FoodService();
        
        
        this.view.btnCrearPlato.addActionListener(e -> openEditor());
        loadDishes();
        this.view.setVisible(true);
    }

    private void loadDishes() { // esto es parte del modelo***
        view.gridPanel.removeAll();
        List<FoodDto> list = service.getAllFoods();
        for(FoodDto f : list) {
            try {
                java.lang.reflect.Method m = view.getClass().getDeclaredMethod("addDishCard", String.class);
                m.setAccessible(true);
                m.invoke(view, f.getName());
            } catch (Exception e) {}
        }
        view.gridPanel.revalidate();
        view.gridPanel.repaint();
    }

    private void openEditor() {
        DishEditorView editor = new DishEditorView();
        editor.btnGuardar.addActionListener(e -> {
            try {
                service.create(new CreateFoodDto(
                    editor.txtNombre.getText(), 
                    editor.txtDescripcion.getText(), 
                    0.0, 0.0, new Integer[]{1}));
                JOptionPane.showMessageDialog(editor, "Guardado");
                editor.dispose();
                loadDishes();
            } catch(Exception ex) { JOptionPane.showMessageDialog(editor, "Error: " + ex.getMessage()); }
        });
        editor.btnCancelar.addActionListener(e -> editor.dispose());
        editor.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //***
    }
}