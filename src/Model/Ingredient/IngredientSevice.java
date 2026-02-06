package Model.Ingredient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Common.CommonServices;
// import Model.Ingredient.IngredientModel;
import Utils.Dates;
import Utils.Formatters;

public class IngredientSevice {
  String FILE_PATH = "src/Database/Ingredient/ingredients.json";
  private CommonServices commonServices = new CommonServices();
  private Dates datesUtil = new Dates();
  private Formatters formatters = new Formatters();

  public List<IngredientModel> getAllIngredients() {
    List<IngredientModel> ingredients = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_PATH);
      if (file.exists()) {
        List<IngredientModel> ingredientModels = mapper.readValue(file,
            mapper.getTypeFactory().constructCollectionType(List.class, IngredientModel.class));
        for (IngredientModel ingredientModel : ingredientModels) {
          if (ingredientModel.getDeletedAt() == null) { // Filtrar ingredientes con deletedAt como null
            ingredients.add(ingredientModel);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return ingredients;
  }

  public IngredientModel getIngredientById(Integer id) {
    List<IngredientModel> ingredients = this.getAllIngredients();
    IngredientModel found = null;
    for (IngredientModel ingredient : ingredients) {
      if (ingredient.getId().equals(id)) {
        found = ingredient;
        break;
      }
    }
    return found;
  }

  public IngredientModel create(IngredientModel ingredient) {
    Integer nextId = this.commonServices.getLastIndex(FILE_PATH, IngredientModel.class);
    String date = this.datesUtil.getCurrentDateTime();
    String name = this.formatters.toUpperCase(ingredient.getName());
    IngredientModel newIngredient = new IngredientModel(
        nextId, name, ingredient.getUnit(), ingredient.getQuantity(), ingredient.getPrice(), true, null,
        date, date, null);
    return this.save(newIngredient);
  }

  public IngredientModel update(IngredientModel ingredient) {
    IngredientModel existing = getIngredientById(ingredient.getId());
    if (existing == null) {
      throw new IllegalArgumentException("Ingredient not found with id: " + ingredient.getId());
    }
    String name = this.formatters.toUpperCase(ingredient.getName());
    String date = this.datesUtil.getCurrentDateTime();
    IngredientModel updatedIngredient = new IngredientModel(
        existing.getId(),
        name,
        ingredient.getUnit(),
        ingredient.getQuantity(),
        ingredient.getPrice(),
        existing.getIsActive(),
        existing.getCreatedAt(),
        existing.getCreatedAt(),
        date,
        null);
    return this.edit(updatedIngredient);
  }

  public Boolean delete(Integer id) {
    IngredientModel existing = this.getIngredientById(id);
    if (existing != null) {
      existing.setDeletedAt(this.datesUtil.getCurrentDateTime());
      this.edit(existing);
      return true;
    }
    return false;
  }

  public IngredientModel getByName(String name) {
    String formattedName = this.formatters.toUpperCase(name);
    List<IngredientModel> ingredients = this.getAllIngredients();
    IngredientModel found = null;
    for (IngredientModel ingredient : ingredients) {
      if (ingredient.getName().equalsIgnoreCase(formattedName)) {
        found = ingredient;
        break;
      }
    }
    return found;
  }

  // metodos privados
  private IngredientModel save(IngredientModel ingredient) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_PATH);
      List<IngredientModel> ingredients = this.commonServices.getAllElements(FILE_PATH, IngredientModel.class);
      if (ingredients == null) {
        ingredients = new ArrayList<>();
      }
      ingredients.add(ingredient);
      mapper.writeValue(file, ingredients);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    return ingredient;
  }

  private IngredientModel edit(IngredientModel ingredient) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_PATH);
      List<IngredientModel> ingredients = this.commonServices.getAllElements(FILE_PATH, IngredientModel.class);
      // Find and update the ingredient
      for (Integer i = 0; i < ingredients.size(); i++) {
        if (ingredients.get(i).getId().equals(ingredient.getId())) {
          ingredients.set(i, ingredient);
          break;
        }
      }
      mapper.writeValue(file, ingredients);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    return ingredient;
  }

}
