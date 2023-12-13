package football.start.allOfFootball.common;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
