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
public class Sms {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long smsId;
    private String phone;
    private String certificationNumber;
    private LocalDateTime expireDate;
}
