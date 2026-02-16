package Controllers.IngredientControllers;

import View.Ingredient.IngredientListView;
import View.Ingredient.IngredientEditorView;
import Model.Ingredient.IngredientSevice; 
import DTO.Ingredient.CreateIngredientDto;
import DTO.Ingredient.IngredientDto;
import javax.swing.JOptionPane;
import java.util.List;

public class IngredientController {
    private IngredientListView view;
    private IngredientSevice service;

    public IngredientController(IngredientListView view) {
        this.view = view;
        this.service = new IngredientSevice();
        loadIngredients();
        
        this.view.btnNuevoIngrediente.addActionListener(e -> openEditor());
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
                JOptionPane.showMessageDialog(editor, "Guardado");
                editor.dispose();
                loadIngredients();
            } catch(Exception ex) { JOptionPane.showMessageDialog(editor, "Error: " + ex.getMessage()); }
        });
        editor.btnCancelar.addActionListener(e -> editor.dispose());
        editor.setVisible(true);
    }
}