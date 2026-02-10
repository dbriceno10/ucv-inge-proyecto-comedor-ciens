package Model.User;

// import Model.User.BaseUserModel;

public class UserModel extends BaseUserModel {
  private String password;
  private Boolean isActive;
  private String createdAt = null;
  private String updatedAt = null;
  private String deletedAt = null;
  private String role; // 'ADMIN' | 'COMMENSAL'

  public UserModel() {
  }

  public UserModel(Integer id, String firstName, String lastName, String email, String password, String role,
      String type,
      Boolean isActive, String createdAt, String updatedAt, String deletedAt, Integer documentId) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.role = role;
    this.type = type;
    this.isActive = isActive;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
    this.documentId = documentId;
  }

  public UserModel(UserModel user) {
    this.id = user.id;
    this.firstName = user.firstName;
    this.lastName = user.lastName;
    this.email = user.email;
    this.password = user.password;
    this.role = user.role;
    this.type = user.type;
    this.isActive = user.isActive;
    this.createdAt = user.createdAt;
    this.updatedAt = user.updatedAt;
    this.deletedAt = user.deletedAt;
    this.documentId = user.documentId;
  }

  public UserModel(String firstName, String lastName, String email, String password, String role,
      String type, Integer documentId) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.role = role;
    this.type = type;
    this.documentId = documentId;
  }

  // getters
  public String getPassword() {
    return password;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public String getDeletedAt() {
    return deletedAt;
  }

  public String getRole() {
    return role;
  }

  // setters
  public void setDeletedAt(String deletedAt) {
    this.deletedAt = deletedAt;
  }
}
