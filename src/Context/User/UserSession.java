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

  public void logout() {
    user = null; // Limpiamos la sesión
  }

  // metodos para proteger servicios
  public void isAuthenticated() {
    if (!isLoggedIn()) {
      throw new RuntimeException("Usuario no autenticado");
    }
  }

  public void hasRole(String requiredRole) {
    isAuthenticated(); // Primero verificamos que esté autenticado
    if (!user.getRole().equals(requiredRole)) {
      throw new RuntimeException("Acceso denegado: se requiere el rol " + requiredRole);
    }
  }
}