package football.start.allOfFootball.domain;

import football.start.allOfFootball.enums.GenderEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "PLAY")
@SequenceGenerator(name = "SEQ_PLAY", sequenceName = "SEQ_PLAY_ID", allocationSize = 1)
public class Play {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PLAY")
    private Long playId;

    @ManyToOne
    @JoinColumn(name = "fieldID")
    private Field field;

    private LocalDateTime playDate;
    private int playTime;

    @Enumerated(EnumType.STRING)
    private GenderEnum playGender;
    private int playCount;
    private char playEndStatus;
}
