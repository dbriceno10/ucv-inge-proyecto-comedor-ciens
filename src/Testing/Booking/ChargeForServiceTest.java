package Testing.Booking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Model.Booking.BookingService;
import DTO.Booking.BookingDto;
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

  private final String IMAGE_PATH = "assets/filemanager/test/prueba_cobro_no_borrar.png";
  private final Integer BOOKING_ID_INSUFFICIENT_FUNDS = 18;
  private final Integer BOOKING_ID_SUCCESS = 16;
  private BookingService bookingService = new BookingService(FILE_PATH, FILE_USER, FILE_UCV_USERS, WALLET_FILE_PATH, MOVEMENTS_FILE_PATH, MENU_FILE_PATH, INGREDIENT_FILE_PATH, FOOD_FILE_PATH, CONFIG_FILE_PATH);

  @Test
  public void testChargeForService_InsufficientFunds() {
    assertThrows(IllegalArgumentException.class, () -> {
      bookingService.chargeForService(BOOKING_ID_INSUFFICIENT_FUNDS, IMAGE_PATH);
    });
  }

  @Test
  public void testChargeForService_Success() {
    BookingDto result = bookingService.chargeForService(BOOKING_ID_SUCCESS, IMAGE_PATH);

    assertNotNull(result, "El resultado no debería ser nulo");
    assertEquals(BookingStatus.CONFIRMED, result.getStatus(),
        "El estado de la reserva debería ser CONFIRMADA");
  }
}