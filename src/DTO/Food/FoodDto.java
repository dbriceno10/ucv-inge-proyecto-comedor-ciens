package DTO.Food;

import java.util.ArrayList;
import DTO.Ingredient.IngredientDto;

public class FoodDto {
  private Integer id;
  private String name;
  private String description = null;
  private Double decrease;// porcentaje de merma
  private Boolean isActive;
  private String createdAt = null;
  private String updatedAt = null;
  private ArrayList<IngredientDto> ingredients;

  public FoodDto(
      Integer id,
      String name,
      String description,
      Double decrease,
      Boolean isActive,
      String createdAt,
      String updatedAt,
      ArrayList<IngredientDto> ingredients) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.decrease = decrease;
    this.isActive = isActive;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.ingredients = ingredients;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Double getDecrease() {
    return decrease;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public ArrayList<IngredientDto> getIngredients() {
    return ingredients;
  }
}
