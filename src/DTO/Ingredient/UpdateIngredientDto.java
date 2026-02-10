package DTO.Ingredient;

public class UpdateIngredientDto {
  private Integer id;
  private String name;
  private Double price;
  private String unit;
  private Double quantity;

  public UpdateIngredientDto(Integer id, String name, String unit, Double quantity, Double price) {
    this.id = id;
    this.name = name;
    this.unit = unit;
    this.quantity = quantity;
    this.price = price;
  }

  // getters
  public String getName() {
    return name;
  }

  public String getUnit() {
    return unit;
  }

  public Double getQuantity() {
    return quantity;
  }

  public Double getPrice() {
    return price;
  }

  public Integer getId() {
    return id;
  }
}
