package football.start.allOfFootball.service;

import football.start.allOfFootball.controller.login.EmailDto;
import football.start.allOfFootball.controller.login.RegisterDto;
import football.start.allOfFootball.domain.Member;

import java.util.Map;

public interface RegisterService {
    Map<String, String> validEmail(EmailDto emailDto, Map<String, String> map);

    void save(Member member);
}
