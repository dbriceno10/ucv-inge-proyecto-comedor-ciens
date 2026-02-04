package Model.Ingredient;

public class IngredientModel {
  private Integer id;
  private String name;
  private Double price;
  private Boolean isActive;
  private String createdAt = null;
  private String updatedAt = null;
  private String deletedAt = null;
  // *Estos atributos son para luego */
  private String unit;
  private Double quantity;
  private String expirationDate = null;

  public IngredientModel() {
  }

  public IngredientModel(Integer id, String name, String unit, Double quantity, Double price, Boolean isActive,
      String expirationDate,
      String createdAt, String updatedAt, String deletedAt) {
    this.id = id;
    this.name = name;
    this.unit = unit;
    this.quantity = quantity;
    this.price = price;
    this.isActive = isActive;
    this.expirationDate = expirationDate;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
  }

  // getters
  public Integer getId() {
    return id;
  }

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

  public String getExpirationDate() {
    return expirationDate;
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

  public Boolean getIsActive() {
    return isActive;
  }

  // setters
  public void expirationDate(String expirationDate) {
    this.expirationDate = expirationDate;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setDeletedAt(String deletedAt) {
    this.deletedAt = deletedAt;
  }
}
