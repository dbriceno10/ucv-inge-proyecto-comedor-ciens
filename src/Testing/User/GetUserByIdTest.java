package Testing.User;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Model.User.UserService;
import Model.User.UserModel;
import Enums.UserRoles;

public class GetUserByIdTest {

  @Test
  public void testGetUserByIdSuccess() {
    UserService userService = new UserService();
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
    UserService userService = new UserService();
    Integer userId = 999; // Usuario que no existe

    UserModel user = userService.getUserById(userId);

    assertNull(user, "El usuario debería ser nulo ya que no existe");
  }
}
