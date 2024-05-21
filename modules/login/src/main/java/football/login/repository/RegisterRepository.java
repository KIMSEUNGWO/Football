package football.login.repository;

import football.common.domain.Member;
import football.common.domain.Social;

import java.util.Optional;

public interface RegisterRepository {
    Optional<Member> findByMemberEmail(String email);

    void save(Member member);

    void saveSocial(Social saveSocial);
}
