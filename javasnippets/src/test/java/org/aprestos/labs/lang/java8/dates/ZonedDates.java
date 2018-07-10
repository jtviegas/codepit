package org.aprestos.labs.lang.java8.dates;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

public class ZonedDates {

  @Test
  public void one() {

    ZonedDateTime date = LocalDateTime.of(2018, Month.MAY, 3, 00, 25).atZone(ZoneId.of("+2"));

    System.out.println("local date time : " + date.toLocalDateTime());
    System.out.println("local date : " + date.toLocalDate());

    System.out.println("utc date : " + LocalDateTime.ofEpochSecond(date.toEpochSecond(), 0, ZoneOffset.UTC));

    ZoneId australia = ZoneId.of("Australia/Sydney");
    String str = "2015-01-05 17:00";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime localtDateAndTime = LocalDateTime.parse(str, formatter);
    ZonedDateTime dateAndTimeInSydney = ZonedDateTime.of(localtDateAndTime, australia);

    System.out.println("Current date and time in a particular timezone : " + dateAndTimeInSydney);

    ZonedDateTime utcDate = dateAndTimeInSydney.withZoneSameInstant(ZoneOffset.UTC);

    System.out.println("Current date and time in UTC : " + utcDate);

  }

}
