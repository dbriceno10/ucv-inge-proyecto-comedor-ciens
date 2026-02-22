package DTO.Wallet;

public class MovementDto {
  private Integer id;
  private Integer walletId;
  private String type;
  private Double amount;
  private String date;
  private String description = null;

  public MovementDto() {
  }

  public MovementDto(Integer id, Integer walletId, String type, Double amount, String date, String description) {
    this.id = id;
    this.walletId = walletId;
    this.type = type;
    this.amount = amount;
    this.date = date;
    this.description = description;
  }

  // getters
  public Integer getId() {
    return id;
  }

  public Integer getWalletId() {
    return walletId;
  }

  public String getType() {
    return type;
  }

  public Double getAmount() {
    return amount;
  }

  public String getDate() {
    return date;
  }

  public String getDescription() {
    return description;
  }

}
