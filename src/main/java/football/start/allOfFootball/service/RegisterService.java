package football.start.allOfFootball.service;

import football.start.allOfFootball.controller.api.kakaoLogin.LoginResponse;
import football.start.allOfFootball.controller.login.EmailDto;
import football.internal.database.domain.KakaoToken;
import football.internal.database.domain.Member;
import football.common.dto.JsonDefault;
import org.springframework.http.ResponseEntity;

public interface RegisterService {
    ResponseEntity<JsonDefault> validEmail(EmailDto emailDto);

    void save(Member member);

    boolean distinctEmail(String email);

    Member socialSave(LoginResponse userInfo, KakaoToken kakaoToken);
}
