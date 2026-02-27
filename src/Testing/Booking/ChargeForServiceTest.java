package Testing.Booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import Model.Booking.BookingService;
import DTO.Booking.BookingDto;
import Model.Wallet.WalletService;
import DTO.Wallet.WalletDto;
import Enums.BookingStatus;

public class ChargeForServiceTest {

    private BookingService bookingService;
    private WalletService walletService;
    private final String IMAGE_PATH = "assets/filemanager/test/prueba_cobro_no_borrar.png";
    private final Integer USER_ID = 12;
    private final Integer BOOKING_ID_INSUFFICIENT_FUNDS = 18;
    private final Integer BOOKING_ID_SUCCESS = 16;
    
    private Double initialBalance;

    @BeforeEach
    public void setUp() {
        bookingService = new BookingService();
        walletService = new WalletService();
        
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
