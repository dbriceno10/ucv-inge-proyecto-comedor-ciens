package Utils;

public class Formatters {
  public String toUpperCase(String input) {
    if (input == null || input.isEmpty()) {
      return input;
    }
    return input.toUpperCase();
  }
}
