package football.common.formatter;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.util.Locale.KOREAN;

@Component
public class DateFormatter {


    public static String format(final String format, final LocalDateTime date) {
        return date.format(formatter(format));
    }
    public static String format(final String format, final LocalDate date) {
        return date.format(formatter(format));
    }

    public static LocalDateTime toLocalDateTime(final String date, final String format) {
        return LocalDateTime.parse(date, formatter(format));
    }

    public static LocalDate toLocalDate(final String date, final String format) {
        return LocalDate.parse(date, formatter(format));
    }

    private static DateTimeFormatter formatter(String format) {
        return DateTimeFormatter.ofPattern(format, KOREAN);
    }

}
