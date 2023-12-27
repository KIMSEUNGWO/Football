package football.start.allOfFootball.repository;

import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Social;

import java.util.Optional;

public interface RegisterRepository {
    Optional<Member> findByMemberEmail(String email);

    void save(Member member);

    void saveSocial(Social saveSocial);
}
