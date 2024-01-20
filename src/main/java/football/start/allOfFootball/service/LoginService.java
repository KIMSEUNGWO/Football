package football.start.allOfFootball.service;

import football.start.allOfFootball.domain.Member;

import java.util.Optional;

public interface LoginService {

    Optional<Member> login(String email, String password);

    Optional<Member> findByEmail(String email);

    boolean findByPhone(String phone);

    void renewLoginTime(Member loginMember);
}
