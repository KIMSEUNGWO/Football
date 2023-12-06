package football.start.allOfFootball.service.domainService;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import football.start.allOfFootball.controller.admin.EditMatchForm;
import football.start.allOfFootball.controller.admin.SaveMatchForm;
import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.repository.domainRepository.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
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
}
