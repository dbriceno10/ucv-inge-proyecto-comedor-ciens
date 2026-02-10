package Utils;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import Enums.Days;

public class Dates {
  public String getCurrentDateTime() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    return ZonedDateTime.now(java.time.ZoneOffset.UTC).format(formatter);
  }

  public String getDayOfWeek(String dateTime) {
    // Parse the input date string
    ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTime);
    // Get the day of the week
    DayOfWeek dayOfWeek = zonedDateTime.getDayOfWeek();
    // Return the day of the week as a string (e.g., "MONDAY", "TUESDAY", etc.)
    switch (dayOfWeek) {
      case MONDAY:
        return Days.MONDAY;
      case TUESDAY:
        return Days.TUESDAY;
      case WEDNESDAY:
        return Days.WEDNESDAY;
      case THURSDAY:
        return Days.THURSDAY;
      case FRIDAY:
        return Days.FRIDAY;
      case SATURDAY:
        return Days.SATURDAY;
      case SUNDAY:
        return Days.SUNDAY;
      default:
        return dayOfWeek.toString().toUpperCase();
    }

  }
}
