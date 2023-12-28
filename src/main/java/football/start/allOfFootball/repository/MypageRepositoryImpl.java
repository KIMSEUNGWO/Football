package football.start.allOfFootball.repository;

import football.start.allOfFootball.common.MessageConvert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MypageRepositoryImpl implements MypageRepository{

    private final MessageConvert messageConvert;

    @Override
    public void validNowPw(HashMap<String, String> result, List<FieldError> nowPassword) {
        if (nowPassword.isEmpty()) {
            result.put("nowPwError", "");
            return;
        }
        for (FieldError fieldError : nowPassword) {
            String nowPwMessage = messageConvert.getErrorMessage(fieldError);
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
            String changePwMessage = messageConvert.getErrorMessage(fieldError);
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
            String checkPwMessage = messageConvert.getErrorMessage(fieldError);
            result.put("checkPwError", checkPwMessage);
            return;
        }
    }
}
