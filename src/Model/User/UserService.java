package Model.User;

import Model.User.UserModel;
import Model.User.BaseUserModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserService {
  private static final String FILE_USER = "src/Database/User/users.json";
  private static final String FILE_UCV_USERS = "src/Database/User/ucvUsers.json";

  // Métodos para manejar usuarios (crear, leer, actualizar, eliminar)
  // TODO: manejar excepciones, se debe manejar que el email sea unico en crear y
  // editar

  public List<UserModel> getAllUsers() {
    List<UserModel> users = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_USER);
      if (file.exists()) {
        List<UserModel> userModels = mapper.readValue(file,
            mapper.getTypeFactory().constructCollectionType(List.class, UserModel.class));
        for (UserModel userModel : userModels) {
          if (userModel.getDeletedAt() == null) { // Filtrar usuarios con deletedAt como null
            users.add(mapUserModelToUser(userModel));
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return users;
  }

  public UserModel getUserById(Number id) {
    List<UserModel> users = getAllUsers();
    for (UserModel user : users) {
      if (user.getId().equals(id)) {
        return user;
      }
    }
    return null;
  }

  public UserModel getUserByEmail(String email) {
    List<UserModel> users = getAllUsers();
    for (UserModel user : users) {
      if (user.getEmail().equalsIgnoreCase(email)) {
        return user;
      }
    }
    return null;
  }

  public UserModel create(UserModel user) {
    UserModel existing = this.getUserByEmail(user.getEmail());
    if (existing != null) {
      throw new IllegalArgumentException("Email already in use: " + user.getEmail());
    }
    UserModel newUser = new UserModel(
        this.getLastIndex(),
        user.getFirstName(),
        user.getLastName(),
        user.getEmail(),
        user.getPassword(),
        user.getRole(),
        user.getType(),
        true,
        this.getCurrentDateTime(),
        this.getCurrentDateTime(),
        null);
    return this.save(newUser);
  }

  public UserModel update(UserModel user) {
    UserModel existingUser = this.getUserById(user.getId());
    if (existingUser != null) {
      return this.edit(user);
    }
    return null;
  }

  public boolean delete(Number id) {
    UserModel existingUser = this.getUserById(id);
    if (existingUser != null) {
      existingUser.setDeletedAt(this.getCurrentDateTime());
      this.edit(existingUser);
      return true;
    }
    return false;
  }

  public List<BaseUserModel> getAllUCVUsers() {
    List<BaseUserModel> users = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_UCV_USERS);
      if (file.exists()) {
        users = mapper.readValue(file,
            mapper.getTypeFactory().constructCollectionType(List.class, BaseUserModel.class));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return users;
  }

  public BaseUserModel getUCVUserByEmail(String email) {
    System.out.println("Searching UCV user by email: " + email);
    List<BaseUserModel> users = getAllUCVUsers();
    BaseUserModel foundUser = null;
    for (BaseUserModel user : users) {
      if (user.getEmail().equalsIgnoreCase(email)) {
        foundUser = user;
        break;
      }
    }
    return foundUser;
  }

  // metodos privados

  private int getLastIndex() {
    List<UserModel> users = getAllUsers();
    int lastIndex = 0;

    for (UserModel user : users) {
      if (user.getId() instanceof Integer) {
        lastIndex = Math.max(lastIndex, (Integer) user.getId());
      }
    }

    return lastIndex + 1; // Retorna el siguiente ID disponible
  }

  private UserModel mapUserModelToUser(UserModel userModel) {
    UserModel user = new UserModel(
        userModel.getId(),
        userModel.getFirstName(),
        userModel.getLastName(),
        userModel.getEmail(),
        null,
        userModel.getRole(),
        userModel.getType(),
        userModel.getStatus(),
        userModel.getCreatedAt(),
        userModel.getUpdatedAt(),
        userModel.getDeletedAt());
    return user;
  }

  private String getCurrentDateTime() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    return ZonedDateTime.now(java.time.ZoneOffset.UTC).format(formatter);
  }

  private UserModel save(UserModel user) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_USER);
      List<UserModel> users = new ArrayList<>();
      // If the file exists, read the existing users
      if (file.exists()) {

        users = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, UserModel.class));
      }
      users.add(user);
      // Write the updated list back to the file
      mapper.writeValue(file, users);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    return user;
  }

  private UserModel edit(UserModel user) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_USER);
      List<UserModel> users = new ArrayList<>();
      // If the file exists, read the existing users
      if (file.exists()) {
        users = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, UserModel.class));
      }
      // Find and update the user
      for (int i = 0; i < users.size(); i++) {
        if (users.get(i).getId().equals(user.getId())) {
          users.set(i, user);
          break;
        }
      }
      // Write the updated list back to the file
      mapper.writeValue(file, users);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    return user;
  }
}
