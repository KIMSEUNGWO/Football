package football.start.allOfFootball.controller.api.kakaoLogin;

import football.start.allOfFootball.SessionConst;
import football.start.allOfFootball.common.alert.AlertTemplate;
import football.start.allOfFootball.domain.KakaoToken;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Social;
import football.start.allOfFootball.enums.SocialEnum;
import football.start.allOfFootball.service.LoginService;
import football.start.allOfFootball.service.RegisterService;
import football.start.allOfFootball.service.domainService.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static football.start.allOfFootball.enums.SocialEnum.*;

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
                                    HttpServletRequest request, HttpServletResponse response) {

        KakaoToken kakaoToken = kakaoLoginService.getKakaoAccessToken(code);
        System.out.println("accessToken = " + kakaoToken.getAccess_token());

        LoginResponse userInfo = kakaoLoginService.getUserInfo(kakaoToken.getAccess_token());
        System.out.println("userInfo = " + userInfo);

        Optional<Member> findMember =  loginService.findByEmail(userInfo.getEmail());
        Member loginMember = null;
        if (findMember.isEmpty()) {
            boolean phoneDistinct = loginService.findByPhone(userInfo.getPhone());
            if (phoneDistinct) {
                execute(response, "이미 가입한 계정이 존재합니다.");
                return null;
            }
            loginMember = registerService.socialSave(userInfo, kakaoToken);
            System.out.println("loginMember = " + loginMember);
        } else {
            Member member = findMember.get();
            Social social = member.getSocial();
            if (social != null && social.getSocialType() == KAKAO && social.getSocialNum().equals(userInfo.getId())) {
                loginMember = findMember.get();
                kakaoLoginService.updateKakaoToken(loginMember, kakaoToken);
            } else {
                execute(response, "이미 가입한 계정이 존재합니다.");
                return null;
            }
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember.getMemberId());
        execute(response, null);
        return null;

    }

    @GetMapping("/logout/kakao/{memberId}")
    public String kakaoLogout(@PathVariable Long memberId) {
        Optional<Member> byMemberId = memberService.findByMemberId(memberId);
        if (byMemberId.isEmpty()) return "redirect:/";

        Member member = byMemberId.get();
        Social social = member.getSocial();
        if (social == null || social.getSocialType() != KAKAO) return "redirect:/";
        KakaoToken kakaoToken = social.getKakaoToken();
        kakaoLoginService.logout(kakaoToken);
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
