package football.start.allOfFootball.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCrypt {


    private final BCryptPasswordEncoder bc;
    @Autowired
    public BCrypt(BCryptPasswordEncoder bc) {
        this.bc = bc;
    }

    public String encodeBCrypt(String password) {
        return bc.encode(password);
    }

    public boolean matchBCrypt(String password, String encodedPassword) {
        return bc.matches(password, encodedPassword);
    }
}
