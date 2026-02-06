package DTO.Ingredient;

public class IngredientDto {
  private Integer id;
  private String name;
  private Double price;
  private Boolean isActive;
  private String createdAt = null;
  private String updatedAt = null;

  public IngredientDto(
      Integer id,
      String name,
      Double price,
      Boolean isActive,
      String createdAt,
      String updatedAt) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.isActive = isActive;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  // getters
  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Double getPrice() {
    return price;
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
}
