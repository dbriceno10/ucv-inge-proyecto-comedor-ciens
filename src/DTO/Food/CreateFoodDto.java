package DTO.Food;

public class CreateFoodDto {
  private String name;
  private String description = null;
  private Double decrease;// porcentaje de merma
  private Double valueCF;// valor de costos fijos como servicios, salarios, etc.
  private Integer[] ingredientIds;

  public CreateFoodDto(
      String name,
      String description,
      Double decrease,
      Double valueCF,
      Integer[] ingredientIds) {
    this.name = name;
    this.description = description;
    this.decrease = decrease;
    this.valueCF = valueCF;
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

  public Double getValueCF() {
    return valueCF;
  }
}
