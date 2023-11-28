package football.start.allOfFootball.domain;

import football.start.allOfFootball.common.file.FileUploadDto;
import football.start.allOfFootball.dto.File;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "PROFILE")
@SequenceGenerator(name = "SEQ_PROFILE", sequenceName = "SEQ_PROFILE_ID", allocationSize = 1)
public class Profile extends File {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROFILE")
    private Long profileId;

    @OneToOne
    @JoinColumn(name = "memberId")
    private Member member;

    private String profileName;
    private String profileStoreName;

}
