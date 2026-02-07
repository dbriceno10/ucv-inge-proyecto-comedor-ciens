package DTO.Menu;

import java.util.ArrayList;
import DTO.Food.FoodDto;

public class MenuDto {
  private Integer id;
  private String day; // e.g., "LUNES", "MARTES", etc.
  private ArrayList<FoodDto> foods;
  private String date; // e.g., "2024-06-10"
  private Boolean isActive;
  private String createdAt = null;
  private String updatedAt = null;

  public MenuDto(
      Integer id,
      String day,
      ArrayList<FoodDto> foods,
      String date,
      Boolean isActive,
      String createdAt,
      String updatedAt) {
    this.id = id;
    this.day = day;
    this.foods = foods;
    this.date = date;
    this.isActive = isActive;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
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

}
