package football.start.allOfFootball.service.domainService;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import football.start.allOfFootball.controller.admin.EditMatchForm;
import football.start.allOfFootball.controller.admin.SaveMatchForm;
import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.domain.Orders;
import football.start.allOfFootball.enums.matchEnums.MatchStatus;
import football.start.allOfFootball.repository.domainRepository.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MatchService {

    private final MatchRepository matchRepository;

    public Optional<Match> findByMatch(Long matchId) {
        return matchRepository.findByMatch(matchId);
    }

    public void saveMatch(Field field, SaveMatchForm saveMatchForm) {
        Match saveMatch = Match.build(field, saveMatchForm);
        matchRepository.saveMatch(saveMatch);
    }

    public void editMatch(Match match, EditMatchForm editMatchForm) {
        match.setEditMatch(editMatchForm);
    }

    public List<Orders> getOrders(Match match) {
        return match.getOrdersList();
    }

    public boolean distinctCheck(Match match, Long memberId) {
        List<Orders> ordersList = match.getOrdersList();
        return ordersList.stream().filter(x -> x.getMember().getMemberId() == memberId).findFirst().isPresent();
    }

    public void refreshMatchStatus(Match match) {
        int max = match.getMaxPerson() * match.getMatchCount(); // 참여할 수 있는 최대 인원 수

        int nowPerson = match.getOrdersList().size() + 1; // + 1 인거 주의해서 사용할 것

        int line = (int) (max * 0.8);
        if (max == nowPerson) {
            match.setMatchStatus(MatchStatus.마감);
            return;
        }
        if (nowPerson >= line) {
            match.setMatchStatus(MatchStatus.임박);
        }

    }

    public boolean maxCheck(Match match) {
        int max = match.getMaxPerson() * match.getMatchCount(); // 참여할 수 있는 최대 인원 수

        int nowPerson = match.getOrdersList().size();

        return max <= nowPerson;
    }
}
