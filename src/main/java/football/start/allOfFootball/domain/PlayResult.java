package football.start.allOfFootball.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "PLAY_RESULT")
@SequenceGenerator(name = "SEQ_PLAY_RESULT", sequenceName = "SEQ_PLAY_RESULT_ID", allocationSize = 1)
public class PlayResult {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PLAY_RESULT")
    private Long playResultId;

    @OneToOne
    @JoinColumn(name = "playId")
    private Play play;

    @OneToOne
    @JoinColumn(name = "playCancelId")
    private PlayCancel playCancel;

    private Integer redScore;
    private Integer blueScore;
}
