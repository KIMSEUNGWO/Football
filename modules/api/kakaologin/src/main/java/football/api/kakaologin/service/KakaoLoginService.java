package football.api.kakaologin.service;

import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import football.api.kakaologin.dto.KakaoRequestTo;
import football.common.domain.Token;
import football.login.dto.LoginResponse;
import football.api.kakaologin.repository.KakaoLoginRepository;
import football.common.enums.matchenum.GenderEnum;
import football.common.enums.SocialEnum;
import football.common.formatter.DateFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginService {

    @Value("${kakaoLogin.restApi}")
    private String REST_API_KEY;
    private static final String TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    private static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";
    private static final String REDIRECT_URI = "http://localhost:8080/login/kakao";
    private static final String KAKAO_LOGOUT_URI = "https://kapi.kakao.com/v1/user/logout";

    private final KakaoRequestTo requestTo;

    private final KakaoLoginRepository kakaoLoginRepository;

    public Token getKakaoAccessToken(String code) {

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", REST_API_KEY);
        body.add("redirect_uri", REDIRECT_URI);
        body.add("code", code);

        String post = requestTo.postAccessToken(TOKEN_URI, body);

        JsonElement element = JsonParser.parseString(post);

        String accessToken = element.getAsJsonObject().get("access_token").getAsString();
        String refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

        System.out.println("accessToken : " + accessToken);
        System.out.println("refreshToken : " + refreshToken);

        return new Token(accessToken, refreshToken);
    }

    public LoginResponse getUserInfo(String accessToken) {

        String post = requestTo.postRequireAccessToken(USER_INFO_URI, accessToken);

        System.out.println("response body : " + post);
        JsonElement element = JsonParser.parseString(post);
        String id = element.getAsJsonObject().get("id").getAsString();

        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
        JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

        String nickname = properties.getAsJsonObject().get("nickname").getAsString();
        String profile = properties.getAsJsonObject().get("profile_image").getAsString();

        String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
        String phone = kakaoAccount.getAsJsonObject().get("phone_number").getAsString();

        String birthYear = kakaoAccount.getAsJsonObject().get("birthyear").getAsString();
        String birthDay = kakaoAccount.getAsJsonObject().get("birthday").getAsString();
        String gender = kakaoAccount.getAsJsonObject().get("gender").getAsString();

        return LoginResponse.builder()
            .socialType(SocialEnum.KAKAO)
            .id(Long.parseLong(id.replaceAll("^[0-9]", "")))
            .nickName(nickname)
            .profile(profile)
            .email(email)
            .phone(getPhone(phone))
            .birthday(DateFormatter.toLocalDate(birthYear + birthDay, "yyyyMMdd"))
            .gender(GenderEnum.getGender(gender))
            .build();
    }

    private String getPhone(String phone) {
//        "+82 10-0000-0000";
        int index = phone.indexOf("-") - 1; // 0-0000-0000
        return "01" +  phone.substring(index).replace("-", ""); // 01000000000
    }

    @Transactional
    public void updateKakaoToken(Token nowToken, Token newToken) {
        nowToken.updateToken(newToken);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("client_id", REST_API_KEY);
        params.add("refresh_token", nowToken.getRefresh_token());

        String post = requestTo.postAccessToken(TOKEN_URI, params);

        System.out.println("update token response body : " + post);

        JsonElement element = JsonParser.parseString(post);

        Set<String> keySet = element.getAsJsonObject().keySet();

        // 새로 발급 받은 accessToken 불러오기
        String accessToken = element.getAsJsonObject().get("access_token").getAsString();
        // refreshToken은 유효 기간이 1개월 미만인 경우에만 갱신되어 반환되므로,
        // 반환되지 않는 경우의 상황을 if문으로 처리해주었다.

        String refreshToken = "";
        if(keySet.contains("refresh_token")) {
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();
        }

        nowToken.setAccess_token(accessToken);
        if(!refreshToken.equals("")) {
            nowToken.setRefresh_token(refreshToken);
        }

        log.info("KakaoToken Update : access_token : {}, refresh_token : {}", accessToken, refreshToken);
    }

    @Transactional
    public void logout(Token token) {
        log.info("카카오 로그아웃 시작");
        requestTo.postRequireAccessToken(KAKAO_LOGOUT_URI, token.getAccess_token());
        token.logout();
    }

    public String serviceLogout() {
        return String.format("https://kauth.kakao.com/oauth/logout?logout_redirect_uri=http://localhost:8080&client_id=%s", REST_API_KEY);
    }

    public Token findByKakaoToken(Long memberId, SocialEnum socialEnum) {
        return kakaoLoginRepository.findByKakaoToken(memberId, socialEnum);
    }
}
