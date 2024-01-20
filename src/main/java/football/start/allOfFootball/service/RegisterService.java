package football.start.allOfFootball.service;

import football.start.allOfFootball.controller.api.kakaoLogin.LoginResponse;
import football.start.allOfFootball.controller.login.EmailDto;
import football.start.allOfFootball.domain.KakaoToken;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.dto.json.JsonDefault;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface RegisterService {
    ResponseEntity<JsonDefault> validEmail(EmailDto emailDto);

    void save(Member member);

    boolean distinctEmail(String email);

    Member socialSave(LoginResponse userInfo, KakaoToken kakaoToken);
}
