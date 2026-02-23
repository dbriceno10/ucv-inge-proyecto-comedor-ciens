package DTO.Menu;

import java.util.ArrayList;
import DTO.Food.FoodDto;

public class MenuDto {
  private Integer id;
  private String day; // e.g., "LUNES", "MARTES", etc.
  private String type; // e.g., "DESAYUNO", "ALMUERZO", "CENA"
  private ArrayList<FoodDto> foods;
  private String date; // e.g., "2024-06-10"
  private Boolean isActive;
  private String createdAt = null;
  private String updatedAt = null;
  private String image = null;
  private Integer qty;
  private Integer currentQty;

  public MenuDto(
      Integer id,
      String day,
      String type,
      ArrayList<FoodDto> foods,
      String date,
      Boolean isActive,
      String createdAt,
      String updatedAt,
      Integer qty,
      Integer currentQty,
      String image) {
    this.id = id;
    this.day = day;
    this.foods = foods;
    this.date = date;
    this.isActive = isActive;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.type = type;
    this.image = image;
    this.qty = qty;
    this.currentQty = currentQty;
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

  public String getImage() {
    return image;
  }

  public Integer getQty() {
    return qty;
  }

  public Integer getCurrentQty() {
    return currentQty;
  }

}
