package Model.User;

// import Model.User.UserModel;
// import Model.User.UserService;
// import Model.User.AuthUserModel;
// import Model.User.BaseUserModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import Enums.UserRoles;
import Enums.UserTypes;

public class AuthUserService {
  UserService userService = new UserService();
  UserRoles userRoles = new UserRoles();
  UserTypes userTypes = new UserTypes();
  private static final String FILE_USER = "src/Database/User/users.json";

  public AuthUserModel register(String email, String role, String firstName, String lastName, String password,
      String repeatPassword) {

    if (email == null || role == null || firstName == null || lastName == null || password == null
        || repeatPassword == null ||
        email.isEmpty() || role.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty()
        || repeatPassword.isEmpty()) {
      throw new IllegalArgumentException("All fields are required for registration.");
    }

    BaseUserModel existingUCV = userService.getUCVUserByEmail(email);

    if (existingUCV == null) { // No se encontró el usuario en la base de datos UCV
      throw new IllegalArgumentException("User not found with the provided email: " + email);
    }

    String type = existingUCV.getType();

    if (role == UserRoles.ADMIN && type != UserTypes.ADMIN) {
      throw new IllegalArgumentException("Only UCV ADMIN users can register as ADMIN role.");
    }

    if (!password.equals(repeatPassword)) {
      throw new IllegalArgumentException("Passwords do not match.");
    }

    // Crear nuevo usuario
    UserModel newUser = new UserModel(
        firstName, lastName, email, password, role, type);

    newUser = userService.create(newUser);

    return new AuthUserModel(newUser.getId(), newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(),
        newUser.getRole(), newUser.getType(), "token", newUser.getIsActive());
  }

  public AuthUserModel login(String email, String password) {
    if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
      throw new IllegalArgumentException("Email and password are required for login.");
    }
    UserModel user = this.getUserByEmail(email);
    if (user == null || !user.getPassword().equals(password)) {
      throw new IllegalArgumentException("Invalid email or password.");
    }
    return new AuthUserModel(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
        user.getRole(), user.getType(), "token", user.getIsActive());
  }

  private List<UserModel> getAllUsers() {
    List<UserModel> users = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_USER);
      if (file.exists()) {
        List<UserModel> userModels = mapper.readValue(file,
            mapper.getTypeFactory().constructCollectionType(List.class, UserModel.class));
        for (UserModel userModel : userModels) {
          if (userModel.getDeletedAt() == null) { // Filtrar usuarios con deletedAt como null
            users.add(userModel);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return users;
  }

  private UserModel getUserByEmail(String email) {
    List<UserModel> users = this.getAllUsers();
    for (UserModel user : users) {
      if (user.getEmail().equalsIgnoreCase(email)) {
        return user;
      }
    }
    return null;
  }

}
