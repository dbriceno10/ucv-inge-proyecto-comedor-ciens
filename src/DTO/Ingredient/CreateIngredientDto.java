package DTO.Ingredient;

public class CreateIngredientDto {
  private String name;
  private Double price;
  private String unit;
  private Double quantity;

  public CreateIngredientDto(String name, String unit, Double quantity, Double price) {
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
}
