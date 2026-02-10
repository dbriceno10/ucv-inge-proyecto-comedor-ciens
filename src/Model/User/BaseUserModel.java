package Model.User;

public class BaseUserModel {
  protected Integer id;
  protected String firstName;
  protected String lastName;
  protected String email;
  protected String type; // 'STUDENT' | 'PROFESSOR' | 'WORKER' | 'ADMIN'
  protected Integer documentId;

  public BaseUserModel() {
  }

  public BaseUserModel(Integer id, String firstName, String lastName, String email, String type, Integer documentId) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.type = type;
    this.documentId = documentId;
  }

  public BaseUserModel(UserModel user) {
    this.id = user.getId();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.email = user.getEmail();
    this.type = user.getType();
    this.documentId = user.getDocumentId();
  }

  // getters
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

  public Integer getDocumentId() {
    return documentId;
  }
}
