package football.start.allOfFootball.service;

import football.common.domain.Member;
import football.common.enums.SocialEnum;

import java.util.Optional;

public interface LoginService {

    Optional<Member> login(String email, String password);

    Member socialLogin(String email, SocialEnum socialEnum, int loginUser_id);

    boolean existsByPhone(String phone);

    void renewLoginTime(Member loginMember);
}
