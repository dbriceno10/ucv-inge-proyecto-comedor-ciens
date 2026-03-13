package Model.DTO.Menu;

public class MenuQtyDto {
  private Integer foodId;
  private Integer qty;
  private Integer currentQty;

  public MenuQtyDto() {
  }

  public MenuQtyDto(Integer foodId, Integer qty, Integer currentQty) {
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

}
