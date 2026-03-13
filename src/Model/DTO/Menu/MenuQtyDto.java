package Model.DTO.Menu;

public class MenuQtyDto {
  private Integer foodId;
  private Integer qty;
  private Integer currentQty;

  public MenuQtyDto() {}

  public MenuQtyDto(Integer foodId, Integer qty, Integer currentQty) {
    this.foodId = foodId;
    this.qty = qty;
    this.currentQty = currentQty;
  }

  // getters
  public Integer getFoodId() { return foodId; }
  public Integer getQty() { return qty; }
  public Integer getCurrentQty() { return currentQty; }

  // setters
  public void setFoodId(Integer foodId) { this.foodId = foodId; }
  public void setQty(Integer qty) { this.qty = qty; }
  public void setCurrentQty(Integer currentQty) { this.currentQty = currentQty; }
}