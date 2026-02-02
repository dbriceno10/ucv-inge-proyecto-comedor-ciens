package Model.User;

import Model.User.UserModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
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
          users.add(mapUserModelToUser(userModel));
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

  private UserModel mapUserModelToUser(UserModel userModel) {
    UserModel user = new UserModel();
    user.setId(userModel.getId());
    user.setFirstName(userModel.getFirstName());
    user.setLastName(userModel.getLastName());
    user.setEmail(userModel.getEmail());
    user.setRole(userModel.getRole());
    user.setStatus(userModel.getStatus());
    user.setCreatedAt(userModel.getCreatedAt());
    user.setUpdatedAt(userModel.getUpdatedAt());
    user.setDeletedAt(userModel.getDeletedAt());
    return user;
  }
}
