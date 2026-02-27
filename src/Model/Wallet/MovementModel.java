package Model.Wallet;

public class MovementModel {
  private Integer id;
  private Integer walletId;
  private String type;
  private Double amount;
  private String date;
  private String description = null;
  private String reference = null;
  private String bankName = null;

  public MovementModel() {
  }

  public MovementModel(Integer id, Integer walletId, String type, Double amount, String date, String description,
      String reference, String bankName) {
    this.id = id;
    this.walletId = walletId;
    this.type = type;
    this.amount = amount;
    this.date = date;
    this.description = description;
    this.reference = reference;
    this.bankName = bankName;
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

  public String getReference() {
    return reference;
  }

  public String getBankName() {
    return bankName;
  }

}
