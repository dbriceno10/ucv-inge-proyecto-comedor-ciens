package Model.Food;

import DTO.Food.CreateFoodDto;

public class FoodModel {
  private Integer id;
  private String name;
  private String description = null;
  private Double decrease;// porcentaje de merma
  private Double valueCF;// valor de costos fijos como servicios, salarios, etc.
  private Double valueCV = null;// valor de costos variables como ingredientes, etc.
  private Boolean isActive;
  private String createdAt = null;
  private String updatedAt = null;
  private String deletedAt = null;
  private Integer[] ingredientIds;

  public FoodModel(
      Integer id,
      String name,
      String description,
      Double decrease,
      Double valueCF,
      Boolean isActive,
      String createdAt,
      String updatedAt,
      String deletedAt,
      Integer[] ingredientIds) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.decrease = decrease;
    this.isActive = isActive;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
    this.ingredientIds = ingredientIds;
    this.valueCF = valueCF;
    this.valueCV = null;
  }

  public FoodModel(
      Integer id,
      String name,
      String description,
      Double decrease,
      Double valueCF,
      Double valueCV,
      Boolean isActive,
      String createdAt,
      String updatedAt,
      String deletedAt,
      Integer[] ingredientIds) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.decrease = decrease;
    this.isActive = isActive;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
    this.ingredientIds = ingredientIds;
    this.valueCF = valueCF;
    this.valueCV = valueCV;
  }

  public FoodModel(CreateFoodDto foodDto, Integer id, String createdAt) {
    this.id = id;
    this.name = foodDto.getName();
    this.description = foodDto.getDescription();
    this.decrease = foodDto.getDecrease();
    this.isActive = true;
    this.createdAt = createdAt;
    this.updatedAt = createdAt;
    this.ingredientIds = foodDto.getIngredientIds();
    this.valueCF = foodDto.getValueCF();
    this.valueCV = 0.0;
  }

  public FoodModel() {
  }

  // getters
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

  public String getDeletedAt() {
    return deletedAt;
  }

  public Integer[] getIngredientIds() {
    return ingredientIds;
  }

  public Double getValueCF() {
    return valueCF;
  }

  public Double getValueCV() {
    return valueCV;
  }

  // setters
  public void deletedAt(String deletedAt) {
    this.deletedAt = deletedAt;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setValueCV(Double valueCV) {
    this.valueCV = valueCV;
  }

}
