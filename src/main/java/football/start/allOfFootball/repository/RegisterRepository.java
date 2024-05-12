package football.start.allOfFootball.repository;

import football.internal.database.domain.Member;
import football.internal.database.domain.Social;

import java.util.Optional;

public interface RegisterRepository {
    Optional<Member> findByMemberEmail(String email);

    void save(Member member);

    void saveSocial(Social saveSocial);
}
