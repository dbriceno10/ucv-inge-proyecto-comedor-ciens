package Model.Food;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Common.CommonServises;
import Utils.Dates;
import DTO.Food.CreateFoodDto;
import DTO.Food.UpdateFoodDto;
import DTO.Food.FoodDto;
import Model.Ingredient.IngredientSevice;
import Model.Ingredient.IngredientModel;
import DTO.Ingredient.IngredientDto;

public class FoodService {
  String FILE_PATH = "src/Database/Food/foods.json";
  private CommonServises commonServises = new CommonServises();
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

  public FoodModel getFoodById(Integer id) {
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

  public FoodDto create(CreateFoodDto foodDto) {
    Integer nextId = this.commonServises.getLastIndex(FILE_PATH, FoodModel.class);
    String date = this.datesUtil.getCurrentDateTime();
    FoodModel newFood = new FoodModel(foodDto, nextId, date);
    FoodModel food = this.save(newFood);
    return this.mapToDto(food);
  }

  public FoodDto update(UpdateFoodDto foodDto) {
    FoodModel existingFood = this.getFoodById(foodDto.getId());
    if (existingFood == null) {
      throw new IllegalArgumentException("Food not found with id: " + foodDto.getId());
    }
    FoodModel updatedFood = new FoodModel(
        existingFood.getId(),
        foodDto.getName(),
        foodDto.getDescription(),
        foodDto.getDecrease(),
        existingFood.getIsActive(),
        existingFood.getCreatedAt(),
        this.datesUtil.getCurrentDateTime(),
        existingFood.getDeletedAt(),
        foodDto.getIngredientIds());
    FoodModel food = this.update(updatedFood);
    return this.mapToDto(food);
  }

  // metodos privadis

  private FoodModel save(FoodModel food) {
    List<FoodModel> foods = this.getAll();
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

  private FoodModel update(FoodModel food) {
    List<FoodModel> foods = this.getAll();
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
      IngredientModel ingredientModel = this.ingredientSevice.getIngredientById(ingredientId);
      if (ingredientModel != null) {
        IngredientDto ingredientDto = new IngredientDto(
            ingredientModel.getId(),
            ingredientModel.getName(),
            ingredientModel.getPrice(),
            ingredientModel.getIsActive(),
            ingredientModel.getCreatedAt(),
            ingredientModel.getUpdatedAt());
        ingredientDtos.add(ingredientDto);
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

}