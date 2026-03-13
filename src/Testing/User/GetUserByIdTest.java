package Testing.User;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Model.User.UserService;
import Model.User.UserModel;
import Enums.UserRoles;

public class GetUserByIdTest {
  private static final String FILE_USER = "src/Model/TestDatabase/User/users.json";
  private static final String FILE_UCV_USERS = "src/Model/TestDatabase/User/ucvUsers.json";
  private static final String WALLET_FILE_PATH = "src/Model/TestDatabase/Wallet/wallets.json";
  private static final String MOVEMENTS_FILE_PATH = "src/Model/TestDatabase/Wallet/movements.json";

  private UserService userService = new UserService(FILE_USER, FILE_UCV_USERS, WALLET_FILE_PATH, MOVEMENTS_FILE_PATH);

  @Test
  public void testGetUserByIdSuccess() {

    Integer userId = 1; // Usuario que existe en users.json

    UserModel user = userService.getUserById(userId);

    assertNotNull(user, "El usuario no debería ser nulo");
    assertEquals(1, user.getId(), "El ID del usuario debería ser 1");
    assertEquals("Juan", user.getFirstName(), "El nombre debería ser Juan");
    assertEquals("Pérez", user.getLastName(), "El apellido debería ser Pérez");
    assertEquals("juan.perez@example.com", user.getEmail(), "El email debería coincidir");
    assertEquals(UserRoles.ADMIN, user.getRole(), "El rol debería ser " + UserRoles.ADMIN);
    assertTrue(user.getIsActive(), "El usuario debería estar activo");
  }

  @Test
  public void testGetUserByIdNotFound() {
    Integer userId = 999; // Usuario que no existe

    UserModel user = userService.getUserById(userId);

    assertNull(user, "El usuario debería ser nulo ya que no existe");
  }
}
