package football.login.config.oauth;

import football.common.domain.Member;
import football.common.jpaRepository.JpaMemberRepository;
import football.login.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final JpaMemberRepository jpaMemberRepository;
    private final PrincipalOauth2UserRepository principalOauth2UserRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest.getClientRegistration = " + userRequest.getClientRegistration());
        System.out.println("userRequest.getClientRegistration().getClientId() = " + userRequest.getClientRegistration().getClientId());
        System.out.println("userRequest.getClientRegistration().getClientName() = " + userRequest.getClientRegistration().getClientName());
        System.out.println("userRequest.getAccessToken() = " + userRequest.getAccessToken().getTokenValue());
        System.out.println("userRequest.getAdditionalParameters() = " + userRequest.getAdditionalParameters());

        String accessToken = userRequest.getAccessToken().getTokenValue();

        OAuth2MemberInfo memberInfo = new KakaoMemberInfo(super.loadUser(userRequest).getAttributes());

        String socialName = userRequest.getClientRegistration().getClientName();
        if ("kakao".equals(socialName)) {

        }

        Member member = principalOauth2UserRepository.socialLogin(memberInfo.getEmail(), memberInfo.getSocialType(), memberInfo.getId());

        if (member == null) {
            boolean distinct = jpaMemberRepository.existsByMemberPhone(memberInfo.getPhone());
            if (distinct) throw new OAuth2AuthenticationException(new OAuth2Error("200"), "이미 가입된 회원입니다.");
            member = principalOauth2UserRepository.socialSave(memberInfo, accessToken);
        }

        member.renewLoginTime();

        return new PrincipalDetails(member, super.loadUser(userRequest).getAttributes());
    }
}

/**
 * {
 *      id=3243684669,
 *      connected_at=2023-12-27T07:49:45Z,
 *      properties={
 *                      nickname=김승우,
 *                      profile_image=http://k.kakaocdn.net/dn/iZlhC/btsHas2P68U/eFGOhl9qbAJrBICkcOkwkk/img_640x640.jpg,
 *                      thumbnail_image=http://k.kakaocdn.net/dn/iZlhC/btsHas2P68U/eFGOhl9qbAJrBICkcOkwkk/img_110x110.jpg
 *                 },
 *      kakao_account={
 *                      profile_nickname_needs_agreement=false,
 *                      profile_image_needs_agreement=false,
 *                      profile={
 *                                  nickname=김승우,
 *                                  thumbnail_image_url=http://k.kakaocdn.net/dn/iZlhC/btsHas2P68U/eFGOhl9qbAJrBICkcOkwkk/img_110x110.jpg,
 *                                  profile_image_url=http://k.kakaocdn.net/dn/iZlhC/btsHas2P68U/eFGOhl9qbAJrBICkcOkwkk/img_640x640.jpg,
 *                                  is_default_image=false,
 *                                  is_default_nickname=false
 *                              },
 *                      name_needs_agreement=false,
 *                      name=김승우,
 *                      has_email=true,
 *                      email_needs_agreement=false,
 *                      is_email_valid=true,
 *                      is_email_verified=true,
 *                      email=tmd8633@naver.com,
 *                      has_phone_number=true,
 *                      phone_number_needs_agreement=false,
 *                      phone_number=+82 10-6603-8635,
 *                      has_birthyear=true,
 *                      birthyear_needs_agreement=false,
 *                      birthyear=1996,
 *                      has_birthday=true,
 *                      birthday_needs_agreement=false,
 *                      birthday=0110,
 *                      birthday_type=SOLAR,
 *                      has_gender=true,
 *                      gender_needs_agreement=false,
 *                      gender=male
 *                    }
 * }
 */