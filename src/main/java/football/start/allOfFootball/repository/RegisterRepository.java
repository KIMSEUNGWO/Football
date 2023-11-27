package football.start.allOfFootball.repository;

import football.start.allOfFootball.domain.Member;

import java.util.Optional;

public interface RegisterRepository {
    Optional<Member> findByMemberEmail(String email);

    void save(Member member);
}
