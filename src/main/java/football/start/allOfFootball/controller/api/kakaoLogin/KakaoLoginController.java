package football.start.allOfFootball.controller.api.kakaoLogin;

import football.common.common.alert.AlertTemplate;
import football.common.domain.KakaoToken;
import football.common.domain.Member;
import football.common.domain.Social;
import football.start.allOfFootball.service.LoginService;
import football.start.allOfFootball.service.RegisterService;
import football.start.allOfFootball.service.domainService.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static football.common.consts.SessionConst.LOGIN_MEMBER;
import static football.common.enums.SocialEnum.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginController {

    private final MemberService memberService;
    private final KakaoLoginService kakaoLoginService;
    private final LoginService loginService;
    private final RegisterService registerService;


    @ResponseBody
    @GetMapping("/login/kakao")
    public LoginResponse kakaoLogin(@RequestParam(required = false) String code,
                                    HttpSession session, HttpServletResponse response) throws DistinctRegisterException {

        KakaoToken kakaoToken = kakaoLoginService.getKakaoAccessToken(code);
        LoginResponse userInfo = kakaoLoginService.getUserInfo(kakaoToken.getAccess_token());

        Member loginMember =  loginService.socialLogin(userInfo.getEmail(), KAKAO, userInfo.getId());

        // 로그인시도 (이메일, 소셜타입, 소셜고유번호)
        // 있으면 token 업데이트

        // 없으면 휴대폰 확인
        // 휴대폰정보가 있으면 DistinctRegisterException
        // 없으면 회원가입

        if (loginMember == null) {
            boolean phoneDistinct = loginService.existsByPhone(userInfo.getPhone());
            if (phoneDistinct) throw new DistinctRegisterException();

            loginMember = registerService.socialSave(userInfo, kakaoToken);
            System.out.println("loginMember = " + loginMember);
        }

        loginService.renewLoginTime(loginMember);
        session.setAttribute(LOGIN_MEMBER, loginMember.getMemberId());

        execute(response, null);
        return null;

    }

    @GetMapping("/logout/kakao/{memberId}")
    public String kakaoLogout(@PathVariable Long memberId) {
        Optional<Member> byMemberId = memberService.findByMemberId(memberId);
        if (byMemberId.isEmpty()) return "redirect:/";

        Member member = byMemberId.get();
        Social social = member.getSocial();
        if (!member.socialTypeIs(KAKAO)) return "redirect:/";
        kakaoLoginService.logout(social.getKakaoToken());
        return "redirect:" + kakaoLoginService.serviceLogout();

    }



    private String execute(HttpServletResponse response, String alert) {
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");

        String command = "<script> " + getOption(alert) + " window.self.close(); </script>";
        try (PrintWriter out = response.getWriter()) {
            out.println(command);
            out.flush();
        } catch (IOException e) {
            log.error("Alert IOException 발생! = {}", AlertTemplate.class);
        }
        return null;
    }

    private String getOption(String option) {
        // redirect url이 있으면 url로 이동
        if (option == null) {
            return "const urlParams = new URLSearchParams(opener.location.search); " +
                    "let redirect = urlParams.get('url');" +
                    "if (redirect == null) redirect = '/';" +
                    "opener.location.href=redirect;";
        }
        return String.format("alert('%s');", option);
    }
}
