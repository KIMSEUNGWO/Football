package football.start.allOfFootball.common.alert;

import jakarta.servlet.http.HttpServletResponse;

public class AlertUtils {

    private static final AlertTemplate template = new AlertTemplate();

    public static String alert(HttpServletResponse response, String text) {
        template.execute(response, text, "");
        return null;
    }
    public static String alertAndBack(HttpServletResponse response, String text) {
        template.execute(response, text, "history.go(-1);");
        return null;
    }
    public static String alertAndMove(HttpServletResponse response, String text, String nextPage) {
        template.execute(response, text, "location.href ='" + nextPage + "';");
        return null;
    }
}
