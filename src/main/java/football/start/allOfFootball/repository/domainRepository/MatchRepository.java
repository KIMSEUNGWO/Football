package football.start.allOfFootball.repository.domainRepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.domain.Orders;
import football.start.allOfFootball.jpaRepository.JpaMatchRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class MatchRepository {

    private final JpaMatchRepository jpaMatchRepository;
    private final JPAQueryFactory query;

    public MatchRepository(JpaMatchRepository jpaMatchRepository, EntityManager em) {
        this.jpaMatchRepository = jpaMatchRepository;
        this.query = new JPAQueryFactory(em);
    }

    public Optional<Match> findByMatch(Long matchId) {
        return jpaMatchRepository.findById(matchId);
    }

    public void saveMatch(Match saveMatch) {
        jpaMatchRepository.save(saveMatch);
    }

    public boolean isContainsMember(List<Orders> ordersList, Long memberId) {
        for (Orders orders : ordersList) {
            Long memberId1 = orders.getMember().getMemberId();
            if (memberId1 == memberId) {
                return true;
            }
        }
        return false;
    }
}
