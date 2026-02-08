package Model.Food;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Common.CommonServices;
import Utils.Dates;
import DTO.Food.CreateFoodDto;
import DTO.Food.UpdateFoodDto;
import DTO.Food.FoodDto;
import Model.Ingredient.IngredientSevice;
import DTO.Ingredient.IngredientDto;

public class FoodService {
  private String FILE_PATH = "src/Database/Food/foods.json";
  private CommonServices commonServices = new CommonServices();
  private Dates datesUtil = new Dates();
  private IngredientSevice ingredientSevice = new IngredientSevice();

  public List<FoodDto> getAllFoods() {
    List<FoodDto> foods = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_PATH);
      if (file.exists()) {
        List<FoodModel> foodModels = mapper.readValue(file,
            mapper.getTypeFactory().constructCollectionType(List.class, FoodModel.class));
        for (FoodModel foodModel : foodModels) {
          if (foodModel.getDeletedAt() == null) { // Filtrar alimentos con deletedAt como null
            foods.add(this.mapToDto(foodModel));
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return foods;
  }

  public FoodDto getFoodById(Integer id) {
    FoodModel foodModel = this.getById(id);
    if (foodModel == null) {
      return null;
    }
    return this.mapToDto(foodModel);
  }

  public FoodDto create(CreateFoodDto foodDto) {
    Integer nextId = this.commonServices.getLastIndex(FILE_PATH, FoodModel.class);
    String date = this.datesUtil.getCurrentDateTime();
    FoodModel newFood = new FoodModel(foodDto, nextId, date);
    FoodDto auxFood = this.mapToDto(newFood);
    Double variableCV = this.calculateVariableCost(auxFood);
    newFood.setValueCV(variableCV);
    FoodModel food = this.save(newFood);
    if (food == null) {
      return null;
    }
    return this.mapToDto(food);
  }

  public FoodDto update(UpdateFoodDto foodDto) {
    FoodModel existingFood = this.getById(foodDto.getId());
    if (existingFood == null) {
      throw new IllegalArgumentException("Food not found with id: " + foodDto.getId());
    }
    FoodModel updatedFood = new FoodModel(
        existingFood.getId(),
        foodDto.getName(),
        foodDto.getDescription(),
        foodDto.getDecrease(),
        foodDto.getValueCF(),
        existingFood.getIsActive(),
        existingFood.getCreatedAt(),
        this.datesUtil.getCurrentDateTime(),
        existingFood.getDeletedAt(),
        foodDto.getIngredientIds());
    FoodDto auxFood = this.mapToDto(updatedFood);
    Double variableCV = this.calculateVariableCost(auxFood);
    updatedFood.setValueCV(variableCV);
    FoodModel food = this.edit(updatedFood);
    if (food == null) {
      return null;
    }
    return this.mapToDto(food);
  }

  public Boolean delete(Integer id) {
    FoodModel existing = this.getById(id);
    if (existing != null) {
      existing.deletedAt(this.datesUtil.getCurrentDateTime());
      this.edit(existing);
      return true;
    }
    return false;
  }

  // metodos privados

  private FoodModel save(FoodModel food) {
    List<FoodModel> foods = this.commonServices.getAllElements(FILE_PATH, FoodModel.class);
    foods.add(food);
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), foods);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    return food;
  }

  private FoodModel edit(FoodModel food) {
    List<FoodModel> foods = this.commonServices.getAllElements(FILE_PATH, FoodModel.class);
    boolean found = false;
    for (int i = 0; i < foods.size(); i++) {
      if (foods.get(i).getId().equals(food.getId())) {
        foods.set(i, food);
        found = true;
        break;
      }
    }
    if (!found) {
      throw new IllegalArgumentException("Food not found with id: " + food.getId());
    }
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), foods);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    return food;
  }

  private FoodDto mapToDto(FoodModel food) {
    ArrayList<IngredientDto> ingredientDtos = new ArrayList<>();
    for (Integer ingredientId : food.getIngredientIds()) {
      IngredientDto ingredient = this.ingredientSevice.getIngredientById(ingredientId);
      if (ingredient != null) {
        ingredientDtos.add(ingredient);
      }
    }
    return new FoodDto(
        food.getId(),
        food.getName(),
        food.getDescription(),
        food.getDecrease(),
        food.getIsActive(),
        food.getCreatedAt(),
        food.getUpdatedAt(),
        ingredientDtos);
  }

  private List<FoodModel> getAll() {
    List<FoodModel> foods = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_PATH);
      if (file.exists()) {
        List<FoodModel> foodModels = mapper.readValue(file,
            mapper.getTypeFactory().constructCollectionType(List.class, FoodModel.class));
        for (FoodModel foodModel : foodModels) {
          if (foodModel.getDeletedAt() == null) { // Filtrar alimentos con deletedAt como null
            foods.add(foodModel);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return foods;
  }

  private FoodModel getById(Integer id) {
    List<FoodModel> foods = this.getAll();
    FoodModel found = null;
    for (FoodModel food : foods) {
      if (food.getId().equals(id)) {
        found = food;
        break;
      }
    }
    return found;
  }

  private Double calculateVariableCost(FoodDto foodDto) {
    Double totalCost = 0.0;
    for (IngredientDto ingredient : foodDto.getIngredients()) {
      totalCost += ingredient.getPrice();
    }
    return totalCost;
  }

}