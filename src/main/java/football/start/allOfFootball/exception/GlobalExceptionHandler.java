package football.start.allOfFootball.exception;

import football.common.dto.JsonDefault;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Enum Convert Exception
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<JsonDefault> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.error("Enum Convert Exception : {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new JsonDefault("입력값이 올바르지 않습니다. 다시 시도해주세요."));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<JsonDefault> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.error("Method Argument Type Mismatch Exception: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new JsonDefault("입력값이 유효한 형식이 아닙니다. 다시 시도해주세요."));
    }

    // DB Error
    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<JsonDefault> invalidData(InvalidDataAccessResourceUsageException ex) {
        log.error("DB InvalidData Exception : {}", ex.getMessage());
        return ResponseEntity.internalServerError().body(new JsonDefault("저장 실패! 관리자에게 문의해주세요."));
    }

}
