package Controllers;

import View.Ingredient.IngredientListView;
import View.Ingredient.IngredientEditorView;
import View.Menu.MenuListView;
import View.Food.DishListView;
import Model.Ingredient.IngredientSevice; 
import DTO.Ingredient.CreateIngredientDto;
import DTO.Ingredient.IngredientDto;
import javax.swing.JOptionPane;
import java.util.List;

// NOTA: Ya no usamos "implements ActionListener" aquí
public class IngredientController {
    private IngredientListView view;
    private IngredientSevice service;

    public IngredientController(IngredientListView view) {
        this.view = view;
        this.service = new IngredientSevice();
        loadIngredients();
        
        // --- 1. BOTÓN IR A MENÚ (Navegación) ---
        this.view.btnMenu.addActionListener(e -> {
            System.out.println("Navegando a Menu..."); 
            view.dispose();
            new MenuController(new MenuListView());
        });

        // --- 2. BOTÓN IR A PLATOS (Navegación) ---
        this.view.btnPlatos.addActionListener(e -> {
            System.out.println("Navegando a Platos...");
            view.dispose();
            new DishController(new DishListView());
        });
        
        // --- 3. BOTÓN NUEVO INGREDIENTE ---
        this.view.btnNuevoIngrediente.addActionListener(e -> {
            view.dispose();
            openEditor();
        });
        
        this.view.setVisible(true);
    }

    private void loadIngredients() {
        view.gridPanel.removeAll();
        List<IngredientDto> list = service.getAllIngredients();
        for(IngredientDto i : list) {
            try {
                java.lang.reflect.Method m = view.getClass().getDeclaredMethod("addIngredientCard", String.class, String.class, String.class);
                m.setAccessible(true);
                m.invoke(view, i.getName(), i.getQuantity() + " " + i.getUnit(), i.getName().toLowerCase());
            } catch (Exception e) {}
        }
        view.gridPanel.revalidate();
        view.gridPanel.repaint();
    }

    private void openEditor() {
        IngredientEditorView editor = new IngredientEditorView();
        
        editor.btnGuardar.addActionListener(e -> {
            try {
                service.create(new CreateIngredientDto(
                    editor.txtNombre.getText(), 
                    (String)editor.cmbUnidad.getSelectedItem(), 
                    Double.valueOf(editor.txtStock.getText()), 
                    0.0));
                JOptionPane.showMessageDialog(editor, "Ingrediente Guardado");
                editor.dispose();
                new IngredientController(new IngredientListView());
            } catch(Exception ex) { JOptionPane.showMessageDialog(editor, "Error: " + ex.getMessage()); }
        });

        editor.btnCancelar.addActionListener(e -> {
            editor.dispose();
            new IngredientController(new IngredientListView());
        });
        
        editor.setVisible(true);
    }
}