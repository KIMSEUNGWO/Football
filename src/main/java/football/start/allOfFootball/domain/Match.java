package football.start.allOfFootball.domain;

import football.start.allOfFootball.enums.GenderEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "MATCH")
@SequenceGenerator(name = "SEQ_MATCH", sequenceName = "SEQ_MATCH_ID", allocationSize = 1)
public class Match {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MATCH")
    private Long matchId;

    @ManyToOne
    @JoinColumn(name = "fieldID")
    private Field field;

    private LocalDate matchDate;
    private Integer matchTime;

    @Enumerated(EnumType.STRING)
    private GenderEnum matchGender;

    private Integer maxPerson; // 6 vs 6 일때 maxPerson = 6

    private Character matchEndStatus;
}
