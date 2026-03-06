package Model.DTO.Menu;

import java.util.ArrayList;

public class CreateMenuDto {
  private String day; // e.g., "LUNES", "MARTES", etc.
  private String type; // e.g., "DESAYUNO", "ALMUERZO", "CENA"
  private ArrayList<Integer> foodIds;
  private String date; // e.g., "2024-06-10"
  private String image;
  private Integer qty;

  public CreateMenuDto() {
  }

  public CreateMenuDto(String day, String type, ArrayList<Integer> foodIds, Integer qty, String date) {
    this.day = day;
    this.foodIds = foodIds;
    this.date = date;
    this.type = type;
    this.image = null;
    this.qty = qty;
  }

  public CreateMenuDto(String day, String type, ArrayList<Integer> foodIds, String date, Integer qty, String image) {
    this.day = day;
    this.foodIds = foodIds;
    this.date = date;
    this.type = type;
    this.image = image;
    this.qty = qty;
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

  public String getType() {
    return type;
  }

  public String getImage() {
    return image;
  }

  public Integer getQty() {
    return qty;
  }
}
