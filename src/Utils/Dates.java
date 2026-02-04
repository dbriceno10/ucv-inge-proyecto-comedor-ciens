package Utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Dates {
  public String getCurrentDateTime() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    return ZonedDateTime.now(java.time.ZoneOffset.UTC).format(formatter);
  }
}
