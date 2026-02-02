package Model.User;

public class UCVUserModel {
  private Number id;
  private String firstName;
  private String lastName;
  private String email;
  private String type; // 'STUDENT' | 'PROFESSOR' | 'WORKER' | 'ADMIN'

  public UCVUserModel() {
  }

  public UCVUserModel(Number id, String firstName, String lastName, String email, String type) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.type = type;
  }

  public UCVUserModel(UserModel user) {
    this.id = user.getId();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.email = user.getEmail();
    this.type = user.getType();
  }

  // getters
  public Number getId() {
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
}
