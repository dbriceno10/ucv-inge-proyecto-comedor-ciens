package Model.User;

// import Model.User.UserModel;
// import Model.User.BaseUserModel;
import Model.Common.CommonServices;
import Utils.Dates;
import DTO.Wallet.CreateWalletDto;
import Model.Wallet.WalletService;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
  private static final String FILE_USER = "src/Database/User/users.json";
  private static final String FILE_UCV_USERS = "src/Database/User/ucvUsers.json";
  private CommonServices commonServices = new CommonServices();
  private Dates datesUtil = new Dates();
  private WalletService walletService = new WalletService();

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

  public UserModel getUserById(Integer id) {
    List<UserModel> users = getAllUsers();
    UserModel foundUser = null;
    for (UserModel user : users) {
      if (user.getId().equals(id)) {
        foundUser = user;
        break;
      }
    }
    return foundUser;
  }

  public UserModel getUserByEmail(String email) {
    List<UserModel> users = getAllUsers();
    UserModel foundUser = null;
    for (UserModel user : users) {
      if (user.getEmail().equalsIgnoreCase(email)) {
        foundUser = user;
        break;
      }
    }
    return foundUser;
  }

  public UserModel create(UserModel user) {
    UserModel existing = this.getUserByEmail(user.getEmail());
    if (existing != null) {
      throw new IllegalArgumentException("Email already in use: " + user.getEmail());
    }
    String date = this.datesUtil.getCurrentDateTime();
    UserModel newUser = new UserModel(
        this.commonServices.getLastIndex(FILE_USER, UserModel.class),
        user.getFirstName(),
        user.getLastName(),
        user.getEmail(),
        user.getPassword(),
        user.getRole(),
        user.getType(),
        true,
        date,
        date,
        null,
        user.getDocumentId());
    UserModel createdUser = this.save(newUser);
    if (createdUser != null) {
      // Crear wallet para el nuevo usuario
      CreateWalletDto newWallet = new CreateWalletDto(
          0.0, createdUser.getId());
      walletService.create(newWallet);
    }
    return createdUser;
  }

  public UserModel update(UserModel user) {
    UserModel existingUser = this.getUserById(user.getId());
    if (existingUser != null) {
      return this.edit(user);
    }
    return null;
  }

  public Boolean delete(Integer id) {
    UserModel existingUser = this.getUserById(id);
    if (existingUser != null) {
      existingUser.setDeletedAt(this.datesUtil.getCurrentDateTime());
      this.edit(existingUser);
      return true;
    }
    return false;
  }

  public List<BaseUserModel> getAllUCVUsers() {
    return this.commonServices.getAllElements(FILE_UCV_USERS, BaseUserModel.class);
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

  private UserModel mapUserModelToUser(UserModel userModel) {
    UserModel user = new UserModel(
        userModel.getId(),
        userModel.getFirstName(),
        userModel.getLastName(),
        userModel.getEmail(),
        null,
        userModel.getRole(),
        userModel.getType(),
        userModel.getIsActive(),
        userModel.getCreatedAt(),
        userModel.getUpdatedAt(),
        userModel.getDeletedAt(),
        userModel.getDocumentId());
    return user;
  }

  private UserModel save(UserModel user) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_USER);
      List<UserModel> users = this.commonServices.getAllElements(FILE_USER, UserModel.class);
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
      List<UserModel> users = this.commonServices.getAllElements(FILE_USER, UserModel.class);
      // Find and update the user
      for (Integer i = 0; i < users.size(); i++) {
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
