package football.start.allOfFootball.interceptor;

import football.common.domain.Member;
import football.start.allOfFootball.service.domainService.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

import static football.common.consts.SessionConst.LOGIN_MEMBER;

@Slf4j
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("인증 체크 인터셉터 실행 {}", requestURI);
        HttpSession session = request.getSession(true);
        if (session == null || session.getAttribute(LOGIN_MEMBER) == null) {
            log.info("인증되지않은 사용자 요청");
            response.sendRedirect("/login?url=" + requestURI);
            return false;
        }

        // 이후 처리
        String memberIdStr = String.valueOf(session.getAttribute(LOGIN_MEMBER));
        if (memberIdStr == null) {
            log.info("로그인 세션값 : null");
            response.sendRedirect("/login?url=" + requestURI);
            return false;
        }
        Long memberId = Long.parseLong(memberIdStr);
        Optional<Member> byMemberId = memberService.findByMemberId(memberId);
        if (byMemberId.isEmpty()) {
            log.info("존재하지 않는 회원의 접근입니다.");
            response.sendRedirect("/login?url=" + requestURI);
            return false;
        }

        log.info("인증된 사용자");
        return true;
    }
}
