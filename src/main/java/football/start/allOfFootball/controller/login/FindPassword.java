package football.start.allOfFootball.controller.login;

import football.api.sms.dto.SmsRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FindPassword extends SmsRequest {

    private String password;
    private String passwordCheck;


    public String getPassword() {
        return password;
    }
    public String getPasswordCheck() {
        return passwordCheck;
    }

    public FindPassword(String email, String phone, String certificationNumber, String password, String passwordCheck) {
        super(email, phone, certificationNumber);
        this.password = password;
        this.passwordCheck = passwordCheck;
    }

}
