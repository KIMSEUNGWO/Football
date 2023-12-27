package football.start.allOfFootball.controller.api.kakaoLogin;

import football.start.allOfFootball.SessionConst;
import football.start.allOfFootball.common.alert.AlertTemplate;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.service.LoginService;
import football.start.allOfFootball.service.RegisterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;
    private final LoginService loginService;
    private final RegisterService registerService;

    @GetMapping("/login/kakao")
    public LoginResponse kakaoLogin(@RequestParam(required = false) String code, HttpServletRequest request, HttpServletResponse response) {

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

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember.getMemberId());

        return null;

    }

    private String execute(HttpServletResponse response) {
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");

        String command = "<script> opener.location.href='/'; window.self.close(); </script>";
        try (PrintWriter out = response.getWriter()) {
            out.println(command);
            out.flush();
        } catch (IOException e) {
            log.error("Alert IOException 발생! = {}", AlertTemplate.class);
        }
        return null;
    }
}
