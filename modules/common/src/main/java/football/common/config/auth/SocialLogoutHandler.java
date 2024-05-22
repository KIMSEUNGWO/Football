package football.common.config.auth;

import football.common.domain.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SocialLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest req, HttpServletResponse res, Authentication auth) {
        if (auth != null && auth.getPrincipal() instanceof PrincipalDetails userDetails) {
            Member member = userDetails.getMember();
            if (member.isSocial()) {
                String logoutUrl = "/logout/kakao/" + member.getMemberId();
                System.out.println("카카오 회원 로그아웃 SocialLogoutHandler");
                try {
                    res.sendRedirect(logoutUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
