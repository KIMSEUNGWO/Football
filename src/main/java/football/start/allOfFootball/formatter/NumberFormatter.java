package football.start.allOfFootball.formatter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
public class NumberFormatter {

    @Autowired
    private static final DecimalFormat df = new DecimalFormat("###,###");

    public static String format(int number) {
        return df.format(number);
    }
}
