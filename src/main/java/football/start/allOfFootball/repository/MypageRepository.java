package football.start.allOfFootball.repository;

import football.start.allOfFootball.domain.Member;

import java.util.Optional;

public interface MypageRepository {
    Optional<Member> findById(Long memberId);
}
