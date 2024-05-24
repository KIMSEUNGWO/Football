package football.api.kakaologin.service;

import football.api.kakaologin.dto.KakaoRequestTo;
import football.common.domain.Token;
import football.api.kakaologin.repository.KakaoLoginRepository;
import football.common.enums.SocialEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginService {

    @Value("${kakaoLogin.restApi}")
    private String REST_API_KEY;
    private static final String KAKAO_LOGOUT_URI = "https://kapi.kakao.com/v1/user/logout";

    private final KakaoRequestTo requestTo;
    private final KakaoLoginRepository kakaoLoginRepository;

    @Transactional
    public void logout(Token token) {
        log.info("카카오 로그아웃 시작");
        requestTo.post(KAKAO_LOGOUT_URI, token.getAccess_token());
        token.logout();
    }

    public String serviceLogout() {
        return String.format("https://kauth.kakao.com/oauth/logout?logout_redirect_uri=http://localhost:8080&client_id=%s", REST_API_KEY);
    }

    public Token findByKakaoToken(Long memberId, SocialEnum socialEnum) {
        return kakaoLoginRepository.findByKakaoToken(memberId, socialEnum);
    }
}
