package Controllers;

import View.Food.DishListView;
import View.Food.DishEditorView;
import View.Menu.MenuListView;
import View.Ingredient.IngredientListView;
import Model.Food.FoodService;
import DTO.Food.CreateFoodDto;
import DTO.Food.FoodDto;
import javax.swing.*;
import java.util.List;

// NOTA: Ya no usamos "implements ActionListener" aquí
public class DishController {
    private DishListView view;
    private FoodService service;

    public DishController(DishListView view) {
        this.view = view;
        this.service = new FoodService();
        
        loadDishes();
        
        // --- 1. BOTÓN IR A MENÚ (Navegación) ---
        this.view.btnMenu.addActionListener(e -> {
            System.out.println("Navegando a Menu..."); // Debug en consola
            view.dispose();
            new MenuController(new MenuListView()); 
        });

        // --- 2. BOTÓN IR A INVENTARIO (Navegación) ---
        this.view.btnInventario.addActionListener(e -> {
            System.out.println("Navegando a Inventario..."); // Debug en consola
            view.dispose();
            new IngredientController(new IngredientListView());
        });
        
        // --- 3. BOTÓN CREAR PLATO ---
        this.view.btnCrearPlato.addActionListener(e -> {
            view.dispose(); 
            openEditor();   
        });
        
        // Aseguramos que se vea (por si acaso la vista no lo hizo)
        this.view.setVisible(true);
    }

    private void loadDishes() {
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
                JOptionPane.showMessageDialog(editor, "Plato Guardado");
                editor.dispose();
                new DishController(new DishListView()); 
            } catch(Exception ex) { JOptionPane.showMessageDialog(editor, "Error: " + ex.getMessage()); }
        });

        editor.btnCancelar.addActionListener(e -> {
            editor.dispose();
            new DishController(new DishListView());
        });
        
        editor.setVisible(true);
    }
}