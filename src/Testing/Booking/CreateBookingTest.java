package Testing.Booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import Model.Booking.BookingService;
import Model.DTO.Booking.BookingDto;
import Model.DTO.Booking.CreateBookingDto;
import Enums.Days;
import Enums.MenuTypes;

public class CreateBookingTest {
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
  private final Integer USER_ID = 12;
  private Integer createdBookingId;

  @BeforeEach
  public void setUp() {
    bookingService = new BookingService(FILE_PATH, FILE_USER, FILE_UCV_USERS, WALLET_FILE_PATH, MOVEMENTS_FILE_PATH, MENU_FILE_PATH, INGREDIENT_FILE_PATH, FOOD_FILE_PATH, CONFIG_FILE_PATH);
  }

  @AfterEach
  public void tearDown() {
    // Limpiar la reserva creada para no afectar futuras pruebas
    if (createdBookingId != null) {
      try {
        bookingService.delete(createdBookingId);
      } catch (Exception e) {
        // Ignorar si falla
      }
    }
  }

  @Test
  public void testCreateBooking_Success() {
    // Crear una reserva nueva para el usuario 12
    // Usaremos el menú 1 (DESAYUNO, MARTES) que contiene los foods [4, 5, 6]
    // Fecha: 2026-03-03 (un martes futuro para no chocar con existentes)

    CreateBookingDto createDto = new CreateBookingDto(
        USER_ID,
        1, // menuId
        4, // foodId (Sopa de Pollo, está en el menú 1)
        MenuTypes.BREACKFAST,
        "2026-03-03",
        Days.TUESDAY);

    BookingDto result = bookingService.create(createDto);

    assertNotNull(result, "El resultado no debería ser nulo");
    assertEquals(USER_ID, result.getUserId(), "El ID del usuario debe coincidir");
    assertEquals(1, result.getMenuId(), "El ID del menú debe coincidir");
    assertEquals(4, result.getFoodId(), "El ID de la comida debe coincidir");
    assertEquals(MenuTypes.BREACKFAST, result.getShift(), "El turno debe coincidir");
    assertEquals("2026-03-03", result.getDate(), "La fecha debe coincidir");
    assertEquals(Days.TUESDAY, result.getDay(), "El día debe coincidir");

    // Guardar el ID para limpiarlo después
    createdBookingId = result.getId();
  }

  @Test
  public void testCreateBooking_DuplicateShiftAndDay() {
    // Intentar crear una reserva que repita el día y turno de la reserva 17
    // Reserva 17: userId=12, menuId=5, foodId=7, shift=ALMUERZO, date=2026-02-25,
    // day=VIERNES

    CreateBookingDto createDto = new CreateBookingDto(
        USER_ID,
        5, // menuId
        7, // foodId (Arroz con Pollo, está en el menú 5)
        MenuTypes.LUNCH,
        "2026-02-25",
        Days.FRIDAY);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      bookingService.create(createDto);
    });

    assertTrue(exception.getMessage().contains("User already has a booking for the same date and shift."),
        "Debe lanzar excepción por reserva duplicada en el mismo turno y día");
  }
}
