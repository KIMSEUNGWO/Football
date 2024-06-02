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

    @ExceptionHandler(RequirePolicyAgreementException.class)
    public ResponseEntity<String> requirePolicyAgreement(RequirePolicyAgreementException e) {
        String alertScript = "<script>alert('모든 약관에 동의해주세요.'); location.href='" + e.getRedirectURI() + "';</script>";
        return ResponseEntity.badRequest().body(alertScript);
    }

    @ExceptionHandler(MatchFullException.class)
    public ResponseEntity<String> matchFullException(MatchFullException e) {
        String alertScript = "<script>alert('모집이 마감되었습니다.'); location.href='/';</script>";
        return ResponseEntity.badRequest().body(alertScript);
    }
}
