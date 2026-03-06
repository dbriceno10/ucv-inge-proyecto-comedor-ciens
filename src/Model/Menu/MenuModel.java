package Model.Menu;

import java.util.ArrayList;

import Model.DTO.Menu.CreateMenuDto;

public class MenuModel {
  private Integer id;
  private String day; // e.g., "LUNES", "MARTES", etc.
  private String type; // e.g., "DESAYUNO", "ALMUERZO", "CENA"
  private ArrayList<Integer> foodIds;
  private String date; // e.g., "2024-06-10"
  private Boolean isActive;
  private String createdAt = null;
  private String updatedAt = null;
  private String deletedAt = null;
  private ArrayList<MenuQty> qtys;

  public MenuModel(
      Integer id,
      String day,
      String type,
      ArrayList<Integer> foodIds,
      String date,
      Boolean isActive,
      String createdAt,
      String updatedAt,
      String deleted,
      ArrayList<MenuQty> qtys) {
    this.id = id;
    this.day = day;
    this.type = type;
    this.foodIds = foodIds;
    this.date = date;
    this.isActive = isActive;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = null;
    this.qtys = qtys;
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

  public ArrayList<Integer> getFoodIds() {
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

  public ArrayList<MenuQty> getQtys() {
    return qtys;
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

  public void setQtys(ArrayList<MenuQty> qtys) {
    this.qtys = qtys;
  }
}
