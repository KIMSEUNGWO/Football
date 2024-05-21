package football.start.allOfFootball.service;

import football.start.allOfFootball.controller.login.EmailDto;
import football.common.domain.KakaoToken;
import football.common.domain.Member;
import football.common.dto.json.JsonDefault;
import football.start.allOfFootball.dto.LoginResponse;
import org.springframework.http.ResponseEntity;

public interface RegisterService {
    ResponseEntity<JsonDefault> validEmail(EmailDto emailDto);

    void save(Member member);

    boolean distinctEmail(String email);

    Member socialSave(LoginResponse userInfo, KakaoToken kakaoToken);
}
