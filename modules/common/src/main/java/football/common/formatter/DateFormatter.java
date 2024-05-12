package football.common.formatter;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class DateFormatter {

    private static final Locale locale = Locale.KOREAN;

    public static String format(final String format, final LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, locale);
        return date.format(formatter);
    }
    public static String format(final String format, final LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, locale);
        return date.format(formatter);
    }

}
