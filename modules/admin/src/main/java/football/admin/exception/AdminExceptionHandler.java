package football.admin.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdminExceptionHandler {

    @ExceptionHandler(NotExistsFieldException.class)
    public ResponseEntity<String> notExistsFieldException() {
        String alertScript = "<script>alert('존재하지 않는 구장입니다'); location.href='/admin/ground'</script>";
        return ResponseEntity.badRequest().body(alertScript);
    }
}
