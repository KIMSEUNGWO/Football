package football.start.allOfFootball.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "PLAY")
@SequenceGenerator(name = "SEQ_PLAY", sequenceName = "SEQ_PLAY_ID")
public class Play {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PLAY")
    private Long playId;
    private Long fieldId;

    private LocalDateTime playDate;
    private int playTime;
    private String playGender;
    private int playCount;
    private char playEndStatus;
}
