package football.start.allOfFootball.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(NotEnoughCashException.class)
    public ResponseEntity<String> notEnoughCash(NotEnoughCashException e) {
        String alertScript = "<script>alert('잔액이 부족합니다.'); location.href='/cash/charge?url='" + e.getRedirectURI() + "</script>";
        return ResponseEntity.badRequest().body(alertScript);
    }
}
