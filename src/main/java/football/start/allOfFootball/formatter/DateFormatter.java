package football.start.allOfFootball.formatter;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateFormatter {

    private static final String[] dayOfWeek = {"월요일", "화요일","수요일","목요일","금요일","토요일","일요일"};

    public static String dateFormat(LocalDate date, int hourDate) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        String week = dayOfWeek[date.getDayOfWeek().getValue()-1];
        String hour = String.format("%02d:00", hourDate);

        return String.format("%d월 %d일 %s %s", month, day, week, hour);
    }

    public static String sideMenuDateForm(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        return String.format("%d년 %02d월 %02d일", year, month, day);
    }
}
