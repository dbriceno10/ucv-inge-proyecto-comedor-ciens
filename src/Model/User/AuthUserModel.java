package Model.User;

import Model.User.BaseUserModel;

public class AuthUserModel extends BaseUserModel {
  private String accessToken;
  private String role; // 'ADMIN' | 'COMMENSAL'

  public AuthUserModel() {
  }

  public AuthUserModel(Integer id, String firstName, String lastName, String email, String role, String type,
      String accessToken) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.type = type;
    this.accessToken = accessToken;
    this.role = role;
  }

  // getters
  public String getAccessToken() {
    return accessToken;
  }

  // setters
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

}
