package football.start.allOfFootball.interceptor;

import football.common.domain.Member;
import football.common.jpaRepository.JpaAdminRepository;
import football.start.allOfFootball.service.domainService.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import static football.common.consts.SessionConst.LOGIN_MEMBER;

@Slf4j
@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {

    private final MemberService memberService;
    private final JpaAdminRepository jpaAdminRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("관리자 체크 인터셉터 실행 : {}", requestURI);

        HttpSession session = request.getSession(false);
        if (session == null) {
            log.info("인증되지 않은 사용자 요청");
            response.sendRedirect("/");
            return false;
        }

        Long memberId = (Long) session.getAttribute(LOGIN_MEMBER);
        Member member = memberService.findByMemberId(memberId).get(); // LoginInterceptor 이후 로직이라 검증 X

        if (!jpaAdminRepository.existsByMember(member)) {
            log.info("관리자 권한이 없는 사용자 요청");
            response.sendRedirect("/");
            return false;
        }


        log.info("관리자 권한이 있는 사용자 인증 완료");
        return true;
    }
}
