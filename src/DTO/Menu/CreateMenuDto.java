package DTO.Menu;

public class CreateMenuDto {
  private String day; // e.g., "LUNES", "MARTES", etc.
  private Integer[] foodIds;
  private String date; // e.g., "2024-06-10"

  CreateMenuDto() {
  }

  CreateMenuDto(String day, Integer[] foodIds, String date) {
    this.day = day;
    this.foodIds = foodIds;
    this.date = date;
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
}
