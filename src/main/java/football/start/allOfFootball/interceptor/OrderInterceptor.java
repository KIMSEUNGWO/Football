package football.start.allOfFootball.interceptor;

import football.common.common.alert.AlertUtils;
import football.common.domain.Match;
import football.common.domain.Member;
import football.common.enums.matchenum.GenderEnum;
import football.common.enums.gradeEnums.MatchEnum;
import football.start.allOfFootball.service.domainService.MatchService;
import football.start.allOfFootball.service.domainService.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import static football.common.consts.SessionConst.LOGIN_MEMBER;

@RequiredArgsConstructor
@Slf4j
public class OrderInterceptor implements HandlerInterceptor {

    private final MemberService memberService;
    private final MatchService matchService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        System.out.println("method = " + method);
        log.info("Order 체크 인터셉터 실행 {}", requestURI);

        Long matchId = getMatchId(requestURI);
        Match match = matchService.findByMatch(matchId).get();

        Long memberId = (Long) request.getSession().getAttribute(LOGIN_MEMBER);
        Member member = memberService.findByMemberId(memberId).get();

        if (match.getMatchGender() != GenderEnum.전체 && match.getMatchGender() != member.getMemberGender()) {
            AlertUtils.alertAndBack(response, match.getMatchGender() + "만 참여할 수 있습니다.");
            return false;
        }
        if (match.getMatchGrade() != MatchEnum.전체 && !match.getMatchGrade().getGradeList().contains(member.getGrade())) {
            AlertUtils.alertAndBack(response, match.getMatchGrade().getMatchInfo() + " 만 참여할 수 있습니다.");
            return false;
        }
        boolean distinctMember = matchService.distinctCheck(match, memberId);
        if (distinctMember) {
            AlertUtils.alertAndMove(response, "이미 신청된 경기입니다.", "/");
            return false;
        }
        boolean maxCheck = matchService.maxCheck(match);
        if (maxCheck) {
            AlertUtils.alert(response, "경기가 마감되었습니다.");
            return false;
        }

        // 회원확인

//        Member member = findMember.get();
//        int cache = member.getMemberCash();
//
//        if (cache < 10000) {
//            log.info("잔액 부족");
//            response.sendRedirect("/cache/charge?url=" + request.getRequestURI());
//            return false;
//        }

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
