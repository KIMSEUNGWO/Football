package football.common.formatter;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
public class NumberFormatter {

    private static final DecimalFormat df = new DecimalFormat("###,###");

    public static String format(Number number) {
        return df.format(number);
    }
}
