package football.start.allOfFootball.repository;

import football.start.allOfFootball.common.MessageConvert;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.enums.ErrorLevel;
import football.start.allOfFootball.jpaRepository.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MypageRepositoryImpl implements MypageRepository{

    private final JpaMemberRepository jpaMemberRepository;
    private final MessageConvert messageConvert;

    @Override
    public Optional<Member> findById(Long memberId) {
        return jpaMemberRepository.findById(memberId);
    }

    @Override
    public void validNowPw(HashMap<String, String> result, List<FieldError> nowPassword) {
        if (nowPassword.isEmpty()) {
            result.put("nowPwError", "");
            return;
        }
        for (FieldError fieldError : nowPassword) {
            String nowPwMessage = messageConvert.getErrorMessage(fieldError, ErrorLevel.LEVEL_1);
            result.put("nowPwError", nowPwMessage);
            return;
        }
    }

    @Override
    public void validChangePw(HashMap<String, String> result, List<FieldError> changePassword) {
        if (changePassword.isEmpty()) {
            result.put("changePwError", "");
            return;
        }
        for (FieldError fieldError : changePassword) {
            String changePwMessage = messageConvert.getErrorMessage(fieldError, ErrorLevel.LEVEL_1);
            result.put("changePwError", changePwMessage);
            return;
        }
    }

    @Override
    public void validCheckPw(HashMap<String, String> result, List<FieldError> checkPassword) {
        if (checkPassword.isEmpty()) {
            result.put("checkPwError", "");
            return;
        }
        for (FieldError fieldError : checkPassword) {
            String checkPwMessage = messageConvert.getErrorMessage(fieldError, ErrorLevel.LEVEL_1);
            result.put("checkPwError", checkPwMessage);
            return;
        }
    }
}
