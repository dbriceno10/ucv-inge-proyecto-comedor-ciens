package Testing.Auth;

import Model.User.AuthUserModel;
import Model.User.AuthUserService;
import Enums.UserRoles;

public class Main {
  public static void main(String[] args) {
    AuthUserService authUserService = new AuthUserService();
    System.out.println("Starting registration test 1...");
    try {
      authUserService.register("helloword@email.com", UserRoles.ADMIN, "John", "Doe", "password123", "password123",
          1234567);
    } catch (IllegalArgumentException e) {
      System.out.println("Registration failed: " + e.getMessage());
    }

    System.out.println("Starting registration test 2...");
    try {
      authUserService.register("monica.pena@example.com", UserRoles.COMMENSAL, "John", "Doe", "password123",
          "password123", 2345678);
    } catch (IllegalArgumentException e) {
      System.out.println("Registration failed: " + e.getMessage());
    }

    System.out.println("Starting login test 1...");
    try {
      authUserService.login(null, null);
    } catch (Exception e) {
      System.out.println("Login failed: " + e.getMessage());
    }
    System.out.println("Starting login test 2...");
    try {
      authUserService.login("juan.perez@examploe.com", "password123");
    } catch (Exception e) {
      System.out.println("Login failed: " + e.getMessage());
    }

    System.out.println("Starting login test 3...");
    try {
      authUserService.login("juan.perez@example.com", "password1234");
    } catch (Exception e) {
      System.out.println("Login failed: " + e.getMessage());
    }

    System.out.println("Starting login test 4...");
    try {
      AuthUserModel logged = authUserService.login("juan.perez@example.com", "password123");
      if (logged != null) {
        System.out.println("Login successful for user: " + logged.getEmail());
      } else {
        System.out.println("Login returned null user.");
      }

    } catch (Exception e) {
      System.out.println("Login failed: " + e.getMessage());
    }

  }
}
