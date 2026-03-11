package Model.User;

// import Model.User.UserModel;
// import Model.User.BaseUserModel;
import Model.Common.CommonServices;
import Model.DTO.Wallet.CreateWalletDto;
import Utils.Dates;
import Model.Wallet.WalletService;

import com.fasterxml.jackson.databind.ObjectMapper;

import Enums.UserTypes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
  private String FILE_USER = "src/Model/Database/User/users.json";
  private String FILE_UCV_USERS = "src/Model/Database/User/ucvUsers.json";
  private CommonServices commonServices = new CommonServices();
  private WalletService walletService;
  private Dates datesUtil = new Dates();

  public UserService() {
    this.walletService = new WalletService();
  }

  public UserService(String userFilePath, String ucvUserFilePath, String walletFilePath, String movementsFilePath) {
    this.FILE_USER = userFilePath;
    this.FILE_UCV_USERS = ucvUserFilePath;
    this.walletService = new WalletService(walletFilePath, movementsFilePath);
  }

  // Métodos para manejar usuarios (crear, leer, actualizar, eliminar)
  // TODO: manejar excepciones, se debe manejar que el email sea unico en crear y
  // editar

  public ArrayList<UserModel> getAllUsers() {
    ArrayList<UserModel> users = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_USER);
      if (file.exists()) {
        ArrayList<UserModel> userModels = mapper.readValue(file,
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
    ArrayList<UserModel> users = getAllUsers();
    UserModel foundUser = null;
    for (UserModel user : users) {
      if (user.getId().equals(id)) {
        foundUser = user;
        break;
      }
    }
    return foundUser;
  }

  public Integer getUserIdByDocument(Integer documentId) {
    System.out.println("Buscando ID interno para la cédula: " + documentId);
    ArrayList<UserModel> users = getAllUsers();
    
    for (UserModel user : users) {
      if (user.getDocumentId().equals(documentId)) {
        return user.getId();
      }
    }
    return null;
  }

  public UserModel getUserByEmail(String email) {
    ArrayList<UserModel> users = getAllUsers();
    UserModel foundUser = null;
    for (UserModel user : users) {
      if (user.getEmail().equalsIgnoreCase(email)) {
        foundUser = user;
        break;
      }
    }
    return foundUser;
  }

  public ArrayList<UserModel> getAllStudents() {
    ArrayList<UserModel> users = getAllUsers();
    ArrayList<UserModel> students = new ArrayList<>();
    for (UserModel user : users) {
      if (user.getIsStudent() != null && user.getIsStudent()) {
        students.add(user);
      }
    }
    return students;
  }

  public UserModel changeTypeStudent(Integer userId, String newType) {
    UserModel user = this.getUserById(userId);
    if (user == null) {
      throw new IllegalArgumentException("User not found with id: " + userId);
    }
   if (UserTypes.STUDENT.equals(newType) || UserTypes.SCHOLAR.equals(newType) || UserTypes.EXONERATED.equals(newType)) {
      user.setType(newType);
    } else {
      throw new IllegalArgumentException("Invalid type. Type must be STUDENT, SCHOLAR or EXONERATED.");
    }
    return this.update(user);
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
      return this.mapUserModelToUser(createdUser);
    }
    return null;
  }

  public UserModel update(UserModel user) {
    UserModel existingUser = this.getById(user.getId());
    if (existingUser == null) {
      throw new IllegalArgumentException("User not found with id: " + user.getId());
    }
    UserModel editedUser = this.edit(user);
    if (editedUser != null) {
      return this.mapUserModelToUser(editedUser);
    }
    return null;
  }

  public Boolean delete(Integer id) {
    UserModel existingUser = this.getById(id);
    if (existingUser != null) {
      existingUser.setDeletedAt(this.datesUtil.getCurrentDateTime());
      this.edit(existingUser);
      return true;
    }
    return false;
  }

  public ArrayList<BaseUserModel> getAllUCVUsers() {
    return this.commonServices.getAllElements(FILE_UCV_USERS, BaseUserModel.class);
  }

  public BaseUserModel getUCVUserByEmail(String email) {
    ArrayList<BaseUserModel> users = getAllUCVUsers();
    BaseUserModel foundUser = null;
    for (BaseUserModel user : users) {
      if (user.getEmail().equalsIgnoreCase(email)) {
        foundUser = user;
        break;
      }
    }
    return foundUser;
  }

  public BaseUserModel getUCVUserByDocumentId(Integer documentId) {
    ArrayList<BaseUserModel> users = getAllUCVUsers();
    BaseUserModel foundUser = null;
    for (BaseUserModel user : users) {
      if (user.getDocumentId().equals(documentId)) {
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
      ArrayList<UserModel> users = this.commonServices.getAllElements(FILE_USER, UserModel.class);
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
      ArrayList<UserModel> users = this.commonServices.getAllElements(FILE_USER, UserModel.class);
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

  private ArrayList<UserModel> getAll() {
    ArrayList<UserModel> users = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_USER);
      if (file.exists()) {
        ArrayList<UserModel> userModels = mapper.readValue(file,
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

  private UserModel getById(Integer id) {
    ArrayList<UserModel> users = getAll();
    UserModel foundUser = null;
    for (UserModel user : users) {
      if (user.getId().equals(id)) {
        foundUser = user;
        break;
      }
    }
    return foundUser;
  }
}
