package football.start.allOfFootball.common.alert;

import jakarta.servlet.http.HttpServletResponse;

public class AlertUtils {

    private static final AlertTemplate template = new AlertTemplate();

    public static void alert(HttpServletResponse response, String text) {
        template.execute(response, text, "");
    }
    public static void alertAndBack(HttpServletResponse response, String text) {
        template.execute(response, text, "history.go(-1);");
    }
    public static void alertAndMove(HttpServletResponse response, String text, String nextPage) {
        template.execute(response, text, "location.href ='" + nextPage + "';");
    }
}
