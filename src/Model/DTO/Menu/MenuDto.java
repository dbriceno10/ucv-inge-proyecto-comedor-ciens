package Model.DTO.Menu;

import java.util.ArrayList;

import Model.DTO.Food.FoodDto;

public class MenuDto {
  private Integer id;
  private String day; // e.g., "LUNES", "MARTES", etc.
  private String type; // e.g., "DESAYUNO", "ALMUERZO", "CENA"
  private ArrayList<FoodDto> foods;
  private String date; // e.g., "2024-06-10"
  private Boolean isActive;
  private String createdAt = null;
  private String updatedAt = null;
  private ArrayList<MenuQtyDto> qtys;

  public MenuDto(
      Integer id,
      String day,
      String type,
      ArrayList<FoodDto> foods,
      String date,
      Boolean isActive,
      String createdAt,
      String updatedAt,
      ArrayList<MenuQtyDto> qtys) {
    this.id = id;
    this.day = day;
    this.foods = foods;
    this.date = date;
    this.isActive = isActive;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.type = type;
    this.qtys = qtys;
  }

  public MenuDto() {
  }

  public Integer getId() {
    return id;
  }

  public String getDay() {
    return day;
  }

  public ArrayList<FoodDto> getFoods() {
    return foods;
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

  public String getType() {
    return type;
  }

  public ArrayList<MenuQtyDto> getQtys() {
    return qtys;
  }
}
