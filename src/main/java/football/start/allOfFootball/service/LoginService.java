package football.start.allOfFootball.service;

import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.enums.SocialEnum;

import java.util.Optional;

public interface LoginService {

    Optional<Member> login(String email, String password);
    Optional<Member> login(Member member, SocialEnum socialType, String Id);

    Optional<Member> findByEmail(String email);

    boolean findByPhone(String phone);
}
