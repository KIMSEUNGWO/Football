package football.common.common;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BCrypt {

    private final BCryptPasswordEncoder bc;

    public String encodeBCrypt(String password) {
        return bc.encode(password);
    }

    public boolean matchBCrypt(String password, String encodedPassword) {
        return bc.matches(password, encodedPassword);
    }

}
