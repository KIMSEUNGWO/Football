package football.start.allOfFootball.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BCrypt {


    private static final BCryptPasswordEncoder bc = new BCryptPasswordEncoder(10);

    public static String encodeBCrypt(String password) {
        return bc.encode(password);
    }

    public static boolean matchBCrypt(String password, String encodedPassword) {
        return bc.matches(password, encodedPassword);
    }

}
