package DTO.Wallet;

public class CreateWalletDto {
  private Double balance;
  private Integer userId;

  public CreateWalletDto(Double balance, Integer userId) {
    this.balance = balance;
    this.userId = userId;
  }

  // getters
  public Double getBalance() {
    return balance;
  }

  public Integer getUserId() {
    return userId;
  }
}
