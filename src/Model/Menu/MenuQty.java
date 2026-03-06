package Model.Menu;

public class MenuQty {
  private Integer foodId;
  private Integer qty;
  private Integer currentQty;

  public MenuQty() {
  }

  public MenuQty(Integer foodId, Integer qty, Integer currentQty) {
    this.foodId = foodId;
    this.qty = qty;
    this.currentQty = currentQty;
  }

  // getters

  public Integer getFoodId() {
    return foodId;
  }

  public Integer getQty() {
    return qty;
  }

  public Integer getCurrentQty() {
    return currentQty;
  }

  // setters

  public void setCurrentQty(Integer currentQty) {
    this.currentQty = currentQty;
  }
}
