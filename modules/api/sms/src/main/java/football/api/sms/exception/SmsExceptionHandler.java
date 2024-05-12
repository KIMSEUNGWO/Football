package football.api.sms.exception;

import football.common.dto.JsonDefault;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static football.common.consts.Constant.ERROR;

@RestControllerAdvice
@Slf4j
public class SmsExceptionHandler {

    @ExceptionHandler(MessageSendException.class)
    public ResponseEntity<JsonDefault> messageException(MessageSendException e) {
        return ResponseEntity.internalServerError().body(new JsonDefault(ERROR, e.getMessage()));
    }

    @ExceptionHandler(IllegalPhoneException.class)
    public ResponseEntity<JsonDefault> illegalPhoneException() {
        return ResponseEntity.internalServerError().body(new JsonDefault(ERROR, "휴대폰 번호를 다시 확인해주세요."));
    }

    @ExceptionHandler(DistinctPhoneException.class)
    public ResponseEntity<JsonDefault> distinctPhoneException() {
        return ResponseEntity.internalServerError().body(new JsonDefault(ERROR, "이미 가입한 이력이 있습니다."));
    }

    @ExceptionHandler(NotFoundCertificationNumberException.class)
    public ResponseEntity<JsonDefault> notFoundCertificationNumberException() {
        return ResponseEntity.badRequest().body(new JsonDefault(ERROR, "인증정보가 존재하지 않습니다."));
    }

    @ExceptionHandler(ExpireCertificationException.class)
    public ResponseEntity<JsonDefault> expireCertificationException() {
        return ResponseEntity.badRequest().body(new JsonDefault(ERROR, "인증시간이 만료되었습니다."));
    }

    @ExceptionHandler(CertificationException.class)
    public ResponseEntity<JsonDefault> certificationException() {
        return ResponseEntity.badRequest().body(new JsonDefault(ERROR, "인증과정에서 문제가 발생했습니다. 관리자에게 문의해주세요."));
    }


}
