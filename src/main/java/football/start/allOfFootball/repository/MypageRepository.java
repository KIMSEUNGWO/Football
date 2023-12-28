package football.start.allOfFootball.repository;

import football.start.allOfFootball.domain.Member;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface MypageRepository {

    void validNowPw(HashMap<String, String> result, List<FieldError> nowPassword);

    void validChangePw(HashMap<String, String> result, List<FieldError> changePassword);

    void validCheckPw(HashMap<String, String> result, List<FieldError> checkPassword);
}
