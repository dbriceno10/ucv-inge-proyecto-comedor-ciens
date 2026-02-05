package Testing.Ingredients;

import Model.Common.CommonServises;
import Model.Ingredient.IngredientModel;
import Model.Ingredient.IngredientSevice;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    IngredientSevice ingredientService = new IngredientSevice();
    CommonServises commonServises = new CommonServises();

    System.out.println("Starting ingredient tests...");

    // Test 1: Obtener todos los ingredientes
    System.out.println("Test 1: Get all ingredients");
    try {
      List<IngredientModel> ingredients = ingredientService.getAllIngredients();
      if (ingredients != null && !ingredients.isEmpty()) {
        System.out.println("Total ingredients: " + ingredients.size());
        for (IngredientModel ingredient : ingredients) {
          System.out.println("Ingredient: " + ingredient.getName() + ", Quantity: " + ingredient.getQuantity());
        }
      } else {
        System.out.println("No ingredients found.");
      }
    } catch (Exception e) {
      System.out.println("Failed to get ingredients: " + e.getMessage());
    }

    // Test 2: Contar todos los ingredientes
    System.out.println("Test 2: Count all ingredients");
    try {
      int totalIngredients = commonServises.countAllElements("src/Database/Ingredient/ingredients.json",
          IngredientModel.class);
      System.out.println("Total ingredients (including deleted): " + totalIngredients);
    } catch (Exception e) {
      System.out.println("Failed to count ingredients: " + e.getMessage());
    }

    // Test 3: Buscar un ingrediente por ID
    System.out.println("Test 3: Get ingredient by ID");
    try {
      IngredientModel ingredient = ingredientService.getIngredientById(1);
      if (ingredient != null) {
        System.out.println("Ingredient found: " + ingredient.getName() + ", Quantity: " + ingredient.getQuantity());
      } else {
        System.out.println("Ingredient not found.");
      }
    } catch (Exception e) {
      System.out.println("Failed to get ingredient by ID: " + e.getMessage());
    }

    // Test 4: Crear un nuevo ingrediente
    System.out.println("Test 4: Create a new ingredient");
    try {
      IngredientModel newIngredient = new IngredientModel(
          0,
          "LEMON",
          "kg",
          5.0,
          3.0,
          null,
          null, null, null,
          null);
      IngredientModel createdIngredient = ingredientService.create(newIngredient);
      System.out.println("Ingredient created: " + createdIngredient.getName());
    } catch (Exception e) {
      System.out.println("Failed to create ingredient: " + e.getMessage());
    }

    // Test 5: Actualizar un ingrediente
    System.out.println("Test 5: Update an ingredient");
    try {
      IngredientModel updatedIngredient = new IngredientModel(
          1,
          "TOMATO",
          "kg",
          15.0, // Actualizamos la cantidad
          2.5,
          true,
          "2026-03-01",
          "2026-02-01",
          "2026-02-03", // Actualizamos la fecha de actualización
          null);
      IngredientModel result = ingredientService.update(updatedIngredient);
      System.out.println("Ingredient updated: " + result.getName() + ", New Quantity: " + result.getQuantity());
    } catch (Exception e) {
      System.out.println("Failed to update ingredient: " + e.getMessage());
    }

    // Test 6: Eliminar un ingrediente
    System.out.println("Test 6: Delete an ingredient");
    try {
      Boolean isDeleted = ingredientService.delete(1);
      if (isDeleted) {
        System.out.println("Ingredient deleted successfully.");
      } else {
        System.out.println("Failed to delete ingredient.");
      }
    } catch (Exception e) {
      System.out.println("Failed to delete ingredient: " + e.getMessage());
    }
  }
}