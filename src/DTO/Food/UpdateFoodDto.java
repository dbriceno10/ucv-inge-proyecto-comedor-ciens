package DTO.Food;

public class UpdateFoodDto {
  private Integer id;
  private String name;
  private String description = null;
  private Double decrease;// porcentaje de merma
  private Double valueCF;// valor de costos fijos como servicios, salarios, etc.
  private Double valueCV = null;// valor de costos variables como ingredientes, etc.
  private Integer[] ingredientIds;

  public UpdateFoodDto() {
  }

  public UpdateFoodDto(
      Integer id,
      String name,
      String description,
      Double decrease,
      Double valueCF,
      Double valueCV,
      Integer[] ingredientIds) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.decrease = decrease;
    this.ingredientIds = ingredientIds;
    this.valueCF = valueCF;
    this.valueCV = valueCV;
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

  public Double getValueCV() {
    return valueCV;
  }
}
