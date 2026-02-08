package Model.Wallet;

public class WalletModel {
  private Integer id;
  private Double balance;
  private Integer userId;
  private Boolean isActive;
  private String createdAt;
  private String updatedAt;
  private String deletedAt;

  public WalletModel() {
  }

  public WalletModel(
      Integer id,
      Double balance,
      Integer userId,
      Boolean isActive,
      String createdAt,
      String updatedAt,
      String deletedAt) {
    this.id = id;
    this.balance = balance;
    this.userId = userId;
    this.isActive = isActive;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
  }

  // getters
  public Integer getId() {
    return id;
  }

  public Double getBalance() {
    return balance;
  }

  public Integer getUserId() {
    return userId;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public String getDeletedAt() {
    return deletedAt;
  }

  // setters
  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setDeletedAt(String deletedAt) {
    this.deletedAt = deletedAt;
  }
}
