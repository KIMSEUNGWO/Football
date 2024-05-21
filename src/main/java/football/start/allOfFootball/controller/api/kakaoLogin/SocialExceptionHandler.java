package football.start.allOfFootball.controller.api.kakaoLogin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SocialExceptionHandler {

    @ExceptionHandler(DistinctRegisterException.class)
    public ResponseEntity<String> distinctRegisterException(DistinctRegisterException e) {
        e.printStackTrace();
        String alertMessage = "<script> alert('이미 가입한 계정이 존재합니다.'); window.self.close(); </script>";
        return ResponseEntity.ok(alertMessage);
    }
}
