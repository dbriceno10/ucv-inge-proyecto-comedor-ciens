package Context.User;

import DTO.User.AuthUserDto;

public class UserSession {
  private static UserSession instance;

  // Aquí guardamos AL DTO, no al revés
  private AuthUserDto user;

  private UserSession() {
  }

  // Este método crea la instancia si no existe (Lazy Loading)
  public static UserSession getInstance() {
    if (instance == null) {
      instance = new UserSession(); // ¡Aquí nace la instancia!
    }
    return instance;
  }

  public AuthUserDto getUser() {
    return user;
  }

  public void setUser(AuthUserDto user) {
    this.user = user;
  }

  public boolean isLoggedIn() {
    return user != null;
  }
}