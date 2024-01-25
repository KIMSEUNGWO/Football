package football.start.allOfFootball.controller.api.smsAPI;

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
