package Testing.Wallet;

import Model.Common.CommonServices;
import Model.Wallet.WalletModel;
import Model.Wallet.WalletService;

import java.util.List;

import DTO.Wallet.CreateWalletDto;
import DTO.Wallet.WalletDto;
import DTO.Wallet.UpdateWalletDto;

public class Main {
  public static void main(String[] args) {
    WalletService walletService = new WalletService();
    CommonServices commonServices = new CommonServices();

    System.out.println("Starting wallet tests...");

    // Test 1: Obtener todas las wallets
    System.out.println("Test 1: Get all wallets");
    try {
      List<WalletDto> wallets = walletService.getAllWallets();
      if (wallets != null && !wallets.isEmpty()) {
        System.out.println("Total wallets: " + wallets.size());
        for (WalletDto wallet : wallets) {
          System.out.println("Wallet ID: " + wallet.getId() + ", Balance: " + wallet.getBalance());
        }
      } else {
        System.out.println("No wallets found.");
      }
    } catch (Exception e) {
      System.out.println("Failed to get wallets: " + e.getMessage());
    }

    // Test 2: Contar todas las wallets
    System.out.println("Test 2: Count all wallets");
    try {
      int totalWallets = commonServices.countAllElements("src/Database/Wallet/wallets.json", WalletModel.class);
      System.out.println("Total wallets (including deleted): " + totalWallets);
    } catch (Exception e) {
      System.out.println("Failed to count wallets: " + e.getMessage());
    }

    // Test 3: Buscar una wallet por ID
    System.out.println("Test 3: Get wallet by ID");
    try {
      WalletDto wallet = walletService.getWalletById(1);
      if (wallet != null) {
        System.out.println("Wallet found: ID: " + wallet.getId() + ", Balance: " + wallet.getBalance());
      } else {
        System.out.println("Wallet not found.");
      }
    } catch (Exception e) {
      System.out.println("Failed to get wallet by ID: " + e.getMessage());
    }

    // Test 4: Crear una nueva wallet
    System.out.println("Test 4: Create a new wallet");
    try {
      CreateWalletDto newWallet = new CreateWalletDto(

          500.0, 100000);
      WalletDto createdWallet = walletService.create(newWallet);
      System.out.println("Wallet created: ID: " + createdWallet.getId() + ", Balance: " + createdWallet.getBalance());
    } catch (Exception e) {
      System.out.println("Failed to create wallet: " + e.getMessage());
    }

    // Test 5: Actualizar una wallet
    System.out.println("Test 5: Update a wallet");
    try {
      UpdateWalletDto updatedWallet = new UpdateWalletDto(
          11,
          1000.0,
          true);
      WalletDto result = walletService.update(updatedWallet);
      System.out.println("Wallet updated: ID: " + result.getId() + ", New Balance: " + result.getBalance());
    } catch (Exception e) {
      System.out.println("Failed to update wallet: " + e.getMessage());
    }

    // Test 6: Eliminar una wallet
    System.out.println("Test 6: Delete a wallet");
    try {
      Boolean isDeleted = walletService.delete(1);
      if (isDeleted) {
        System.out.println("Wallet deleted successfully.");
      } else {
        System.out.println("Failed to delete wallet.");
      }
    } catch (Exception e) {
      System.out.println("Failed to delete wallet: " + e.getMessage());
    }
  }
}