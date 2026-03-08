package Testing.User;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import Model.User.UserService;

public class GetDinerStatisticsTest {
  private static final String FILE_USER = "src/Model/TestDatabase/User/users.json";
  private static final String FILE_UCV_USERS = "src/Model/TestDatabase/User/ucvUsers.json";
  private static final String WALLET_FILE_PATH = "src/Model/TestDatabase/Wallet/wallets.json";
  private static final String MOVEMENTS_FILE_PATH = "src/Model/TestDatabase/Wallet/movements.json";

  private UserService userService = new UserService(FILE_USER, FILE_UCV_USERS, WALLET_FILE_PATH, MOVEMENTS_FILE_PATH);

  @Test
  public void testGetDinerStatistics() {
    ArrayList<Integer> attendeeIds = new ArrayList<>();
    // ID 2 -> ESTUDIANTE
    attendeeIds.add(2);
    // ID 5 -> OBRERO
    attendeeIds.add(5);
    // ID 8 -> PROFESOR
    attendeeIds.add(8);
    // ID 11 -> ESTUDIANTE
    attendeeIds.add(11);
    
    ArrayList<String> stats = userService.getDinerStatistics(attendeeIds);
    
    assertNotNull(stats, "La lista de estadísticas no debería ser nula");
    assertEquals(6, stats.size(), "La lista debería contener 6 elementos (uno por cada tipo)");
    
    assertTrue(stats.contains("ESTUDIANTE: 2"), "Debería haber 2 estudiantes");
    assertTrue(stats.contains("OBRERO: 1"), "Debería haber 1 obrero");
    assertTrue(stats.contains("PROFESOR: 1"), "Debería haber 1 profesor");
    assertTrue(stats.contains("BECARIO: 0"), "Debería haber 0 becarios");
    assertTrue(stats.contains("EXONERADO: 0"), "Debería haber 0 exonerados");
    assertTrue(stats.contains("ADMINISTRADOR: 0"), "Debería haber 0 administradores");
  }

  @Test
  public void testGetDinerStatisticsEmptyList() {
    ArrayList<Integer> emptyList = new ArrayList<>();
    ArrayList<String> stats = userService.getDinerStatistics(emptyList);
    
    assertEquals(6, stats.size());
    assertTrue(stats.contains("ESTUDIANTE: 0"));
    assertTrue(stats.contains("OBRERO: 0"));
    assertTrue(stats.contains("PROFESOR: 0"));
    assertTrue(stats.contains("BECARIO: 0"));
    assertTrue(stats.contains("EXONERADO: 0"));
    assertTrue(stats.contains("ADMINISTRADOR: 0"));
  }
}
