package football.common.exception.match;

import football.common.dto.json.JsonDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static football.common.consts.Constant.FAIL;

@RestControllerAdvice
public class MatchRestExceptionHandler {

    @ExceptionHandler(NotExistsMatchException.class)
    public ResponseEntity<JsonDefault> notExistsMatchException() {
        return ResponseEntity.badRequest().body(new JsonDefault(FAIL, "매치정보가 잘못되었습니다."));
    }
}
