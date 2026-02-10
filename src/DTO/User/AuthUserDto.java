package DTO.User;

public class AuthUserDto {
  protected Integer id;
  protected String firstName;
  protected String lastName;
  protected String email;
  protected String type; // 'STUDENT' | 'PROFESSOR' | 'WORKER' | 'ADMIN'
  protected Integer documentId;
  private String accessToken;
  private String role; // 'ADMIN' | 'COMMENSAL'
  private Boolean isActive;

  public AuthUserDto() {
  }

  public AuthUserDto(Integer id, String firstName, String lastName, String email, String role, String type,
      String accessToken, Boolean isActive, Integer documentId) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.type = type;
    this.accessToken = accessToken;
    this.role = role;
    this.isActive = isActive;
    this.documentId = documentId;
  }

  public AuthUserDto(AuthUserDto user) {
    this.id = user.getId();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.email = user.getEmail();
    this.type = user.getType();
    this.documentId = user.getDocumentId();
    this.accessToken = user.getAccessToken();
    this.role = user.getRole();
    this.isActive = user.getIsActive();
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

  public Integer getDocumentId() {
    return documentId;
  }
}
