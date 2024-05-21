package football.login.repository;

import football.common.domain.Member;
import football.common.domain.Social;

public interface RegisterRepository {

    void save(Member member);
    void saveSocial(Social saveSocial);
}
