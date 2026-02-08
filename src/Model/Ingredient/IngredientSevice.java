package Model.Ingredient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import DTO.Ingredient.CreateIngredientDto;
import DTO.Ingredient.IngredientDto;
import DTO.Ingredient.UpdateIngredientDto;
import Model.Common.CommonServices;
// import Model.Ingredient.IngredientModel;
import Utils.Dates;
import Utils.Formatters;

public class IngredientSevice {
  private static final String FILE_PATH = "src/Database/Ingredient/ingredients.json";
  private CommonServices commonServices = new CommonServices();
  private Dates datesUtil = new Dates();
  private Formatters formatters = new Formatters();

  public List<IngredientDto> getAllIngredients() {
    List<IngredientDto> ingredients = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_PATH);
      if (file.exists()) {
        List<IngredientModel> ingredientModels = mapper.readValue(file,
            mapper.getTypeFactory().constructCollectionType(List.class, IngredientModel.class));
        for (IngredientModel ingredientModel : ingredientModels) {
          if (ingredientModel.getDeletedAt() == null) { // Filtrar ingredientes con deletedAt como null
            ingredients.add(this.mapToDto(ingredientModel));
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return ingredients;
  }

  public IngredientDto getIngredientById(Integer id) {
    IngredientModel ingredientModel = this.getById(id);
    if (ingredientModel == null) {
      return null;
    }
    return this.mapToDto(ingredientModel);
  }

  public IngredientDto create(CreateIngredientDto ingredientDto) {
    Integer nextId = this.commonServices.getLastIndex(FILE_PATH, IngredientModel.class);
    String date = this.datesUtil.getCurrentDateTime();
    String name = this.formatters.toUpperCase(ingredientDto.getName());
    IngredientModel newIngredient = new IngredientModel(
        nextId, name, ingredientDto.getUnit(), ingredientDto.getQuantity(), ingredientDto.getPrice(), true, null,
        date, date, null);
    IngredientModel ingredient = this.save(newIngredient);
    if (ingredient == null) {
      return null;
    }
    return this.mapToDto(ingredient);
  }

  public IngredientDto update(UpdateIngredientDto ingredientDto) {
    IngredientModel existing = getById(ingredientDto.getId());
    if (existing == null) {
      throw new IllegalArgumentException("Ingredient not found with id: " + ingredientDto.getId());
    }
    String name = this.formatters.toUpperCase(ingredientDto.getName());
    String date = this.datesUtil.getCurrentDateTime();
    IngredientModel updatedIngredient = new IngredientModel(
        existing.getId(),
        name,
        ingredientDto.getUnit(),
        ingredientDto.getQuantity(),
        ingredientDto.getPrice(),
        existing.getIsActive(),
        existing.getCreatedAt(),
        existing.getCreatedAt(),
        date,
        null);
    IngredientModel ingredient = this.edit(updatedIngredient);
    if (ingredient == null) {
      return null;
    }
    return this.mapToDto(ingredient);
  }

  public Boolean delete(Integer id) {
    IngredientModel existing = this.getById(id);
    if (existing != null) {
      existing.setDeletedAt(this.datesUtil.getCurrentDateTime());
      this.edit(existing);
      return true;
    }
    return false;
  }

  public IngredientDto getByName(String name) {
    String formattedName = this.formatters.toUpperCase(name);
    List<IngredientModel> ingredients = this.getAll();
    IngredientModel found = null;
    for (IngredientModel ingredient : ingredients) {
      if (ingredient.getName().equalsIgnoreCase(formattedName)) {
        found = ingredient;
        break;
      }
    }
    if (found == null) {
      return null;
    }
    return this.mapToDto(found);
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

  private IngredientDto mapToDto(IngredientModel ingredient) {
    return new IngredientDto(
        ingredient.getId(),
        ingredient.getName(),
        ingredient.getPrice(),
        ingredient.getIsActive(),
        ingredient.getCreatedAt(),
        ingredient.getUpdatedAt(),
        ingredient.getUnit(),
        ingredient.getQuantity());
  }

  private List<IngredientModel> getAll() {
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

  private IngredientModel getById(Integer id) {
    List<IngredientModel> ingredients = this.getAll();
    IngredientModel found = null;
    for (IngredientModel ingredient : ingredients) {
      if (ingredient.getId().equals(id)) {
        found = ingredient;
        break;
      }
    }
    return found;
  }
}
