package football.start.allOfFootball.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
@Getter
@Setter
@Table(name = "PROFILE")
@SequenceGenerator(name = "SEQ_PROFILE", sequenceName = "SEQ_PROFILE_ID", allocationSize = 1)
public class Profile {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROFILE")
    private Long profileId;

    private String profileName;
    private String profileStoreName;
}
