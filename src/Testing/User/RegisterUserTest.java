package Testing.User;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Model.DTO.User.AuthUserDto;
import Model.User.AuthUserService;
import Model.User.UserService;
import Enums.UserRoles;

public class RegisterUserTest {

  private static final String FILE_USER = "src/Model/TestDatabase/User/users.json";
  private static final String FILE_UCV_USERS = "src/Model/TestDatabase/User/ucvUsers.json";
  private static final String WALLET_FILE_PATH = "src/Model/TestDatabase/Wallet/wallets.json";
  private static final String MOVEMENTS_FILE_PATH = "src/Model/TestDatabase/Wallet/movements.json";

  private AuthUserService authService = new AuthUserService(FILE_USER, FILE_UCV_USERS, WALLET_FILE_PATH,
      MOVEMENTS_FILE_PATH);
  private UserService userService = new UserService(FILE_USER, FILE_UCV_USERS, WALLET_FILE_PATH, MOVEMENTS_FILE_PATH);

  @Test
  public void testRegisterUser() {

    // Datos del usuario 25 (según ucvUsers.json)
    String email = "martin.soto@example.com";
    String firstName = "Martín";
    String lastName = "Soto";
    String password = "password123";
    String repeatPassword = "password123";
    Integer documentId = 78401234;

    // 1. Intentar registrar un estudiante como ADMINISTRADOR (Debe fallar)
    IllegalArgumentException adminException = assertThrows(IllegalArgumentException.class, () -> {
      authService.register(email, UserRoles.ADMIN, firstName, lastName, password, repeatPassword, documentId);
    });
    assertEquals("Only UCV ADMIN users can register as ADMIN role.", adminException.getMessage());

    // 2. Registrar correctamente como COMENSAL (Debe ser exitoso)
    AuthUserDto registeredUser = authService.register(email, UserRoles.COMMENSAL, firstName, lastName, password,
        repeatPassword, documentId);

    assertNotNull(registeredUser, "El usuario registrado no debería ser nulo");
    assertEquals(email, registeredUser.getEmail(), "El email debería coincidir");
    assertEquals(UserRoles.COMMENSAL, registeredUser.getRole(), "El rol debería ser COMENSAL");
    assertEquals(documentId, registeredUser.getDocumentId(), "El documento de identidad debería coincidir");

    // 3. Intentar registrarlo nuevamente (Debe fallar porque el email ya está en
    // uso)
    IllegalArgumentException duplicateException = assertThrows(IllegalArgumentException.class, () -> {
      authService.register(email, UserRoles.COMMENSAL, firstName, lastName, password, repeatPassword, documentId);
    });
    assertEquals("Email already in use: " + email, duplicateException.getMessage());

    // 4. Eliminar el usuario creado para limpiar la base de datos
    Boolean isDeleted = userService.delete(registeredUser.getId());
    assertTrue(isDeleted, "El usuario debería haberse eliminado correctamente");
  }
}
