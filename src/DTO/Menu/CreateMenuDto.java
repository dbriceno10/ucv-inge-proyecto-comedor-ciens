package DTO.Menu;

public class CreateMenuDto {
  private String day; // e.g., "LUNES", "MARTES", etc.
  private String type; // e.g., "DESAYUNO", "ALMUERZO", "CENA"
  private Integer[] foodIds;
  private String date; // e.g., "2024-06-10"
  private String image;

  public CreateMenuDto() {
  }

  public CreateMenuDto(String day, String type, Integer[] foodIds, String date) {
    this.day = day;
    this.foodIds = foodIds;
    this.date = date;
    this.type = type;
    this.image = null;
  }

  public CreateMenuDto(String day, String type, Integer[] foodIds, String date, String image) {
    this.day = day;
    this.foodIds = foodIds;
    this.date = date;
    this.type = type;
    this.image = image;
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

  public String getType() {
    return type;
  }

  public String getImage() {
    return image;
  }
}
