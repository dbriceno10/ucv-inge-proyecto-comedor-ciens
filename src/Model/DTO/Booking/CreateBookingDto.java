package Model.DTO.Booking;

public class CreateBookingDto {
  private Integer userId;
  private Integer menuId;
  private Integer foodId;
  private String shift; // turno DESAYUNO, ALMUERZO, CENA
  private String date; // fecha del turno
  private String day;

  public CreateBookingDto() {
  }

  public CreateBookingDto(Integer userId, Integer menuId, Integer foodId, String shift, String date, String day) {
    this.userId = userId;
    this.menuId = menuId;
    this.foodId = foodId;
    this.shift = shift;
    this.date = date;
    this.day = day;
  }

  // getters

  public Integer getUserId() {
    return userId;
  }

  public Integer getMenuId() {
    return menuId;
  }

  public Integer getFoodId() {
    return foodId;
  }

  public String getShift() {
    return shift;
  }

  public String getDate() {
    return date;
  }

  public String getDay() {
    return day;
  }
}
