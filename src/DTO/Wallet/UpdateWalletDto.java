package DTO.Wallet;

public class UpdateWalletDto {
  private Integer id;
  private Double balance;
  private Boolean isActive;

  public UpdateWalletDto(Integer id, Double balance, Boolean isActive) {
    this.id = id;
    this.balance = balance;
    this.isActive = isActive;
  }

  // getters
  public Integer getId() {
    return id;
  }

  public Double getBalance() {
    return balance;
  }

  public Boolean getIsActive() {
    return isActive;
  }

}
