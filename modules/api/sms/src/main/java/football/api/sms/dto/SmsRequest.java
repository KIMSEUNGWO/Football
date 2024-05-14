package football.api.sms.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SmsRequest {

    private String email;

    private String phone;

    private String certificationNumber;
}
