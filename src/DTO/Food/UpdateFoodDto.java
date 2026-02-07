package DTO.Food;

public class UpdateFoodDto {
  private Integer id;
  private String name;
  private String description = null;
  private Double decrease;// porcentaje de merma
  private Double valueCF;// valor de costos fijos como servicios, salarios, etc.
  private Integer[] ingredientIds;

  public UpdateFoodDto(
      Integer id,
      String name,
      String description,
      Double decrease,
      Double valueCF,
      Integer[] ingredientIds) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.decrease = decrease;
    this.ingredientIds = ingredientIds;
    this.valueCF = valueCF;
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

  public Integer getId() {
    return id;
  }

  public Double getValueCF() {
    return valueCF;
  }
}
