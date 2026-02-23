package Model.Booking;

public class BookingModel {
  private Integer id;
  private Integer userId;
  private Integer menuId;
  private Integer foodId;
  private String shift; // turno DESAYUNO, ALMUERZO, CENA
  private String date; // fecha del turno
  private String day;
  private Integer userDocumentId;
  private Double price;
  private String createdAt;
  private String updatedAt;
  private String status; // estado de la reserva (PENDIENTE, CONFIRMADA, CANCELADA)
  private String deletedAt = null;

  public BookingModel() {
  }

  public BookingModel(Integer id, Integer userId, Integer menuId, Integer foodId, String shift, String date,
      String day, Integer userDocumentId, Double price, String createdAt, String updatedAt, String status,
      String deletedAt) {
    this.id = id;
    this.userId = userId;
    this.menuId = menuId;
    this.foodId = foodId;
    this.shift = shift;
    this.date = date;
    this.day = day;
    this.userDocumentId = userDocumentId;
    this.price = price;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.status = status;
    this.deletedAt = deletedAt;
  }

  public BookingModel(Integer id, Integer userId, Integer menuId, Integer foodId, String shift, String date,
      String day, Integer userDocumentId, Double price, String createdAt, String updatedAt, String status) {
    this.id = id;
    this.userId = userId;
    this.menuId = menuId;
    this.foodId = foodId;
    this.shift = shift;
    this.date = date;
    this.day = day;
    this.userDocumentId = userDocumentId;
    this.price = price;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.status = status;
    this.deletedAt = null;
  }

  // getters

  public Integer getId() {
    return id;
  }

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

  public Integer getUserDocumentId() {
    return userDocumentId;
  }

  public Double getPrice() {
    return price;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public String getStatus() {
    return status;
  }

  public String getDeletedAt() {
    return deletedAt;
  }

  // setters
  public void setDeletedAt(String deletedAt) {
    this.deletedAt = deletedAt;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }
}
