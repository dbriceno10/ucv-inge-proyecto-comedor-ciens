package Testing.Wallet;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Model.DTO.Wallet.WalletDto;
import Model.Wallet.WalletService;

public class RechargeWalletTest {
  private static final String FILE_PATH = "src/Model/TestDatabase/Wallet/wallets.json";
  private static final String MOVEMENTS_FILE_PATH = "src/Model/TestDatabase/Wallet/movements.json";
  private WalletService walletService = new WalletService(FILE_PATH, MOVEMENTS_FILE_PATH);

  @Test
  public void testRechargeWalletSuccess() {
    Integer userId = 11;
    Double amount = 50.0;
    String reference = "REF123456";
    String bankName = "Banco de Venezuela";

    // Obtener balance inicial
    WalletDto initialWallet = walletService.getWalletByUserId(userId);
    Double initialBalance = initialWallet.getBalance();

    // Realizar recarga
    WalletDto updatedWallet = walletService.rechargeWallet(userId, amount, reference, bankName);

    // Verificaciones
    assertNotNull(updatedWallet, "La wallet actualizada no debería ser nula");
    assertEquals(initialBalance + amount, updatedWallet.getBalance(),
        "El balance debería haberse incrementado en 50.0");

    // Restaurar el balance para no afectar otras pruebas
    // Se realiza un pago por el mismo monto para revertir la recarga
    walletService.makePayment(userId, amount);
  }

  @Test
  public void testRechargeWalletNegativeAmount() {
    Integer userId = 12;
    Double amount = -10.0;
    String reference = "REF789012";
    String bankName = "Banesco";

    // Verificar que se lanza la excepción esperada
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      walletService.rechargeWallet(userId, amount, reference, bankName);
    });

    assertEquals("Amount must be greater than zero.", exception.getMessage());
  }

  @Test
  public void testRechargeWalletUserNotFound() {
    Integer userId = 999; // Usuario inexistente
    Double amount = 100.0;
    String reference = "REF345678";
    String bankName = "Mercantil";

    // Verificar que se lanza la excepción esperada
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      walletService.rechargeWallet(userId, amount, reference, bankName);
    });

    assertEquals("Wallet not found for user id: " + userId, exception.getMessage());
  }
}
