package Testing.Wallet;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Model.Wallet.WalletService;
import DTO.Wallet.WalletDto;

public class GetWalletByUserIdTest {

  private static final String FILE_PATH = "src/Model/TestDatabase/Wallet/wallets.json";
  private static final String MOVEMENTS_FILE_PATH = "src/Model/TestDatabase/Wallet/movements.json";
  private WalletService walletService = new WalletService(FILE_PATH, MOVEMENTS_FILE_PATH);

  @Test
  public void testGetWalletByUserId11() {
    Integer userId = 11;

    WalletDto wallet = walletService.getWalletByUserId(userId);

    assertNotNull(wallet, "La wallet no debería ser nula");
    assertEquals(11, wallet.getId(), "El ID de la wallet debería ser 11");
    assertEquals(userId, wallet.getUserId(), "El ID del usuario debería ser 11");
    assertEquals(0.0, wallet.getBalance(), "El balance debería ser 0.0");
    assertTrue(wallet.getIsActive(), "La wallet debería estar activa");
  }

  @Test
  public void testGetWalletByUserId12() {
    Integer userId = 12;

    WalletDto wallet = walletService.getWalletByUserId(userId);

    assertNotNull(wallet, "La wallet no debería ser nula");
    assertEquals(12, wallet.getId(), "El ID de la wallet debería ser 12");
    assertEquals(userId, wallet.getUserId(), "El ID del usuario debería ser 12");
    assertEquals(200.0, wallet.getBalance(), "El balance debería ser 200.0");
    assertTrue(wallet.getIsActive(), "La wallet debería estar activa");
  }
}
