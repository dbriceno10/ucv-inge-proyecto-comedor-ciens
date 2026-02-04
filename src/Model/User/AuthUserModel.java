package Model.User;

// import Model.User.BaseUserModel;

public class AuthUserModel extends BaseUserModel {
  private String accessToken;
  private String role; // 'ADMIN' | 'COMMENSAL'
  private Boolean isActive;

  public AuthUserModel() {
  }

  public AuthUserModel(Integer id, String firstName, String lastName, String email, String role, String type,
      String accessToken, Boolean isActive) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.type = type;
    this.accessToken = accessToken;
    this.role = role;
    this.isActive = isActive;
  }

  // getters
  public String getAccessToken() {
    return accessToken;
  }

  public String getRole() {
    return role;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public Integer getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getType() {
    return type;
  }

  // setters
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

}
