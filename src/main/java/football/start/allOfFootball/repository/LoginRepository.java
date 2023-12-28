package football.start.allOfFootball.repository;

import football.start.allOfFootball.domain.Member;

import java.util.List;
import java.util.Optional;

public interface LoginRepository {

    Optional<Member> findByMemberId(Long memberId);

    Optional<Member> findByMember(String email);

    void renewLoginTime(Member member);

    Optional<Member> findByPhone(String phone);
}
