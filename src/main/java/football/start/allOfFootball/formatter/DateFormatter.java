package football.start.allOfFootball.formatter;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DateFormatter {

    private static final String[] dayOfWeek = {"월요일", "화요일","수요일","목요일","금요일","토요일","일요일"};
    private static final String[] dayOfWeek2 = {"월", "화","수","목","금","토","일"};

    public static String dateFormatAll(LocalDateTime date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        String week = dayOfWeek[date.getDayOfWeek().getValue()-1];
        String hour = String.format("%02d:00", date.getHour());

        return String.format("%d월 %d일 %s %s", month, day, week, hour);
    }
    public static String dateFormatAndWeek(LocalDateTime date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        String week = dayOfWeek2[date.getDayOfWeek().getValue()-1];

        return String.format("%d년 %d월 %d일 (%s)", year, month, day, week);
    }
    public static String dateFormat(LocalDateTime date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        return String.format("%d년 %d월 %d일", year, month, day);
    }

    public static String sideMenuDateForm(LocalDateTime date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        return String.format("%d년 %02d월 %02d일", year, month, day);
    }
}
