package football.common.exception.match;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MatchExceptionHandler {

    @ExceptionHandler(NotExistsMatchException.class)
    public ResponseEntity<String> notExistsMatch(NotExistsMatchException e) {
        String alertScript = getAlertScript(e.getRedirect());
        return ResponseEntity.badRequest().body(alertScript);
    }

    private String getAlertScript(String redirect) {
        StringBuilder sb = new StringBuilder("<script> alert('존재하지 않는 경기입니다.');");
        if (redirect != null) sb.append(" location.href='").append(redirect).append("';");
        sb.append(" </script>");
        return sb.toString();
    }
}
