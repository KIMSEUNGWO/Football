package football.start.allOfFootball.interceptor;

import football.common.common.alert.AlertUtils;
import football.common.domain.Match;
import football.common.domain.Member;
import football.login.config.auth.PrincipalDetails;
import football.start.allOfFootball.service.domainService.MatchService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import static football.common.consts.SessionConst.*;

@RequiredArgsConstructor
@Slf4j
public class OrderInterceptor implements HandlerInterceptor {

    private final MatchService matchService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        System.out.println("method = " + request.getMethod());
        log.info("Order 체크 인터셉터 실행 {}", requestURI);

        Match match = matchService.findByMatchOrRedirect(getMatchId(requestURI), "/");

        PrincipalDetails user = (PrincipalDetails) request.getSession().getAttribute(SPRING_SECURITY_CONTEXT);
        Member member = user.getMember();

        if (!match.isPossibleGender(member.getMemberGender())) {
            AlertUtils.alertAndBack(response, match.getMatchGender() + "만 참여할 수 있습니다.");
            return false;
        }
        if (!match.isPossibleGrade(member.getGrade())) {
            AlertUtils.alertAndBack(response, match.getMatchGrade().getMatchInfo() + " 만 참여할 수 있습니다.");
            return false;
        }

        boolean distinctMember = matchService.distinctCheck(match, member);
        if (distinctMember) {
            AlertUtils.alertAndMove(response, "이미 신청된 경기입니다.", "/");
            return false;
        }
        if (match.isFull()) {
            AlertUtils.alertAndMove(response, "경기가 마감되었습니다.", "/");
            return false;
        }

        log.info("정상적인 신청 요청");
        return true;
    }

    private Long getMatchId(String requestURI) {
        int index = requestURI.lastIndexOf("/");
        if (index == -1 || requestURI.length() == index + 1) return null;

        String matchIdStr = requestURI.substring(index + 1);
        try {
            return Long.parseLong(matchIdStr);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
