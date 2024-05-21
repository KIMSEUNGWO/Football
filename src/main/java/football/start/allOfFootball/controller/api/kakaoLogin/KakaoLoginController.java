package football.start.allOfFootball.controller.api.kakaoLogin;

import football.common.domain.KakaoToken;
import football.common.domain.Member;
import football.start.allOfFootball.service.LoginService;
import football.start.allOfFootball.service.RegisterService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static football.common.consts.SessionConst.LOGIN_MEMBER;
import static football.common.enums.SocialEnum.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;
    private final LoginService loginService;
    private final RegisterService registerService;


    @ResponseBody
    @GetMapping("/login/kakao")
    public ResponseEntity<String> kakaoLogin(@RequestParam(required = false) String code, HttpSession session) throws DistinctRegisterException {

        KakaoToken newKakaoToken = kakaoLoginService.getKakaoAccessToken(code);
        LoginResponse userInfo = kakaoLoginService.getUserInfo(newKakaoToken.getAccess_token());

        Member loginMember =  loginService.socialLogin(userInfo.getEmail(), KAKAO, userInfo.getId());

        // 로그인시도 (이메일, 소셜타입, 소셜고유번호)
        // 있으면 token 업데이트

        // 없으면 휴대폰 확인
        // 휴대폰정보가 있으면 DistinctRegisterException
        // 없으면 회원가입

        if (loginMember == null) {
            boolean phoneDistinct = loginService.existsByPhone(userInfo.getPhone());
            if (phoneDistinct) throw new DistinctRegisterException();

            loginMember = registerService.socialSave(userInfo, newKakaoToken);
            System.out.println("loginMember = " + loginMember);
        } else {
            KakaoToken nowKakaoToken = loginMember.getSocial().getKakaoToken();
            kakaoLoginService.updateKakaoToken(nowKakaoToken, newKakaoToken);
        }

        loginService.renewLoginTime(loginMember);
        session.setAttribute(LOGIN_MEMBER, loginMember.getMemberId());

        String redirect = "const urlParams = new URLSearchParams(opener.location.search); " +
            "let redirect = urlParams.get('url'); " +
            "if (redirect == null) redirect = '/';" +
            "opener.location.href=redirect;";
        return ResponseEntity.ok(String.format("<script> %s window.self.close(); </script>", redirect));

    }

    @GetMapping("/logout/kakao/{memberId}")
    public String kakaoLogout(@PathVariable Long memberId) {
        KakaoToken findKakaoToken = kakaoLoginService.findByKakaoToken(memberId, KAKAO);
        if (findKakaoToken == null) return "redirect:/";

        kakaoLoginService.logout(findKakaoToken);
        return "redirect:" + kakaoLoginService.serviceLogout();
    }
}
