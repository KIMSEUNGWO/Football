package football.start.allOfFootball.repository;

import football.common.domain.Member;

import java.util.Optional;

public interface LoginRepository {

    Optional<Member> findByMemberEmail(String email);

    void renewLoginTime(Member member);

    Optional<Member> findByPhone(String phone);
}
