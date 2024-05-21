package football.login.repository;

import football.common.domain.Member;
import football.common.enums.SocialEnum;

import java.util.Optional;

public interface LoginRepository {

    Optional<Member> findByEmail(String email);
    Member socialLogin(String email, SocialEnum socialEnum, int loginUser_id);

    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);

}
