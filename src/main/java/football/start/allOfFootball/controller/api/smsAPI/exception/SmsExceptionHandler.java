package football.start.allOfFootball.controller.api.smsAPI.exception;

import football.start.allOfFootball.dto.json.JsonDefault;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class SmsExceptionHandler {

    @ExceptionHandler(MessageSendException.class)
    public ResponseEntity<JsonDefault> messageException(MessageSendException e) {
        return new ResponseEntity<>(new JsonDefault("error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CertificationException.class)
    public ResponseEntity<JsonDefault> certificationException(CertificationException e) {
        log.error("[Certification Exception] : {}", e.getJsonDefault());
        return new ResponseEntity<>(e.getJsonDefault(), e.getCode());
    }


}
