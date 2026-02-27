package Testing.Booking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Model.Booking.BookingService;
import DTO.Booking.BookingDto;
import Enums.BookingStatus;

public class ChargeForServiceTest {

  private final String IMAGE_PATH = "assets/filemanager/test/prueba_cobro_no_borrar.png";
  private final Integer BOOKING_ID_INSUFFICIENT_FUNDS = 18;
  private final Integer BOOKING_ID_SUCCESS = 16;

  @Test
  public void testChargeForService_InsufficientFunds() {
    BookingService bookingService = new BookingService();

    assertThrows(IllegalArgumentException.class, () -> {
      bookingService.chargeForService(BOOKING_ID_INSUFFICIENT_FUNDS, IMAGE_PATH);
    });
  }

  @Test
  public void testChargeForService_Success() {
    BookingService bookingService = new BookingService();

    BookingDto result = bookingService.chargeForService(BOOKING_ID_SUCCESS, IMAGE_PATH);

    assertNotNull(result, "El resultado no debería ser nulo");
    assertEquals(BookingStatus.CONFIRMED, result.getStatus(),
        "El estado de la reserva debería ser CONFIRMADA");
  }
}