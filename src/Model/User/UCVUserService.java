package Model.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UCVUserService {
  private static final String FILE_UCV_USERS = "src/Database/User/ucvUsers.json";

  public List<UCVUserModel> getAllUCVUsers() {
    List<UCVUserModel> users = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_UCV_USERS);
      if (file.exists()) {
        users = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, UCVUserModel.class));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return users;
  }

  public UCVUserModel getUCVUserByEmail(String email) {
    List<UCVUserModel> users = getAllUCVUsers();
    for (UCVUserModel user : users) {
      // if (user.getEmail().equalsIgnoreCase(email)) {
      // return user;
      // }
      if (user.getEmail() == email) {
        return user;
      }
    }
    return null;
  }
}
