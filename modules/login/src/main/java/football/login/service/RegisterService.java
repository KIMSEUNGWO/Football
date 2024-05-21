package football.login.service;

import football.common.domain.KakaoToken;
import football.common.domain.Member;
import football.common.dto.json.JsonDefault;
import football.login.dto.EmailDto;
import football.login.dto.LoginResponse;
import org.springframework.http.ResponseEntity;

public interface RegisterService {
    ResponseEntity<JsonDefault> validEmail(EmailDto emailDto);

    void save(Member member);

    boolean distinctEmail(String email);

    Member socialSave(LoginResponse userInfo, KakaoToken kakaoToken);
}
