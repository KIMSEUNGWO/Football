package football.start.allOfFootball.common.alert;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class AlertTemplate {

    private static void init(HttpServletResponse response) {
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
    }

    public void execute(HttpServletResponse response, String text, String moveTo) {
        init(response);

        String command = getCommand(text, moveTo);
        try (PrintWriter out = response.getWriter()) {
            out.println(command);
            out.flush();
        } catch (IOException e) {
            log.error("Alert IOException 발생! = {}", AlertTemplate.class);
        }

    }

    private String getCommand(String text, String option) {
        return String.format("<script> alert('%s'); %s </script>", text, option);
    }
}
