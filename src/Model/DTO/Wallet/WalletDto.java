package Model.DTO.Wallet;

import java.util.ArrayList;

public class WalletDto {
  private Integer id;
  private Double balance;
  private Integer userId;
  private Boolean isActive;
  private String createdAt;
  private String updatedAt;
  private ArrayList<MovementDto> movements;

  public WalletDto() {
  }

  public WalletDto(
      Integer id,
      Double balance,
      Integer userId,
      Boolean isActive,
      String createdAt,
      String updatedAt,
      ArrayList<MovementDto> movements) {
    this.id = id;
    this.balance = balance;
    this.userId = userId;
    this.isActive = isActive;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.movements = movements;
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

  public ArrayList<MovementDto> getMovements() {
    return movements;
  }
}
