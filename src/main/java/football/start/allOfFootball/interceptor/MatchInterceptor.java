package football.start.allOfFootball.interceptor;

import football.start.allOfFootball.common.alert.AlertUtils;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.service.domainService.MatchService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class MatchInterceptor implements HandlerInterceptor {

    private final MatchService matchService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("Match 인터셉터 실행 {}", requestURI);

        Long matchId = getMatchId(requestURI);
        if (matchId == null) {
            log.info("잘못된 경로");
            AlertUtils.alertAndMove(response, "잘못된 경로입니다.", "/");
            return false;
        }
        Optional<Match> byMatch = matchService.findByMatch(matchId);
        if (byMatch.isEmpty()) {
            log.info("존재하지 않는 매치");
            AlertUtils.alertAndMove(response, "존재하지 않는 경기입니다.", "/");
            return false;
        }

        log.info("정상적인 매치");
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
