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
        String nowPwMessage = messageConvert.getErrorMessage(nowPassword.get(0));
        result.put("nowPwError", nowPwMessage);

    }

    @Override
    public void validChangePw(HashMap<String, String> result, List<FieldError> changePassword) {
        if (changePassword.isEmpty()) {
            result.put("changePwError", "");
            return;
        }
        String changePwMessage = messageConvert.getErrorMessage(changePassword.get(0));
        result.put("changePwError", changePwMessage);
    }

    @Override
    public void validCheckPw(HashMap<String, String> result, List<FieldError> checkPassword) {
        if (checkPassword.isEmpty()) {
            result.put("checkPwError", "");
            return;
        }
        String checkPwMessage = messageConvert.getErrorMessage(checkPassword.get(0));
        result.put("checkPwError", checkPwMessage);
    }
}
