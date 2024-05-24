package football.login.config.auth;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public String handleBadCredentialsException(BadCredentialsException e) {
        e.fillInStackTrace();
        return "/";
    }
}
