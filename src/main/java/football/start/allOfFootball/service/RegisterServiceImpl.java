package football.start.allOfFootball.service;

import football.start.allOfFootball.common.BCrypt;
import football.start.allOfFootball.common.ResultMessage;
import football.start.allOfFootball.controller.login.EmailDto;
import football.start.allOfFootball.controller.login.RegisterDto;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService{

    private final RegisterRepository registerRepository;

    @Override
    public Map<String, String> validEmail(EmailDto emailDto, Map<String, String> map) {
        String email = emailDto.getEmail();
        Optional<Member> findMember = registerRepository.findByMemberEmail(email);
        if (findMember.isPresent()) {
            ResultMessage.errorMessage(map, "중복된 이메일입니다.");
            return map;
        }
        ResultMessage.okMessage(map, "사용가능한 이메일입니다.");
        return map;
    }

    @Override
    public void save(Member member) {
        member.setMemberSalt();

        String memberPassword = member.combineSalt(member.getMemberPassword());
        String encodePassword = BCrypt.encodeBCrypt(memberPassword);
        member.setMemberPassword(encodePassword);


        registerRepository.save(member);
    }
}
