package football.admin.service;

import football.common.domain.*;
import football.common.dto.match.EditMatchRequest;
import football.common.dto.match.SaveMatchRequest;
import football.common.exception.match.NotExistsMatchException;
import football.common.jpaRepository.JpaMatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminMatchService {

    private final JpaMatchRepository jpaMatchRepository;

    public Match findByMatch(Long matchId, String redirectURI) throws NotExistsMatchException {
        Optional<Match> findMatch = jpaMatchRepository.findById(matchId);
        return findMatch.orElseThrow(() -> new NotExistsMatchException(redirectURI));
    }

    public void saveMatch(Field field, SaveMatchRequest saveMatchForm) {
        Match saveMatch = Match.build(field, saveMatchForm);
        jpaMatchRepository.save(saveMatch);
    }

    public void editMatch(Match match, EditMatchRequest editMatchForm) {
        match.setEditMatch(editMatchForm);
    }


}
