package Model.DTO.Menu;

import java.util.ArrayList;

public class UpdateMenuDto {
  private Integer id;
  private String day; // e.g., "LUNES", "MARTES", etc.
  private String type; // e.g., "DESAYUNO", "ALMUERZO", "CENA"
  private ArrayList<Integer> foodIds;
  private String date; // e.g., "2024-06-10"
  private String image;
  private ArrayList<MenuQtyDto> qtys;
  private Integer currentQty;

  public UpdateMenuDto(
      Integer id,
      String day,
      String type,
      ArrayList<Integer> foodIds,
      String date,
      ArrayList<MenuQtyDto> qtys,
      Integer currentQty) {
    this.id = id;
    this.day = day;
    this.foodIds = foodIds;
    this.date = date;
    this.type = type;
    this.image = null;
    this.qtys = qtys;
    this.currentQty = currentQty;
  }

  public UpdateMenuDto(
      Integer id,
      String day,
      String type,
      ArrayList<Integer> foodIds,
      String date,
      ArrayList<MenuQtyDto> qtys,
      Integer currentQty,
      String image) {
    this.id = id;
    this.day = day;
    this.foodIds = foodIds;
    this.date = date;
    this.type = type;
    this.image = image;
    this.qtys = qtys;
    this.currentQty = currentQty;
  }

  public UpdateMenuDto() {
  }

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

  public String getType() {
    return type;
  }

  public String getImage() {
    return image;
  }

  public ArrayList<MenuQtyDto> getQtys() {
    return qtys;
  }

  public Integer getCurrentQty() {
    return currentQty;
  }

}
