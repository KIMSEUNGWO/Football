package football.common.common.alert;

import jakarta.servlet.http.HttpServletResponse;

public class AlertUtils {

    private static final AlertTemplate template = new AlertTemplate();

    public static String alert(HttpServletResponse response, String text) {
        return template.execute(response, text, "");
    }
    public static String alertAndBack(HttpServletResponse response, String text) {
        return template.execute(response, text, "history.go(-1);");
    }
    public static String alertAndMove(HttpServletResponse response, String text, String nextPage) {
        return template.execute(response, text, "location.href ='" + nextPage + "';");
    }

    public static String alertAndClose(HttpServletResponse response, String text) {
        return template.execute(response, text, "window.close()");
    }
}
