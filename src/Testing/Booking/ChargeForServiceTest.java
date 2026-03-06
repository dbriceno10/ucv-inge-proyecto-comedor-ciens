package Testing.Booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import Model.Booking.BookingService;
import Model.DTO.Booking.BookingDto;
import Model.DTO.Wallet.WalletDto;
import Model.Wallet.WalletService;
import Enums.BookingStatus;

public class ChargeForServiceTest {
  private static final String FILE_PATH = "src/Model/TestDatabase/Booking/bookings.json";
  private static final String FILE_USER = "src/Model/TestDatabase/User/users.json";
  private static final String FILE_UCV_USERS = "src/Model/TestDatabase/User/ucvUsers.json";
  private static final String WALLET_FILE_PATH = "src/Model/TestDatabase/Wallet/wallets.json";
  private static final String MOVEMENTS_FILE_PATH = "src/Model/TestDatabase/Wallet/movements.json";
  private static final String MENU_FILE_PATH = "src/Model/TestDatabase/Menu/menus.json";
  private static final String INGREDIENT_FILE_PATH = "src/Model/TestDatabase/Ingredient/ingredients.json";
  private static final String FOOD_FILE_PATH = "src/Model/TestDatabase/Food/foods.json";
  private static final String CONFIG_FILE_PATH = "src/Model/TestDatabase/Config/config.json";

  private BookingService bookingService;
  private WalletService walletService;
  private final String IMAGE_PATH = "assets/filemanager/test/prueba_cobro_no_borrar.png";
  private final Integer USER_ID = 12;
  private final Integer BOOKING_ID_INSUFFICIENT_FUNDS = 18;
  private final Integer BOOKING_ID_SUCCESS = 16;

  private Double initialBalance;

  @BeforeEach
  public void setUp() {
    bookingService = new BookingService(FILE_PATH, FILE_USER, FILE_UCV_USERS, WALLET_FILE_PATH, MOVEMENTS_FILE_PATH, MENU_FILE_PATH, INGREDIENT_FILE_PATH, FOOD_FILE_PATH, CONFIG_FILE_PATH);
    walletService = new WalletService(WALLET_FILE_PATH, MOVEMENTS_FILE_PATH);

    // Guardar el balance inicial para restaurarlo después
    WalletDto wallet = walletService.getWalletByUserId(USER_ID);
    if (wallet != null) {
      initialBalance = wallet.getBalance();
    }
  }

  @AfterEach
  public void tearDown() {
    // Restaurar el balance del usuario
    if (initialBalance != null) {
      WalletDto wallet = walletService.getWalletByUserId(USER_ID);
      if (wallet != null && !wallet.getBalance().equals(initialBalance)) {
        Double difference = initialBalance - wallet.getBalance();
        if (difference > 0) {
          walletService.rechargeWallet(USER_ID, difference, "Restauración de prueba", "TestBank");
        } else if (difference < 0) {
          walletService.makePayment(USER_ID, -difference);
        }
      }
    }

    // Restaurar el estado del booking 16 a PENDIENTE
    try {
      bookingService.updateStatus(BOOKING_ID_SUCCESS, BookingStatus.PENDING);
    } catch (Exception e) {
      // Ignorar si falla
    }
  }

  @Test
  public void testChargeForService_InsufficientFunds() {
    // El booking 18 tiene un precio de 500, el usuario 12 tiene 200 de saldo
    // Dependiendo del porcentaje de subsidio, podría o no fallar.
    // Asumiendo que 500 * porcentaje > 200

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      bookingService.chargeForService(BOOKING_ID_INSUFFICIENT_FUNDS, IMAGE_PATH);
    });

    assertTrue(exception.getMessage().contains("Insufficient balance in wallet for user id: " + USER_ID));
  }

  @Test
  public void testChargeForService_Success() {
    // El booking 16 tiene un precio de 50, el usuario 12 tiene 200 de saldo

    WalletDto walletBefore = walletService.getWalletByUserId(USER_ID);
    assertNotNull(walletBefore);
    Double balanceBefore = walletBefore.getBalance();

    BookingDto result = bookingService.chargeForService(BOOKING_ID_SUCCESS, IMAGE_PATH);

    assertNotNull(result);
    assertEquals(BookingStatus.CONFIRMED, result.getStatus());

    WalletDto walletAfter = walletService.getWalletByUserId(USER_ID);
    assertNotNull(walletAfter);

    // El balance debe haber disminuido
    assertTrue(walletAfter.getBalance() < balanceBefore);
  }
}
