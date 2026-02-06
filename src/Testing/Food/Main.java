package Testing.Food;

import DTO.Food.CreateFoodDto;
import DTO.Food.FoodDto;
import DTO.Food.UpdateFoodDto;
import Model.Food.FoodService;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    FoodService foodService = new FoodService();

    System.out.println("Starting food tests...");

    // Test 1: Obtener todos los alimentos
    System.out.println("Test 1: Get all foods");
    try {
      List<FoodDto> foods = foodService.getAllFoods();
      if (foods != null && !foods.isEmpty()) {
        System.out.println("Total foods: " + foods.size());
        for (FoodDto food : foods) {
          System.out.println("Food: " + food.getName() + ", Description: " + food.getDescription());
        }
      } else {
        System.out.println("No foods found.");
      }
    } catch (Exception e) {
      System.out.println("Failed to get foods: " + e.getMessage());
    }

    // Test 2: Buscar un alimento por ID
    System.out.println("Test 2: Get food by ID");
    try {
      FoodDto food = foodService.getFoodById(1);
      if (food != null) {
        System.out.println("Food found: " + food.getName() + ", Description: " + food.getDescription());
      } else {
        System.out.println("Food not found.");
      }
    } catch (Exception e) {
      System.out.println("Failed to get food by ID: " + e.getMessage());
    }

    // Test 3: Crear un nuevo alimento
    System.out.println("Test 3: Create a new food");
    try {
      CreateFoodDto newFood = new CreateFoodDto(
          "Pizza Margherita",
          "Pizza clásica con tomate, mozzarella y albahaca.",
          0.1,
          10.0,
          new Integer[] { 1, 2, 3 });
      FoodDto createdFood = foodService.create(newFood);
      System.out.println("Food created: " + createdFood.getName());
    } catch (Exception e) {
      System.out.println("Failed to create food: " + e.getMessage());
    }

    // Test 4: Actualizar un alimento
    System.out.println("Test 4: Update a food");
    try {
      UpdateFoodDto updatedFood = new UpdateFoodDto(
          1,
          "Ensalada César",
          "Ensalada con lechuga, pollo, crutones y aderezo César.",
          0.05,
          5.0,
          new Integer[] { 4, 5, 6 });
      FoodDto result = foodService.update(updatedFood);
      System.out.println("Food updated: " + result.getName() + ", New Description: " + result.getDescription());
    } catch (Exception e) {
      System.out.println("Failed to update food: " + e.getMessage());
    }

    // Test 5: Eliminar un alimento
    System.out.println("Test 5: Delete a food");
    try {
      Boolean isDeleted = foodService.delete(1);
      if (isDeleted) {
        System.out.println("Food deleted successfully.");
      } else {
        System.out.println("Failed to delete food.");
      }
    } catch (Exception e) {
      System.out.println("Failed to delete food: " + e.getMessage());
    }
  }
}