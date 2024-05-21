package football.api.kakaologin.exception;

import lombok.Getter;

@Getter
public class SocialLoginException extends Exception {

    private final String alertMessage;

    public SocialLoginException(String alertMessage) {
        super(alertMessage);
        this.alertMessage = alertMessage;
    }
}
