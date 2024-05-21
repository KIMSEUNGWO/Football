package football.login.service;

import football.common.domain.KakaoToken;
import football.common.domain.Member;
import football.login.dto.LoginResponse;
import jakarta.servlet.http.Cookie;

public interface RegisterService {

    void save(Member member);

    boolean distinctEmail(String email);

    Member socialSave(LoginResponse userInfo, KakaoToken kakaoToken);

    Cookie createCertCookie();

    Cookie removeCertCookie();

    boolean existsByEmail(String email);
}
