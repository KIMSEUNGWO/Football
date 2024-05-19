package football.api.sms.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "SMS")
@SequenceGenerator(name = "SEQ_SMS", sequenceName = "SEQ_SMS_ID", allocationSize = 1)
public class Sms {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SMS")
    private Long smsId;
    private String phone;
    private String certificationNumber;
    private LocalDateTime expireDate;
}
