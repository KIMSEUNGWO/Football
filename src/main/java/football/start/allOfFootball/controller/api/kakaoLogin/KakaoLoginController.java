package football.start.allOfFootball.controller.api.kakaoLogin;

import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.service.LoginService;
import football.start.allOfFootball.service.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;
    private final LoginService loginService;
    private final RegisterService registerService;

    @GetMapping("/login/kakao")
    public LoginResponse kakaoLogin(@RequestParam(required = false) String code) {

        String accessToken = kakaoLoginService.getKakaoAccessToken(code);
        System.out.println("accessToken = " + accessToken);

        LoginResponse userInfo = kakaoLoginService.getUserInfo(accessToken);
        System.out.println("userInfo = " + userInfo);

        Optional<Member> findMember =  loginService.findByEmail(userInfo.getEmail());
        Member loginMember = null;
        if (findMember.isEmpty()) {
            loginMember = registerService.socialSave(userInfo);
        } else {
            loginMember = findMember.get();
        }



        return null;

    }
}
