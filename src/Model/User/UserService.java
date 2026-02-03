package Model.User;

import Model.User.UserModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserService {
  private static final String FILE_USER = "src/Database/User/users.json";

  // Métodos para manejar usuarios (crear, leer, actualizar, eliminar)

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

  public UserModel create(UserModel user) {
    UserModel newUser = new UserModel(user);
    newUser.setCreatedAt(this.getCurrentDateTime());
    newUser.setId(this.getLastIndex());
    newUser.setUpdatedAt(this.getCurrentDateTime());
    newUser.setStatus(true);
    return this.save(newUser);
  }

  public UserModel update(UserModel user) {
    UserModel existingUser = this.getUserById(user.getId());
    if (existingUser != null) {
      existingUser.setFirstName(user.getFirstName());
      existingUser.setLastName(user.getLastName());
      existingUser.setEmail(user.getEmail());
      existingUser.setRole(user.getRole());
      existingUser.setType(user.getType());
      existingUser.setStatus(user.getStatus());
      existingUser.setUpdatedAt(this.getCurrentDateTime());
      return this.edit(existingUser);
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

  // metodos privados
  private UserModel mapUserModelToUser(UserModel userModel) {
    UserModel user = new UserModel();
    user.setId(userModel.getId());
    user.setFirstName(userModel.getFirstName());
    user.setLastName(userModel.getLastName());
    user.setEmail(userModel.getEmail());
    user.setRole(userModel.getRole());
    user.setType(userModel.getType());
    user.setStatus(userModel.getStatus());
    user.setCreatedAt(userModel.getCreatedAt());
    user.setUpdatedAt(userModel.getUpdatedAt());
    // user.setDeletedAt(userModel.getDeletedAt());
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
