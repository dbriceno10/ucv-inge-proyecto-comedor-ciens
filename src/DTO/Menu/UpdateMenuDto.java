package DTO.Menu;

public class UpdateMenuDto {
  private Integer id;
  private String day; // e.g., "LUNES", "MARTES", etc.
  private Integer[] foodIds;
  private String date; // e.g., "2024-06-10"

  public UpdateMenuDto(
      Integer id,
      String day,
      Integer[] foodIds,
      String date) {
    this.id = id;
    this.day = day;
    this.foodIds = foodIds;
    this.date = date;
  }

  public UpdateMenuDto() {
  }

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
}
