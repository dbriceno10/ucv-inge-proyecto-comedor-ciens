package DTO.Food;

public class CreateFoodDto {
  private String name;
  private String description = null;
  private Double decrease;// porcentaje de merma
  private Integer[] ingredientIds;

  public CreateFoodDto(
      String name,
      String description,
      Double decrease,
      Integer[] ingredientIds) {
    this.name = name;
    this.description = description;
    this.decrease = decrease;
    this.ingredientIds = ingredientIds;
  }

  // getters
  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Double getDecrease() {
    return decrease;
  }

  public Integer[] getIngredientIds() {
    return ingredientIds;
  }
}
