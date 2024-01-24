package football.start.allOfFootball.formatter;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateFormatter {

    private static final String[] dayOfWeek = {"월요일", "화요일","수요일","목요일","금요일","토요일","일요일"};
    private static final String[] dayOfWeek2 = {"월", "화","수","목","금","토","일"};


    public static String format(DateType type, LocalDateTime date) {
        if (type == DateType.M월_D일_W요일_HH_00) {
            return dateFormatAll(date);
        }
        if (type == DateType.YYYY년_M월_D일_W) {
            return dateFormatAndWeek(date);
        }
        if (type == DateType.YYYY년_M월_D일) {
            return dateFormat(date);
        }
        if (type == DateType.YYYY년_MM월_DD일) {
            return sideMenuDateForm(date);
        }
        if (type == DateType.YYYY_MM_DD) {
            return defaultDateForm(date);
        }
        return defaultDateForm(date);
    }
    private static String dateFormatAll(LocalDateTime date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        String week = dayOfWeek[date.getDayOfWeek().getValue()-1];
        String hour = String.format("%02d:00", date.getHour());

        return String.format("%d월 %d일 %s %s", month, day, week, hour);
    }
    private static String dateFormatAndWeek(LocalDateTime date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        String week = dayOfWeek2[date.getDayOfWeek().getValue()-1];

        return String.format("%d년 %d월 %d일 (%s)", year, month, day, week);
    }
    private static String dateFormat(LocalDateTime date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        return String.format("%d년 %d월 %d일", year, month, day);
    }

    private static String sideMenuDateForm(LocalDateTime date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        return String.format("%d년 %02d월 %02d일", year, month, day);
    }

    private static String defaultDateForm(LocalDateTime date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        return String.format("%d-%02d-%02d", year, month, day);
    }
}
