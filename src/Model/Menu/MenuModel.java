package Model.Menu;

import DTO.Menu.CreateMenuDto;

public class MenuModel {
  private Integer id;
  private String day; // e.g., "LUNES", "MARTES", etc.
  private String type; // e.g., "DESAYUNO", "ALMUERZO", "CENA"
  private Integer[] foodIds;
  private String date; // e.g., "2024-06-10"
  private Boolean isActive;
  private String createdAt = null;
  private String updatedAt = null;
  private String deletedAt = null;
  private String image = null;
  private Integer qty;
  private Integer currentQty;

  public MenuModel(
      Integer id,
      String day,
      String type,
      Integer[] foodIds,
      String date,
      Boolean isActive,
      String createdAt,
      String updatedAt,
      String deletedAt,
      Integer qty,
      String image) {
    this.id = id;
    this.day = day;
    this.type = type;
    this.foodIds = foodIds;
    this.date = date;
    this.isActive = isActive;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
    this.image = image;
    this.qty = qty;
    this.currentQty = qty; // Inicialmente, currentQty es igual a qty
  }

  public MenuModel(
      Integer id,
      String day,
      String type,
      Integer[] foodIds,
      String date,
      Boolean isActive,
      String createdAt,
      String updatedAt,
      String deletedAt,
      Integer qty) {
    this.id = id;
    this.day = day;
    this.type = type;
    this.foodIds = foodIds;
    this.date = date;
    this.isActive = isActive;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
    this.image = null;
    this.qty = qty;
    this.currentQty = qty; // Inicialmente, currentQty es igual a qty
  }

  public MenuModel(CreateMenuDto menuDto, Integer id, String createdAt) {
    this.id = id;
    this.day = menuDto.getDay();
    this.foodIds = menuDto.getFoodIds();
    this.date = menuDto.getDate();
    this.isActive = true; // Por defecto, el menú se crea como activo
    this.createdAt = createdAt;
    this.updatedAt = createdAt; // Inicialmente, updatedAt es igual a createdAt
    this.deletedAt = null;
    this.type = menuDto.getType();
  }

  public MenuModel() {
  }

  // getters

  public Integer getId() {
    return id;
  }

  public String getDay() {
    return day;
  }

  public Integer[] getFoodIds() {
    return foodIds;
  }

  public String getDate() {
    return date;
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

  public String getType() {
    return type;
  }

  public String getImage() {
    return image;
  }

    public Integer getQty() {
      return qty;
    }

    public Integer getCurrentQty() {
      return currentQty;
    }

  // seters

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setDeletedAt(String deletedAt) {
    this.deletedAt = deletedAt;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public void setCurrentQty(Integer currentQty) {
    this.currentQty = currentQty;
  }


}
