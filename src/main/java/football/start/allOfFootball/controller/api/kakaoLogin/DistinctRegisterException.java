package football.start.allOfFootball.controller.api.kakaoLogin;

public class DistinctRegisterException extends SocialLoginException{

    public DistinctRegisterException() {
        super("이미 가입한 계정이 존재합니다.");
    }

    public DistinctRegisterException(String alertMessage) {
        super(alertMessage);
    }
}
