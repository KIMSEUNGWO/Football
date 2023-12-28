package football.start.allOfFootball.interceptor;

import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.service.domainService.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

import static football.start.allOfFootball.SessionConst.LOGIN_MEMBER;
import static football.start.allOfFootball.SessionConst.REDIRECT_URL;

@RequiredArgsConstructor
@Slf4j
public class OrderInterceptor implements HandlerInterceptor {

    private final MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        log.info("잔액 체크 인터셉터 실행 {}", requestURI);

        HttpSession session = request.getSession(true);

        // 회원확인
        Long memberId = (Long) session.getAttribute(LOGIN_MEMBER);
        if (memberId == null) {
            log.info("로그인 되어있지 않은 사용자 접근");
            response.sendRedirect("/login?url=" + requestURI);
            return false;
        }

        Optional<Member> findMember = memberService.findByMemberId(memberId);
        if (findMember.isEmpty()) {
            log.info("존재하지 않는 사용자 접근");
            response.sendRedirect("/login?url=" + requestURI);
            return false;
        }

        Member member = findMember.get();
        int cache = member.getMemberCash();

        if (cache < 10000) {
            log.info("잔액 부족");
            response.sendRedirect("/cache/charge?url=" + requestURI);
            return false;
        }

        log.info("정상적인 신청 요청");
        return true;
    }
}
