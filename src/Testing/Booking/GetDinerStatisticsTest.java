package Testing.Booking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import Model.Booking.BookingService;

public class GetDinerStatisticsTest {
  private static final String BOOKING_FILE_PATH = "src/Model/TestDatabase/Booking/bookings.json";
  private static final String FILE_USER = "src/Model/TestDatabase/User/users.json";
  private static final String FILE_UCV_USERS = "src/Model/TestDatabase/User/ucvUsers.json";
  private static final String WALLET_FILE_PATH = "src/Model/TestDatabase/Wallet/wallets.json";
  private static final String MOVEMENTS_FILE_PATH = "src/Model/TestDatabase/Wallet/movements.json";
  private static final String MENU_FILE_PATH = "src/Model/TestDatabase/Menu/menus.json";
  private static final String CURRENT_INGREDIENTS_FILE_PATH = "src/Model/TestDatabase/Ingredient/ingredients.json";
  private static final String FOOD_FILE_PATH = "src/Model/TestDatabase/Food/foods.json";
  private static final String CONFIG_FILE_PATH = "src/Model/TestDatabase/Config/config.json";

  private BookingService bookingService = new BookingService(
      BOOKING_FILE_PATH,
      FILE_USER,
      FILE_UCV_USERS,
      WALLET_FILE_PATH,
      MOVEMENTS_FILE_PATH,
      MENU_FILE_PATH,
      CURRENT_INGREDIENTS_FILE_PATH,
      FOOD_FILE_PATH,
      CONFIG_FILE_PATH);

  @Test
  public void testGetDinerStatistics() {
    ArrayList<String> stats = bookingService.getDinerStatistics("ALMUERZO");

    assertNotNull(stats, "La lista de estadísticas no debería ser nula");
    assertEquals(6, stats.size(), "La lista debería contener 6 elementos (uno por cada tipo)");

    // Since we patched the mock data earlier...
    assertTrue(stats.size() == 6);
  }
}
