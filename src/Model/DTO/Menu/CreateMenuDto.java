package Model.DTO.Menu;

import java.util.ArrayList;

public class CreateMenuDto {
  private String day; 
  private String type; 
  private ArrayList<Integer> foodIds; 
  private String date;
  private String image;
  private ArrayList<MenuQtyDto> qtys; 

  public CreateMenuDto() {}

  public CreateMenuDto(String day, String type, ArrayList<Integer> foodIds, ArrayList<MenuQtyDto> qtys, String date) {
    this.day = day;
    this.type = type;
    this.foodIds = foodIds;
    this.qtys = qtys;
    this.date = date;
    this.image = null;
  }

  public CreateMenuDto(String day, String type, ArrayList<Integer> foodIds, String date, ArrayList<MenuQtyDto> qtys, String image) {
    this.day = day;
    this.type = type;
    this.foodIds = foodIds;
    this.date = date;
    this.qtys = qtys;
    this.image = image;
  }

  public String getDay() { return day; }
  public String getType() { return type; }
  public ArrayList<Integer> getFoodIds() { return foodIds; }
  public String getDate() { return date; }
  public String getImage() { return image; }
  public ArrayList<MenuQtyDto> getQtys() { return qtys; }
}